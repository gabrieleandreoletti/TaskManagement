package org.elis.repository;

import java.util.List;

import org.elis.model.Customer;
import org.elis.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepositoryJpa extends JpaRepository<Task, Long> {

	@Query("select t from Task t where t.titolo = :titolo")
	Task selectTaskByTitle(String titolo);

	@Query("select t from Task t where t.autore.id = :id")
	List<Task> selectTaskByCustomer(@Param("id") Long id);

}
