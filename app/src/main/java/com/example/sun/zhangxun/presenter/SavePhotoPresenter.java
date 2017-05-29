package com.example.sun.zhangxun.presenter;

import android.os.Environment;

import com.example.sun.zhangxun.app.BasePresenter;
import com.example.sun.zhangxun.http.HttpManager;
import com.example.sun.zhangxun.presenter.Constracts.ISavePhotoConstract;
import com.umeng.socialize.sina.helper.MD5;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sun on 2017/5/27.
 *
 */

public class SavePhotoPresenter extends BasePresenter implements ISavePhotoConstract.ISavePhotoPresenter {

    private ISavePhotoConstract.ISavaPhotoView mView;

    public SavePhotoPresenter(ISavePhotoConstract.ISavaPhotoView view) {
        this.mView = view;
    }

    @Override
    public void save(final String url) {


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> e) throws Exception {
                OkHttpClient client = HttpManager.getOkHttpClient();
                Request request = new Request
                        .Builder()
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                        || !Environment.isExternalStorageRemovable()) {
                    String fileName = MD5.hexdigest(url) + ".jpg";
                    File dir = new File(Environment.getExternalStorageDirectory(),"zhangxun");
                    if (!dir.exists()){
                        dir.mkdirs();
                    }
                    File file = new File(dir, fileName);
                    InputStream inputStream ;
                    FileOutputStream fos ;
//                    String path = Environment.getExternalStorageDirectory() + File.separator + "zhangxun";   ///storage/sdcard
//                    File dir = new File(CustomApplication.getContext().getFilesDir(),"zhangxun");
//                    File file = new File(dir,fileName);
                    try {
                        inputStream = response.body().byteStream();
                        fos = new FileOutputStream(file);
                        byte[] buffer = new byte[2048];
                        int len;
                        while ((len = (inputStream.read(buffer))) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        fos.flush();
                        inputStream.close();
                        fos.close();
                        e.onNext(dir.getName());
                    } catch (Exception exception) {
                        e.onError(new Throwable("图片保存失败"));
                    }
                } else {
                    e.onError(new Throwable("sd-card不可用"));
                }


            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String s) {
                        mView.showSucceed(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
