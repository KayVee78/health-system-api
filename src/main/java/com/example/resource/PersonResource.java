/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PersonDAO;
import com.example.exception.InternalServerErrorException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Person;
import com.example.result.ResultData;
import com.example.result.ResultWithNoData;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kithmi
 */
@Path("/persons")
public class PersonResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);

    private PersonDAO personDAO = new PersonDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<List<Person>> getAllPersons() {

        try {
            LOGGER.info("Fetching all the persons");

            List<Person> persons = personDAO.getAllPersons();
            LOGGER.info("{} persons fetched", persons.size());

            if (persons.isEmpty()) {
                throw new ResourceNotFoundException("No persons found!", Response.Status.BAD_REQUEST);
            } else {
                return new ResultData<>(persons, "Persons fetched successfully!", Response.Status.OK);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @GET
    @Path("/{personId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultData<Person> getPersonById(@PathParam("personId") int personId) {
        try {
            LOGGER.info("Getting person by id: {}", personId);
            Person person = personDAO.findPersonById(personId);
            if (person != null) {
                LOGGER.info("Person with ID {} fetched successfully!", personId);
                return new ResultData<>(person, "Persons with " + personId + " fetched successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("No person found with ID: " + personId, Response.Status.NOT_FOUND);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData addPerson(Person person) {
        try {
            List<Person> persons = personDAO.getAllPersons();
            int prevPersonListSize = persons.size();
            personDAO.addPerson(person);
            if (persons.size() > prevPersonListSize) {
                LOGGER.info("New person added successfully!");
                return new ResultWithNoData("New person added successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("Failed to add a new person!", Response.Status.NOT_FOUND);
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @PUT
    @Path("/{personId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWithNoData updatePerson(@PathParam("personId") int personId, Person updatePerson) {
        try {
            Person existingPerson = personDAO.findPersonById(personId);

            if (existingPerson != null) {
                updatePerson.setId(personId);
                personDAO.updatePerson(updatePerson);
                LOGGER.info("Person with ID {} updated successfully!", personId);
                return new ResultWithNoData("Person with ID " + personId + " updated successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("No person found with ID: " + personId, Response.Status.NOT_FOUND);
            }
        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{personId}")
    public ResultWithNoData deletePerson(@PathParam("personId") int personId) {
        try {
            Person person = personDAO.findPersonById(personId);
            if (person != null) {
                personDAO.deletePerson(personId);
                LOGGER.info("Person with ID {} removed successfully!", personId);

                return new ResultWithNoData("Person with ID " + personId + " removed successfully!", Response.Status.OK);
            } else {
                throw new ResourceNotFoundException("No person found with ID: " + personId, Response.Status.NOT_FOUND);
            }

        } catch (InternalServerErrorException e) {
            throw new InternalServerErrorException("Internal Server Error occured");
        }
    }

}
