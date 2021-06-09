package com.douzone.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExecutionTimeAspect {
	/**
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 * 
	 * repository가 실행되는 동안의 시간을 StopWatch를 이용하여 측정. 
	 * getTotalTimeMillis() 메소드 사용. 
	 */
	
	// repository 패키지 밑의 모든 클래스의 모든 메소드.
	@Around(value="execution(* *..*.repository.*.*(..)) || execution(* *..*.service.*.*(..)) || execution(* *..*.controller.*.*(..))")
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		// Before
		StopWatch sw = new StopWatch();
		sw.start();
		
		// repository. 코드 실행 
		Object result = pjp.proceed();
		
		// After
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
		
		// pjp가 지정하고 있는 곳의 클래스 이름을 가져와라.
		String className = pjp.getTarget().getClass().getName();
		// pjp가 실행하는 Method의 이름을 가져와라.
		String methodName = pjp.getSignature().getName();
		
		//둘이 합침. 
		String task = className + "." + methodName;
		
		
		System.out.println("[Execution Time][" + task + "] " + totalTime + "(millis)");
		
		return result;
	}
	
}
