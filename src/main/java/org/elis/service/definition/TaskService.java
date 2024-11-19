package org.elis.service.definition;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.TaskDto;
import org.elis.exception.CheckFieldException;

public interface TaskService {
	void insert(TaskDto task) throws CheckFieldException;
	void delete(long id);
	List<TaskDto> selectAll();
	List<TaskDto> selectByCustomer(CustomerDto c);
}
