package next.controller.user;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import next.config.AppConfig;
import next.controller.qna.ApiQuestionControllerTest;
import next.dao.UserDao;
import next.model.User;

import static io.restassured.RestAssured.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ApiUserControllerTest {
	private static final Logger log = LoggerFactory.getLogger(ApiQuestionControllerTest.class);
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void apiUser() throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		User user = new User("userId", "password", "name", "email@a.a");
		String jsonUser = mapper.writeValueAsString(user);
		log.debug("JSONUSER: {}", jsonUser);
		
		User responseUser = given()
			.contentType(ContentType.JSON)
			.body(jsonUser)
		.when()
			.post("/api/users")
		.then()
			.statusCode(200)
			.extract()
			.as(User.class);
		
		log.debug("RESPONSE USER: {}", responseUser);
		assertEquals(user, responseUser);
	}
}
