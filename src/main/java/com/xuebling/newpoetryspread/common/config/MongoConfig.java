package com.xuebling.newpoetryspread.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Configuration
public class MongoConfig {
    private static ThreadLocal<String> collectionName = new ThreadLocal<String>() {
        @Override
        public String initialValue() {
            return "Literature";
        }
    };


    public static String getCollectionName() {
        return collectionName.get();
    }

    public static void setCollectionName(String name) {
        collectionName.set(name);
    }
}
