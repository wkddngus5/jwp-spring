package next.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class ProfilingInterceptor extends HandlerInterceptorAdapter{
	private static final String START_TIME = "startTime";
	private static final Logger log = LoggerFactory.getLogger(ProfilingInterceptor.class);
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute(START_TIME, System.currentTimeMillis());
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long startTime = (long)request.getAttribute(START_TIME);
		log.debug("performance: {}", System.currentTimeMillis() - startTime);
		
	}
}
