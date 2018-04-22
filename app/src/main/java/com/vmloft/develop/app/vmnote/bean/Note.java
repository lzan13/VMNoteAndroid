package com.vmloft.develop.app.vmnote.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lzan13 on 2018/4/22.
 * 笔记信息实体类
 */
@Entity()
public class Note {

    @SerializedName("_id") @Id private String id;
    private String authorId;
    private String content;
    private String tags;
    private boolean pinup;
    private boolean blog;
    private boolean deleted;
    @SerializedName("create_at") private long createAt;
    @SerializedName("update_at") private long updateAt;

    public Note() {}

    @Generated(hash = 436777964)
    public Note(String id, String authorId, String content, String tags, boolean pinup,
            boolean blog, boolean deleted, long createAt, long updateAt) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.tags = tags;
        this.pinup = pinup;
        this.blog = blog;
        this.deleted = deleted;
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public long getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdateAt() {
        return this.updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n\tid:" + id);
        buffer.append("\n\tauthorId:" + authorId);
        buffer.append("\n\tcontent:" + content);
        buffer.append("\n\ttags:" + tags);
        buffer.append("\n\tpinup:" + pinup);
        buffer.append("\n\tblog:" + blog);
        buffer.append("\n\tdeleted:" + deleted);
        buffer.append("\n\tcreateAt:" + createAt);
        buffer.append("\n\tupdateAt:" + updateAt);
        return buffer.toString();
    }

}
