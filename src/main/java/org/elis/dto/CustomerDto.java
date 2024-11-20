package org.elis.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CustomerDto {
	private long id;
	private List<TaskDto> task_create;
	private String username;

}
