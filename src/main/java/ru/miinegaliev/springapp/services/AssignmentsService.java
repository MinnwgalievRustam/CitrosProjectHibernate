package ru.miinegaliev.springapp.services;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.miinegaliev.springapp.models.Assignment;
import ru.miinegaliev.springapp.models.User;
import ru.miinegaliev.springapp.repositories.AssignmentRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AssignmentsService {

    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentsService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> findAll(){
        return assignmentRepository.findAll();
    }

    public Assignment findOne(Integer id) {
        Optional<Assignment> foundAssignment = assignmentRepository.findById(id);
        return foundAssignment.orElse(null);
    }

    @Transactional
    public void save(Assignment assignment){
        assignmentRepository.save(assignment);
    }

    @Transactional
    public void update(Integer id, Assignment assignmentUpdate){
        assignmentUpdate.setId(id);
        assignmentRepository.save(assignmentUpdate);
    }

    @Transactional
    public void delete(Integer id){
        assignmentRepository.deleteById(id);
    }

    public Optional<Assignment> getAssignmentFullInfo(String str){
        return assignmentRepository.findBySubjectOrder(str);
    }

    public List<User> getUsersByAssignmentId(Integer id){
        Optional<Assignment> assignment = assignmentRepository.findById(id);

        if (assignment.isPresent()){
            Hibernate.initialize(assignment.get().getUsers());
            assignment.get().getUsers().forEach(user -> {long diffInMillie = Math.abs(user.getTakenAt().getTime() - new Date().getTime());

                if (diffInMillie > 432000000) {
                    user.setExpired(true); // поручение просрочено более чем на 5 дней
                }
            });
            return assignment.get().getUsers();
        }
        else {
            return Collections.emptyList();
        }
    }

}
