package org.elis.service.definition;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.dto.TaskDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.PasswordNotCorrectException;
import org.elis.model.Customer;

public interface CustomerService {
	
	void insert(RegistrationCustomerDto customer) throws EntityIsPresentException, CheckFieldException;

	void delete(long id);

	String login(LoginCustomerDto customer) throws EntityNotFoundException,CheckFieldException,PasswordNotCorrectException;

	CustomerDto selectByID(long id);
	
	CustomerDto selectByUsername(String username) throws EntityNotFoundException, CheckFieldException;

	List<CustomerDto> selectAll();

	void updateUsername(CustomerDto customer, String username);

	void updatePassword(CustomerDto customer, String password);
	
	
}
