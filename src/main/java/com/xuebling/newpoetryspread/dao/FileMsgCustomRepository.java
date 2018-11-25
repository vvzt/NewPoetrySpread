package com.xuebling.newpoetryspread.dao;

import com.xuebling.newpoetryspread.pojo.fileupload.FileMsg;

public interface FileMsgCustomRepository {
    FileMsg updateUnfinishedById(String id, Integer item);
}
