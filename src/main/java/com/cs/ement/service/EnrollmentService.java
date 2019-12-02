package com.cs.ement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.ement.domain.Enrollment;
import com.cs.ement.repository.EnrollmentRepository;

@Service
public class EnrollmentService {
	
	@Autowired
	EnrollmentRepository enrollmentRepo;
	
	public void save(Enrollment enrollment) {
		enrollmentRepo.save(enrollment);
	}
	
	public List<Enrollment> findBySubjectCode(String subjectCode) {
		return enrollmentRepo.findBySubjectCode(subjectCode);
	}
	
	public List<Enrollment> findAll() {
		return enrollmentRepo.findAll();
	}
	
	public Optional<Enrollment> findByStudentNumberAndSubjectCode(int studentNumber, String subjectCode) {
		return enrollmentRepo.findByStudentNumberAndSubjectCode(studentNumber, subjectCode);
	}
	
	public List<Enrollment> deleteByStudentNumberAndSubjectCode(int studentNumber, String subjectCode) {
		enrollmentRepo.deleteByStudentNumberAndSubjectCode(studentNumber, subjectCode);
		return this.findAll();
	}
	
	public List<Enrollment> findByStudentNumber(int studentNumber) {
		return enrollmentRepo.findByStudentNumber(studentNumber);
	}

}
