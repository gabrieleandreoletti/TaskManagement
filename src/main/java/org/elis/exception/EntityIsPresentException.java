package org.elis.exception;

import lombok.Data;

@Data
public class EntityIsPresentException extends Exception {
	public EntityIsPresentException() {
		super("Entità già presente nela Database");
	}
}
