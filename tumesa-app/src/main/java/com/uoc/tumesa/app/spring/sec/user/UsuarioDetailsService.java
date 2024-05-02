package com.uoc.tumesa.app.spring.sec.user;

import com.google.common.collect.Lists;
import com.uoc.tumesa.app.model.LoginResult;
import com.uoc.tumesa.app.model.SignupResult;
import com.uoc.tumesa.app.spring.sec.jwt.JwtService;
import com.uoc.tumesa.repo.Repository;
import com.uoc.tumesa.repo.dao.UsersDAO;
import com.uoc.tumesa.repo.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Clase encargada de obtener el usuario a partir del nombre y contraseña aportados.
 * */
public class UsuarioDetailsService implements UserDetailsService {

    private final Repository repository;
    private final JwtService jwtService;

    public UsuarioDetailsService(Repository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }


    @Override
    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByName(username)
                .map(this::toUsuario)
                .orElseThrow(() -> new NoSuchElementException(String.format("No se encontró al usuario %s", username)));
    }

    /**
     * Método que obtiene el usuario asociado a la petición indicada.
     * */
    public Usuario getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String username = jwtService.extractUsername(token);

        return loadUserByUsername(username);
    }

    /**
     * Método que obtiene la autenticación del usuario indicado para las credenciales dadas.
     * */
    public LoginResult login(String username, String password) {
        Optional<User> user = findUserByName(username);

        if (user.isEmpty())
            return new LoginResult(LoginResult.Result.UnregisteredUser, null);

        if (!user.get().getPassword().equals(password))
            return new LoginResult(LoginResult.Result.InvalidPassword, null);

        return new LoginResult(LoginResult.Result.Ok, toUsuario(user.get()));
    }

    /**
     * Método que registra un usuario con las credenciales dadas.
     * */
    public SignupResult signup(String username, String password, String email) {
        Optional<User> user = findUserByName(username);

        if (user.isPresent())
            return new SignupResult(SignupResult.Result.AlreadyRegisteredUser, null);

        User newUser = createUser(username, password, email);
        return new SignupResult(SignupResult.Result.Ok, toUsuario(newUser));
    }

    /**
     * Método que crea un usuario con las credenciales dadas.
     * */
    private User createUser(String username, String password, String email) {
        return repository
                .getDAO(UsersDAO.class)
                .save(new User(username, password, email));
    }

    /**
     * Método que busca el usuario para el nombre indicado.
     * */
    private Optional<User> findUserByName(String username) {
        return repository
                .getDAO(UsersDAO.class)
                .findByName(username);
    }

    /**
     * Método que convierte un usuario del repositorio en un usuario autenticado en la aplicación.
     * */
    private Usuario toUsuario(User user) {
        String jwtToken = jwtService.generateToken(user.getName());
        return new Usuario(user.getName(), user.getPassword(), jwtToken, Lists.newArrayList(RolUsuario.USER));
    }
}