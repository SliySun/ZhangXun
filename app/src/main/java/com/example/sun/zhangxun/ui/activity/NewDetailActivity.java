package com.example.sun.zhangxun.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.app.BaseMvpActivity;
import com.example.sun.zhangxun.app.BasePresenter;
import com.example.sun.zhangxun.app.CustomApplication;
import com.example.sun.zhangxun.app.SwipeBackMvpActivity;
import com.example.sun.zhangxun.bean.News;
import com.example.sun.zhangxun.greendao.DaoSession;
import com.example.sun.zhangxun.greendao.GreenDaoBean;
import com.example.sun.zhangxun.greendao.GreenDaoBeanDao;
import com.example.sun.zhangxun.http.MJavascriptInterface;
import com.example.sun.zhangxun.presenter.Constracts.INewDetailConstract;
import com.example.sun.zhangxun.presenter.NewDetailPresenter;
import com.example.sun.zhangxun.utils.permission.PermissionCall;
import com.example.sun.zhangxun.utils.permission.Permissions;
import com.example.sun.zhangxun.widget.NewMorePopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.SocializeUtils;


public class NewDetailActivity extends SwipeBackMvpActivity implements INewDetailConstract.INewDetailView,View.OnClickListener,PopupWindow.OnDismissListener {

    private ImageView iv_left_back;
    private TextView tv_title;
    private ImageView iv_more;
    private ProgressBar pb_load;
    private WebView wv_content;
    private Toolbar mToolbar;

    private NewMorePopupWindow mNewpopWin;
    private ProgressDialog dialog;

    private String mUrl;
    private News.NewInfo mInfo;

    private NewDetailPresenter presenter;

    private WebSettings settings;

    private GreenDaoBeanDao dao;
    private boolean isFavor = false;
    private GreenDaoBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    protected void handleIntent(Intent intent) {
        mUrl = intent.getStringExtra("url");
        mInfo = (News.NewInfo) intent.getSerializableExtra("newInfo");

    }

    @Override
    protected void onInitPresenters() {
        presenter = new NewDetailPresenter(this);
    }

    @Override
    protected BasePresenter[] getPresenters() {
        return new BasePresenter[]{presenter};
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wv_content!=null &&wv_content.canGoBack()){
            wv_content.goBack();
        }
    }


    protected void initData() {

        initDb();
        webViewShowContent();

        initPopWinData();
    }

    private void initDb() {
        DaoSession daoSession = CustomApplication.getDaoInstant();
        dao = daoSession.getGreenDaoBeanDao();
    }

    private void initPopWinData() {
        // TODO: 2017/5/13 从sharepreference中获取字体大小
        WebSettings.TextSize size=settings.getTextSize();
        updateFromDb();
    }

    private void updateFromDb() {
        presenter.query(dao,mInfo);
    }

    private void webViewShowContent() {
        wv_content.loadUrl(mUrl);

        //不让从当前网页跳转到系统浏览器
        wv_content.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.getSettings().setJavaScriptEnabled(false);
                super.onPageStarted(view, url, favicon);
                presenter.parseHtml(mUrl);
                pb_load.setVisibility(View.VISIBLE);
                wv_content.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设置支持js
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageFinished(view, url);
                wv_content.setVisibility(View.VISIBLE);
                pb_load.setVisibility(View.INVISIBLE);
//                addImageClickListener(view);
            }

        });

        wv_content.addJavascriptInterface(new MJavascriptInterface(this),"imagelistener");
    }

    private void addImageClickListener(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++) " +
                "{"
                + " objs[i].onclick=function() " +
                " { "
                + "  window.imagelistener.openImage(this.src); " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
