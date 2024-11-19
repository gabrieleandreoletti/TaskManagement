package org.elis.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationCustomerDto {

	private long id;
	@NotBlank
	@Length(min = 2 , max = 12)
	private String username,password;
}
