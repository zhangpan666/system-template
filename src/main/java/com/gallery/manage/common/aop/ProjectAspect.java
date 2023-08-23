package com.gallery.manage.common.aop;


import com.gallery.manage.common.aop.annotations.Operation;
import com.gallery.manage.common.model.OperationRecord;
import com.gallery.manage.common.service.OperationRecordService;
import com.gallery.manage.common.util.LoginUtil;
import com.gallery.manage.common.util.threadpool.ThreadPoolUtil;
import com.light.config.util.RequestUtil;
import com.light.core.model.CommonResult;
import com.light.core.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Aspect
@Component
@Slf4j
public class ProjectAspect {

    @Autowired
    OperationRecordService operationRecordService;

    private static final ConcurrentHashMap<String, Method> METHOD_MAP = new ConcurrentHashMap<>();


    @Pointcut("@annotation(com.gallery.manage.common.aop.annotations.Operation)")
    public void operationRecordPointCut() {

    }


    @After("operationRecordPointCut()")
    public void operationRecord(JoinPoint joinPoint) {
        Long userId = LoginUtil.getAdminLoginUserId();
        HttpServletRequest httpServletRequest = RequestUtil.getHttpServletRequest();
        if (userId != null) {
            ThreadPoolUtil.executorService.submit(() -> {
                try {
                    String methodName = joinPoint.getSignature().getName();
                    Method method = currentMethod(joinPoint, methodName);
                    Operation operation = method.getAnnotation(Operation.class);
                    String module = operation.module();
                    String description = operation.description();
                    Object[] args = joinPoint.getArgs();
                    String ip = "未知";
                    if (httpServletRequest != null) {
                        ip = HttpUtil.getIpAddr(httpServletRequest);
                    }
                    OperationRecord operationRecord = new OperationRecord().setOperationUserId(userId)
                            .setDescription(description).setIp(ip).setModule(module);
                    if (args != null && args.length > 0) {
                        Object arg = args[0];
                        String relatedId = "";
                        if (arg != null) {
                            if (arg instanceof Object[]) {
                                List list = CollectionUtils.arrayToList(arg);
                                relatedId = list.stream().map(Object::toString).collect(Collectors.joining(",")).toString();
                            } else {
                                relatedId = arg.toString();
                            }
                        }
                        operationRecord.setRelatedId(relatedId);
                    }
                    operationRecordService.save(operationRecord);
                } catch (Exception e) {
                    e.printStackTrace();
                    OperationRecord operationRecord = new OperationRecord().setOperationUserId(userId)
                            .setDescription("操作记录异常");
                    operationRecordService.save(operationRecord);
                    log.info("用户【{}】操作记录异常", userId);
                }
            });
        }
    }

    @Pointcut("@annotation(com.light.config.annotations.NeedLogin)")
    public void needLoginPointCut() {

    }

    @Around("needLoginPointCut()")
    public Object needLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res = null;
        Long userId = LoginUtil.getLoginUserId();
        if (userId == null) {
            return CommonResult.noLogin();
        }
        res = joinPoint.proceed();
        return res;
    }


    @Pointcut("@annotation(com.light.config.annotations.NeedAdminLogin)")
    public void needAdminLoginPointCut() {

    }

    @Around("needAdminLoginPointCut()")
    public Object needAdminLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res = null;
        Long userId = LoginUtil.getAdminLoginUserId();
        if (userId == null) {
            return CommonResult.noLogin();
        }
        res = joinPoint.proceed();
        return res;
    }


    private Method currentMethod(JoinPoint joinPoint, String methodName) {
        Class<?> aClass = joinPoint.getTarget().getClass();
        String fullMethodName = aClass.getName() + "." + methodName;
        Method resultMethod = METHOD_MAP.get(fullMethodName);
        if (resultMethod != null) {
            return resultMethod;
        }
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        METHOD_MAP.put(fullMethodName, resultMethod);
        return resultMethod;
    }
}
