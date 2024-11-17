package org.elis.service.implementation;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.TaskDto;
import org.elis.mapper.TaskMapper;
import org.elis.repository.TaskRepositoryJpa;
import org.elis.service.definition.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceJpa implements TaskService {

	@Autowired
	private final TaskRepositoryJpa repository;
	@Autowired
	private final TaskMapper mapper;

	@Override
	public void insert(TaskDto task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TaskDto> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TaskDto> selectByCustomer(CustomerDto c) {
		// TODO Auto-generated method stub
		return null;
	}

}
