package org.elis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActiveTaskDto {
	@NotNull
	long id;
}
