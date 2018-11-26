package com.xuebling.newpoetryspread.common.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {
    private static ThreadLocal<String> collectionName = new ThreadLocal<>();

    public static String getCollectionName() {
        return collectionName.get();
    }

    public static void setCollectionName(String name) {
        collectionName.set(name);
    }
}
