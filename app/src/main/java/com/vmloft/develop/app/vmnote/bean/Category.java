package com.vmloft.develop.app.vmnote.bean;

/**
 * Created by lzan13 on 2018/4/25.
 */
public class Category {
    private String id;
    private String authorId;
    private String title;
    private String createAt;
    private String updateAt;
    private boolean isCreate = false;
    private boolean sync = true;

    public Category() {}

    public Category(String id, String authorId, String title, String createAt, String updateAt,
            boolean isCreate, boolean sync) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.isCreate = isCreate;
        this.sync = sync;
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

    public boolean getIsCreate() {
        return this.isCreate;
    }

    public void setIsCreate(boolean isCreate) {
        this.isCreate = isCreate;
    }

    public boolean getSync() {
        return this.sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

}
