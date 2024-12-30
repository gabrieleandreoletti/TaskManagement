package org.elis.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String titolo;
	private String descrizione;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private State stato;
	@ManyToMany(mappedBy = "activeTasks")
	private List<Customer> customers;
	@ManyToOne
	@JoinColumn(name = "id_autore", nullable = false)
	private Customer autore;

}
