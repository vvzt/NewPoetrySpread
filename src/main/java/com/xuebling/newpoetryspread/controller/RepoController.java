package com.xuebling.newpoetryspread.controller;

import com.xuebling.newpoetryspread.common.config.MongoConfig;
import com.xuebling.newpoetryspread.dao.LibraryRepository;
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

import javax.swing.text.html.Option;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/repo")
public class RepoController {
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LibraryRepository libraryRepository;
    //查询库的所有内容
    @GetMapping(path = "visit/{repoId}")
    public Object visitRepo(@PathVariable(value = "repoId")String repoId) {
        return new Object();
    }
    //获取所有库的列表
    @GetMapping(path = "/repos")
    public Object getRepos() {
        ArrayList<String> list = new ArrayList<>();
        list.add("5bfd5b5556bc3b2e483950d9");
        list.add("5bfd9bc156bc3b2eb48ff69b");
        list.add("5bfdac7256bc3b24c0cd33b8");
        /**插入**/
//        if(!libraryRepository.insertEmbedDoc(list,new Library())){
//            return new Response(ResponseMsg.TARGETNULL);
//        }
        /**删除**/
        if (!libraryRepository.deleteEmbedDoc(list)){
            return new Response(ResponseMsg.TARGETNULL);
        }
        /**更新**/
//        libraryRepository.updateEmbedDoc(list,"repoName","ccc");
        /**新建**/
//        Library lib = new Library();
//        lib.setRepoName("xxx");
//        Library lib1 = new Library();
//        lib1.setRepoName("yyy");
//        Library lib2 = new Library();
//        lib2.setRepoName("zzz");
//        lib1.addSubRepo(lib2);
//        lib.addSubRepo(lib1);
//        libraryRepository.save(lib);
        /**不管**/
//        Optional<Library> library = libraryRepository.findById("5bfbae7e56bc3b2f900e5bbc");
//        MongoConfig.setCollectionName("xxxxxxxx");
//        Object result = literatureRepository.save(new Literature());
        return new Response(ResponseMsg.SUCCESS);
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
