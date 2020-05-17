package com.svop.asprcts;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LoggerAspect {

    @Pointcut("execution(public * com.svop.service..*(..))")
    public void callSevice() { }

    @Pointcut("execution(public * com.svop.controllers..*(..))")
    public void callControllers() {}

    @Before("callSevice() || callControllers()")
    public void before(JoinPoint joinPoint) {
        StringBuilder args=new StringBuilder("");
        if (joinPoint.getArgs()!=null) {
           for(Object obj:joinPoint.getArgs())
           {
               args.append(obj!=null ? obj.toString() : "null");
               args.append(",");
           }
        }
        log.info("********** До вызова " + joinPoint.toString() + ", Аргументы=[" + args + "] **********");
    }

    @After("callSevice() || callControllers()")
    public void after(JoinPoint joinPoint) {
        log.info("********** После вызова " + joinPoint.toString()+" **********");
    }



    @AfterThrowing(value = "callSevice() ||callControllers()",throwing ="exception" )
    public void afterThrowing(Exception exception) throws Throwable  {
        log.error("********** После вызова " +exception+" **********");
        /**
         *
         */
    }


}
