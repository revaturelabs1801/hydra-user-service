package com.revature.demo.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Allan Poindexter, David Graves / Batch 1712_dec11th_Java_Steve
 * 
 *         Generic Logger to indicate the start and end of Controllers and
 *         Methods.
 */
@Aspect
@Component
public class ServiceAndControllerLogger {

	private static final Logger logger = LogManager.getLogger(ServiceAndControllerLogger.class);

	/**
	 * Writes the start of a Controller method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Controller Method
	 */
	@Before("execution (* com.revature.bam.controller.*.*(..))")
	public void beforeControllerMethod(JoinPoint jp) {
		JSONify jsonify = new JSONify();
		String json = "";
		json += jsonify.addEvent("start", "controller") + ",";
		json += jsonify.addKey("controllerClass") + jsonify.addValue(jp.getSignature().getDeclaringTypeName());
		json += jsonify.addKey("controllerMethod") + jsonify.addEndValue(jp.getSignature().getName());
		logger.debug(json);
	}

	/**
	 * Writes the end of a Controller method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Controller Method
	 */
	@After("execution (* com.revature.bam.controller.*.*(..))")
	public void afterControllerMethod(JoinPoint jp) {
		JSONify jsonify = new JSONify();
		String json = "";
		json += jsonify.addEvent("end", "controller") + ",";
		json += jsonify.addKey("controllerClass") + jsonify.addValue(jp.getSignature().getDeclaringTypeName());
		json += jsonify.addKey("controllerMethod") + jsonify.addEndValue(jp.getSignature().getName());
		logger.debug(json);
	}

	/**
	 * Writes the exception thrown by a Controller method.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * 		   Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Controller Method
	 * @param ex
	 *            -The Exception Thrown
	 */
	@AfterThrowing(pointcut = "execution (* com.revature.bam.controller.*.*(..))", throwing = "ex")
	public void afterControllerThrows(JoinPoint jp, Exception ex) throws Exception {
		JSONify jsonify = new JSONify();
		String json = "";
		json += jsonify.addKey("controllerException") + jsonify.addValue(ex.getCause().getMessage());
		json += jsonify.addKey("controllerClass") + jsonify.addValue(jp.getSignature().getDeclaringTypeName());
		json += jsonify.addKey("controllerMethod") + jsonify.addValue(jp.getSignature().getName());
		json += jsonify.addKey("stackTrace") + jsonify.addEndValue(ex.getCause().getStackTrace().toString());
		logger.error(json);
	}

	/**
	 * Writes the status code of Controller methods.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * 		   Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Controller Method
	 * @param retVal
	 *            -The ResponseEntity returned
	 */
	@AfterReturning(pointcut = "execution (* com.revature.bam.controller.*.*(..))", returning = "retVal")
	public void afterControllerReturns(JoinPoint jp, ResponseEntity<?> retVal) {
		JSONify jsonify = new JSONify();
		String json = "";
		json += jsonify.addKey("controllerClass") + jsonify.addValue(jp.getSignature().getDeclaringTypeName());
		json += jsonify.addKey("controllerMethod") + jsonify.addValue(jp.getSignature().getName());
		json += jsonify.addKey("returnedControllerStatus") + jsonify.addEndValue(String.valueOf(retVal.getStatusCodeValue()));
		logger.info(json);
		if (retVal.hasBody()) {
			json = "";
			json += jsonify.addKey("controllerClass") + jsonify.addValue(jp.getSignature().getDeclaringTypeName());
			json += jsonify.addKey("controllerMethod") + jsonify.addValue(jp.getSignature().getName());
			json += jsonify.addKey("returnedControllerValue") + retVal.getBody();
			logger.info(json);
		}
	}

	/**
	 * Writes the exception thrown by a Service method.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * 		   Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Service Method
	 * @param ex
	 *            -The Exception Thrown
	 */
	@AfterThrowing(pointcut = "execution (* com.revature.bam.service.*.*(..))", throwing = "ex")
	public void afterServiceThrows(JoinPoint jp, Exception ex) throws Exception {
		JSONify jsonify = new JSONify();
		String json = "";
		json += jsonify.addKey("serviceException") + jsonify.addValue(ex.getCause().getMessage());
		json += jsonify.addKey("serviceClass") + jsonify.addValue(jp.getSignature().getDeclaringTypeName());
		json += jsonify.addKey("serviceMethod") + jsonify.addValue(jp.getSignature().getName());
		json += jsonify.addKey("stackTrace") + jsonify.addEndValue(ex.getCause().getStackTrace().toString());
		logger.error(json);
	}

