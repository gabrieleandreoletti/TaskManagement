package org.elis.service.implementation;

import java.util.List;
import java.util.Optional;

import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.mapper.CustomerMapper;
import org.elis.mapper.TaskMapper;
import org.elis.model.Customer;
import org.elis.model.Role;
import org.elis.repository.CustomerRepositoryJpa;
import org.elis.service.definition.CustomerService;
import org.elis.utilities.JWTUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceJpa implements CustomerService {

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
	public void insert(RegistrationCustomerDto customer) throws EntityIsPresentException, CheckFieldException {
		Optional<Customer> c_Optional = repository.findCustomerByUsername(customer.getUsername());
		if (c_Optional.isPresent()) {
			throw new EntityIsPresentException();
		} else if (customer.getUsername().isBlank() || customer.getPassword().isBlank()
				|| customer.getUsername() == null || customer.getPassword() == null) {
					throw new CheckFieldException();
		} else {
			String psw_cifrata = passwordEncoder.encode(customer.getPassword());
			Customer c = customerMapper.toCustomer(customer);
			c.setPassword(psw_cifrata);
			c.setRuolo(Role.BASE);
			repository.save(c);
		}
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
