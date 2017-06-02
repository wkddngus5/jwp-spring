package next.interceptor;

import java.nio.charset.Charset;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String auth = request.getHeader("Authorization");
		if(auth == null) {
			return true;
		}
		String base64Credentials = auth.substring("Basic".length()).trim();
		String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
		String[] values = credentials.split(":");
		String userId = values[0];
		String password = values[1];

		log.debug("ID: {}, PASS: {}", userId, password);
		
		User user = userDao.findByUserId(userId);
        if (user == null) {
            return true;
        }
        
        if (user.matchPassword(password)) {
        	request.getSession().setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        }
        
		return true;
	}
}
