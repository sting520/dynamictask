package com.power.taskcenter.aop;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.power.taskcenter.util.JwtTokenUtils;
import com.power.taskcenter.util.ResultBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspectAOP {
    private static final Log log = LogFactory.get();

    @Pointcut("execution(public * com.power.taskcenter.controller.LoginController.login(..))")
    public void httpAuthClient() {
    }
    @Pointcut("execution(public * com.power.taskcenter.controller.DynamicTaskController.*(..))")
    public void httpAuthUser() {
    }

    @Pointcut("httpAuthClient() || httpAuthUser()")
    private void authUser(){}

    @ResponseBody
    @Around("authUser())")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String clientId = request.getHeader("clientId");
        String token = request.getHeader("accessToken");
        String classMethod = joinPoint.getSignature().getName();

       if (classMethod.equals("login")){
            return  joinPoint.proceed();
        }else {
            if (clientId==null){
                return  ResultBean.error("非法请求");
            }
            if (JwtTokenUtils.isTokenExpired(token)){
               String user = JwtTokenUtils.getAuthenticationeFromToken(request) ;
               if (user == null){
                   return  ResultBean.error("非法请求");
               }
               if (!user.equals(clientId)){
                   return  ResultBean.error("非法请求");
               }
                return  joinPoint.proceed();
            }else{
                return  ResultBean.error("非法请求");
            }
       }
    }
}
