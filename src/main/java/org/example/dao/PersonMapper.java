package org.example.dao;

import org.example.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setId(rs.getInt("ID"));
        person.setName(rs.getString("Name"));
        person.setAge(rs.getInt("Age"));
        person.setEmail(rs.getString("Email"));

        return person;
    }
}
