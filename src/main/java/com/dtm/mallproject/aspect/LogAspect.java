package com.dtm.mallproject.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/2 18:35
 * @description : Log日志的AOP
 */
@Slf4j
@Component
@Aspect
public class LogAspect {
    /**
     * 定义切入点，将 handler 包下除 MyMetaObjectHandler 类以为的类都声明为切面
     * 定义一个无返回值，参数和方法体为空的方法来命名切入点
     */
	@Pointcut("execution(* com.dtm.mallproject.handler.*.*(..))"+
			"&&"+"!execution(* com.dtm.mallproject.config.MetaObjectHandlerConfig.*(..))")
	private void handlerPc() {
	}

	/**
	 * 前置通知
	 * @param jp joinPoint
	 */
	@Before("handlerPc()")
	public void beforeAdvice(JoinPoint jp){
		log.info("+++++方法开始，进入切面+++++");
		Signature signature = jp.getSignature();
		log.info("执行方法："+signature.getDeclaringTypeName()+"."+signature.getName()+
				" 方法参数："+ Arrays.toString(jp.getArgs()));
	}

	/**
	 * 后置通知
	 * @param jp joinPoint
	 * @param returnValue 方法返回值
	 */
	@AfterReturning(pointcut = "handlerPc()", returning = "returnValue")
	public void afterReturningAdvice(JoinPoint jp, Object returnValue){
		log.info("方法 "+jp.getSignature().getName()+" 的返回值是："+returnValue);
	}

	/**
	 * 异常通知
	 * @param jp joinPoint
	 * @param e 出现的异常
	 */
	@AfterThrowing(pointcut = "handlerPc()", throwing = "e")
	public void afterThrowingAdvice(JoinPoint jp, Exception e){
		log.error("***方法 "+jp.getSignature().getName()+" 出现异常***");
		log.error("异常信息："+ e.getMessage());
	}

	/**
	 * 最终通知
	 * @param jp joinPoint
	 */
	@After("handlerPc()")
	public void after(JoinPoint jp){
		log.info("方法 "+jp.getSignature().getName()+" 执行结束");
	}
}
