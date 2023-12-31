package br.com.compass.challenge2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.compass.challenge2.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
