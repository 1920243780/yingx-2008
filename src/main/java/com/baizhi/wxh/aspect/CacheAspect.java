package com.baizhi.wxh.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Aspect
@Configuration
public class CacheAspect {
    @Resource
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.baizhi.wxh.annotation.AddCache)")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint){
        Object result = null;

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        StringBuilder stringBuilder = new StringBuilder();
        String className = proceedingJoinPoint.getTarget().getClass().getName();

        String methodName = proceedingJoinPoint.getSignature().getName();
        stringBuilder.append(methodName + "-");

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            stringBuilder.append(arg);
        }

        String key = stringBuilder.toString();

        HashOperations hashOperations = redisTemplate.opsForHash();

        Boolean aBoolean = hashOperations.hasKey(className, key);

        //如果为true,则从redis中获取数据
        if(aBoolean){
            result = hashOperations.get(className, key);
        }else {
            //否则重新查询数据库
            try {
                result = proceedingJoinPoint.proceed();

                hashOperations.put(className,key,result);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return result;
    }

    @AfterReturning("@annotation(com.baizhi.wxh.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(className);
    }
}
