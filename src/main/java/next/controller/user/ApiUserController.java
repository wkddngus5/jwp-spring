package next.controller.user;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import next.dao.UserDao;
import next.model.User;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public User create(@Valid @RequestBody User user, BindingResult result) throws Exception {
    	if (result.hasErrors()) {
    		return null;
    	}
    	
        log.debug("User : {}", user);
        userDao.insert(user);
		return userDao.findByUserId(user.getUserId());
	}
}
