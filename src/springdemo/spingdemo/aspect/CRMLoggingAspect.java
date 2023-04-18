package springdemo.spingdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
//logger
	private Logger myLogger= Logger.getLogger(getClass().getName());
	//pointcut
  @Pointcut("execution(* springdemo.controller.*.*(..))")
   private void forController() {}
  
  @Pointcut("execution(* springdemo.service.*.*(..))")
  private void forService() {}

  @Pointcut("execution(* springdemo.dao.*.*(..))")
  private void forDAO() {}

  @Pointcut("forController()||forService()||forDAO()")
  private void forAppflow() {}
  //@before advice
  @Before("forAppflow()")
  public void before(JoinPoint theJoinPoint) {
	  String theMethod= theJoinPoint.getSignature().toShortString();
	  myLogger.info("===> in @Before calling method "+theMethod);
	  
	  //display arguments to the method 
	  //get the arguments
	  Object[] args= theJoinPoint.getArgs();
	  for(Object myArgs : args) {
		  myLogger.info("===>"+myArgs);
	  }
	  
  }
	//after advice
    @AfterReturning(
    		pointcut="forAppflow()",
    		returning="theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
    	 String theMethod= theJoinPoint.getSignature().toShortString();
   	  myLogger.info("===> in @AfterReturning method "+theMethod);
   	  
   	  //display data returned
   	myLogger.info("==>result :" +theResult);
    }
}
