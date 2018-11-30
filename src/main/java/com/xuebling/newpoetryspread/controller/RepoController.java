package com.xuebling.newpoetryspread.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuebling.newpoetryspread.common.config.MongoConfig;
import com.xuebling.newpoetryspread.common.utils.RepoUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/repo")
public class RepoController {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LiteratureRepository literatureRepository;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private HttpServletRequest request;
    @GetMapping(path = "test")
    public Object test(){
        /**  **/
//        ArrayList<String> list = new ArrayList<>();
//        list.add("5bfd5b5556bc3b2e483950d9");
//        list.add("5bfd9bc156bc3b2eb48ff69b");
//        list.add("5bfdac7256bc3b24c0cd33b8");
        /**插入**/
//        if(!libraryRepository.insertEmbedDoc(list,new Library())){
//            return new Response(ResponseMsg.TARGETNULL);
//        }
        /**删除**/
//        if (!libraryRepository.deleteEmbedDoc(list)){
//            return new Response(ResponseMsg.TARGETNULL);
//        }
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
        //对文献进行操作前需要先设置集合
        MongoConfig.setCollectionName("5bfd5b5556bc3b2e483950d9");
        Object result = literatureRepository.save(new Literature());
        return new Response(ResponseMsg.SUCCESS);
    }
    //查询库的所有内容
    @GetMapping(path = "**")
//    @GetMapping(path = "**")@PathVariable(value = "repoId")String repoId
    public Object visitRepo(HttpServletRequest request) {
        try{
            ArrayList<String> id = (ArrayList<String>)request.getAttribute("id");
            //将最子集合的id设为集合名
            MongoConfig.setCollectionName(id.get(id.size()-1));
            List<Literature> result = literatureRepository.findAll();
            return new ResponseData(ResponseMsg.SUCCESS,result);
        }catch (Exception e){
            //一般都是集合不存在才会出错
            return new Response(ResponseMsg.TARGETNULL);
        }
    }
    //获取所有库的列表
    @GetMapping(path = "/repos")
    public Object getRepos() {
        try {
            List<Library> list = libraryRepository.findAll();
            return new ResponseData(ResponseMsg.SUCCESS,list);
        }catch (Exception e){
            return new Response(ResponseMsg.FAIL);
        }
    }
    //新建子库,repoId为父库的id
    @PutMapping(path = "**")
    public Object createRepo(HttpServletRequest request,@Valid Library library) {
        //校验信息
        logger.info(library.toString());
        ArrayList<String> id = (ArrayList<String>)request.getAttribute("id");
        try{
            libraryRepository.insertEmbedDoc(id,library);
            return new Response(ResponseMsg.SUCCESS);
        }catch (Exception e){
            return new Response(ResponseMsg.FAIL);
        }
    }
    //新建根库,就是啥都不填
    @PutMapping(path = "")
    public Object createRootRepo(@Valid Library library) {
        //校验信息
        try{
            libraryRepository.save(library);
            return new Response(ResponseMsg.SUCCESS);
        }catch (Exception e){
            return new Response(ResponseMsg.FAIL);
        }
    }
    //修改文献库
    @PostMapping(path = "**")
    public Object modifyRepo(HttpServletRequest request, JSONObject object) {
        //对比修改
        ArrayList<String> id = (ArrayList<String>)request.getAttribute("id");
        Set<String> keySet = object.keySet();
        Iterator iterator = keySet.iterator();
        try{
            //利用spring的序列化自动判断异常
            JSON.toJavaObject(object,Library.class);
        }catch (Exception e){
            e.printStackTrace();
            return new Response(ResponseMsg.DATAILLEGAL);
        }
        //遍历键,修改
        while (iterator.hasNext()){
            //如果未更新成功
            if (!libraryRepository.updateEmbedDoc(id,iterator.next().toString(),object.get(iterator.next().toString()))){
                return new Response(ResponseMsg.FIELDNONE);
            }
        }
        return new Response(ResponseMsg.SUCCESS);
    }
    //迁移库
    @PostMapping(path = "movement")
    public Object moveRepo() {
        return new Object();
    }
    //备份库
    @GetMapping(path = "backup/**")
    public Object backupRepo(HttpServletRequest request) {
        ArrayList<String> id = (ArrayList<String>)request.getAttribute("id");

        return new Response(ResponseMsg.SUCCESS);
    }


}
