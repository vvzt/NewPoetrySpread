package com.xuebling.newpoetryspread.dao;

import com.xuebling.newpoetryspread.pojo.literaturelib.Library;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface LibraryRepository extends MongoRepository<Library,String>,LibraryCustomRepository {
}
