package ru.miinegaliev.springapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.miinegaliev.springapp.models.Assignment;

import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Integer> {
    Optional<Assignment> findBySubjectOrder(String str);
}
