package com.xuebling.newpoetryspread.dao.Impl;

import com.mongodb.client.result.UpdateResult;
import com.xuebling.newpoetryspread.dao.LibraryCustomRepository;
import com.xuebling.newpoetryspread.pojo.literaturelib.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class LibraryCustomRepositoryImpl implements LibraryCustomRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    private ArrayList<Integer> getIndex(ArrayList<String> id) throws NoSuchElementException{
        try {
            Optional<Library> library = findOne(id.get(0));
            if (!library.isPresent()){
                throw new NoSuchElementException("没有找到该文档");
            }
            ArrayList<Integer> index = new ArrayList<>();
            return getIndex(library.get().getSubRepos(),id,index);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            throw new NoSuchElementException();
        }
    }
    private Optional<Library> findOne(String id){
        Query query = new Query(Criteria.where("id").is(id));
//        System.out.println("findOne查找到的library为"+library.toString());
        return Optional.of(mongoTemplate.findOne(query,Library.class));
    }
    private ArrayList<Integer> getIndex(ArrayList<Library> list ,ArrayList<String> id, ArrayList<Integer> index) throws NoSuchElementException{
        //如果id为1,就说明查完了就返回
        if(id.size()==1){
            return index;
        }
        try{
            Optional<Library> lib = Optional.empty();
            for(int i=0;i<list.size()&&id.size()>1;i++){
                //id从第二个开始,第一个是根库,不用管
                if(list.get(i).getId().toString().equals(id.get(1))){
                    //将查到的库存入
                    lib = Optional.of(list.get(i));
                    index.add(i);
                    //移除
                    id.remove(1);
                }
            }
            if(lib.isPresent()){
                return getIndex(lib.get().getSubRepos(),id,index);
            }else {
                throw new Exception("没有找到该子文档");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new NoSuchElementException("没有找到该文档");
        }
    }
    //根据index构造
    private String getTargetString(ArrayList<String> id,String field) throws Exception{
        try{
            /**构建查询语句**/
            if (id.size()==1){
                System.out.println("构建的sql为"+field);
                return field;
            }
            ArrayList<Integer> index = getIndex(id);
            StringBuilder sql = new StringBuilder();
            for(int i=0;i<index.size();i++){
                sql.append("subRepos.").append(index.get(i)).append(".");
            }
            //判断嵌套层数,为一就直接查询
            sql.append(field);
            System.out.println("构建的sql为"+sql.toString());
            /**构建查询语句结束**/
            return sql.toString();
        }catch (Exception e){
            throw new Exception();
        }

    }
    private String getDeleteString(ArrayList<String> id) {
        /**构建查询语句**/
        if (id.size()==1){
            return "subRepos";
        }
        ArrayList<Integer> index = getIndex(id);
        StringBuilder sql = new StringBuilder();
        for(int i=0;i<index.size()-1;i++){
            sql.append("subRepos.").append(index.get(i)).append(".");
        }
        //判断嵌套层数,为一就直接查询
        sql.append("subRepos");
        System.out.println("构建的sql为"+sql.toString());
        /**构建查询语句结束**/
        return sql.toString();
    }
    //查找内嵌文档
    private Library findEmbedDoc(ArrayList<String> id,ArrayList<Integer> list) throws Exception{
        try{
            Library library = findOne(id.get(0)).get();
            id.remove(0);
            while (id.size()!=0){
                library = library.getSubRepos().get(list.get(0));
                list.remove(0);
                id.remove(0);
            }
//            System.out.println("embed文档为"+library.toString());
            return library;
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("文档不存在");
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("文档不存在");
        }catch (Exception e){
            throw new Exception("文档不存在");
        }
    }

    @Override
    public boolean deleteEmbedDoc(ArrayList<String> id) {
        try{
            Query query = new Query(Criteria.where("id").is(id.get(0)));
            Update update = new Update();
//            update.
            ArrayList<String> docId = new ArrayList<>(id);
            ArrayList<String> deleteId = new ArrayList<>(id);
            ArrayList<Integer> list = getIndex(id);
            update.pull(getDeleteString(deleteId),findEmbedDoc(docId,list));
            UpdateResult updateResult = mongoTemplate.updateFirst(query,update,Library.class);
            System.out.println("修改的数量为"+updateResult.getModifiedCount());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public <E> boolean updateEmbedDoc(ArrayList<String> id, String key, E value) {
        try{
            Query query = new Query(Criteria.where("id").is(id.get(0)));
            Update update = new Update();
            update.set(getTargetString(id,key),value);
            UpdateResult updateResult = mongoTemplate.updateFirst(query,update,Library.class);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public <E> boolean insertEmbedDoc(ArrayList<String> id, E value) {
        try {
            Query query = new Query(Criteria.where("id").is(id.get(0)));
            Update update = new Update();
            String key = "subRepos";
            update.push(getTargetString(id,key),value);
            mongoTemplate.updateFirst(query,update,Library.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
