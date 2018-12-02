package com.xuebling.newpoetryspread.pojo.user;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="UserAuth")
public class Auth {
    private String user_id;
//    private String login_type;
//    private String login_uid;
//    private String login_token;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String userId) {
        this.user_id = userId;
    }
}
