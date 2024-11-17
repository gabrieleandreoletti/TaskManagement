package org.elis.service.definition;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.TaskDto;

public interface TaskService {
	void insert(TaskDto task);
	void delete(long id);
	List<TaskDto> selectAll();
	List<TaskDto> selectByCustomer(CustomerDto c);
}
