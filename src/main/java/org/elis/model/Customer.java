package org.elis.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private Role ruolo;
	@ManyToMany
	@JoinTable(name = "customer_task", joinColumns = @JoinColumn(name = "id_customer"), inverseJoinColumns = @JoinColumn(name = "id_task"))
	private List<Task> tasks;

	@ManyToMany
	@JoinTable(name = "customer_team", joinColumns = @JoinColumn(name = "id_customer"), inverseJoinColumns = @JoinColumn(name = "id_team"))
	private List<Team> teams;

}
