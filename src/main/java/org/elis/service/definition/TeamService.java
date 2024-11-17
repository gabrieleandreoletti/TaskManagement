package org.elis.service.definition;

import java.util.List;

import org.elis.dto.TeamDto;

public interface TeamService {
	
	void insert(TeamDto team);
	void delete(long id);
	List<TeamDto> selectAll();
	TeamDto selectByName(String nome);
	void updateNome(TeamDto team , String nome);
}
