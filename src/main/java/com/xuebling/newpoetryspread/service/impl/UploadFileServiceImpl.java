package com.xuebling.newpoetryspread.service.impl;

import com.xuebling.newpoetryspread.common.utils.UploadUtils;
import com.xuebling.newpoetryspread.dao.FileMsgRepository;
import com.xuebling.newpoetryspread.pojo.fileupload.FileChunk;
import com.xuebling.newpoetryspread.pojo.fileupload.FileMsg;
import com.xuebling.newpoetryspread.pojo.enums.ResponseMsg;
import com.xuebling.newpoetryspread.pojo.result.ResponseData;
import com.xuebling.newpoetryspread.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
public class UploadFileServiceImpl implements UploadFileService {
    @Autowired
    private FileMsgRepository fileMsgRepository;
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Value("${fileStorePath}")
    private String fileStorePath;


    //对文件块进行md5校验,返回处理结果,如果所有文件块都上传完毕,则进行源文件完整性校验
    @Override
    public Object finishChunkUpload(FileMsg fileMsg) {
        File targetFile = fileMsg.getTargetFile(fileStorePath);
        //全部记录被删除或者文件没有被分块,开始校验完整文件的md5值
        logger.info("所有文件块上传完成,正在校验完整文件");
        String completeFileMD5 = null;
        try {
            completeFileMD5 = UploadUtils.validateFile(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("合并后文件的md5为"+completeFileMD5);
        if (completeFileMD5.equals(fileMsg.getSourceFileMD5())){
            //返回一个uri
            logger.info("校验完整性成功");
            return new ResponseData(ResponseMsg.ALLDONE,targetFile.getName());
        }
        //校验失败
        else return new ResponseData(ResponseMsg.ALLFAIL,fileMsg);
    }

    @Override
    public FileMsg writeAndSaveProcess(FileMsg fileMsg, FileChunk fileChunk) {
        logger.info("文件正在写入");
        //验证成功,将文件写入,如果写入失败,则直接报错了
        try {
            UploadUtils.writeByRandomAccess(fileChunk,fileMsg.getTargetFile(fileStorePath),fileMsg.getChunkSize());
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("文件写入成功,正在将记录写入数据库");
        return fileMsgRepository.updateUnfinishedById(fileMsg.getTaskId(),fileChunk.getChunk());//fixme 如何解决事务性的问题
    }

}
