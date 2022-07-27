package org.example.util;

import org.example.models.Person;
import org.example.repositories.PeopleRepository;
import org.example.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
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

        if (peopleService.findOne(person.getId(), person.getEmail()).isPresent())
            errors.rejectValue("email", "", "This email is already in use");
    }
}
