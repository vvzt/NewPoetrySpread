package com.xuebling.newpoetryspread.pojo.user;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;

@Document(collection="User")
public class Editor {
    @Id
    @Indexed
    private ObjectId id = new ObjectId();//id
    @NotEmpty(message = "用户名不能为空")
    private String account;
    @NotEmpty(message = "密码不能为空")
    private String token;
    @NotEmpty(message = "用户昵称不能为空")
    private String nickname;//昵称
    private String avatar;//头像名称
    @Max(1)
    @Min(0)
    private Integer lock;//锁定
    @Max(value = 3, message = "最低等级为1，最高等级为3")
    @Min(value = 1, message = "最低等级为1，最高等级为3")
    private Integer level;//用户等级

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
