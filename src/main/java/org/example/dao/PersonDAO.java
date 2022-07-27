package org.example.dao;

import org.example.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> show(String email, int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                "select p from Person p where p.email=:Email and not p.id=:ID", Person.class)
                .setParameter("Email", email)
                .setParameter("ID", id)
                .stream()
                .findAny();
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();

        Person person = session.get(Person.class, id);

        person.setName(updatedPerson.getName());
        person.setAge(updatedPerson.getAge());
        person.setEmail(updatedPerson.getEmail());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }
}
