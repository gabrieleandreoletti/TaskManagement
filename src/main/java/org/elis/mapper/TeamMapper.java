package org.elis.mapper;

import org.elis.dto.TeamDto;
import org.elis.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")

public interface TeamMapper {
	
	public Team toTeam(TeamDto t);
	
	public TeamDto toTeamDto(Team t);
}
