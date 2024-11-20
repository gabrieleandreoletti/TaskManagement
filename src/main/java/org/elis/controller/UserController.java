package org.elis.controller;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.dto.TaskDto;
import org.elis.dto.UpdatePasswordDto;
import org.elis.dto.UpdateUsernameDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.NoUserLoggedException;
import org.elis.exception.PasswordNotCorrectException;
import org.elis.exception.UsingOldPswException;
import org.elis.mapper.CustomerMapper;
import org.elis.model.Customer;
import org.elis.service.definition.CustomerService;
import org.elis.service.definition.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private final CustomerService customerService;

	@Autowired
	private final TaskService taskService;

	@PostMapping("all/registrazione")
	public ResponseEntity<RegistrationCustomerDto> registrazione(@Valid @RequestBody RegistrationCustomerDto json)
			throws EntityIsPresentException, CheckFieldException {
		customerService.insert(json);
		return ResponseEntity.ok().body(json);
	}

	@GetMapping("all/login")
	public ResponseEntity<LoginCustomerDto> login(@Valid @RequestBody LoginCustomerDto json)
			throws EntityIsPresentException, CheckFieldException, EntityNotFoundException, PasswordNotCorrectException {
		String token = customerService.login(json);
		return ResponseEntity.ok().header("Authorization", token).build();

	}

	@PutMapping("/base/updateUsername")
	public ResponseEntity<LoginCustomerDto> updateUsername(@Valid @RequestBody UpdateUsernameDto usernameDto,
	        UsernamePasswordAuthenticationToken u)
	        throws EntityNotFoundException, CheckFieldException, NoUserLoggedException, PasswordNotCorrectException, EntityIsPresentException {
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
	public ResponseEntity<CustomerDto> updatePassword(@Valid @RequestBody UpdatePasswordDto password,
			UsernamePasswordAuthenticationToken u)
			throws CheckFieldException, EntityNotFoundException, PasswordNotCorrectException, NoUserLoggedException, UsingOldPswException {
		String username = (String) u.getPrincipal();
		CustomerDto user = customerService.selectByUsername(username);
		if (user != null) {
			customerService.updatePassword(user, password);
			return ResponseEntity.ok().body(user);
		} else {
			throw new NoUserLoggedException();
		}

	}

	@PostMapping("base/creaTask")
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

}
