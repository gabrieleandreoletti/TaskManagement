package org.elis.mapper;

import org.elis.dto.TaskDto;
import org.elis.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")

public interface TaskMapper {
	public Task toTask(TaskDto t);
	public TaskDto toTaskDto(Task t);
}
