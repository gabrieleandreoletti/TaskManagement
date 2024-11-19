package org.elis.mapper;

import org.elis.dto.CustomerDto;
import org.elis.dto.LoginCustomerDto;
import org.elis.dto.RegistrationCustomerDto;
import org.elis.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CustomerMapper {

	public Customer toCustomer(CustomerDto c);

	public CustomerDto toCustomerDto(Customer c);

	public Customer toCustomer(LoginCustomerDto c);

	public LoginCustomerDto toLoginCustomerDto(Customer c);

	public Customer toCustomer(RegistrationCustomerDto c);

	public RegistrationCustomerDto toRegistrationCustomerDto(Customer c);

}
