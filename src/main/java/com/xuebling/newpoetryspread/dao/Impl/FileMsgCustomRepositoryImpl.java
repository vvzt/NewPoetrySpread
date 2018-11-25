package com.xuebling.newpoetryspread.dao.Impl;

import com.xuebling.newpoetryspread.dao.FileMsgCustomRepository;
import com.xuebling.newpoetryspread.pojo.fileupload.FileMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class FileMsgCustomRepositoryImpl implements FileMsgCustomRepository {
    @Autowired
    protected MongoTemplate mongoTemplate;
    public FileMsg updateUnfinishedById(String id, Integer item){
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().pull("unfinishedChunk",item);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        return mongoTemplate.findAndModify(query,update,options,FileMsg.class);
    }
}
