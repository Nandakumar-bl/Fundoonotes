package com.bridgelabz.fundoonotes.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Aspect
@Component
public class Logger 
{
	@Before("execution(* com.bridgelabz.fundoonotes.serviceimplementation.UserImplementation.login(*))")
	public void beforeloginMethod(JoinPoint JointPoint) throws Throwable
	{
		log.info("Before login");
	    log.info("method invoked: "+JointPoint.getSignature().getName()+" Logged-in user:"+JointPoint.getArgs()[0]);	
		
	}
	@AfterReturning("execution(* com.bridgelabz.fundoonotes.serviceimplementation.UserImplementation.login(*))")
	public void afterLoginMethod()
	{
		log.info("Login Executed");
	}
	
	
	  @Around("execution(* com.bridgelabz.fundoonotes.*.*.*(*)) && !execution(* com.bridgelabz.fundoonotes.serviceimplementation.UserImplementation.login(*))") 
	  public Object commonMethod(ProceedingJoinPoint JointPoint) throws Throwable 
	  {
	  log.info("Before "+JointPoint.getSignature().getName()+" method of "+JointPoint.getSignature().getDeclaringType().getSimpleName());
	  Object returner=  JointPoint.proceed();
	  log.info("After "+JointPoint.getSignature().getName()+"  method of class"+JointPoint.getSignature().getDeclaringType().getSimpleName()); 
	  return returner;
	  }
	  
	 

}
