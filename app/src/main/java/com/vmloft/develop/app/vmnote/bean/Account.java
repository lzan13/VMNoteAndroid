package com.vmloft.develop.app.vmnote.bean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lzan13 on 2017/11/24.
 * 账户实体类
 */
@Entity
public class Account {
    @SerializedName("_id") @Id private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String avatar;
    private String cover;
    private String gender;
    private String address;
    private String nickname;
    private String description;
    @SerializedName("create_at") private String createAt;
    @SerializedName("update_at") private String updateAt;
    private String token;
    private int code;
    private boolean activated;
    private boolean deleted;
    private boolean admin;

    public Account() {}

    public Account(String account, String password) {
        this.email = account;
        this.password = password;
    }

    @Generated(hash = 170254465)
    public Account(String id, String name, String email, String phone,
            String password, String avatar, String cover, String gender,
            String address, String nickname, String description, String createAt,
            String updateAt, String token, int code, boolean activated,
            boolean deleted, boolean admin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.avatar = avatar;
        this.cover = cover;
        this.gender = gender;
        this.address = address;
        this.nickname = nickname;
        this.description = description;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.token = token;
        this.code = code;
        this.activated = activated;
        this.deleted = deleted;
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n\tid:" + id);
        buffer.append("\n\tname:" + name);
        buffer.append("\n\temail:" + email);
        buffer.append("\n\tphones:" + phone);
        buffer.append("\n\tavatar:" + avatar);
        buffer.append("\n\tcover:" + cover);
        buffer.append("\n\tgender:" + gender);
        buffer.append("\n\taddress:" + address);
        buffer.append("\n\tnickname:" + nickname);
        buffer.append("\n\tdescription:" + description);
        buffer.append("\n\tcreateAt:" + createAt);
        buffer.append("\n\tupdateAt:" + updateAt);
        buffer.append("\n\ttoken:" + token);
        buffer.append("\n\tcode:" + code);
        buffer.append("\n\tactivated:" + activated);
        buffer.append("\n\tdeleted:" + deleted);
        buffer.append("\n\tadmin:" + admin);
        return buffer.toString();
    }

}
