package org.example.services;

import org.example.models.Person;
import org.example.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    public Optional<Person> findOne(int id, String email) {
        return peopleRepository.findByEmailAndIdNotLike(email, id);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person personToUpdate) {
        personToUpdate.setId(id);
        peopleRepository.save(personToUpdate);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
