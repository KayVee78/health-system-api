/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Person;
import com.example.model.Person;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class PersonDAO {

    public static List<Person> persons = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDAO.class);

    static {
        persons.add(new Person(1, "Yasas Guruge", "0772572800", "Maharagama"));
        persons.add(new Person(2, "Kasuntha Perera", "0712359807", "Colombo-04"));
    }

    public List<Person> getAllPersons() {
        return persons;
    }

    public void deletePerson(int id) {
        persons.removeIf(person -> person.getId() == id);
    }

    public void addPerson(Person person) {
        if (person.getName() != null && person.getAddress() != null && person.getContactInfo() != null) {
            int newUserId = getNextUserId();
            person.setId(newUserId);
            persons.add(person);
        } else {
            LOGGER.error("Missing mandatory field(s) in person data. Failed to add a new Person!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in person data. Failed to add a new Person!");
        }

    }

    public void updatePerson(Person updatePerson) {
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            if (person.getId() == updatePerson.getId()) {
                persons.set(i, updatePerson);
                return;
            }
        }
    }

    //HELPER METHODS
    public Person findPersonById(int id) {
        Person personFound = null;
        for (Person person : persons) {
            if (person.getId() == id) {
                personFound = person;
                break;
            }
        }
        return personFound;
    }

    public int getNextUserId() {
        int maxUserId = Integer.MIN_VALUE;

        for (Person person : persons) {
            int userId = person.getId();
            if (userId > maxUserId) {
                maxUserId = userId;
            }
        }

        return maxUserId + 1;
    }
}
