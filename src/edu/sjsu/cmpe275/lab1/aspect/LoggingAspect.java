package edu.sjsu.cmpe275.lab1.aspect;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import edu.sjsu.cmpe275.lab1.exception.UnauthorizedException;

/**
 * This is the Aspect class where all the cross cutting concerns are handled.
 * @author Jenil
 * 
 */
@Aspect
public class LoggingAspect {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	Map<String, Object> storeSecretMap = new LinkedHashMap<String, Object>();
	
	/**
	 * This method executes after store secret method is returned. It logs the created secretId.
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "execution(* *.storeSecret(..))",returning="result")
	public void storeSecretAfterReturning(JoinPoint joinPoint, Object result){
		Object[] arr = joinPoint.getArgs();
		storeSecretMap.put(arr[0].toString(), result);
		log.info(arr[0].toString() + " creates a secret with ID: " + result);	
	}
	
	/**
	 * This method uses Around annotation. It logs before and after the execution of read secret method.
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Around("execution(* *.readSecret(..))")
	public void readSecretAround(ProceedingJoinPoint joinPoint) throws Throwable{
		try{
			Object[] arr = joinPoint.getArgs();	
			log.info(arr[0].toString() + " reads the secret of ID: " + arr[1].toString());
			if(storeSecretMap.get(arr[0].toString()) == null){
				throw new UnauthorizedException();
			}
			if(arr[1].equals(storeSecretMap.get(arr[0].toString()))){
					joinPoint.proceed();
			}
		}
		catch(UnauthorizedException e){
			log.error("An exception " + e + " has been thrown in " + joinPoint.getSignature().getName() + "()" );
		}
	}
	
	/**
	 * This method uses Around annotation. It logs before and after the execution of share secret method.
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Around("execution(* *.shareSecret(..))")
	public void shareSecretLogging(ProceedingJoinPoint joinPoint) throws Throwable{
		try{
			Object[] arr = joinPoint.getArgs();	
			log.info(arr[0].toString() + " shares the secret of ID: " + arr[1].toString() + " with " + arr[2].toString());
			if(storeSecretMap.get(arr[0].toString()) == null){
				throw new UnauthorizedException();
			}
			else if(!(arr[1].equals(storeSecretMap.get(arr[0].toString())))){
				throw new UnauthorizedException();
			}
			else{
				joinPoint.proceed();
				storeSecretMap.put(arr[2].toString(), arr[1]);
			}
		 }
		 catch(UnauthorizedException e){
			log.error("An exception " + e + " has been thrown in " + joinPoint.getSignature().getName() + "()" );
		 }
	} 
	
	/**
	 * This method uses Around annotation. It logs before and after the execution of unshare secret method.
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Around("execution(* *.unshareSecret(..))")
	public void unshareSecretAfter(ProceedingJoinPoint joinPoint) throws Throwable{
		try{
			Object[] arr = joinPoint.getArgs();
			log.info(arr[0].toString() + " unshares the secret of ID: " + arr[1].toString() + " with " + arr[2].toString());
			Set<Entry<String,Object>> mapValues = storeSecretMap.entrySet();
			int mapLength = mapValues.size();
			@SuppressWarnings("unchecked")
			Entry<String, Object>[] array = new Entry[mapLength];
			mapValues.toArray(array);
			
			if(arr[0].equals(array[0].getKey())){
				Iterator<Map.Entry<String,Object>> iter = storeSecretMap.entrySet().iterator();
				while (iter.hasNext()) {
				    Map.Entry<String,Object> entry = iter.next();
				    if(arr[1].equals(entry.getValue())){
				        iter.remove();
				    }
				}
			}
			joinPoint.proceed();
			if(!(arr[0].equals(array[0].getKey()))){
				if(arr[1].equals(storeSecretMap.get(arr[0]))){
				}
				else{
					throw new UnauthorizedException();
				}	
			}
		}
		catch(UnauthorizedException e){
			log.error("An exception " + e + " has been thrown in " + joinPoint.getSignature().getName() + "()" );
		}
	}
}