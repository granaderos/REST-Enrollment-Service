package com.cs.ement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.ement.domain.Student;
import com.cs.ement.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	StudentRepository studentRepo;
	
	public void save(Student student) {
		studentRepo.save(student);
	}
	
	public Optional<Student> findByStudentNumber(int studentNumber) {
		return studentRepo.findByStudentNumber(studentNumber);
	}
	
	public List<Student> findAll() {
		return studentRepo.findAll();
	}
	
}
