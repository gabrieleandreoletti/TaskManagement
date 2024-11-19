package org.elis.exception;

import lombok.Data;

@Data
public class CheckFieldException extends Exception{

	public CheckFieldException() {
		super("Controllare il formato dei campi inseriti");
	}
	
}
