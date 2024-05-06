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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class PersonDAO {

    //static list to store person objects
    public static List<Person> persons = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDAO.class);

    static {
        persons.add(new Person(1, "Yasas Guruge", "0772572800", "Maharagama"));
        persons.add(new Person(2, "Kasuntha Perera", "0712359807", "Colombo-04"));
    }

    //Method to retrieve all the persons inserted to the list
    public List<Person> getAllPersons() {
        return persons;
    }

    //Method to delete the person on provided id
    public void deletePerson(int id) {
        persons.removeIf(person -> person.getId() == id);
    }

    //Method to add a new person record to the list
    public void addPerson(Person person) {
        if ((person.getName() != null && person.getName() instanceof String && !person.getName().isEmpty()) && (person.getAddress() != null && person.getAddress() instanceof String && !person.getAddress().isEmpty()) && (person.getContactInfo() != null && person.getContactInfo() instanceof String && !person.getContactInfo().isEmpty())) {
            int newUserId = getNextUserId();
            person.setId(newUserId);
            persons.add(person);
        } else {
            LOGGER.error("Missing mandatory field(s) in person data. Failed to add a new Person!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in person data. Failed to add a new Person!", Status.BAD_REQUEST);
        }

    }

    //Method to update an existing person object on provided id
    public void updatePerson(Person updatePerson) {
        if ((updatePerson.getName() != null && updatePerson.getName() instanceof String && !updatePerson.getName().isEmpty()) && (updatePerson.getAddress() != null && updatePerson.getAddress() instanceof String && !updatePerson.getAddress().isEmpty()) && (updatePerson.getContactInfo() != null && updatePerson.getContactInfo() instanceof String && !updatePerson.getContactInfo().isEmpty())) {
            for (int i = 0; i < persons.size(); i++) {
                Person person = persons.get(i);
                if (person.getId() == updatePerson.getId()) {
                    persons.set(i, updatePerson);
                    return;
                }
            }
        } else {
            LOGGER.error("Missing mandatory field(s) in person data. Failed to update Person!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in person data. Failed to update Person!", Response.Status.BAD_REQUEST);
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
        int maxUserId = 0;
        if (persons.size() > 0) {
            maxUserId = Integer.MIN_VALUE;

            for (Person person : persons) {
                int userId = person.getId();
                if (userId > maxUserId) {
                    maxUserId = userId;
                }
            }
            return maxUserId + 1;
        } else {
            maxUserId = 1;
        }
        return maxUserId;
    }
}
