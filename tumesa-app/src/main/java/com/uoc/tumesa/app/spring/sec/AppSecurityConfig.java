package com.uoc.tumesa.app.spring.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class AppSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
		return new UsuarioDetailsService();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
			UserDetailsService userDetailService) throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder
				.userDetailsService(userDetailService)
				.passwordEncoder(bCryptPasswordEncoder);

		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(registry -> registry
					.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
					.requestMatchers("/admin/**").hasAnyRole("ADMIN")
					.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/login/**").permitAll()
					.anyRequest()
					.authenticated()
				)
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web
				.ignoring()
				.requestMatchers("/css/**", "/js/**", "/img/**", "/resources/**");
	}
}