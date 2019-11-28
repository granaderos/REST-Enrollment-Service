package com.cs.ement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cs.ement.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
