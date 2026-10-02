package com.upc.huellasdeauxilio.security.services;

import com.upc.huellasdeauxilio.entidades.Usuario;
import com.upc.huellasdeauxilio.repositorios.UsuarioRepositorio;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    public CustomUserDetailsService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo);
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        if (usuario.getRol() != null) {
            // El rol viene de nuestra base de datos (ej: ROLE_CIUDADANO)
            authorities.add(new SimpleGrantedAuthority(usuario.getRol().getTipoRol()));
        }

        // Spring Security maneja el usuario nativo de esta forma
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getCorreo())
                .password(usuario.getContraseña())
                .authorities(authorities)
                .build();
    }
}
