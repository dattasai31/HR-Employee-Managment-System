package com.example.demo.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LeaveAOP {
	@Pointcut("execution(* com.example.demo.Controller.LeaveController.*(..))")
	public void pointcut()
	{
		
	}
	@Around("pointcut()")
	public Object executionTime(ProceedingJoinPoint jp) throws Throwable
	{
		String methodName=jp.getSignature().getName();
		System.out.println("Execution started for this method: "+methodName);
		long start=System.currentTimeMillis();
		try
		{
			return jp.proceed();
		}
		finally
		{
			long end=System.currentTimeMillis();
			System.out.println("Execution completed for this method "+ methodName+" in "+(end-start)+" ms");
			System.out.println("--------------------------------------------");
		}
		
	}

}
