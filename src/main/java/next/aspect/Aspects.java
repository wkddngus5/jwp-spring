package next.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class Aspects {
	private static final Logger log = LoggerFactory.getLogger(Aspects.class);
	
	@Pointcut("within(next.controller..*) || within(next.dao..*) || within(next.service..*)")
	public void myPointcut() {
		
	}
	
	@Around("myPointcut()")
	public Object profiling(ProceedingJoinPoint pjp) throws Throwable {
		log.debug("excution class: {}", pjp.getTarget());
		
		StopWatch watch = new StopWatch();
		watch.start();
		Object ret = pjp.proceed();
		watch.stop();
		log.debug("excution time: {}", watch.getTotalTimeMillis());
		
		return ret;
	}
	
	@Around("myPointcut()")
	public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {

	  final String methodName = pjp.getSignature().getName();
	  log.debug("{}(): {}", methodName, pjp.getArgs());

	  Object result = pjp.proceed();
	  log.debug("{}(): result={}", methodName, result);

	  return result;
	}
}
