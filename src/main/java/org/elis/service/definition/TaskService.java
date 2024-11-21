package org.elis.service.definition;

import java.util.List;

import org.elis.dto.ActiveTaskDto;
import org.elis.dto.CustomerDto;
import org.elis.dto.TaskDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.NotAllowedException;
import org.elis.exception.TaskNonAttivaException;

public interface TaskService {
	void insert(TaskDto task) throws CheckFieldException, EntityNotFoundException;
	void delete(long id) throws EntityNotFoundException, NotAllowedException;
	List<TaskDto> selectAll();
	List<TaskDto> selectByCustomer(CustomerDto c);
	TaskDto attivaTask(ActiveTaskDto task, CustomerDto customer) throws EntityNotFoundException, NotAllowedException;
	TaskDto concludiTask(ActiveTaskDto task, CustomerDto customer) throws NotAllowedException, EntityNotFoundException, TaskNonAttivaException;
	TaskDto modificaTask(TaskDto taskDto) throws EntityNotFoundException;
	TaskDto getTaskById(long id) throws EntityNotFoundException;
}