//                "  return true;"+
                " } " +
                "}" +
                "})()");
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_activity_new_detail);
        iv_left_back= (ImageView) findViewById(R.id.imageView_new_detail_left_back);
        tv_title = (TextView) findViewById(R.id.textView_new_detail_title);
        iv_more = (ImageView) findViewById(R.id.imageView_new_detail_more);
        pb_load = (ProgressBar) findViewById(R.id.progressBar_new_detail_load);
        wv_content = (WebView) findViewById(R.id.webView_new_detail_content);

        wv_content.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings = wv_content.getSettings();

        dialog = new ProgressDialog(this);

        mNewpopWin = new NewMorePopupWindow(this);

    }

    @Override
    protected void setListener() {
        iv_left_back.setOnClickListener(this);
        iv_more.setOnClickListener(this);

        mNewpopWin.setOnItemClickLstener(this);
        mNewpopWin.setOnDismissListener(this);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_new_detail;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_new_detail_left_back:
                finish();
                break;
            case R.id.imageView_new_detail_more:
                showMenu2();
                break;
            case R.id.item1_popup_win_sina:
                Toast.makeText(this,"新浪分享",Toast.LENGTH_SHORT).show();
                share(SHARE_MEDIA.SINA);
                break;
            case R.id.item1_popup_win_wxfriends:
                Toast.makeText(this,"朋友圈",Toast.LENGTH_SHORT).show();
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.item1_popup_win_wxfriend:
                Toast.makeText(this,"微信",Toast.LENGTH_SHORT).show();
                share(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.item1_popup_win_qq:
                Toast.makeText(this,"qq",Toast.LENGTH_SHORT).show();
                share(SHARE_MEDIA.QQ);
                break;
            case R.id.item1_popup_win_qzone:
                Toast.makeText(this,"qzone",Toast.LENGTH_SHORT).show();
                share(SHARE_MEDIA.QZONE);
                break;
            case R.id.item1_popup_win_gmail:
                Toast.makeText(this,"gmail",Toast.LENGTH_SHORT).show();
                share(SHARE_MEDIA.EMAIL);
                break;
            case R.id.item1_popup_win_sms:
                Toast.makeText(this,"sms",Toast.LENGTH_SHORT).show();
                share(SHARE_MEDIA.SMS);
                break;
            case R.id.item1_popup_win_favor:
                setFavorOrNot(isFavor);
                break;
            case R.id.button_popWin_back:
                mNewpopWin.dismiss();
                break;
        }
    }

    private void share(final SHARE_MEDIA platform) {

        requestRunTimePermissions( Permissions.Share,
                new PermissionCall() {
                    @Override
                    public void requestSuccess() {
                        Toast.makeText(NewDetailActivity.this,"yes",Toast.LENGTH_SHORT).show();
                        callShare(platform);
                    }

                    @Override
                    public void refused() {
                        Toast.makeText(NewDetailActivity.this,"无法分享",Toast.LENGTH_SHORT).show();
                    }
                }, Permissions.SHARE_REQUEST);


    }

    private void callShare(final SHARE_MEDIA platform) {
        UMWeb web = new UMWeb(mInfo.url);
        web.setTitle("This is web title");
        web.setThumb(new UMImage(this, mInfo.thumbnail_pic_s));
        web.setDescription("my description");

        ShareAction action=new ShareAction(NewDetailActivity.this);
        if (platform == SHARE_MEDIA.SMS || platform == SHARE_MEDIA.EMAIL){
            action.withText("分享自掌讯app：[" +mInfo.title + "];链接："+mInfo.url);
        }else {
            action
                    .withText("分享自掌讯app：[" +mInfo.title + "];链接："+mInfo.url)
                    .withMedia(web);
        }

        action.setPlatform(platform)
        .setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                SocializeUtils.safeShowDialog(dialog);
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(NewDetailActivity.this,platform.name()+"分享成功",Toast.LENGTH_SHORT).show();
                SocializeUtils.safeCloseDialog(dialog);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                SocializeUtils.safeCloseDialog(dialog);
                Toast.makeText(NewDetailActivity.this,platform.name()+"分享失败"+throwable.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                SocializeUtils.safeCloseDialog(dialog);
                Toast.makeText(NewDetailActivity.this,"分享取消",Toast.LENGTH_SHORT).show();
            }
        }).share();
    }

    private void setFavorOrNot(boolean i) {
        if (i){
            dao.delete(bean);
            Toast.makeText(this,"取消收藏",Toast.LENGTH_SHORT).show();
        }else {
            GreenDaoBean bean = new GreenDaoBean(null,mInfo);
            dao.insert(bean);
            Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
        }
        updateFromDb();
    }



    private void showMenu2() {
        mNewpopWin.showAtLocation(getLayoutInflater().inflate(R.layout.activity_new_detail,null), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        //设置背景半透明
        backgroundAlpha(0.9f);
    }


    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv_content.canGoBack()){
            wv_content.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }



    @Override
    public void isFavor(GreenDaoBean b) {
        bean=b;
        if (b!=null){
            mNewpopWin.setFavorButtonState(true);
            isFavor = true;
        }else {
            mNewpopWin.setFavorButtonState(false);
            isFavor=false;
        }

    }

    @Override
    public void addClick() {
        addImageClickListener(wv_content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.pull_left_out);
    }
}
