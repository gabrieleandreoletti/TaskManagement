package org.elis.exception;

import lombok.Data;

@Data
public class NotAllowedException extends Exception {
	public NotAllowedException() {
		super("Non hai il permesso di effettuare questa operazione");
	}

}
