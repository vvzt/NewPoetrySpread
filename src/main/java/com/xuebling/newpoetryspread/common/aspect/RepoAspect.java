package com.xuebling.newpoetryspread.common.aspect;

import com.xuebling.newpoetryspread.common.utils.RepoUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Aspect
public class RepoAspect {
    @Pointcut("execution(* com.xuebling.newpoetryspread.controller.RepoController.*(javax.servlet.http.HttpServletRequest)) && args(request)")
    public void handleRequest(HttpServletRequest request){
        //只是一个切入点,不需要做任何事
    }
    @Around(value = "handleRequest(request)",argNames = "joinPoint,request")
    public Object test(ProceedingJoinPoint joinPoint, HttpServletRequest request)throws Throwable{
        Pattern pattern = Pattern.compile("/repo/");
        Matcher matcher = pattern.matcher(request.getRequestURI());
        String repoId = matcher.replaceFirst("");
        ArrayList<String> id = RepoUtils.transformURL(repoId);
        request.setAttribute("id",id);
        return joinPoint.proceed(new Object[]{request});
    }
}
