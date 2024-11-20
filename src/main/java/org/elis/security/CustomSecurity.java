package org.elis.security;

import org.elis.model.Role;
import org.elis.security.filter.MyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class CustomSecurity {

	private final MyFilter filter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeHttpRequests(t -> {
			t.requestMatchers("/base/**").hasAnyRole(Role.ADMIN.toString(), Role.BASE.toString());
			t.requestMatchers("/admin/**").hasRole(Role.ADMIN.toString());
			t.requestMatchers("all/**").permitAll();
		});

		httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		httpSecurity.csrf(t -> t.disable());
		httpSecurity.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return httpSecurity.build();

	}

}
