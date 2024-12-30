package org.elis.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.elis.dto.CustomerDto;
import org.elis.dto.SemplifiedTeamDto;
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
				if (c.getRuolo() == Role.ADMIN) {
					Team t = new Team();
					t.setNome(team.getNome());
					t.setLeader(c);
					repository.save(t);
					c.setTeam(t);
					customerRepository.save(c);

				} else {
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
	public void delete(long id) throws EntityNotFoundException {
		Optional<Team> optTeam = repository.findById(id);
		Team t = optTeam.orElseThrow(() -> new EntityNotFoundException());
		repository.delete(t);
	}

	@Override
	public List<TeamDto> selectAll() throws EntityNotFoundException {
		List<Team> team = repository.findAll();
		if (!team.isEmpty()) {
			List<TeamDto> teamDto = new ArrayList<>();
			for (Team t : team) {
				teamDto.add(mapper.toTeamDto(t));
			}
			return teamDto;
		} else {
			throw new EntityNotFoundException();
		}
	}

	@Override
	public TeamDto selectByName(String nome) throws EntityNotFoundException {
		Optional<Team> optTeam = repository.selectTeamByNome(nome);
		Team t = optTeam.orElseThrow(() -> new EntityNotFoundException());
		return mapper.toTeamDto(t);

	}

	@Override
	public void updateNome(TeamDto team, String nome) throws EntityIsPresentException, EntityNotFoundException {
		if (!nome.isBlank() && nome != null && nome.length() < 12 && nome.length() > 3) {
			Optional<Team> optTeam = repository.findById(team.getId());
			Team t = optTeam.orElseThrow(() -> new EntityNotFoundException());
			if (!repository.selectTeamByNome(nome).isPresent()) {
				t.setNome(nome);
				repository.save(t);
			} else {
				throw new EntityIsPresentException();
			}
		}

	}

	@Override
	public void addMember(CustomerDto c, SemplifiedTeamDto t) throws EntityNotFoundException, EntityIsPresentException {
		if (t.getNome() != null && !t.getNome().isBlank()) {
			Optional<Team> optTeam = repository.findById(t.getId());
			Team team = optTeam.orElseThrow(() -> new EntityNotFoundException());
			if (!c.getTeam().getNome().equals(t.getNome())) {
				Optional<Customer> cusOpt = customerRepository.findById(c.getId());
				Customer customer = cusOpt.orElseThrow(() -> new EntityNotFoundException());
				customer.setTeam(team);
				repository.save(team);
				customerRepository.save(customer);
			} else {
				throw new EntityIsPresentException();
			}
		}

	}

}
