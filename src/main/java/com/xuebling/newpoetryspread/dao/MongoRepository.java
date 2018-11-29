package com.xuebling.newpoetryspread.dao;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
//一个mongoDB的dao,通过此接口调用mongo的方法
public interface MongoRepository {


    //获取数据库实例
    void setConfig(String collectionName);
    MongoDatabase getDb();
    MongoCollection<Object> getCollection();
    Optional<ArrayList<Object>> findAll() ;

    Optional<Object> findById(String id);

    boolean deleteById(String id);

    void insert(Object object) ;

    boolean updateById(String id, Object object);

    boolean createCollection(String collectionName);
}
