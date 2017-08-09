package org.bumishi.admin.aspect;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.bumishi.admin.annotation.SystemServiceLog;
import org.bumishi.admin.domain.modle.SysLog;
import org.bumishi.admin.domain.repository.SysLogRepository;
import org.bumishi.admin.security.SecurityUser;
import org.bumishi.admin.security.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Order(2)
@Aspect
@Component
public class SysLogAop {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private SysLogRepository sysLogRepository;

    @Pointcut("@annotation(org.bumishi.admin.annotation.SystemServiceLog)")
    public void LogAspect() {
    }

    @AfterThrowing(pointcut = "LogAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint point, Throwable e) throws Exception {
        
    }

    @Around("LogAspect()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
    	Object result = null;
    	result = point.proceed();
        return result;
    }
}