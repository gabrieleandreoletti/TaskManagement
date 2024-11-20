package org.elis.service.implementation;

import java.util.List;
import java.util.Optional;

import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.dto.TaskDto;
import org.elis.dto.UpdatePasswordDto;
import org.elis.dto.UpdateUsernameDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.PasswordNotCorrectException;
import org.elis.exception.UsingOldPswException;
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
	public void delete(long id) throws EntityNotFoundException {
		Optional<Customer> cOpt = repository.findById(id);
		if(cOpt.isPresent()) {
			Customer c = cOpt.get();
			repository.delete(c);
		}else {
			throw new EntityNotFoundException();
		}

	}

	@Override
	public String login(LoginCustomerDto customer)
			throws EntityNotFoundException, CheckFieldException, PasswordNotCorrectException {
		Optional<Customer> cOptional = repository.findCustomerByUsername(customer.getUsername());
		if (!cOptional.isPresent()) {
			throw new EntityNotFoundException();
		} else if (customer.getUsername().length() <= 2 || customer.getPassword().length() <= 2) {
			throw new CheckFieldException();
		} else {
			Customer user = cOptional.get();
			if (passwordEncoder.matches(customer.getPassword(), user.getPassword())) {
				return jwtUtilities.generateToken(user);
			} else {
				throw new PasswordNotCorrectException();
			}
		}
	}

	@Override
	public List<CustomerDto> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUsername(LoginCustomerDto customer, UpdateUsernameDto username)
			throws CheckFieldException, EntityNotFoundException, EntityIsPresentException {
		if (username.getUsername() != null && !username.getUsername().isBlank() && username.getUsername().length() < 12
				&& username.getUsername().length() > 2) {
			Optional<Customer> cOpt = repository.findById(customer.getId());
			if (cOpt.isPresent()) {
				Optional<Customer> cOpt2 = repository.findCustomerByUsername(username.getUsername());
				if (!cOpt2.isPresent()) {
					Customer c = cOpt.get();
					c.setUsername(username.getUsername());
					repository.save(c);
				} else {
					throw new EntityIsPresentException();
				}
			} else {
				throw new EntityNotFoundException();
			}
		} else {
			throw new CheckFieldException();
		}

	}

	@Override
	public void updatePassword(CustomerDto customer, UpdatePasswordDto pswDto)
			throws UsingOldPswException, PasswordNotCorrectException, EntityNotFoundException, CheckFieldException {
		if (pswDto.getPassword() != null && !pswDto.getPassword().isBlank() && pswDto.getPassword().length() > 3
				&& pswDto.getPassword().length() < 13 && pswDto.getConfermaPassword() != null
				&& !pswDto.getConfermaPassword().isBlank() && pswDto.getConfermaPassword().length() > 3
				&& pswDto.getConfermaPassword().length() < 13) {
			Optional<Customer> cOpt = repository.findById(customer.getId());
			if (cOpt.isPresent()) {
				Customer c = cOpt.get();
				if (pswDto.getPassword().equals(pswDto.getConfermaPassword())) {
					if (!passwordEncoder.matches(pswDto.getPassword(), c.getPassword())) {
						c.setPassword(passwordEncoder.encode(pswDto.getPassword()));
						repository.save(c);
					} else {
						throw new UsingOldPswException();
					}
				} else {
					throw new PasswordNotCorrectException();
				}
			} else {
				throw new EntityNotFoundException();
			}

		} else {
			throw new CheckFieldException();
		}
	}

	@Override
	public CustomerDto selectByUsername(String username) throws EntityNotFoundException, CheckFieldException {
		if (username.length() > 2) {
			Optional<Customer> cOpt = repository.findCustomerByUsername(username);
			if (cOpt.isPresent()) {
				Customer c = cOpt.get();
				return customerMapper.toCustomerDto(c);
			} else {
				throw new EntityNotFoundException();
			}
		} else {
			throw new CheckFieldException();
		}
	}

	@Override
	public LoginCustomerDto selectLogCustomerByUsername(String username)
			throws EntityNotFoundException, CheckFieldException {
		if (username.length() > 2) {
			Optional<Customer> cOpt = repository.findCustomerByUsername(username);
			if (cOpt.isPresent()) {
				Customer c = cOpt.get();
				return customerMapper.toLoginCustomerDto(c);
			} else {
				throw new EntityNotFoundException();
			}
		} else {
			throw new CheckFieldException();
		}
	}

}
