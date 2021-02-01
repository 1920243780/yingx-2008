package com.baizhi.wxh.aspect;

import com.baizhi.wxh.annotation.AddLog;
import com.baizhi.wxh.dao.LogMapper;
import com.baizhi.wxh.entity.Admin;
import com.baizhi.wxh.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Aspect
@Configuration
public class LogAspect {
    @Autowired
    private HttpSession session;

    @Autowired
    private LogMapper logMapper;

    @Around("@annotation(com.baizhi.wxh.annotation.AddLog)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
        Admin admin = (Admin) session.getAttribute("admin");

        //获取切面切到的方法名
//        String name = proceedingJoinPoint.getSignature().getName();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

        Method method = signature.getMethod();

        AddLog addLog = method.getAnnotation(AddLog.class);

        String name = addLog.value();

        Object result = null;

        String message = null;
        try {
            result = proceedingJoinPoint.proceed();
            message = "success";
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            message = "error";
        }

        Log log = new Log(UUID.randomUUID().toString(),admin.getUsername(),new Date(),name,message);

        //添加日志信息入库
        logMapper.insertSelective(log);

        return result;
    }
}
