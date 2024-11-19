package org.elis.service.definition;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.model.Customer;

public interface CustomerService {
	
	void insert(RegistrationCustomerDto customer) throws EntityIsPresentException, CheckFieldException;

	void delete(long id);

	void login(LoginCustomerDto customer);

	CustomerDto selectByID(long id);

	List<CustomerDto> selectAll();

	void updateUsername(CustomerDto customer, String username);

	void updatePassword(CustomerDto customer, String password);
	
}
