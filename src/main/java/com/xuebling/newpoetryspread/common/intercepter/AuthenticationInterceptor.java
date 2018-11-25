package com.xuebling.newpoetryspread.common.intercepter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.impl.JWTParser;
import com.xuebling.newpoetryspread.common.annotation.CustomAnnotation;
import com.xuebling.newpoetryspread.dao.UserRepository;
import com.xuebling.newpoetryspread.pojo.user.Editor;
import com.xuebling.newpoetryspread.pojo.user.Root;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.Optional;

//拦截器,继承HandlerInterceptor接口
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    @Autowired
    UserRepository userRepository;
    @Value("${jwt.secret}")
    private static String secret;
    @Override//请求被处理之前执行
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 判断是不是HandlerMethod的一个实例, 这个类用于封装方法定义相关的信息,如类,方法,参数
        // 如果object是一个实例,拦截的方法是一个映射的方法,会进行处理,如果不是,直接return true,予以通过
//        logger.info("header为" + httpServletRequest.getHeader("Upgrade"));
        if (!(object instanceof HandlerMethod)) {//&&httpServletRequest.getHeader("Upgrade").isEmpty()
            return true;
        }
        //把对象转为一个HandlerMethod对象
        HandlerMethod handlerMethod = (HandlerMethod) object;
        //获取该对象的方法
        Method method = handlerMethod.getMethod();
        //检查是否有PassToken注解,写这个注解可以快速通过拦截器,所以最好加上
        if (method.isAnnotationPresent(CustomAnnotation.PassToken.class)) {
            return true;
        }
        //检查是否有NeedToken注解
        if (!method.isAnnotationPresent(CustomAnnotation.NeedToken.class)) {
            return true;
        }
        // 执行认证
        if (token == null) {
            throw new AccessDeniedException("拒绝访问");
        }
        String account;
        try {
            //从token中获取userId
            account = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new AccessDeniedException("拒绝访问");
        }
        //在数据库中查找该用户,觉得效率太低,不用了
//        Editor user = userRepository.findByAccount(account);
//        if (user == null) {
//            throw new AccessDeniedException("拒绝访问");
//        }
        // 验证audience中的account是否和sign里的是一样的,确认token没有被篡改
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account)).build();//fixme 使用公钥和私钥,或者动态的公钥
        try {
            //使用验证器验证token
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new AccessDeniedException("拒绝访问");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        //如何将刷新后的token主动推送给用户
//        if (new Date().after(new Date(oldDate.getTime() + 300000))){
//
//        }
    }
}
