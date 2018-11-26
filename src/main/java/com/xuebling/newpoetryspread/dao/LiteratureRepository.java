package com.xuebling.newpoetryspread.dao;

import com.xuebling.newpoetryspread.pojo.literaturelib.Literature;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LiteratureRepository extends MongoRepository<Literature,String>{

}
