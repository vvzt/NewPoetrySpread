package com.xuebling.newpoetryspread.pojo.literaturelib;

import com.xuebling.newpoetryspread.pojo.enums.LibState;
import com.xuebling.newpoetryspread.pojo.literaturelib.library.Operator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Library{
    @BsonId
    private ObjectId id;
    private String repoName;
    private String types;
    private String description="asdasd";
    private LibState state;
    private Operator creator;
    private Operator locker;
    private Operator deleter;
    private ArrayList<String> subRepos;//存放子库的id
    private LinkedList<Literature> docs;//直接存放文档

    //将一个元素放到文档集合里
    public void pushDocs(Literature item){
        this.docs.add(item);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibState getState() {
        return state;
    }

    public void setState(LibState state) {
        this.state = state;
    }

    public Operator getCreator() {
        return creator;
    }

    public void setCreator(Operator creator) {
        this.creator = creator;
    }

    public Operator getLocker() {
        return locker;
    }

    public void setLocker(Operator locker) {
        this.locker = locker;
    }

    public Operator getDeleter() {
        return deleter;
    }

    public void setDeleter(Operator deleter) {
        this.deleter = deleter;
    }

    public ArrayList<String> getSubRepos() {
        return subRepos;
    }

    public void setSubRepos(ArrayList<String> subRepos) {
        this.subRepos = subRepos;
    }

    public LinkedList<Literature> getDocs() {
        return docs;
    }

    public void setDocs(LinkedList<Literature> docs) {
        this.docs = docs;
    }
}
