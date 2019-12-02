package com.cs.ement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cs.ement.domain.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	List<Enrollment> findBySubjectCode(String subjectCode);
	Optional<Enrollment> findByStudentNumberAndSubjectCode(int studentNumber, String subjectCode);
	void deleteByStudentNumberAndSubjectCode(int studentNumber, String subjectCode);
	List<Enrollment> findByStudentNumber(int studentNumber);
	
	
}
