package com.xuebling.newpoetryspread.controller;

import com.alibaba.fastjson.JSONObject;
import com.xuebling.newpoetryspread.common.annotation.CustomAnnotation;
import com.xuebling.newpoetryspread.dao.FileMsgRepository;
import com.xuebling.newpoetryspread.pojo.fileupload.FileChunk;
import com.xuebling.newpoetryspread.pojo.fileupload.FileMsg;
import com.xuebling.newpoetryspread.pojo.enums.ResponseMsg;
import com.xuebling.newpoetryspread.pojo.result.Response;
import com.xuebling.newpoetryspread.pojo.result.ResponseData;
import com.xuebling.newpoetryspread.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UploadController {
    //引入logger
    protected Logger logger =  LoggerFactory.getLogger(this.getClass());

    //引入文件的储存路径
    @Value("${fileStorePath}")
    public String fileStorePath;
//    @Value("${fileTempPath}")
//    public String fileTempPath;
    @Autowired
    private FileMsgRepository fileMsgRepository;

    @Autowired
    private UploadFileService uploadFileService;

    @CustomAnnotation.PassToken
    //准备上传文件,秒传功能
    @PostMapping(path = "file")
    public Object beginUpload(@Valid @RequestBody FileMsg fileMsg){
        Optional<FileMsg> fileRecord = fileMsgRepository.findBySourceFileMD5(fileMsg.getSourceFileMD5());
        //查找是否已经有这样的文件
        if (fileRecord.isPresent()&&fileRecord.get().getUnfinishedChunk().size()==0){
            return new ResponseData(ResponseMsg.SURPRISE,fileRecord.get().getFileName());
        }
        //如果有同样的任务但是未完成,返回result数组
        if (fileRecord.isPresent()){
            JSONObject data = new JSONObject();
            data.put("taskId",fileRecord.get().getTaskId());
            data.put("result",fileRecord.get().getUnfinishedChunk());
            return new ResponseData(ResponseMsg.TASKUNFINISHED,data);
        }
        //对传来的信息进行初始化
        fileMsg.init(fileStorePath);
        String taskId = fileMsgRepository.insert(fileMsg).getTaskId();
        logger.info(taskId);
        //构建目录名,好像不太需要
//        String dir = fileTempPath + "temp" + fileMsg.getBeginTime();
//        File tempDir = new File(dir);
//        tempDir.mkdirs();
        //返回id,前端根据id进行操作
        JSONObject data = new JSONObject();
        data.put("taskId",taskId);
        data.put("result",fileMsg.getUnfinishedChunk());
        return new ResponseData(ResponseMsg.SUCCESS,data);
    }

    //上传文件块
    @PostMapping(path = "/file/chunk")
    @CustomAnnotation.PassToken
    public Object uploadFile(FileChunk fileChunk){
        //查找对象
        Optional<FileMsg> fileMsg = fileMsgRepository.findById(fileChunk.getTaskId());
        //是否为空
        if(!fileMsg.isPresent()){
            logger.error("对象不存在,该任务在数据库中没有存档");
            return new Response(ResponseMsg.TARGETNULL);
        }
        //判断文件块是否已被上传
        if(!fileMsg.get().getUnfinishedChunk().contains(fileChunk.getChunk())) {
            logger.info("文件块已被上传");
            return new Response(ResponseMsg.CHUNKDONE);
        }//请求验证结束

        //将文件写入并记录任务进度
        FileMsg newFileMsg = uploadFileService.writeAndSaveProcess(fileMsg.get(),fileChunk);
        //是否完成了文件上传
        if(newFileMsg.getUnfinishedChunk().size()==0||fileMsg.get().getChunkNum()==1){
            return uploadFileService.finishChunkUpload(newFileMsg);
        }else {
            return new Response(ResponseMsg.ONEDONE);
        }
    }

    //继续任务,返回未完成的文件块的序列
    @GetMapping(path = "file/unfinished/{taskId}")
    public Object resumeTask(@PathVariable(value = "taskId")String taskId){
        Optional<FileMsg> fileMsg = fileMsgRepository.findById(taskId);
        if (fileMsg.isPresent()){
            JSONObject data = new JSONObject();
            data.put("result",fileMsg.get().getUnfinishedChunk());
            return new ResponseData(ResponseMsg.SUCCESS,data);
        }else return new Response(ResponseMsg.TARGETNULL);
    }

    @GetMapping(path = "file/allTask")
    public Object getAllTask(){
        return new Object();
    }

    //删除任务
    @DeleteMapping(path = "file")
    public Object deleteTask(String id){
        if(fileMsgRepository.existsById(id)){
            fileMsgRepository.deleteById(id);
            return new Response(ResponseMsg.SUCCESS);
        }else return new Response(ResponseMsg.TARGETNULL);
    }

}
