package next.controller.qna;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.restassured.http.ContentType;
import next.config.AppConfig;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ApiQuestionControllerTest {
	private static final Logger log = LoggerFactory.getLogger(ApiQuestionControllerTest.class);
	
	@Autowired
	QuestionDao questionDao;
	
	@Test
	public void getUser() {
		Question question = new Question("writer", "title", "contents");
		Question insertedQuestion = questionDao.insert(question);
		
		Question actual = 
		given()
			.auth().preemptive().basic("admin", "password")
			.contentType(ContentType.JSON)
		.when()
			.get("/api/questions/" + insertedQuestion.getQuestionId())
		.then()
			.statusCode(200)
			.extract()
			.as(Question.class);
		
		assertEquals(insertedQuestion, actual);
	}
	
	@Test
	public void deleteTest() {
		Question question = new Question("admin", "title", "contents");
		Question insertedQuestion = questionDao.insert(question);
		long questionId = insertedQuestion.getQuestionId();
		
		Result result = given()
			.auth().preemptive().basic("admin", "password")
			.contentType(ContentType.JSON)
		.when()
			.delete("/api/questions/"+ questionId)
		.then()
			.statusCode(200)
			.extract()
			.as(Result.class);
		
		log.debug("RESULT: {}", result);
		
		assertTrue(result.isStatus());
	}

}
