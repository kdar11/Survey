package com.example.servey.controller;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.servey.model.Question;
import com.example.servey.model.Survey;
import com.example.servey.service.SurveyService;
 

@RestController
public class UserController {
	@Autowired
	private SurveyService surveyService;

	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveQuestions(@PathVariable String surveyId) {
		return surveyService.retrieveQuestions(surveyId);
	}
	
	@GetMapping(path = "/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveQuestion(@PathVariable String surveyId,
            @PathVariable String questionId) {
        return surveyService.retrieveQuestion(surveyId, questionId);
    }
	@GetMapping(path = "/surveys")
    public List<Survey> retrieveSurveys() {
        return surveyService.retrieveAllSurveys();
    }
	
	@PostMapping(path = "/surveys")
	ResponseEntity<?> add(
            @RequestBody Survey survey) {

         Survey createdTodo = surveyService.addSurvey(survey);

        if (createdTodo == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();

        return ResponseEntity.created(location).build();

    }
    
	
	@PostMapping("/surveys/{surveyId}/questions")
    ResponseEntity<?> add(@PathVariable String surveyId,
            @RequestBody Question question) {

        Question createdTodo = surveyService.addQuestion(surveyId, question);

        if (createdTodo == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();

        return ResponseEntity.created(location).build();

    }
}
