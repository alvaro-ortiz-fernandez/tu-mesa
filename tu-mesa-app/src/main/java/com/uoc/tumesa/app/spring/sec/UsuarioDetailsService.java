package com.uoc.tumesa.app.spring.sec;

import com.google.common.collect.Lists;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Clase encargada de obtener el usuario a partir del nombre y contraseña aportados.
 * */
public class UsuarioDetailsService implements UserDetailsService {

    public UsuarioDetailsService() {}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new Usuario("", "", Lists.newArrayList());
    }
}