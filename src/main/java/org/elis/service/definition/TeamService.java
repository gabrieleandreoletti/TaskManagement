package org.elis.service.definition;

import java.util.List;

import org.elis.dto.CustomerDto;
import org.elis.dto.TeamDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.NotAllowedException;
import org.elis.model.Customer;

public interface TeamService {
	
	void insert(TeamDto team,CustomerDto leader) throws EntityIsPresentException, CheckFieldException, EntityNotFoundException, NotAllowedException;
	void delete(long id);
	List<TeamDto> selectAll();
	TeamDto selectByName(String nome);
	void updateNome(TeamDto team , String nome);
}
