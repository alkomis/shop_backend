package com.alkomis.shop.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Class represents aspect which responsible for logging requests and responses of {@link com.alkomis.shop.controller} package classes.
 */
@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {

    /**
     * Pointcut for all methods in {@link com.alkomis.shop.controller} package.
     */
    @Pointcut("within(com.akvelon.onlineshop.controller..*)")
    public void anyMethodInControllerPackage() {
    }

    /**
     * Around type advice for logging HTTP requests and responses.
     *
     * @param proceedingJoinPoint joinPoint for accessing method around which advice logic will be executed.
     * @return result of joinPoint proceeding.
     * @throws Throwable throws exception which thrown inside targeted method if it's thrown.
     */
    @Around("anyMethodInControllerPackage()")
    public Object controllerLogs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();

        String methodName = proceedingJoinPoint.getSignature().getName(); // Extracting method name.
        String className = proceedingJoinPoint.getTarget().getClass().toString(); // Extracting class name.
        Object[] signatureArgs = proceedingJoinPoint.getArgs(); // Extracting method arguments.

        // Checking if method arguments has something and perform logging for request.
        if (signatureArgs.length > 0 && signatureArgs[0] != null) {
            log.info("Method invoked from " + className + ", method name : " + methodName + ". Request Payload: " + mapper.writeValueAsString(signatureArgs[0]) + ".");
        } else {
            log.info("Method invoked from " + className + ", method name : " + methodName + ".");
        }

        // Proceed with target method.
        Object object = proceedingJoinPoint.proceed();

        // Logging method response.
        log.info("Method returns following response object: " + mapper.writeValueAsString(object) + ".");

        return object;
    }

    /**
     * AfterThrowing type of advice for logging information about thrown exception.
     *
     * @param joinPoint joinPoint for accessing method where exception was thrown.
     * @param exception thrown exception.
     */
    @AfterThrowing(pointcut = ("anyMethodInControllerPackage()"), throwing = "exception")
    public void endpointAfterThrowing(JoinPoint joinPoint, Exception exception) {
        log.error("Exception thrown in " + joinPoint.getTarget().getClass().getSimpleName() + " class, inside " + joinPoint.getSignature().getName() +
                " method. Details: " + exception.getMessage());
    }

}

