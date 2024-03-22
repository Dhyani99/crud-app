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

import com.aquent.crudapp.dao.ClientDao;
import com.aquent.crudapp.dao.PersonDao;
import com.aquent.crudapp.model.Client;
import com.aquent.crudapp.model.Person;
import com.aquent.crudapp.service.ClientService;
import com.aquent.crudapp.service.PersonService;

/**
 * Default implementation of {@link ClientService}.
 */
@Component
public class DefaultClientService implements ClientService {

	private final ClientDao clientDao;
	private final Validator validator;
	private final PersonDao personDao;

	public DefaultClientService(ClientDao clientDao, PersonDao personDao, Validator validator) {
		this.clientDao = clientDao;
		this.personDao = personDao;
		this.validator = validator;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Client> listClients() {
		return clientDao.listClients();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Client readClient(Integer id) {
		return clientDao.readClient(id);
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean readClientByUri(String uri) {
		return clientDao.readClientByUri(uri);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public Integer createClient(Client client) {
		return clientDao.createClient(client);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void updateClient(Client client) {
		clientDao.updateClient(client);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void deleteClient(Integer id) {
		List<Person> people = personDao.listPeopleByClientId(id);
		for(Person person : people) {
			person.setClientId(null);
			personDao.updatePerson(person);
		}
		clientDao.deleteClient(id);
	}

	@Override
	public List<String> validateClient(Client client, boolean isCreate) {
		Set<ConstraintViolation<Client>> violations = validator.validate(client);
		List<String> errors = new ArrayList<String>(violations.size());
		for (ConstraintViolation<Client> violation : violations) {
			errors.add(violation.getMessage());
		}
		if(errors.isEmpty() && isCreate) {
			boolean isExistingClient = clientDao.readClientByUri(client.getUri());
			if (isExistingClient) {
				errors.add("Client already exists.");
			}
		}
		Collections.sort(errors);
		return errors;
	}

}
