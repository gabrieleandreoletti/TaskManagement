package org.elis.exception;

import lombok.Data;

@Data
public class PasswordNotCorrectException extends Exception{public PasswordNotCorrectException() {
		super("Password non corretta");
	}

}
