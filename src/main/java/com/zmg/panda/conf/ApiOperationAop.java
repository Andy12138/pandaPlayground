package com.zmg.panda.conf;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class ApiOperationAop {

    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void apiOperationAop() {
    }

    @Before("apiOperationAop()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        System.out.println("doBefore");
    }

    @AfterReturning(value = "apiOperationAop()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
        System.out.println("doAfterReturning"+ret.toString());
    }

    @Around("apiOperationAop()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
//            webLog.setDescription(log.value());
        }
        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        WebLog webLog = new WebLog();
        // 执行方法
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        String urlStr = request.getRequestURL().toString();
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("url", URLDecoder.decode(urlStr, "utf-8"));
        logMap.put("method",request.getMethod());
        logMap.put("parameter",getParameter(method, joinPoint.getArgs()));
//        logMap.put("spendTime",webLog.getSpendTime());
        logMap.put("description",method.getAnnotation(ApiOperation.class).value());
//        log.info("{}", JSONUtil.parse(webLog));
//        log.info(Markers.appendEntries(logMap), JSONUtil.parse(webLog).toString());
        log.info(JSONObject.toJSONString(logMap));
        return result;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        Map<String, Object> result = new HashMap<>();
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                result.put(parameters[i].getName(), args[i]);
//                argList.add(args[i]);
            }
            Validated validated = parameters[i].getAnnotation(Validated.class);
            if (validated != null) {
                result.put(parameters[i].getName(), args[i]);
//                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
//                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                result.put(key, args[i]);
//                argList.add(map);
            }
            // 将PathValue注解修饰的参数作为请求参数
            PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
            if (pathVariable != null) {
//                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(pathVariable.value())) {
                    key = pathVariable.value();
                }
                result.put(key, args[i]);
//                argList.add(map);
            }
        }
        if (result.size() == 0) {
            return null;
        } else {
            return result;
        }
//        if (argList.size() == 0) {
//            return null;
//        } else if (argList.size() == 1) {
//            return argList.get(0);
//        } else {
//            return argList;
//        }
    }
}

