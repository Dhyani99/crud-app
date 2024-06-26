package com.aquent.crudapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aquent.crudapp.model.Person;

/**
 * Person operations.
 */
@Service
public interface PersonService {

	/**
	 * Retrieves all of the person records.
	 *
	 * @return list of person records
	 */
	List<Person> listPeople();

	/**
	 * Creates a new person record.
	 *
	 * @param person the values to save
	 * @return the new person ID
	 */
	Integer createPerson(Person person);

	/**
	 * Retrieves a person record by ID.
	 *
	 * @param id the person ID
	 * @return the person record
	 */
	Person readPerson(Integer id);

	/**
	 * Updates an existing person record.
	 *
	 * @param person the new values to save
	 */
	void updatePerson(Person person);

	/**
	 * Deletes a person record by ID.
	 *
	 * @param id the person ID
	 */
	void deletePerson(Integer id);

	/**
	 * Validates populated person data.
	 *
	 * @param person   the values to validate
	 * @param isCreate the value to check if it is edit or create request
	 * 
	 * @return list of error messages
	 */
	List<String> validatePerson(Person person, boolean isCreate);

	/**
	 * Retrieves all person records by client ID.
	 *
	 * @param id the client ID
	 * @return list of person records
	 */
	List<Person> listPeopleByClientId(Integer clientId);

	/**
	 * Retrieves all person records that do not match the current client ID.
	 *
	 * @param clientId the client ID
	 * @return list of person records
	 */
	List<Person> listPeopleByNotHavingClientId(Integer clientId);

	/**
	 * Returns true if person record with the email exists 
	 * otherwise returns false.
	 *
	 * @param string the email
	 * @return boolean value
	 */
	boolean readPersonByEmail(String email);
}
