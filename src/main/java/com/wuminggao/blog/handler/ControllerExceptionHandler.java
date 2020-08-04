package com.wuminggao.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLSyntaxErrorException;

/**
 * @author wuminggao
 * @create 2020-07-27-下午1:01
 * @Description 拦截异常处理
 */
@ControllerAdvice // 表示拦截所有带有@Controller注解的控制器
public class ControllerExceptionHandler {

    // 记录错误到日志中
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Description 程序发生异常时的拦截方法
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        // 记录异常信息：请求的URL，异常信息
        logger.error("Request URL : {}，Exception : {}", request.getRequestURL(), e);

        // 当标识了状态码的时候就不拦截
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        // 将url和异常信息发送给页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURI());
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("error/error");
        return modelAndView;

    }


}



