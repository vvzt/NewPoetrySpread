package com.xuebling.newpoetryspread.pojo.fileupload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

@Document(collection = "FileMsg")
public class FileMsg implements Serializable {
    @Id
    @Null
    @Indexed
    private String taskId;
    private String fileName;
    private Long fileSize;
    private Long chunkSize;//单位为B,1024*1024=1 048 576,5M=5 242 880
    private Integer chunkNum;
    @NotNull
    private String sourceFileMD5;

    private String beginTime;
    private LinkedList<Integer> unfinishedChunk = new LinkedList<>();//直接存放序号 1 2 3 4

    //对数据初始化
    public void init(String fileStorePath){
        //生成时间戳
        beginTime = String.valueOf(System.currentTimeMillis()/1000);
        //生成未上传的文件块的列表
        for (int i=0;i<chunkNum;i++){//从0开始生成序列
            unfinishedChunk.add(i);
        }
        String targetFileName = beginTime+fileName;
        File targetFile = new File(fileStorePath+targetFileName);
        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(unfinishedChunk.toString());
    }

    public File getTargetFile(String fileStorePath){
        return new File(fileStorePath + beginTime + fileName);
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setChunkNum(Integer chunkNum) {
        this.chunkNum = chunkNum;
    }

    public Integer getChunkNum() {
        return chunkNum;
    }

    public String getSourceFileMD5() {
        return sourceFileMD5;
    }

    public void setSourceFileMD5(String sourceFileMD5) {
        this.sourceFileMD5 = sourceFileMD5;
    }

    public LinkedList<Integer> getUnfinishedChunk() {
        return unfinishedChunk;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public void setUnfinishedChunk(LinkedList<Integer> unfinishedChunk) {
        this.unfinishedChunk = unfinishedChunk;
    }

}
