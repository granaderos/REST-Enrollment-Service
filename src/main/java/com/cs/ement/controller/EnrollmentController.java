package com.cs.ement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.ement.domain.Enrollment;
import com.cs.ement.domain.Student;
import com.cs.ement.domain.Subject;
import com.cs.ement.service.EnrollmentService;
import com.cs.ement.service.StudentService;
import com.cs.ement.service.SubjectService;

@RestController
@RequestMapping("enrollments")
public class EnrollmentController {
	
	@Autowired
	EnrollmentService enrollmentService;
	@Autowired
	StudentService studentService;
	@Autowired
	SubjectService subjectService;
	
	@GetMapping
	public ResponseEntity<Object> getEnrollments() {
		List<Enrollment> enrollments = enrollmentService.findAll();
		
		if(enrollments.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No enrollments found.");
		
		return ResponseEntity.status(HttpStatus.OK).body(enrollments);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Object> addEnrollment(@RequestBody Enrollment enrollment) {
		Optional<Subject> subject = subjectService.findBySubjectCode(enrollment.getSubjectCode());
		if(!subject.isPresent()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject code not found.");
		Optional<Student> student = studentService.findByStudentNumber(enrollment.getStudentNumber());
		if(!student.isPresent()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student number not found.");
		if(subject.get().getStatus() == 0) 
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot enroll to an inactive subject.");
		
		Optional<Enrollment> existingEnrollment = enrollmentService.findByStudentNumberAndSubjectCode(enrollment.getStudentNumber(), enrollment.getSubjectCode());
		if(existingEnrollment.isPresent())
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are already enrolled to this subject.");
		
		enrollmentService.save(enrollment);
		return ResponseEntity.status(HttpStatus.OK).body(enrollment);
	}
	
	@Transactional
	@DeleteMapping("/drop")
	public ResponseEntity<Object> dropSubject(@RequestBody Enrollment enrollment) {
		Optional<Subject> subject = subjectService.findBySubjectCode(enrollment.getSubjectCode());
		if(!subject.isPresent()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject code not found.");
		Optional<Student> student = studentService.findByStudentNumber(enrollment.getStudentNumber());
		if(!student.isPresent()) 
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student number not found.");
		
		Optional<Enrollment> existingEnrollment = enrollmentService.findByStudentNumberAndSubjectCode(enrollment.getStudentNumber(), enrollment.getSubjectCode());
		if(!existingEnrollment.isPresent())
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not enrolled to this subject.");
		
		enrollmentService.deleteByStudentNumberAndSubjectCode(enrollment.getStudentNumber(), enrollment.getSubjectCode());
		return ResponseEntity.status(HttpStatus.OK).body(enrollmentService.findAll());
	}
	
	@GetMapping("/{studentNumber}")
	public ResponseEntity<Object> getEnrolledSubjects(@PathVariable int studentNumber) {
		List<Enrollment> enrollments = enrollmentService.findByStudentNumber(studentNumber);
		List<Subject> subjects = new ArrayList<Subject>();
		for(Enrollment ement : enrollments) {
			subjects.add(subjectService.findBySubjectCode(ement.getSubjectCode()).get());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(subjects);
	}
}