	/**
	 * Writes the returned value of Service methods.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * 		   Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Service Method
	 * @param retVal
	 *            -The value returned
	 */
	@AfterReturning(pointcut = "execution (* com.revature.bam.service.*.*(..))", returning = "retVal")
	public void afterServiceReturns(JoinPoint jp, Object retVal) {
		JSONify jsonify = new JSONify();
		String json = "";
		json += jsonify.addKey("serviceClass") + jsonify.addValue(jp.getSignature().getDeclaringTypeName());
		json += jsonify.addKey("serviceMethod") + jsonify.addValue(jp.getSignature().getName());
		json += jsonify.addKey("returnedServiceValue") + retVal;
		logger.info(json);
	}

	/**
	 * Writes the start of a Service method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Service Method
	 */
	@Before("execution (* com.revature.bam.service.*.*(..))")
	public void beforeServiceMethod(JoinPoint jp) {
		JSONify jsonify = new JSONify();
		String json = "";
		json += jsonify.addEvent("start", "service") + ",";
		json += jsonify.addKey("serviceClass") + jsonify.addValue(jp.getSignature().getDeclaringTypeName());
		json += jsonify.addKey("serviceMethod") + jsonify.addEndValue(jp.getSignature().getName());
		logger.debug(json);
	}

	/**
	 * Writes the end of a Service method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Service Method
	 */
	@After("execution (* com.revature.bam.service.*.*(..))")
	public void afterServiceMethod(JoinPoint jp) {
		JSONify jsonify = new JSONify();
		String json = "";
		json += jsonify.addEvent("end", "service") + ",";
		json += jsonify.addKey("serviceClass") + jsonify.addValue(jp.getSignature().getDeclaringTypeName());
		json += jsonify.addKey("serviceMethod") + jsonify.addEndValue(jp.getSignature().getName());
		logger.debug(json);
	}

	/**
	 * Displays the run time for Service or Controller methods. For the Controller,
	 * it will display the total runtime.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param pjp
	 *            - The Service or Controller Methods
	 * @return - object. This is required, or else the controller will return a
	 *         blank JSON object, or a service will throw a NullPointerException.
	 * @throws -
	 *             Throwable. This is required when using
	 *             PreceedingJoinPoint.proceed()
	 */
	@Around("execution (* com.revature.bam.controller.*.*(..)) ||" + "execution (* com.revature.bam.service.*.*(..))")
	public Object classNameAndTime(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		Object object = pjp.proceed();
		long end = System.currentTimeMillis();
		
		JSONify jsonify = new JSONify();
		String json = "";
		json += jsonify.addKey("totalRuntime") + jsonify.addValue((end-start) + " milliseconds");
		json += jsonify.addKey("class") + jsonify.addValue(pjp.getSignature().getDeclaringTypeName());
		json += jsonify.addKey("method") + jsonify.addEndValue(pjp.getSignature().getName());
		logger.debug(json);
		return object;
	}

	/**
	 * Indicates a GET mapped method has fired. POST equivalent does not exist. See
	 * Issue on GitHub.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param classRequestMapping
	 * @param getMapping
	 */
	@Pointcut("@target(classRequestMapping) && @annotation(getMapping) && execution(* com.revature.bam.controller.*.*(..))")
	public void controller(RequestMapping classRequestMapping, GetMapping getMapping) {
	}

	@Before("controller(classRequestMapping, getMapping)")
	public void advice(JoinPoint jp, RequestMapping classRequestMapping, GetMapping getMapping) {
		JSONify jsonify = new JSONify();
		String json = "";
		json += jsonify.addKey("requestMethod") + jsonify.addValue("GET");
		json += jsonify.addKey("class") + jsonify.addValue(jp.getSignature().getDeclaringTypeName());
		json += jsonify.addKey("method") + jsonify.addEndValue(jp.getSignature().getName());
		logger.info(json);
	}
}