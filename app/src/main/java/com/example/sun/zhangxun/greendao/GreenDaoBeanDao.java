package com.example.sun.zhangxun.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.sun.zhangxun.bean.News.NewInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GREEN_DAO_BEAN".
*/
public class GreenDaoBeanDao extends AbstractDao<GreenDaoBean, Long> {

    public static final String TABLENAME = "GREEN_DAO_BEAN";

    /**
     * Properties of entity GreenDaoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Info = new Property(1, String.class, "info", false, "INFO");
    }

    private final NewInfoTypeConverter infoConverter = new NewInfoTypeConverter();

    public GreenDaoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public GreenDaoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GREEN_DAO_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"INFO\" TEXT);"); // 1: info
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GREEN_DAO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GreenDaoBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        NewInfo info = entity.getInfo();
        if (info != null) {
            stmt.bindString(2, infoConverter.convertToDatabaseValue(info));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GreenDaoBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        NewInfo info = entity.getInfo();
        if (info != null) {
            stmt.bindString(2, infoConverter.convertToDatabaseValue(info));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public GreenDaoBean readEntity(Cursor cursor, int offset) {
        GreenDaoBean entity = new GreenDaoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : infoConverter.convertToEntityProperty(cursor.getString(offset + 1)) // info
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GreenDaoBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setInfo(cursor.isNull(offset + 1) ? null : infoConverter.convertToEntityProperty(cursor.getString(offset + 1)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GreenDaoBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GreenDaoBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GreenDaoBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
