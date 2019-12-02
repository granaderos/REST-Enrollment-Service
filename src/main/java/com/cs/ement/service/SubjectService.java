package com.cs.ement.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cs.ement.domain.Subject;
import com.cs.ement.repository.SubjectRepository;

@Service
public class SubjectService {
	@Autowired
	SubjectRepository subjectRepo;
	
	public void save(Subject subject) {
		subjectRepo.save(subject);
	}

	public Optional<Subject> findBySubjectCode(String subjectCode) {
		return subjectRepo.findBySubjectCode(subjectCode);
	}
	
	public List<Subject> findAll() {
		return subjectRepo.findAll();
	}
	
	public List<Subject> findByStatus(int status) {
		return subjectRepo.findByStatus(status);
	}
}
