package org.elis.exception;

import lombok.Data;

@Data
public class EmptyListException extends Exception{public EmptyListException() {
		super("La lista da visionare è vuota");
	}

	
}
