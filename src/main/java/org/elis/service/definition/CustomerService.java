package org.elis.service.definition;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.model.Customer;

public interface CustomerService {
	
	void insert(CustomerDto customer);

	void delete(long id);

	void login(LoginCustomerDto customer);

	CustomerDto selectByID(long id);

	List<CustomerDto> selectAll();

	void updateUsername(CustomerDto customer, String username);

	void updatePassword(CustomerDto customer, String password);
	
}
