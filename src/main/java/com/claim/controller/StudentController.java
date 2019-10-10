package com.claim.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.claim.entity.Recipe;
import com.claim.entity.Student;
import com.claim.repository.RecipeRepository;
import com.claim.repository.StudentRepository;



@RestController
@CrossOrigin // only do this in Claim
public class StudentController {

	@Autowired // used to inject dependencies
	private StudentRepository studentRepository;

	@RequestMapping(value = "/submitStudentDetails",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	// you can also use as a quicker cleaner route
	// @PostMapping(value="/submitStudentDetails")
	public void submitStudentDetails(@RequestBody Student student) {
		this.studentRepository.save(student);
		
		System.out.println(student);
	}
	
	@GetMapping(value="/findStudentById")
	@ResponseBody 
	private ResponseEntity<Student>findStudent(String email){
		Student student = this.studentRepository.findById(email).get();
		return new ResponseEntity<>(student,HttpStatus.OK);
	}
	
	@PostMapping(value="/login")
	@ResponseBody
	private ResponseEntity<Student>login(@RequestBody Student student) {
		Optional<Student> databaseStudent = this.studentRepository.findById(student.getEmail());
		if(!databaseStudent.isPresent()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		else if(student.getPassword().equals(databaseStudent.get().getPassword())){
			return new ResponseEntity<>(databaseStudent.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	@GetMapping(value="/showStudents")
	@ResponseBody
	private ResponseEntity<List<Student>>showStudents(){
		List<Student> students = this.studentRepository.findAll();
		return new ResponseEntity<>(students, HttpStatus.OK);
		
	}
	
	
}
