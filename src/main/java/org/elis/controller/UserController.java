package org.elis.controller;

import org.elis.dto.RegistrationCustomerDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.mapper.CustomerMapper;
import org.elis.service.definition.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	private CustomerMapper customerMapper;

	@PostMapping("all/registrazione")
	public ResponseEntity<RegistrationCustomerDto> registrazione(@Valid @RequestBody RegistrationCustomerDto json)
			throws EntityIsPresentException, CheckFieldException {
		customerService.insert(json);
		return ResponseEntity.ok().body(json);
	}
}
