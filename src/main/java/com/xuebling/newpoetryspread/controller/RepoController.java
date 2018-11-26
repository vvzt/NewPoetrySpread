package com.xuebling.newpoetryspread.controller;

import com.xuebling.newpoetryspread.common.config.MongoConfig;
import com.xuebling.newpoetryspread.dao.LiteratureRepository;
import com.xuebling.newpoetryspread.pojo.enums.ResponseMsg;
import com.xuebling.newpoetryspread.pojo.literaturelib.Library;
import com.xuebling.newpoetryspread.pojo.literaturelib.Literature;
import com.xuebling.newpoetryspread.pojo.result.Response;
import com.xuebling.newpoetryspread.pojo.result.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repo")
public class RepoController {
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LiteratureRepository literatureRepository;
    //查询库的所有内容
    @GetMapping(path = "visit/{repoId}")
    public Object visitRepo(@PathVariable(value = "repoId")String repoId) {
        return new Object();
    }
    //获取所有库的列表
    @GetMapping(path = "/repos")
    public Object getRepos() {

        MongoConfig.setCollectionName("xxxxxxxx");
        Object result = literatureRepository.save(new Literature());
        return new ResponseData(ResponseMsg.SUCCESS,result);
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
    public Object backupRepo(@PathVariable(value = "repoId")String repoId) {
        return new Object();
    }
}
