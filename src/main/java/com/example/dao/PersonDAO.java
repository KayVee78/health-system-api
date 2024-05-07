/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Doctor;
import com.example.model.Patient;
import com.example.model.Person;
import com.example.model.Person;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import jdk.internal.org.jline.terminal.spi.Pty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
public class PersonDAO {

    public static List<Person> persons = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDAO.class);

//    static {
//        persons.add(new Person(1, "Yasas Guruge", "0772572800", "Maharagama"));
//        persons.add(new Person(2, "Kasuntha Perera", "0712359807", "Colombo-04"));
//    }

    public List<Person> getAllPersons() {
        return persons;
    }

    public void deletePerson(int id) {
        persons.removeIf(person -> person.getId() == id);
    }

    public void addPerson(Person person) {
        if ((person.getName() != null && person.getName() instanceof String && !person.getName().isEmpty()) && (person.getAddress() != null && person.getAddress() instanceof String && !person.getAddress().isEmpty()) && (person.getContactInfo() != null && person.getContactInfo() instanceof String && !person.getContactInfo().isEmpty())) {
            int newUserId = 0;
            if (person.getId() == 0) {
                newUserId = getNextUserId();
            } else {
                newUserId = person.getId();
            }
            person.setId(newUserId);
            persons.add(person);
        } else {
            LOGGER.error("Missing mandatory field(s) in person data. Failed to add a new Person!");
            throw new ResourceNotFoundException("Missing mandatory field(s) in person data. Failed to add a new Person!", Status.BAD_REQUEST);
        }

    }

    public void updatePerson(Person updatePerson) {
        PatientDAO patientDAO = new PatientDAO();
        DoctorDAO doctorDAO = new DoctorDAO();
        if ((updatePerson.getName() != null && updatePerson.getName() instanceof String && !updatePerson.getName().isEmpty()) && (updatePerson.getAddress() != null && updatePerson.getAddress() instanceof String && !updatePerson.getAddress().isEmpty()) && (updatePerson.getContactInfo() != null && updatePerson.getContactInfo() instanceof String && !updatePerson.getContactInfo().isEmpty())) {
            for (int i = 0; i < persons.size(); i++) {
                Person person = persons.get(i);
                if (person.getId() == updatePerson.getId()) {
                    persons.set(i, updatePerson);
                    // Update patient data if the person is also a patient
                    Patient updatingPatient = patientDAO.findPatientByPersonId(updatePerson.getId());
                    if (updatingPatient != null) {
                        patientDAO.updatePatientFromPerson(new Patient(updatePerson.getId(), updatePerson.getName(), updatePerson.getContactInfo(), updatePerson.getAddress(), updatingPatient.getPatientId(), updatingPatient.getAge(), updatingPatient.getMedicalHistory(), updatingPatient.getCurrentHealthStatus()));
                        break;
                    }

                    // Update doctor data if the person is also a doctor
                    Doctor updatingDoctor = doctorDAO.findDoctorByPersonId(updatePerson.getId());
                    if (updatingDoctor != null) {
                        doctorDAO.updateDoctorFromPerson(new Doctor(updatePerson.getId(), updatePerson.getName(), updatePerson.getContactInfo(), updatePerson.getAddress(), updatingDoctor.getDoctorId(), updatingDoctor.getSpecialization(), updatingDoctor.getDoctorFee()));
                        break;
                    }
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
            return maxUserId = 1;
        }
    }
}
