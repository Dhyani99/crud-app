package com.aquent.crudapp.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aquent.crudapp.dao.ClientDao;
import com.aquent.crudapp.dao.PersonDao;
import com.aquent.crudapp.model.Client;
import com.aquent.crudapp.model.Person;

/**
 * Spring JDBC implementation of {@link ClientDao}.
 */
@Component
public class JdbcClientDao implements ClientDao {

	private static final String SQL_LIST_CLIENTS = "SELECT * FROM client ORDER BY client_name, uri, client_id";
	private static final String SQL_READ_CLIENT = "SELECT * FROM client WHERE client_id = :clientId";
	private static final String SQL_DELETE_CLIENT = "DELETE FROM client WHERE client_id = :clientId";
	private static final String SQL_UPDATE_CLIENT = "UPDATE client SET (client_name, uri, phone_number, street_address, city, state, zip_code)"
			+ " = (:clientName, :uri, :phoneNumber, :streetAddress, :city, :state, :zipCode)"
			+ " WHERE client_id = :clientId";
	private static final String SQL_CREATE_CLIENT = "INSERT INTO client (client_name, uri, phone_number, street_address, city, state, zip_code)"
			+ " VALUES (:clientName, :uri, :phoneNumber, :streetAddress, :city, :state, :zipCode)";
	private static final String SQL_READ_CLIENT_BY_URI = "SELECT * FROM client WHERE uri = :uri";

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcClientDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Client> listClients() {
		return namedParameterJdbcTemplate.getJdbcOperations().query(SQL_LIST_CLIENTS, new ClientRowMapper());
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Client readClient(Integer clientId) {
		return namedParameterJdbcTemplate.queryForObject(SQL_READ_CLIENT,
				Collections.singletonMap("clientId", clientId), new ClientRowMapper());
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean readClientByUri(String uri) {
		Client client;
		try {
			client = namedParameterJdbcTemplate.queryForObject(SQL_READ_CLIENT_BY_URI,
					Collections.singletonMap("uri", uri), new ClientRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Integer createClient(Client client) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(SQL_CREATE_CLIENT, new BeanPropertySqlParameterSource(client), keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void updateClient(Client client) {
		namedParameterJdbcTemplate.update(SQL_UPDATE_CLIENT, new BeanPropertySqlParameterSource(client));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void deleteClient(Integer clientId) {
		namedParameterJdbcTemplate.update(SQL_DELETE_CLIENT, Collections.singletonMap("clientId", clientId));
	}

	/**
	 * Row mapper for client records.
	 */
	private static final class ClientRowMapper implements RowMapper<Client> {

		@Override
		public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
			Client client = new Client();
			client.setClientId(rs.getInt("client_id"));
			client.setClientName(rs.getString("client_name"));
			client.setUri(rs.getString("uri"));
			client.setPhoneNumber(rs.getString("phone_number"));
			client.setStreetAddress(rs.getString("street_address"));
			client.setCity(rs.getString("city"));
			client.setState(rs.getString("state"));
			client.setZipCode(rs.getString("zip_code"));
			return client;
		}
	}

}
