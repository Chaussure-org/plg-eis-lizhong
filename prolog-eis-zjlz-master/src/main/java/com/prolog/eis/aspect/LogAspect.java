package com.prolog.eis.aspect;

import com.prolog.eis.util.IPUtils;
import com.prolog.eis.configuration.ServerConfiguration;
import com.prolog.eis.dto.log.LogDto;
import com.prolog.eis.log.service.ILogService;
import com.prolog.eis.util.LogInfo;
import com.prolog.framework.utils.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
@Order(1)
public class LogAspect {

    @Autowired
    private ILogService logService;

    @Autowired
    private ServerConfiguration serverConfiguration;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Pointcut("@annotation(com.prolog.eis.util.LogInfo)")
    public void doLog() {
    }

    @Around("doLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        LogDto log = new LogDto();
        log.setHostPort(serverConfiguration.getUrl());
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
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != method) {
            LogInfo logInfo = method.getAnnotation(LogInfo.class);
            log.setDescri(logInfo.desci());
            log.setDirect(logInfo.direction());
            log.setSystemType(logInfo.systemType());
            log.setType(logInfo.type());
            if (args != null && args.length > 0) {
                Object arg = args[0];
                log.setParams(JsonUtils.toString(arg));
            }
            log.setMethodName(methodName);
            try {
                Object proceed = joinPoint.proceed();
                log.setSuccess(true);
                log.setCreateTime(new Date());
                System.out.println(log);
                logService.save(log);
                rabbitTemplate.convertAndSend("sunppLog", "sun", JsonUtils.toString(log));
                return proceed;
            } catch (Exception e) {
                log.setSuccess(false);
                log.setException(e.getMessage().toString());
                log.setCreateTime(new Date());
                logService.save(log);
                rabbitTemplate.convertAndSend("sunppLog", "sun", JsonUtils.toString(log));
                //调试
                e.printStackTrace();
                throw e;
            }
        } else {
            return joinPoint.proceed();
        }
    }
}
