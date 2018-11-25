package com.xuebling.newpoetryspread.controller;

import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.xuebling.newpoetryspread.common.annotation.CustomAnnotation;
import com.xuebling.newpoetryspread.dao.FileMsgRepository;
import com.xuebling.newpoetryspread.dao.Impl.MongoRepositoryImpl;
import com.xuebling.newpoetryspread.dao.MongoRepository;
import com.xuebling.newpoetryspread.pojo.enums.LibState;
import com.xuebling.newpoetryspread.pojo.enums.ResponseMsg;
import com.xuebling.newpoetryspread.pojo.fileupload.FileMsg;
import com.xuebling.newpoetryspread.pojo.literaturelib.Library;
import com.xuebling.newpoetryspread.pojo.literaturelib.Literature;
import com.xuebling.newpoetryspread.pojo.result.Response;
import com.xuebling.newpoetryspread.pojo.result.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/repo")
public class RepoController {
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MongoRepositoryImpl mongoRepository;
    //查询库的所有内容
    @GetMapping(path = "visit/{repoId}")
    public Object visitRepo(@PathVariable(value = "repoId")String repoId) {
        return new Object();
    }
    //获取所有库的列表
    @GetMapping(path = "/repos")
    public Object getRepos() {
        mongoRepository.setConfig("Library");
//        Library library = new Library();
//        library.setRepoName("asd");
//        library.setState(LibState.deleted);
        Optional<Object> result = mongoRepository.findById("5bf95d23a4a76af63c937ecb");
        Library library = (Library) result.get();
        System.out.println(library.getId().toString());
//        Boolean result = mongoRepository.updateById("5bf95d23a4a76af63c937ecb",library);
        return new ResponseData(ResponseMsg.SUCCESS,result);
//        mongoRepository.insert(new Library());
//        Optional<ArrayList<Object>> result = mongoRepository.findAll();
//        return new ResponseData(ResponseMsg.SUCCESS,result.get());
    }
    //新建库,repoId为父库的id
    @PostMapping(path = "new/{repoId}")
    public Object createRepo(@PathVariable(value = "repoId")String repoId, Library library) {

        return new Response(ResponseMsg.TARGETNULL);
    }
    //修改文献库
    @PostMapping(path = "modifying/{repoId}")
    public Object modifyRepo(@PathVariable(value = "repoId")String repoId) {
        return new Object();
    }
    //迁移库
    @GetMapping(path = "movement/{repoId}/")
    public Object moveRepo(@PathVariable(value = "repoId")String repoId) {
        return new Object();
    }
    //备份库
    @GetMapping(path = "backup/{repoId}")
    public Object backupRepo(@PathVariable(value = "repoName")String repoName) {
        return new Object();
    }
}
