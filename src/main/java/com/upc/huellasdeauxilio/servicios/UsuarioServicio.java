package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Rol;
import com.upc.huellasdeauxilio.entidades.Usuario;
import com.upc.huellasdeauxilio.repositorios.RolRepositorio;
import com.upc.huellasdeauxilio.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    // INYECTAMOS EL ENCRIPTADOR DE CONTRASEÑAS
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario insertar(Usuario usuario) {

        if (usuario == null ||
                usuario.getRol() == null ||
                usuario.getRol().getIdRol() == null) {
            return null;
        }

        Rol rol = rolRepositorio
                .findById(usuario.getRol().getIdRol())
                .orElse(null);

        if (rol == null) {
            return null;
        }

        usuario.setRol(rol);
        usuario.setEstadoUsuario(true);

        // ¡MAGIA DE SEGURIDAD! Encriptamos la clave antes de guardarla en la base de datos
        String bcryptPassword = passwordEncoder.encode(usuario.getContraseña());
        usuario.setContraseña(bcryptPassword);

        return usuarioRepositorio.save(usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }

    public Usuario cambiarContrasena(
            String correo,
            String nuevaContrasena) {

        Usuario usuario =
                usuarioRepositorio.findByCorreo(correo);

        if (usuario != null) {
            // Encriptamos la nueva contraseña antes de actualizar
            usuario.setContraseña(passwordEncoder.encode(nuevaContrasena));
            return usuarioRepositorio.save(usuario);
        }

        return null;
    }

    // ELIMINADO LOGICO DE USUARIO
    public Usuario eliminadoLogico(
            String correo,
            String contraseña,
            Long id) {

        Usuario usuario =
                usuarioRepositorio.findById(id).orElse(null);

        // Aquí usamos matches() porque la clave en la BD está encriptada y la que manda el usuario no
        if (usuario != null &&
                usuario.getCorreo().equals(correo) &&
                passwordEncoder.matches(contraseña, usuario.getContraseña())) {

            usuario.setEstadoUsuario(false);

            return usuarioRepositorio.save(usuario);
        }

        return null;
    }

    public Usuario cambiarContrasenaDesdePerfil(
            String correo,
            String contrasenaActual,
            String nuevaContrasena,
            String confirmarContrasena) {

        if (correo == null || correo.isBlank() ||
                contrasenaActual == null || contrasenaActual.isBlank() ||
                nuevaContrasena == null || nuevaContrasena.isBlank() ||
                confirmarContrasena == null || confirmarContrasena.isBlank()) {

            return null;
        }

        Usuario usuario =
                usuarioRepositorio.findByCorreo(correo);

        if (usuario == null ||
                usuario.getContraseña() == null) {
            return null;
        }

        // Verificamos con el encriptador si la clave actual es correcta
        if (!passwordEncoder.matches(contrasenaActual, usuario.getContraseña())) {
            return null;
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            return null;
        }

        return cambiarContrasena(
                correo,
                nuevaContrasena
        );
    }
}