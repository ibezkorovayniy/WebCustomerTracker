package com.luv2code.springdemo.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
    public void forControllerPackage() {

    }

    @Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
    public void forServicePackage() {

    }

    @Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
    public void forDaoPackage() {

    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    public void forAppFlow() {

    }


    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {

        String theMethod = joinPoint.getSignature().toShortString();
        myLogger.info("=======>>>>>>in @Before: calling the method: " + theMethod+ "\n\n");

        Object[] args = joinPoint.getArgs();

        for(Object tempArg: args) {
            myLogger.info("======>>>>argument: " + tempArg);
        }
    }

    @AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
    public void afterReturning(JoinPoint joinPoint, Object theResult) {

        String theMethod = joinPoint.getSignature().toShortString();
        myLogger.info("=======>>>>>>in @AfterReturning: from the method: " + theMethod+ "\n");
        myLogger.info("=======>>>>>>result: " + theResult);


    }




}
