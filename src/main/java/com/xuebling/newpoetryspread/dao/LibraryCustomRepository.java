package com.xuebling.newpoetryspread.dao;

import com.xuebling.newpoetryspread.pojo.literaturelib.Library;

import java.util.ArrayList;
import java.util.Optional;

public interface LibraryCustomRepository {
    boolean deleteEmbedDoc(ArrayList<String> id);
    //key为要修改的字段,value为字段的值
    //如果要修改的字段比较多,那么可以直接调用save方法,save是saveOrUpdate的
    <E> boolean updateEmbedDoc(ArrayList<String> id, String key, E value);
    //保存一个对象,新建库或者更新信息的时候用
    <E> boolean insertEmbedDoc(ArrayList<String> id,E value);
}
