package org.example.dao;

import org.example.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private static List<Person> people = new ArrayList<>();

    private static Connection dbConnection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://svyatptm.beget.tech:3306/svyatptm_test",
                    "svyatptm_test", "Qwerty@2002");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> index() {
        try {
            Statement statement = dbConnection.createStatement();
            String sql = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(sql);

            people.clear();
            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("ID"));
                person.setName(resultSet.getString("Name"));
                person.setAge(resultSet.getInt("Age"));
                person.setEmail(resultSet.getString("Email"));

                people.add(person);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people;
    }

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM Person WHERE ID=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            person = new Person();
            person.setId(id);
            person.setName(resultSet.getString("Name"));
            person.setAge(resultSet.getInt("Age"));
            person.setEmail(resultSet.getString("Email"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return person;
    }

    public void save(Person person) {
        try {
            PreparedStatement statement =
                    dbConnection.prepareStatement("INSERT INTO Person (Name, Age, Email) VALUES (?, ?, ?)");

            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement statement = dbConnection.prepareStatement(
                    "UPDATE Person SET Name=?, Age=?, Email=? WHERE ID=?");

            statement.setString(1, updatedPerson.getName());
            statement.setInt(2, updatedPerson.getAge());
            statement.setString(3, updatedPerson.getEmail());
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        PreparedStatement statement = null;
        try {
            statement = dbConnection.prepareStatement("DELETE FROM Person WHERE ID=?");

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
