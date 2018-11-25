package com.xuebling.newpoetryspread.dao.Impl;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xuebling.newpoetryspread.common.utils.BeanUtils;
import com.xuebling.newpoetryspread.dao.MongoRepository;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Repository
public class MongoRepositoryImpl implements MongoRepository {

    /**
     * MongoClient的实例代表数据库连接池，是线程安全的，可以被多线程共享，客户端在多线程条件下仅维持一个实例即可
     * Mongo是非线程安全的，目前mongodb API中已经建议用MongoClient替代Mongo
     */
    private MongoClient mongoClient = null;

    //私有构造函数
    public MongoRepositoryImpl() {
        if (mongoClient == null) {
            /**配置mongo**/
            MongoClientOptions.Builder build = new MongoClientOptions.Builder();
            //与目标数据库能够建立的最大connection数量为50
            build.connectionsPerHost(50);
//            build.autoConnectRetry(true);   //自动重连数据库启动
            //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
            build.threadsAllowedToBlockForConnectionMultiplier(50);
            /*
             * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟
             * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception
             * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
             */
            build.maxWaitTime(1000 * 60 * 2);
            //与数据库建立连接的timeout设置为1分钟
            build.connectTimeout(1000 * 60 * 1);
            /**配置codec**/
            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).build()));
            /**构建一个option**/
            MongoClientOptions myOptions = build.codecRegistry(pojoCodecRegistry).build();
            try {
                //数据库连接实例
                mongoClient = new MongoClient("127.0.0.1", myOptions);
            } catch (MongoException e) {
                e.printStackTrace();
            }

        }
    }


    //类初始化时，自行实例化，饿汉式单例模式保证线程安全
    private static final MongoRepositoryImpl mongoDBDaoImpl = new MongoRepositoryImpl();

    /**
     * 方法名：getMongoDBDaoImplInstance
     * 描述：单例的静态工厂方法
     */
    public static MongoRepositoryImpl getMongoDBDaoImplInstance() {
        return mongoDBDaoImpl;
    }

    /************************单例模式声明结束*************************************/

    //配置参数,通过修改配置参数控制要操作哪个库,哪个集合
    //将参数本地线程化,保证线程安全
    private String dbName = "test";//使用的数据库,不可配置
    private ThreadLocal<String> collectionName = new ThreadLocal<>();
//    private ThreadLocal<Class<?>> tClass = new ThreadLocal<>();

    /**
     * 进行操作前必须要先配置
     **/
    public void setConfig(String collectionName) {
        System.out.println("集合名为" + collectionName);
        this.collectionName.set(collectionName);
//        this.tClass.set(tClass);
    }

    @Override
    public MongoDatabase getDb() {
        return mongoClient.getDatabase(this.dbName);
    }

//    可以自定义一个注解,NoSuchCollection
    @Override
    public MongoCollection<Object> getCollection() {
        MongoCollection mongoCollection = mongoClient.getDatabase(dbName).getCollection(collectionName.get());
        return mongoCollection;
    }

    @Override
    public Optional<ArrayList<Object>> findAll() {
        //获取集合
        MongoCollection<Object> mongoCollection = getCollection();
        FindIterable findIterable = mongoCollection.find();
        MongoCursor<Object> mongoCursor = findIterable.iterator();
        ArrayList<Object> resultList = new ArrayList<>();
        while(mongoCursor.hasNext()){
            resultList.add(mongoCursor.next());
        }
        //返回一个Optional
        Optional<ArrayList<Object>> result = Optional.of(resultList);
        return result;
    }

    @Override
    public Optional<Object> findById(String id) {
        MongoCollection<Object> mongoCollection = getCollection();
        //转化成ObjectId
        ObjectId objectId = new ObjectId(id);
        FindIterable findIterable = mongoCollection.find(eq("_id", objectId));
        MongoCursor<Object> mongoCursor = findIterable.iterator();
        Optional<Object> result;
        if (mongoCursor.hasNext()){
            result = Optional.of(mongoCursor.next());
        }else {
            result = Optional.empty();
        }
        return result;
    }

    //不管怎末操作都返回true
    //要进行异常处理,有异常返回错误结果
    @Override
    public boolean deleteById(String id) {
        MongoCollection<Object> mongoCollection = getCollection();
        ObjectId objectId = new ObjectId(id);
        DeleteResult result = mongoCollection.deleteOne(eq("_id", objectId));
        if (result.wasAcknowledged()){
            return true;
        }else {
            return false;
        }
    }

    //要进行异常处理,有异常返回错误结果
    @Override
    public void insert(Object object) {
        MongoCollection<Object> mongoCollection = getCollection();
        mongoCollection.insertOne(object);
    }

    //要进行异常处理,有异常返回错误结果
    @Override
    public boolean updateById(String id,Object object) {
        MongoCollection<Object> mongoCollection = getCollection();
        ObjectId objectId = new ObjectId(id);
        UpdateResult updateResult = mongoCollection.updateOne((eq("_id", objectId)),new Document("$set",object));
        if(updateResult.wasAcknowledged()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean createCollection(String collectionName){
        MongoDatabase db = getDb();
        try {
            db.createCollection(collectionName);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

