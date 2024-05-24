package com.uoc.tumesa.app.spring.sec;

import com.uoc.tumesa.app.spring.sec.jwt.JwtService;
import com.uoc.tumesa.app.spring.sec.user.UsuarioDetailsService;
import com.uoc.tumesa.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de Spring Boot, para configurar la securización de la aplicación.
 * */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	@Autowired
	private Repository repository;

	@Autowired
	private JwtService jwtService;

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
			UserDetailsService userDetailService) throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder
				.userDetailsService(userDetailService)
				.passwordEncoder(passwordEncoder);

		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(registry -> registry
					.anyRequest()
					.permitAll()
				)
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web
				.ignoring()
				.requestMatchers("/css/**", "/js/**", "/img/**", "/resources/**");
	}

	@Bean(name = "userDetailsService")
	public UsuarioDetailsService userDetailsService() {
		return new UsuarioDetailsService(repository, jwtService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}