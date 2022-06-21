package ru.miinegaliev.springapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.miinegaliev.springapp.models.Assignment;
import ru.miinegaliev.springapp.services.AssignmentsService;

@Component
public class AssignmentValidator implements Validator {

    private final AssignmentsService assignmentsService;

    @Autowired
    public AssignmentValidator(AssignmentsService assignmentsService) {
        this.assignmentsService = assignmentsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Assignment.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Assignment assignment = (Assignment) o;
        if (assignmentsService.getAssignmentFullInfo(assignment.getTextOrder()).isPresent())
            errors.rejectValue("textOrder","","Поручение с таким содержанием существует");
    }
}
