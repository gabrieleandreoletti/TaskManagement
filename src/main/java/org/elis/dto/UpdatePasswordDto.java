package org.elis.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordDto {
	@NotBlank
	String password,confermaPassword;
}
