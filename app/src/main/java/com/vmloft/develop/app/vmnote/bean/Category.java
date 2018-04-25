package com.vmloft.develop.app.vmnote.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lzan13 on 2018/4/25.
 */
@Entity
public class Category {
    @SerializedName("_id") @Id private String id;
    @SerializedName("author_id") private String authorId;
    private String title;
    @SerializedName("create_at") private String createAt;
    @SerializedName("update_at") private String updateAt;

    public Category() {}

    @Generated(hash = 140334443)
    public Category(String id, String authorId, String title, String createAt,
            String updateAt) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return this.updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\tid:" + id);
        builder.append("\n\tauthorId:" + authorId);
        builder.append("\n\ttitle:" + title);
        builder.append("\n\tcreateAt:" + createAt);
        builder.append("\n\tupdateAt:" + updateAt);
        return builder.toString();
    }

}
