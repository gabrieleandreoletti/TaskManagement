package org.elis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class TeamDto {

	private long id;
	private String nome;
	@JsonIgnore
	private List<CustomerDto> customers;
}
