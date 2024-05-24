package com.uoc.tumesa.app.spring.sec.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

/**
 * Clase abstracta para representar roles de la sede.
 */
public record RolUsuario(String rol) {

    public static final RolUsuario USER = new RolUsuario("USER");
    public static final RolUsuario ADMIN = new RolUsuario("ADMIN");


    public String getAuthority() {
        return "ROLE_" + rol;
    }

    public GrantedAuthority getGrantedAuthority() {
        return new SimpleGrantedAuthority(getAuthority());
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return Collections.singletonList(getGrantedAuthority());
    }

    @Override
    public String toString() {
        return String.format("Rol: [%s]", rol);
    }
}