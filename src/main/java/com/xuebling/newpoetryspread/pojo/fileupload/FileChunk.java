package com.xuebling.newpoetryspread.pojo.fileupload;

import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class FileChunk {
    @NotNull
    String taskId;
    @NotNull
    private MultipartFile file;
    @NotNull
    private Integer chunk;//文件块的序列

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getChunk() {
        return chunk;
    }

    public void setChunk(Integer chunk) {
        this.chunk = chunk;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
