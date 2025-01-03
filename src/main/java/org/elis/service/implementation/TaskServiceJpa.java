package org.elis.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.elis.dto.ActiveTaskDto;
import org.elis.dto.CustomerDto;
import org.elis.dto.TaskDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EmptyListException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.NotAllowedException;
import org.elis.exception.TaskNonAttivaException;
import org.elis.mapper.CustomerMapper;
import org.elis.mapper.TaskMapper;
import org.elis.model.Customer;
import org.elis.model.State;
import org.elis.model.Task;
import org.elis.repository.CustomerRepositoryJpa;
import org.elis.repository.TaskRepositoryJpa;
import org.elis.service.definition.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@Autowired
	private final CustomerMapper customerMapper;

	@Override
	@CacheEvict("tasks")

	public void insert(TaskDto task) throws CheckFieldException, EntityNotFoundException {
		if (task.getTitolo().isBlank() || task.getTitolo() == null) {
			throw new CheckFieldException();
		} else {
			Task t = mapper.toTask(task);
			t.setStato(State.InAttesa);
			Optional<Customer> cOpt = customerRepository.findCustomerByUsername(task.getCreator().getUsername());
			Customer c = cOpt.orElseThrow(() -> new EntityNotFoundException());
			t.setAutore(c);
			repository.save(t);
		}
	}

	@Override
	@CacheEvict("tasks")

	public TaskDto attivaTask(ActiveTaskDto task, CustomerDto customer)
			throws EntityNotFoundException, NotAllowedException {
		Optional<Customer> cOpt = customerRepository.findById(customer.getId());
		Customer c = cOpt.orElseThrow(() -> new EntityNotFoundException());
		Optional<Task> tOpt = repository.findById(task.getId());
		Task t = tOpt.orElseThrow(() -> new EntityNotFoundException());
		if (t.getAutore().getUsername().equals(customer.getUsername())) {
			c.getActiveTasks().add(t);
			t.getCustomers().add(c);
			t.setStato(State.InCorso);
			repository.save(t);
			customerRepository.save(c);
			return mapper.toTaskDto(t);
		} else {
			throw new NotAllowedException();
		}

	}

	@Override
	@CacheEvict("tasks")

	public TaskDto concludiTask(ActiveTaskDto task, CustomerDto customer)
			throws NotAllowedException, EntityNotFoundException, TaskNonAttivaException {
		Optional<Customer> cOpt = customerRepository.findById(customer.getId());
		Customer c = cOpt.orElseThrow(() -> new EntityNotFoundException());
		Optional<Task> tOpt = repository.findById(task.getId());
		Task t = tOpt.orElseThrow(() -> new EntityNotFoundException());
		System.out.println("Stato della task" + t.getStato());
		if (t.getStato() == State.InCorso) {
			if (t.getAutore().getUsername().equals(customer.getUsername())) {
				c.getActiveTasks().remove(t);
				t.getCustomers().remove(c);
				t.setStato(State.Concluso);
				repository.save(t);
				customerRepository.save(c);
				return mapper.toTaskDto(t);
			} else {
				throw new NotAllowedException();
			}
		} else {
			throw new TaskNonAttivaException();
		}
	}

	@Override
	@CacheEvict("tasks")
	public void delete(long id) throws EntityNotFoundException, NotAllowedException {
		Optional<Task> tOpt = repository.findById(id);
		Task t = tOpt.orElseThrow(() -> new EntityNotFoundException());
		if (t.getStato() == State.InAttesa) {
			repository.delete(t);
		} else {
			throw new NotAllowedException();
		}
	}

	@Cacheable("tasks")
	@Override
	public List<TaskDto> selectAll() throws EmptyListException {
		List<Task> tasks = repository.findAll();
		if (!tasks.isEmpty()) {
			List<TaskDto> tasksDto = new ArrayList<>();

			for (Task t : tasks) {
				tasksDto.add(mapper.toTaskDto(t));
			}
			return tasksDto;
		} else {
			throw new EmptyListException();
		}
	}

	@Cacheable("personalTasks")
	@Override
	public List<TaskDto> selectByCustomer(CustomerDto c) throws EntityNotFoundException, EmptyListException {
		Optional<Customer> cOpt = customerRepository.findById(c.getId());
		Customer customer = cOpt.orElseThrow(() -> new EntityNotFoundException());
		List<Task> tasks = repository.selectTaskByCustomer(customer.getId());
		if (!tasks.isEmpty()) {
			List<TaskDto> tasksDto = new ArrayList<>();
			for (Task t : tasks) {
				tasksDto.add(mapper.toTaskDto(t));
			}
			return tasksDto;
		} else {
			throw new EmptyListException();
		}

	}

	@Override
	public TaskDto getTaskById(long id) throws EntityNotFoundException {
		Optional<Task> tOpt = repository.findById(id);
		Task t = tOpt.orElseThrow(() -> new EntityNotFoundException());
		TaskDto tDto = mapper.toTaskDto(t);
		return tDto;
	}

	@Override
	@CacheEvict("tasks")
	public TaskDto modificaTask(TaskDto taskDto) throws EntityNotFoundException {
		Optional<Task> tOpt = repository.findById(taskDto.getId());
		Task t = tOpt.orElseThrow(() -> new EntityNotFoundException());
		t.setTitolo(taskDto.getTitolo());
		t.setDescrizione(taskDto.getDescrizione());
		repository.save(t);
		return mapper.toTaskDto(t);

	}
}