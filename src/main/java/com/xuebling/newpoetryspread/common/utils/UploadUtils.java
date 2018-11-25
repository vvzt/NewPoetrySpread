package com.xuebling.newpoetryspread.common.utils;

import com.xuebling.newpoetryspread.pojo.fileupload.FileChunk;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
// some static method
public class UploadUtils {
    //写入文件
    public static void writeByRandomAccess(FileChunk fileChunk,File targetFile,Long chunkSize) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile,"rw");
        System.out.println(fileChunk.toString());
        Long offset = fileChunk.getChunk() * chunkSize;
        //定位到该分片的偏移量
        randomAccessFile.seek(offset);
        //写入该分片数据
        randomAccessFile.write(fileChunk.getFile().getBytes());
        // 释放
        randomAccessFile.close();
    }
    //验证文件
    public static String validateFile(File file) throws IOException {
        // 缓冲区大小
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            // 拿到一个转换器,可以设一个全局变量,便于切换算法
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用DigestInputStream
            fileInputStream = new FileInputStream(file);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            // read()会一次读取bufferSize个字符
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0) ;
            // 获取最终的MessageDigest
            messageDigest = digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 同样，把字节数组转换成字符串
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        } finally {
            try {
                digestInputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
            }
        }
    }
    public static String validateFile(MultipartFile file) {
        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            String hashString = new BigInteger(1, digest).toString(16);
            return hashString;
        } catch (Exception e) {
        }
        return null;
    }
    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符,这里使用大写字母
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        // new一个字符数组，这个就是用来组成结果字符串的,一个byte是八位二进制，也就是2位十六进制字符,2的8次方等于16的2次方
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
}