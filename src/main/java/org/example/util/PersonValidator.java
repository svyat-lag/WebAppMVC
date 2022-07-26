package org.example.util;

import org.example.dao.PersonDAO;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private PersonDAO personDAO;

    public PersonValidator() {
    }

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (!Character.isUpperCase(person.getName().charAt(0)))
            errors.rejectValue("name", "", "The name should starts with a capital letter");

        if (personDAO.show(person.getEmail(), person.getId()).isPresent())
            errors.rejectValue("email", "", "This email is already in use");
    }
}
