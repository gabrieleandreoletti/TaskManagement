package org.elis.service.definition;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.SemplifiedTeamDto;
import org.elis.dto.TeamDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.NotAllowedException;
import org.elis.model.Customer;

public interface TeamService {
	
	void insert(TeamDto team,CustomerDto leader) throws EntityIsPresentException, CheckFieldException, EntityNotFoundException, NotAllowedException;
	void delete(long id) throws EntityNotFoundException;
	List<TeamDto> selectAll() throws EntityNotFoundException;
	TeamDto selectByName(String nome) throws EntityNotFoundException;
	void updateNome(TeamDto team , String nome) throws EntityIsPresentException, EntityNotFoundException;
	void addMember(CustomerDto c,SemplifiedTeamDto t) throws EntityNotFoundException, EntityIsPresentException;
}
