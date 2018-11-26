package com.xuebling.newpoetryspread.dao;

import com.xuebling.newpoetryspread.pojo.literaturelib.Library;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface LibraryRepository extends MongoRepository<Library,String> {

}
