package org.elis.exception;

import lombok.Data;

@Data
public class NoUserLoggedException extends Exception {
	public NoUserLoggedException() {
		super("Nessun utente loggato in questa sessione, riprovare l'accesso");
	}

}
