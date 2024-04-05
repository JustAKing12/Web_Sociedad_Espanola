package ar.edu.unnoba.Proyecto.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class AdministradorAspect {
    private final Logger logger = LoggerFactory.getLogger(AdministradorAspect.class);

    @Pointcut("execution(* ar.edu.unnoba.Proyecto.controller.AdministradorController.*(..))")
    private void forControllerPackage() {}

    @Before("forControllerPackage()")
    public void before(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("=====>> En @Before: llamando al método: " + method);
        Object[] args = joinPoint.getArgs();
        for (Object argument : args) {
            logger.info("=====>> Argumento: " + argument);
        }
        String timestamp = new SimpleDateFormat("yyyy|MM|dd HH|mm|ss").format(new Date());
        logger.info("=====>> Fecha y hora de ejecución: " + timestamp);
    }

    @AfterReturning(pointcut = "forControllerPackage()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("=====>> En @AfterReturning: desde el método: " + method);
        logger.info("=====>> Resultado: " + result);
    }
}