package com.xuebling.newpoetryspread.pojo.user;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="User")
public class Editor {
    private String userId;//id
    private String account;//账号
    private String nickName;//昵称
    private String avatar;//头像名称
    private String lock;//锁定
    private String level;//用户等级

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLock() {
        return lock;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
