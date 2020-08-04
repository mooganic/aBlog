package com.wuminggao.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author wuminggao
 * @create 2020-07-27-下午1:22
 */
@Aspect //Aspect declaration
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.wuminggao.blog.controller.*.*(..))") // controller包下的所有类的所有方法
    public void pointCut(){
    }

    // 在切面前执行
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取访问者的ip和请求的url
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteHost();

        //获取请求方法
        String classMethod =
                joinPoint.getSignature().getDeclaringTypeName()// 全限定类名
                + "."
                + joinPoint.getSignature().getName();           // 方法名

        //获取请求参数
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);

        //打印日志
        logger.info("Request: {}", requestLog);
    }

    // 切入后执行
    @After("pointCut()")
    public void doAfter(){
    }

    // 返回后执行
    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void doAfterReturning(Object result){
        logger.info("Request result: {}", result);
    }

    // 封装请求的参数和方法，url，ip
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getClassMethod() {
            return classMethod;
        }

        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }
    }


}
