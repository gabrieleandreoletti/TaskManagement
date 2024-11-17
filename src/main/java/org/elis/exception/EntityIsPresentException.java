package org.elis.exception;

public class EntityIsPresentException extends Exception {
	public EntityIsPresentException() {
		super("Entità già presente nela Database");
	}
}
