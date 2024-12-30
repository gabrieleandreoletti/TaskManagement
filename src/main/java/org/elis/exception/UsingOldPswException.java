package org.elis.exception;

import lombok.Data;

@Data
public class UsingOldPswException extends Exception{

	public UsingOldPswException() {
		super("Non puoi riutilizzare una password che hai gia usato");
	}

}
