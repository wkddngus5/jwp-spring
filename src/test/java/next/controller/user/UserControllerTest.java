package next.controller.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import next.config.AppConfig;

import static io.restassured.RestAssured.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserControllerTest {
	
	@Test
	public void 회원가입() {
		given()
			.contentType(ContentType.HTML)
			.queryParam("userId", "userId")
			.queryParam("password", "password")
			.queryParam("name", "name")
			.queryParam("email", "email@a.a")
		.when()
			.post("/users")
		.then()
			.statusCode(302);
	}
}
