package com.uoc.tumesa.app.spring.sec.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representa a un usuario en la aplicación.
 * */
public record Usuario(String usuario, String password, String jwtToken, List<RolUsuario> roles) implements UserDetails {

	@Override
	public String getUsername() {
		return usuario;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public List<RolUsuario> getRoles() {
		return roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles().stream()
				.map(RolUsuario::getGrantedAuthority)
				.collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonLocked() {
		// Por defecto los usuarios de la aplicación no se bloquean
		return true;
	}

	@Override
	public boolean isAccountNonExpired() {
		// Por defecto los usuarios de la aplicación no caducan
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// Por defecto los usuarios de la aplicación no caducan
		return true;
	}

	@Override
	public boolean isEnabled() {
		// Por defecto los usuarios de la aplicación se encuentran habilitados
		return true;
	}
}