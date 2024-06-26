package com.aquent.crudapp.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aquent.crudapp.dao.PersonDao;
import com.aquent.crudapp.model.Person;
import com.aquent.crudapp.service.PersonService;

/**
 * Default implementation of {@link PersonService}.
 */
@Component
public class DefaultPersonService implements PersonService {

	private final PersonDao personDao;
	private final Validator validator;

	public DefaultPersonService(PersonDao personDao, Validator validator) {
		this.personDao = personDao;
		this.validator = validator;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Person> listPeople() {
		return personDao.listPeople();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Person> listPeopleByClientId(Integer clientId) {
		return personDao.listPeopleByClientId(clientId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Person> listPeopleByNotHavingClientId(Integer clientId) {
		return personDao.listPeopleByNotHavingClientId(clientId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Person readPerson(Integer id) {
		return personDao.readPerson(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean readPersonByEmail(String email) {
		return personDao.readPersonByEmail(email);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public Integer createPerson(Person person) {
		return personDao.createPerson(person);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void updatePerson(Person person) {
		personDao.updatePerson(person);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void deletePerson(Integer id) {
		personDao.deletePerson(id);
	}

	@Override
	public List<String> validatePerson(Person person, boolean isCreate) {
		Set<ConstraintViolation<Person>> violations = validator.validate(person);
		List<String> errors = new ArrayList<String>(violations.size());
		
		for (ConstraintViolation<Person> violation : violations) {
			errors.add(violation.getMessage());
		}
		
		if(errors.isEmpty() && isCreate) {
			boolean isExistingPerson = personDao.readPersonByEmail(person.getEmailAddress());
			if (isExistingPerson) {
				errors.add("Person already exists.");
			}
		}

		Collections.sort(errors);
		return errors;
	}
}
