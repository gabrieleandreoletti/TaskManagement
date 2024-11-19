package org.elis.dto;

import java.util.List;

import org.elis.model.State;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskDto {
	private long id;
	@NotBlank
	private String titolo;
	private String descrizione;
	@JsonIgnore
	private CustomerDto creator;
	@JsonIgnore
	private List<CustomerDto> customers;
	@JsonIgnore
	private State stato;
}
