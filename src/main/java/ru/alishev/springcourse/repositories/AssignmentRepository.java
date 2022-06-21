package ru.alishev.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.models.Assignment;

import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Integer> {
    Optional<Assignment> findBySubjectOrder(String str);
}
