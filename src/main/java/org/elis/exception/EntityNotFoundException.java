package org.elis.exception;

import lombok.Data;

@Data
public class EntityNotFoundException extends Exception{

	public EntityNotFoundException() {
		super("Entità non trovata nel database");
	}

}
