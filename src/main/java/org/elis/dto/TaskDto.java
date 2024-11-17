package org.elis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Data
public class TaskDto {
	private long id;
	private String titolo;
	@JsonIgnore
	private List<CustomerDto> customers;
	private int stato;
}
