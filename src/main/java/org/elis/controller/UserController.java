package org.elis.controller;

import java.util.List;

import org.elis.dto.ActiveTaskDto;
import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.dto.TaskDto;
import org.elis.dto.TeamDto;
import org.elis.dto.UpdatePasswordDto;
import org.elis.dto.UpdateUsernameDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityIsPresentException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.NoUserLoggedException;
import org.elis.exception.NotAllowedException;
import org.elis.exception.PasswordNotCorrectException;
import org.elis.exception.TaskNonAttivaException;
import org.elis.exception.UsingOldPswException;
import org.elis.service.definition.CustomerService;
import org.elis.service.definition.TaskService;
import org.elis.service.definition.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Api", description = "operazioni effettuabili dall'utente di base") // Descrizione Docuentazione Api
public class UserController {

	// SERVIZI

	@Autowired
	private final CustomerService customerService;

	@Autowired
	private final TaskService taskService;

	@Autowired
	private final TeamService teamService;

	// CHIAMATE METODI PER UTENTE BASE SENZA AUTENTICAZIONE

	@PostMapping("all/registrazione") // Mappatura url della chiamata
	@Operation(summary = "registrazione utente", description = "l'utente invia un username ed una password e se supera tutti i controlli verrà inserito nel database") // Descrizione
																																										// operazione
																																										// Api
	public ResponseEntity<RegistrationCustomerDto> registrazione(@Valid @RequestBody RegistrationCustomerDto json)
			throws EntityIsPresentException, CheckFieldException {
		customerService.insert(json);
		return ResponseEntity.ok().body(json);
	}

	@GetMapping("all/login")
	@Operation(summary = "login utente", description = "l'utente invia un username ed una password e se supera tutti i controlli verrà autenticato e gli verrà assegnato un token")
	public ResponseEntity<String> login(@Valid @RequestBody LoginCustomerDto json)
			throws EntityIsPresentException, CheckFieldException, EntityNotFoundException, PasswordNotCorrectException {
		String token = customerService.login(json);
		return ResponseEntity.ok().header("Authorization", token).body("Login effettuato");

	}

	// CHIAMATE METODI UTENTE BASE CHE HA EFFETTUATO L'ACCESSO

	@PutMapping("/base/updateUsername")
	@Operation(summary = "modifica username utente", description = "l'utente invia un username e dopo i controlli viene modificato")
	public ResponseEntity<LoginCustomerDto> updateUsername(@Valid @RequestBody UpdateUsernameDto usernameDto,
			UsernamePasswordAuthenticationToken u) throws EntityNotFoundException, CheckFieldException,
			NoUserLoggedException, PasswordNotCorrectException, EntityIsPresentException {
		String currentUsername = (String) u.getPrincipal();
		LoginCustomerDto user = customerService.selectLogCustomerByUsername(currentUsername);

		if (user != null) {
			customerService.updateUsername(user, usernameDto);
			return ResponseEntity.ok().body(user);
		} else {
			throw new NoUserLoggedException();
		}
	}

	@PutMapping("/base/updatePassword")
	@Operation(summary = "modifica password utente", description = "l'utente invia un username e dopo i controlli viene prima criptata e poi modificata")

	public ResponseEntity<CustomerDto> updatePassword(@Valid @RequestBody UpdatePasswordDto password,
			UsernamePasswordAuthenticationToken u) throws CheckFieldException, EntityNotFoundException,
			PasswordNotCorrectException, NoUserLoggedException, UsingOldPswException {
		String username = (String) u.getPrincipal();
		CustomerDto user = customerService.selectByUsername(username);
		if (user != null) {
			customerService.updatePassword(user, password);
			return ResponseEntity.ok().body(user);
		} else {
			throw new NoUserLoggedException();
		}

	}

	// CHIAMATE METODI UTENTE BASE PER TASK

	@PostMapping("base/creaTask")
	@CacheEvict("personalTasks")
	@Operation(summary = "crea task", description = "l'utente crea una task e ed essa viene assegnata solo a l'utente che la creata")
	public ResponseEntity<TaskDto> creaTask(@Valid @RequestBody TaskDto json, UsernamePasswordAuthenticationToken u)
			throws CheckFieldException, EntityNotFoundException, NoUserLoggedException {
		String username = (String) u.getPrincipal();
		CustomerDto creator = customerService.selectByUsername(username);
		if (creator != null) {
			json.setCreator(creator);
			json.setActiveCustomers(List.of(creator));
			taskService.insert(json);
			return ResponseEntity.ok().body(json);
		} else {
			throw new NoUserLoggedException();
		}
	}

	@PutMapping("base/attivaTask")
	@CacheEvict("tasks")
	public ResponseEntity<TaskDto> attivaTask(@Valid @RequestBody ActiveTaskDto json,
			UsernamePasswordAuthenticationToken u)
			throws EntityNotFoundException, CheckFieldException, NotAllowedException, NoUserLoggedException {
		String username = (String) u.getPrincipal();
		CustomerDto cust = customerService.selectByUsername(username);
		if (cust != null) {
			taskService.attivaTask(json, cust);
			TaskDto tDto = taskService.getTaskById(json.getId());
			return ResponseEntity.ok().body(tDto);
		} else {
			throw new NoUserLoggedException();
		}
	}

	@PutMapping("base/concludiTask")
	@CacheEvict("tasks")
	public ResponseEntity<TaskDto> concludiTask(@Valid @RequestBody ActiveTaskDto json,
			UsernamePasswordAuthenticationToken u) throws EntityNotFoundException, CheckFieldException,
			NotAllowedException, NoUserLoggedException, TaskNonAttivaException {
		String username = (String) u.getPrincipal();
		CustomerDto cust = customerService.selectByUsername(username);
		if (cust != null) {
			taskService.concludiTask(json, cust);
			TaskDto tDto = taskService.getTaskById(json.getId());
			return ResponseEntity.ok().body(tDto);
		} else {
			throw new NoUserLoggedException();
		}
	}

	@PutMapping("base/modificaTask")
	@CacheEvict("tasks")
	public ResponseEntity<TaskDto> modificaTask(@Valid @RequestBody TaskDto jsonDto,
			UsernamePasswordAuthenticationToken u)
			throws EntityNotFoundException, CheckFieldException, NoUserLoggedException {
		String username = (String) u.getPrincipal();
		CustomerDto cust = customerService.selectByUsername(username);
		if (cust != null) {
			taskService.modificaTask(jsonDto);
			TaskDto tDto = taskService.getTaskById(jsonDto.getId());
			return ResponseEntity.ok().body(tDto);
		} else {
			throw new NoUserLoggedException();
		}
	}

	@GetMapping("base/getTaskByUser")
	@Cacheable("personalTasks")
	public ResponseEntity<List<TaskDto>> getTasksByUser(UsernamePasswordAuthenticationToken u)
			throws NoUserLoggedException, EntityNotFoundException, EmptyListException, CheckFieldException {
		String username = (String) u.getPrincipal();
		CustomerDto cust = customerService.selectByUsername(username);
		if (cust != null) {
			return ResponseEntity.ok().body(taskService.selectByCustomer(cust));
		} else {
			throw new NoUserLoggedException();
		}
	}

	// CHIAMATE METODI UTENTE BASE PER TEAM

}
