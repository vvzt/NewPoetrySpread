package com.xuebling.newpoetryspread.service;

import com.xuebling.newpoetryspread.pojo.fileupload.FileChunk;
import com.xuebling.newpoetryspread.pojo.fileupload.FileMsg;



public interface UploadFileService {
    //完成文件块的上传,完成后controller可以直接返回结果
    Object finishChunkUpload(FileMsg fileMsg);
    FileMsg writeAndSaveProcess(FileMsg fileMsg, FileChunk fileChunk);
}
