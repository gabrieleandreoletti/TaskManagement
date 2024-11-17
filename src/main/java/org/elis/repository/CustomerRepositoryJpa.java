package org.elis.repository;

import java.util.Optional;

import org.elis.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepositoryJpa extends JpaRepository<Customer, Long> {

	@Query("select c from Customer c where c.username = :username and c.password = :password")
	Optional<Customer> findLoginUtenteByUsernamePassword(String username, String password);

	@Query("select c from Customer c where c.username = :username")
	Optional<Customer> findCustomerByUsername(String username);
}
