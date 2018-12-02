package com.xuebling.newpoetryspread.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xuebling.newpoetryspread.common.config.MongoConfig;
import com.xuebling.newpoetryspread.dao.UserRepository;
import com.xuebling.newpoetryspread.pojo.enums.ResponseMsg;
import com.xuebling.newpoetryspread.pojo.result.Response;
import com.xuebling.newpoetryspread.pojo.result.ResponseData;
import com.xuebling.newpoetryspread.pojo.user.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class RootController {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    /* 用户激活 */
    @GetMapping(path = "/{userId}")
    public Object validUser(@PathVariable(value = "userId") String userId, @RequestBody JSONObject object) {
        return new Response(ResponseMsg.SUCCESS);
    }

    /* 添加用户 */
    @PutMapping(path = "")
    public Object addUser(@RequestBody JSONObject object) {
        try{
            MongoConfig.setCollectionName("User");
            Root res = object.toJavaObject(Root.class);
            System.out.println(res.getNickname());
            userRepository.insert(res);
            System.out.println(userRepository.findAll());
            return new ResponseData(ResponseMsg.SUCCESS, object);
        }catch (Exception e){
            return new Response(ResponseMsg.FAIL);
        }
    }

    /* 修改用户 */
    @PostMapping(path = "")
    public Object editUser(@RequestBody JSONObject object) {
        return new Object();
    }

    /* 删除用户 */
    @DeleteMapping(path = "/{userId}")
    public Object deleteUser(@PathVariable(value = "userId") String userId) {
        try{
            return new ResponseData(ResponseMsg.SUCCESS, userId);
        }catch (Exception e){
            return new Response(ResponseMsg.FAIL);
        }
    }

    /* 用户权限分配 */
    @PutMapping(path = "/level")
    public Object setUserLevel(@RequestBody JSONObject object) {
        try{
            return new ResponseData(ResponseMsg.SUCCESS, object);
        }catch (Exception e){
            return new Response(ResponseMsg.FAIL);
        }
    }

    /* 用户锁定 */
    @PutMapping(path = "/lock/{userId}")
    public Object setUserLock(@PathVariable(value = "userId")String userId) {
        try{
            return new ResponseData(ResponseMsg.SUCCESS, userId);
        }catch (Exception e){
            return new Response(ResponseMsg.FAIL);
        }
    }

}
