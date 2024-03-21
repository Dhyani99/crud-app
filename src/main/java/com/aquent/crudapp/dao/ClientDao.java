package com.aquent.crudapp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aquent.crudapp.model.Client;

@Repository
public interface ClientDao {

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
	Client readClient(Integer id);

	 /**
     * Retrieves a client record by ID.
     *
     * @param id the client ID
     * @return the client record
     */
	Integer createClient(Client client);

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

}
