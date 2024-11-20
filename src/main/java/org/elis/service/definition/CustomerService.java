package org.elis.service.definition;

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
import org.elis.exception.PasswordNotCorrectException;
import org.elis.exception.UsingOldPswException;
import org.elis.model.Customer;

public interface CustomerService {
	
	void insert(RegistrationCustomerDto customer) throws EntityIsPresentException, CheckFieldException;

	void delete(long id) throws EntityNotFoundException;

	String login(LoginCustomerDto customer) throws EntityNotFoundException,CheckFieldException,PasswordNotCorrectException;
	
	CustomerDto selectByUsername(String username) throws EntityNotFoundException, CheckFieldException;
	
	LoginCustomerDto selectLogCustomerByUsername(String username) throws EntityNotFoundException, CheckFieldException;

	List<CustomerDto> selectAll();

	void updateUsername(LoginCustomerDto user, UpdateUsernameDto username) throws CheckFieldException, EntityNotFoundException, EntityIsPresentException;

	void updatePassword(CustomerDto customer, UpdatePasswordDto password) throws CheckFieldException, EntityNotFoundException, PasswordNotCorrectException, UsingOldPswException;
	
	
}
