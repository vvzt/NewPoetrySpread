package com.xuebling.newpoetryspread.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.xuebling.newpoetryspread.pojo.user.Editor;
import org.springframework.beans.factory.annotation.Value;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

public class GenerateToken {
    @Value("${jwt.secret}")
    private static String secret;//私钥,现在不知道用不用
    public static String getToken(Editor editor) {
        //Algorithm.HMAC256():使用HS256生成token,密钥则是用户的密码，唯一密钥的话可以保存在服务端,我使用的算法没有唯一密钥
        //withAudience()存入需要保存在token的信息，这里我把用户ID存入token中
        String token="";
        Date now = new Date();
        //创建一个token,过期时间为3天
        token= JWT.create().withAudience(editor.getAccount()).withExpiresAt(new Date(now.getTime() + 3*24*60*60*1000))
                .sign(Algorithm.HMAC256(editor.getAccount()));
        return token;
    }
}
