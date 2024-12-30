package org.elis.repository;

import java.util.Optional;

import org.elis.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepositoryJpa extends JpaRepository<Team, Long> {

	@Query("select t from Team t where t.nome =:nome")
	Optional<Team> selectTeamByNome(String nome);
}
