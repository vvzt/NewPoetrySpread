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

@Document(collection="Library")
public class Library{
//    @BsonId
    @Id
    @Indexed
    private ObjectId id = new ObjectId();
    private String repoName="hhh";
    private String types="视听库";
    private String description="asdasd";
    private LibState state = LibState.using;
    //可以有多个吗
    private Operator creator=new Operator();
    private Operator locker;
    private Operator deleter;
    private ArrayList<Library> subRepos = new ArrayList<>();//存放子库
    private String docsCollection;//存放文档集合名


    public void addSubRepo(Library library){
        this.subRepos.add(library);
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

    public ArrayList<Library> getSubRepos() {
        return subRepos;
    }

    public void setSubRepos(ArrayList<Library> subRepos) {
        this.subRepos = subRepos;
    }

    public String getDocsCollection() {
        return docsCollection;
    }

    public void setDocsCollection(String docsCollection) {
        this.docsCollection = docsCollection;
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", repoName='" + repoName + '\'' +
                ", types='" + types + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", creator=" + creator +
                ", locker=" + locker +
                ", deleter=" + deleter +
                ", subRepos=" + subRepos +
                ", docsCollection='" + docsCollection + '\'' +
                '}';
    }
}
