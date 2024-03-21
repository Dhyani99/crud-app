package com.aquent.crudapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aquent.crudapp.model.Client;
import com.aquent.crudapp.model.Person;
import com.aquent.crudapp.service.ClientService;
import com.aquent.crudapp.service.PersonService;

/**
 * Controller for handling basic person management operations.
 */
@Controller
@RequestMapping("person")
public class PersonController {

	public static final String COMMAND_DELETE = "Delete";

	private final PersonService personService;
	private final ClientService clientService;

	public PersonController(PersonService personService, ClientService clientService) {
		this.personService = personService;
		this.clientService = clientService;
	}

	/**
	 * Renders the listing page.
	 *
	 * @return list view populated with the current list of people
	 */
	@GetMapping(value = "list")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("person/list");
		List<Person> people = personService.listPeople();

		List<Client> clients = clientService.listClients();

		Map<Integer, String> personClientMap = new HashMap<>();

		for (Person person : people) {
			for (Client client : clients) {
				if (person.getClientId() == client.getClientId()) {
					personClientMap.put(person.getClientId(), client.getClientName());
				}
			}
		}
		mav.addObject("people", people);
		mav.addObject("map", personClientMap);
		return mav;
	}

	/**
	 * Renders an empty form used to create a new person record.
	 *
	 * @return create view populated with an empty person
	 */
	@GetMapping(value = "create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("person/create");
		List<Client> clients = clientService.listClients();
		mav.addObject("person", new Person());
		mav.addObject("clients", clients);
		mav.addObject("errors", new ArrayList<String>());
		return mav;
	}

	/**
	 * Validates and saves a new person. On success, the user is redirected to the
	 * listing page. On failure, the form is redisplayed with the validation errors.
	 *
	 * @param person populated form bean for the person
	 * @return redirect, or create view with errors
	 */
	@PostMapping(value = "create")
	public ModelAndView create(Person person) {
		List<String> errors = personService.validatePerson(person);
		if (errors.isEmpty()) {
			personService.createPerson(person);
			return new ModelAndView("redirect:/person/list");
		} else {
			List<Client> clients = clientService.listClients();
			ModelAndView mav = new ModelAndView("person/create");
			mav.addObject("clients", clients);
			mav.addObject("person", person);
			mav.addObject("errors", errors);
			return mav;
		}
	}

	/**
	 * Renders an edit form for an existing person record.
	 *
	 * @param personId the ID of the person to edit
	 * @return edit view populated from the person record
	 */
	@GetMapping(value = "edit/{personId}")
	public ModelAndView edit(@PathVariable Integer personId) {
		ModelAndView mav = new ModelAndView("person/edit");
		List<Client> clients = clientService.listClients();
		mav.addObject("clients", clients);
		mav.addObject("person", personService.readPerson(personId));
		mav.addObject("errors", new ArrayList<String>());
		return mav;
	}

	/**
	 * Validates and saves an edited person. On success, the user is redirected to
	 * the listing page. On failure, the form is redisplayed with the validation
	 * errors.
	 *
	 * @param person populated form bean for the person
	 * @return redirect, or edit view with errors
	 */
	@PostMapping(value = "edit")
	public ModelAndView edit(Person person) {
		List<String> errors = personService.validatePerson(person);
		if (errors.isEmpty()) {
			personService.updatePerson(person);
			return new ModelAndView("redirect:/person/list");
		} else {
			ModelAndView mav = new ModelAndView("person/edit");
			mav.addObject("person", person);
			mav.addObject("errors", errors);
			return mav;
		}
	}

	/**
	 * Renders the deletion confirmation page.
	 *
	 * @param personId the ID of the person to be deleted
	 * @return delete view populated from the person record
	 */
	@GetMapping(value = "delete/{personId}")
	public ModelAndView delete(@PathVariable Integer personId) {
		ModelAndView mav = new ModelAndView("person/delete");
		mav.addObject("person", personService.readPerson(personId));
		return mav;
	}

	/**
	 * Handles person deletion or cancellation, redirecting to the listing page in
	 * either case.
	 *
	 * @param command  the command field from the form
	 * @param personId the ID of the person to be deleted
	 * @return redirect to the listing page
	 */
	@PostMapping(value = "delete")
	public String delete(@RequestParam String command, @RequestParam Integer personId) {
		if (COMMAND_DELETE.equals(command)) {
			personService.deletePerson(personId);
		}
		return "redirect:/person/list";
	}

	/**
	 * Renders the people list page after removing any person.
	 *
	 * @param personId the ID of the person to be deleted
	 * @return redirect to people list view
	 */
	@GetMapping(value = "removePersonFromClient/{personId}")
	public String removePersonFromClient(@PathVariable Integer personId) {

		Person person = personService.readPerson(personId);
		Integer clientId = person.getClientId();
		person.setClientId(null);
		personService.updatePerson(person);

		return "redirect:/client/peopleList/" + clientId;
	}

	/**
	 * Renders a form used to add a person record for current client.
	 *
	 * @return create view with a list of people to select from
	 */
	@GetMapping(value = "add/{clientId}")
	public ModelAndView addPerson(@PathVariable Integer clientId) {
		ModelAndView mav = new ModelAndView("client/addPerson");
		List<Person> people = personService.listPeopleByNotHavingClientId(clientId);
		mav.addObject("clientId", clientId);
		mav.addObject("person", new Integer(0));
		mav.addObject("people", people);
		return mav;
	}

	@PostMapping(value = "addPersonForClient")
	public String addPersonForClient(@RequestParam(value = "personId") Integer personId, @RequestParam Integer clientId) {
		Person person = personService.readPerson(personId);
		person.setClientId(clientId);
		personService.updatePerson(person);
		return "redirect:/client/peopleList/" + clientId;

	}

}
