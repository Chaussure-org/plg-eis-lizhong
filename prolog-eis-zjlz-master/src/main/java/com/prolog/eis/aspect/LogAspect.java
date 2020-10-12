package com.prolog.eis.aspect;

import com.prolog.eis.log.service.ILogService;
import com.prolog.eis.model.log.Log;
import com.prolog.eis.util.LogInfo;
import com.prolog.framework.utils.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Author wangkang
 * @Description 日志切面  该切面需要保存参数时要求参数只有一个，且用对象封装
 * @CreateTime 2020-10-10 14:31
 */
@Component
@Aspect
public class LogAspect {

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.prolog.eis.util.LogInfo)")
    public void doLog(){}

    @Around("doLog()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        Log log = new Log();
        Object[] args = joinPoint.getArgs();

        String methodName = joinPoint.getSignature().getName();
        Object target = joinPoint.getTarget();
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();
        Method method = null;
        try{
            method = target.getClass().getMethod(methodName,parameterTypes);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (null != method){
            if (method.isAnnotationPresent(LogInfo.class)) {
                LogInfo logInfo = method.getAnnotation(LogInfo.class);
                log.setDescri(logInfo.desci());
                log.setDirect(logInfo.direction());
                log.setType(logInfo.type());
                Object arg = args[0];
                log.setParams(JsonUtils.toString(arg));
                log.setMethodName(methodName);
                try{
                    Object proceed = joinPoint.proceed();
                    log.setSuccess(true);
                    log.setCreateTime(new Date());
                    System.out.println(log);
                    //logService.save(log);
                }catch (Exception e){
                    log.setSuccess(false);
                    log.setException(e.getMessage().toString());
                    log.setCreateTime(new Date());
                    //logService.save(log);
                }
            }else {
                joinPoint.proceed();
            }
        }else {
            joinPoint.proceed();
        }

    }
}
