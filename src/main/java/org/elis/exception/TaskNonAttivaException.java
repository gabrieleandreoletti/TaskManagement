package org.elis.exception;

import lombok.Data;

@Data
public class TaskNonAttivaException extends Exception{public TaskNonAttivaException() {
		super("Non puoi concludere una task che non è attiva");
	}
	
	
}
