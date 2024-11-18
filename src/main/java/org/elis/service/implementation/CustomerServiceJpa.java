package org.elis.service.implementation;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.mapper.CustomerMapper;
import org.elis.mapper.TaskMapper;
import org.elis.repository.CustomerRepositoryJpa;
import org.elis.service.definition.CustomerService;
import org.elis.utilities.JWTUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceJpa implements CustomerService{
	
	@Autowired
	private final CustomerMapper customerMapper;
	
	@Autowired
	private final TaskMapper taskMapper;
	@Autowired
	private final CustomerRepositoryJpa repository;
	
	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private final JWTUtilities jwtUtilities;

	@Override
	public void insert(CustomerDto customer) {
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(LoginCustomerDto customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CustomerDto selectByID(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerDto> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUsername(CustomerDto customer, String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePassword(CustomerDto customer, String password) {
		// TODO Auto-generated method stub
		
	}
	
	
}
