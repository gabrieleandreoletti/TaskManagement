package org.elis.security.filter;

import org.elis.utilities.JWTUtilities;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class MyFilter {
	
	private final HandlerExceptionResolver resolver;
	private final UserDetailsService service;
	private final JWTUtilities utilities;
	public MyFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,@Lazy UserDetailsService service, JWTUtilities utilities) {
		
		this.resolver = resolver;
		this.service = service;
		this.utilities = utilities;
	}
	
	protected void doFilterInternal() {
		
	}
	
	
}
