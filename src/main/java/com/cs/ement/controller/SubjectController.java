package com.cs.ement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.ement.domain.Enrollment;
import com.cs.ement.domain.Subject;
import com.cs.ement.service.EnrollmentService;
import com.cs.ement.service.SubjectService;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
	
	@Autowired
	SubjectService subjectService;
	
	@Autowired
	EnrollmentService enrollmentService;
	
	@GetMapping
	public ResponseEntity<List<Subject>> getSubjects() {
		List<Subject> subjects =  subjectService.findAll();
		if(subjects.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(subjects);
		}
		return ResponseEntity.status(HttpStatus.OK).body(subjects);	
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<Subject> addSubject(@RequestBody Subject subject) {
		subject.setStatus(1);
		subjectService.save(subject);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(subject);
	}
	
	@PutMapping("/edit/{subjectCode}")
	public ResponseEntity<Object> updateSubject(@RequestBody Subject updatedSubject, @PathVariable String subjectCode) {
		Optional<Subject> subject = subjectService.findBySubjectCode(subjectCode);
		
		if(!subject.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updatedSubject);
		if(updatedSubject.getStatus() == 0) {
			// check if there are enrolled students;
			List<Enrollment> enrollments = enrollmentService.findBySubjectCode(subjectCode);
			if(!enrollments.isEmpty())
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot deactivate subject; students are still enrolled.");
			
		}
		subjectService.save(updatedSubject);
		return ResponseEntity.status(HttpStatus.OK).body(updatedSubject);
	}
	
	@GetMapping("/{status}")
	public ResponseEntity<Object> getSubjectsByStatus(@PathVariable String status) {
		List<Subject> subjects;
		
		if(status.equals("active"))
			subjects = subjectService.findByStatus(1);
		else if(status.equals("inactive"))
			subjects = subjectService.findByStatus(0);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid path variable value.");
		
		if(subjects.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No " + status + " subjects found.");
		
		return ResponseEntity.status(HttpStatus.OK).body(subjects);
	}
	
}
