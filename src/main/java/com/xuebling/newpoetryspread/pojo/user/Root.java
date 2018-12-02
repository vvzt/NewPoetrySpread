package com.xuebling.newpoetryspread.pojo.user;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection="User")
public class Root extends Admin{

    @NotNull
    private String nickname;//头像名称

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
