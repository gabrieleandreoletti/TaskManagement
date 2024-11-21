package org.elis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamDto {

	private long id;
	@NotBlank
	private String nome;
	@JsonIgnore
	private List<CustomerDto> customers;
	private CustomerDto leader;
}
