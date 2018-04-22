package com.vmloft.develop.app.vmnote.db.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.vmloft.develop.app.vmnote.bean.Account;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ACCOUNT".
*/
public class AccountDao extends AbstractDao<Account, String> {

    public static final String TABLENAME = "ACCOUNT";

    /**
     * Properties of entity Account.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Email = new Property(2, String.class, "email", false, "EMAIL");
        public final static Property Phone = new Property(3, String.class, "phone", false, "PHONE");
        public final static Property Password = new Property(4, String.class, "password", false, "PASSWORD");
        public final static Property Avatar = new Property(5, String.class, "avatar", false, "AVATAR");
        public final static Property Cover = new Property(6, String.class, "cover", false, "COVER");
        public final static Property Gender = new Property(7, String.class, "gender", false, "GENDER");
        public final static Property Address = new Property(8, String.class, "address", false, "ADDRESS");
        public final static Property Nickname = new Property(9, String.class, "nickname", false, "NICKNAME");
        public final static Property Description = new Property(10, String.class, "description", false, "DESCRIPTION");
        public final static Property CreateAt = new Property(11, String.class, "createAt", false, "CREATE_AT");
        public final static Property UpdateAt = new Property(12, String.class, "updateAt", false, "UPDATE_AT");
        public final static Property Token = new Property(13, String.class, "token", false, "TOKEN");
        public final static Property Code = new Property(14, int.class, "code", false, "CODE");
        public final static Property Activated = new Property(15, boolean.class, "activated", false, "ACTIVATED");
        public final static Property Deleted = new Property(16, boolean.class, "deleted", false, "DELETED");
        public final static Property Admin = new Property(17, boolean.class, "admin", false, "ADMIN");
    }


    public AccountDao(DaoConfig config) {
        super(config);
    }
    
    public AccountDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ACCOUNT\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"EMAIL\" TEXT," + // 2: email
                "\"PHONE\" TEXT," + // 3: phone
                "\"PASSWORD\" TEXT," + // 4: password
                "\"AVATAR\" TEXT," + // 5: avatar
                "\"COVER\" TEXT," + // 6: cover
                "\"GENDER\" TEXT," + // 7: gender
                "\"ADDRESS\" TEXT," + // 8: address
                "\"NICKNAME\" TEXT," + // 9: nickname
                "\"DESCRIPTION\" TEXT," + // 10: description
                "\"CREATE_AT\" TEXT," + // 11: createAt
                "\"UPDATE_AT\" TEXT," + // 12: updateAt
                "\"TOKEN\" TEXT," + // 13: token
                "\"CODE\" INTEGER NOT NULL ," + // 14: code
                "\"ACTIVATED\" INTEGER NOT NULL ," + // 15: activated
                "\"DELETED\" INTEGER NOT NULL ," + // 16: deleted
                "\"ADMIN\" INTEGER NOT NULL );"); // 17: admin
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ACCOUNT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Account entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(3, email);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(4, phone);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(5, password);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(6, avatar);
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(7, cover);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(8, gender);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(9, address);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(10, nickname);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(11, description);
        }
 
        String createAt = entity.getCreateAt();
        if (createAt != null) {
            stmt.bindString(12, createAt);
        }
 
        String updateAt = entity.getUpdateAt();
        if (updateAt != null) {
            stmt.bindString(13, updateAt);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(14, token);
        }
        stmt.bindLong(15, entity.getCode());
        stmt.bindLong(16, entity.getActivated() ? 1L: 0L);
        stmt.bindLong(17, entity.getDeleted() ? 1L: 0L);
        stmt.bindLong(18, entity.getAdmin() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Account entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(3, email);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(4, phone);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(5, password);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(6, avatar);
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(7, cover);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(8, gender);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(9, address);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(10, nickname);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(11, description);
        }
 
        String createAt = entity.getCreateAt();
        if (createAt != null) {
            stmt.bindString(12, createAt);
        }
 
        String updateAt = entity.getUpdateAt();
        if (updateAt != null) {
            stmt.bindString(13, updateAt);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(14, token);
        }
        stmt.bindLong(15, entity.getCode());
        stmt.bindLong(16, entity.getActivated() ? 1L: 0L);
        stmt.bindLong(17, entity.getDeleted() ? 1L: 0L);
        stmt.bindLong(18, entity.getAdmin() ? 1L: 0L);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public Account readEntity(Cursor cursor, int offset) {
        Account entity = new Account( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // email
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // phone
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // password
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // avatar
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // cover
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // gender
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // address
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // nickname
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // description
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // createAt
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // updateAt
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // token
            cursor.getInt(offset + 14), // code
            cursor.getShort(offset + 15) != 0, // activated
            cursor.getShort(offset + 16) != 0, // deleted
            cursor.getShort(offset + 17) != 0 // admin
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Account entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEmail(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPhone(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPassword(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAvatar(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCover(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setGender(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAddress(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setNickname(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDescription(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCreateAt(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUpdateAt(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setToken(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setCode(cursor.getInt(offset + 14));
        entity.setActivated(cursor.getShort(offset + 15) != 0);
        entity.setDeleted(cursor.getShort(offset + 16) != 0);
        entity.setAdmin(cursor.getShort(offset + 17) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(Account entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(Account entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Account entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
