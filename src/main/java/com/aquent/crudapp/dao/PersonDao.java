package com.aquent.crudapp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aquent.crudapp.model.Person;

/**
 * Operations on the "person" table.
 */
@Repository
public interface PersonDao {

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
	 * @param email the email
	 * @return boolean value
	 */
	boolean readPersonByEmail(String email);

}
