package org.elis.repository;

import org.elis.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepositoryJpa extends JpaRepository<Team, Long> {

	@Query("select t from Team t where t.nome =:nome")
	Team selectTeamByNome(String nome);
}
