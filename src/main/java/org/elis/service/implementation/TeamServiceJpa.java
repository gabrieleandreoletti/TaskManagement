package org.elis.service.implementation;

import java.util.List;
import java.util.Optional;

import org.elis.dto.CustomerDto;
import org.elis.dto.TeamDto;
import org.elis.exception.CheckFieldException;
import org.elis.exception.EntityIsPresentException;
import org.elis.exception.EntityNotFoundException;
import org.elis.exception.NotAllowedException;
import org.elis.mapper.TeamMapper;
import org.elis.model.Customer;
import org.elis.model.Role;
import org.elis.model.Team;
import org.elis.repository.CustomerRepositoryJpa;
import org.elis.repository.TeamRepositoryJpa;
import org.elis.service.definition.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceJpa implements TeamService {

	@Autowired
	private final TeamRepositoryJpa repository;

	@Autowired
	private final TeamMapper mapper;

	@Autowired
	private final CustomerRepositoryJpa customerRepository;

	@Override
	public void insert(TeamDto team, CustomerDto leader)
			throws EntityIsPresentException, CheckFieldException, EntityNotFoundException, NotAllowedException {
		if (team.getNome() != null && !team.getNome().isBlank() && team.getNome().length() > 3
				&& team.getNome().length() < 12) {
			Optional<Team> optTeam = repository.selectTeamByNome(team.getNome());
			Optional<Customer> custOpt = customerRepository.findCustomerByUsername(leader.getUsername());
			Customer c = custOpt.orElseThrow(() -> new EntityNotFoundException());
			if (!optTeam.isPresent()) {
				if(c.getRuolo()==Role.ADMIN) {
					Team t = optTeam.get();
					t.setLeader(c);
					repository.save(t);
				}else {
					throw new NotAllowedException();
				}
			} else {
				throw new EntityIsPresentException();
			}
		} else {
			throw new CheckFieldException();
		}

	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TeamDto> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamDto selectByName(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateNome(TeamDto team, String nome) {
		// TODO Auto-generated method stub

	}

}
