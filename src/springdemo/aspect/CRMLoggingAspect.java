package springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class CRMLoggingAspect {

	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	@Before("forAppFlow()")
	private void beforeMethod(JoinPoint theJoinPoint) {
		
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>>>> in @Before - calling method: " + theMethod + "<<<<====");
		
		Object[] args = theJoinPoint.getArgs();
		
		for(Object tempArg : args) {
			myLogger.info("====>>>> argument: " + tempArg + "<<<<====");
		}
	}

	@AfterReturning(pointcut="forAppFlow()", returning="theResult")
	private void afterReturningMethod(JoinPoint theJoinPoint, Object theResult) {
		
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>>>> in @AfterReturning - calling method: " + theMethod + "<<<<====");
		
		myLogger.info("====>>>> afterReturning RESULT: " + theResult + " <<<<====");
	}
	
	
	
	
	
	
	
}
