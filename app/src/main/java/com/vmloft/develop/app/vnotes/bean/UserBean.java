package com.vmloft.develop.app.vnotes.bean;

/**
 * Created by lzan13 on 2017/11/24.
 * 账户实体类
 */
public class UserBean {
    String name;
    String email;
    String phone;
    String password;
    String avatar;
    String cover;
    String gender;
    String address;
    String description;
    String createAt;
    String updateAt;
    String token;
    String code;
    String activated;
    String deleted;
    String admin;

    public UserBean() {}

    public UserBean(String account, String password) {
        this.email = account;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n\tname:" + name);
        buffer.append("\n\temail:" + email);
        buffer.append("\n\tphones:" + phone);
        buffer.append("\n\tavatar:" + avatar);
        buffer.append("\n\tcover:" + cover);
        buffer.append("\n\tgender:" + gender);
        buffer.append("\n\taddress:" + address);
        buffer.append("\n\tdescription:" + description);
        buffer.append("\n\ttoken:" + token);
        buffer.append("\n\tactivated:" + activated);
        buffer.append("\n\tdeleted:" + deleted);
        buffer.append("\n\tadmin:" + admin);
        return buffer.toString();
    }
}
