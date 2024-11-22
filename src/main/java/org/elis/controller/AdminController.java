package org.elis.controller;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.dto.TeamDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.NoUserLoggedException;
import org.elis.exception.NotAllowedException;
import org.elis.service.definition.CustomerService;
import org.elis.service.definition.TaskService;
import org.elis.service.definition.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminController {

	@Autowired
	private final CustomerService customerService;

	@Autowired
	private final TaskService taskService;

	@Autowired
	private final TeamService teamService;

	@PostMapping("all/registrazioneAdm")
	@Operation(summary = "registrazione admin", description = "l'admin invia un username ed una password e se supera tutti i controlli verrà inserito nel database")
	public ResponseEntity<RegistrationCustomerDto> registrazioneAdm(@Valid @RequestBody RegistrationCustomerDto json)
			throws EntityIsPresentException, CheckFieldException {
		customerService.insertADMIN(json);
		return ResponseEntity.ok().body(json);
	}

	// CHIAMATE METODI ADMIN PER TEAM

	@PostMapping("admin/creaTeam")
	public ResponseEntity<TeamDto> creaTeam(@Valid @RequestBody TeamDto json, UsernamePasswordAuthenticationToken u)
			throws EntityNotFoundException, CheckFieldException, EntityIsPresentException, NotAllowedException,
			NoUserLoggedException {
		String username = (String) u.getPrincipal();
		CustomerDto cust = customerService.selectByUsername(username);
		if (cust != null) {
			teamService.insert(json, cust);
			return ResponseEntity.ok().body(json);
		} else {
			throw new NoUserLoggedException();
		}
	}

	@GetMapping("admin/showCustomers")
	@Cacheable("customers")
	public ResponseEntity<List<CustomerDto>> selectAllCustomers(UsernamePasswordAuthenticationToken u)
			throws NoUserLoggedException, EntityNotFoundException, CheckFieldException {
		String username = (String) u.getPrincipal();
		CustomerDto cust = customerService.selectByUsername(username);
		if (cust != null) {
			return ResponseEntity.ok().body(customerService.selectAll());
		} else {
			throw new NoUserLoggedException();
		}
	}
	
	
}
