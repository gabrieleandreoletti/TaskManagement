package org.elis.repository;

import org.elis.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepositoryJpa extends JpaRepository<Task, Long>{
	
	@Query("select t from Task t where t.titolo = :titolo")
	Task selectTaskByTitle(String titolo);
}
