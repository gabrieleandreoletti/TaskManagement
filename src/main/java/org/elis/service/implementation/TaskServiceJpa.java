package org.elis.service.implementation;

import java.util.List;
import java.util.Optional;

import org.elis.dto.CustomerDto;
import org.elis.dto.TaskDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.exception.EntityNotFoundException;
import org.elis.mapper.CustomerMapper;
import org.elis.mapper.TaskMapper;
import org.elis.model.Customer;
import org.elis.model.Role;
import org.elis.model.State;
import org.elis.model.Task;
import org.elis.repository.CustomerRepositoryJpa;
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

	@Autowired
	private final CustomerRepositoryJpa customerRepository;

	@Override
	public void insert(TaskDto task) throws CheckFieldException, EntityNotFoundException {
		if (task.getTitolo().isBlank() || task.getTitolo() == null) {
			throw new CheckFieldException();
		} else {
			Task t = mapper.toTask(task);
			t.setStato(State.InAttesa);
			Optional<Customer> cOpt = customerRepository.findCustomerByUsername(task.getCreator().getUsername());
			if (cOpt.isPresent()) {
				Customer c = cOpt.get();
				t.setAutore(c);
				repository.save(t);
			}else {
				throw new EntityNotFoundException();
			}

		}

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
