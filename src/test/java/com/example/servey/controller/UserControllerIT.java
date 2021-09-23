package com.example.servey.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.servey.SurveyApplication;
import com.example.servey.model.Question;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SurveyApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIT {
	@LocalServerPort
	private int port;
    
	 TestRestTemplate restTemplate = new TestRestTemplate();
     //String output = restTemplate.getForObject( url, String.class);
     HttpHeaders headers = new HttpHeaders();

	 @BeforeEach
	    public void setupJSONAcceptType() {
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    }

	@Test
	void test() {
		String url = "http://localhost:" + port
				+ "/surveys/Survey1/questions/Question1";
          

 		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

 		ResponseEntity<String> response = restTemplate.exchange(url,
 				HttpMethod.GET, entity, String.class);
 		String expected = "{id:Question1,description:Largest Country in the World,correctAnswer:Russia}";

		try {
			JSONAssert.assertEquals(expected, response.getBody(), false);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           		 
         
	}
	@Test
	void addQuestiontest() throws Exception{
		String url = "http://localhost:" + port
				+ "/surveys/Survey1/questions";
		Question  question = new Question("DOENTMATTER",
                "Largest Country in the World", "Russia", Arrays.asList(
                        "India", "Russia", "United States", "China"));

 		HttpEntity<Question> entity = new HttpEntity<Question>(question, headers);

 		ResponseEntity<String> response = restTemplate.exchange(url,
 				HttpMethod.POST, entity, String.class);
 		
 		String actual=response.getHeaders().get(HttpHeaders.LOCATION).get(0);
 		System.out.println(actual);
 		assertTrue(actual.contains("/surveys/Survey1/questions/"));
 		 

 		 
           		 
         
	}
	@Test
	void  retrieveAllQuestions() throws Exception{
		String url = "http://localhost:" + port
				+ "/surveys/Survey1/questions";
          

 		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

 		ResponseEntity<List<Question>> response = restTemplate.exchange(url,
				HttpMethod.GET,  entity,
				new ParameterizedTypeReference<List<Question>>() {
				});

		Question sampleQuestion = new Question("Question1",
				"Largest Country in the World", "Russia", Arrays.asList(
						"India", "Russia", "United States", "China"));

		assertTrue(response.getBody().contains(sampleQuestion));

}
}
