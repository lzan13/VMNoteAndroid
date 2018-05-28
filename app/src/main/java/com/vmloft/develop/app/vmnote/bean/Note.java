package com.vmloft.develop.app.vmnote.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by lzan13 on 2018/4/22.
 * 笔记信息实体类
 */
@Entity() public class Note {
    @SerializedName("_id") @Id private String id;
    @SerializedName("author_id") private String authorId;
    @SerializedName("category_id") private String categoryId;
    private String content;
    private boolean pinup;
    private boolean blog;
    private boolean deleted;
    private boolean isSync = true;
    private boolean isCreate = false;
    @Transient private boolean isSelected = false;
    @SerializedName("create_at") private String createAt;
    @SerializedName("update_at") private String updateAt;

    public Note() {
    }

    public Note(String id) {
        this.id = id;
    }

    @Generated(hash = 1961848947)
    public Note(String id, String authorId, String categoryId, String content, boolean pinup,
        boolean blog, boolean deleted, boolean isSync, boolean isCreate, String createAt,
        String updateAt) {
        this.id = id;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.content = content;
        this.pinup = pinup;
        this.blog = blog;
        this.deleted = deleted;
        this.isSync = isSync;
        this.isCreate = isCreate;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getPinup() {
        return this.pinup;
    }

    public void setPinup(boolean pinup) {
        this.pinup = pinup;
    }

    public boolean getBlog() {
        return this.blog;
    }

    public void setBlog(boolean blog) {
        this.blog = blog;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getIsSync() {
        return this.isSync;
    }

    public void setIsSync(boolean isSync) {
        this.isSync = isSync;
    }

    public boolean getIsCreate() {
        return this.isCreate;
    }

    public void setIsCreate(boolean isCreate) {
        this.isCreate = isCreate;
    }

    public String getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getUpdateAt() {
        return this.updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n\tid:" + id);
        buffer.append("\n\tauthorId:" + authorId);
        buffer.append("\n\tcategoryId:" + categoryId);
        buffer.append("\n\tcontent:" + content);
        buffer.append("\n\tpinup:" + pinup);
        buffer.append("\n\tblog:" + blog);
        buffer.append("\n\tdeleted:" + deleted);
        buffer.append("\n\tcreateAt:" + createAt);
        buffer.append("\n\tupdateAt:" + updateAt);
        return buffer.toString();
    }
}
