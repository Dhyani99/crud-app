package com.aquent.crudapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aquent.crudapp.model.Client;

@Service
public interface ClientService {

	/**
	 * Retrieves all of the client records.
	 *
	 * @return list of client records
	 */
	List<Client> listClients();

	/**
	 * Creates a new client record.
	 *
	 * @param client the values to save
	 * @return the new client ID
	 */
	Integer createClient(Client client);

	/**
	 * Retrieves a client record by ID.
	 *
	 * @param id the client ID
	 * @return the client record
	 */
	Client readClient(Integer id);

	/**
	 * Updates an existing client record.
	 *
	 * @param client the new values to save
	 */
	void updateClient(Client client);

	/**
	 * Deletes a client record by ID.
	 *
	 * @param id the client ID
	 */
	void deleteClient(Integer id);

	/**
	 * Validates populated client data.
	 *
	 * @param client the values to validate
	 * @param isCreate the value to check whether it is create or edit
	 * 
	 * @return list of error messages
	 */
	List<String> validateClient(Client client, boolean isCreate);

	/**
	 * Returns true if client record with the uri exists 
	 * otherwise returns false.
	 *
	 * @param string the uri
	 * @return boolean value
	 */
	boolean readClientByUri(String uri);
}
