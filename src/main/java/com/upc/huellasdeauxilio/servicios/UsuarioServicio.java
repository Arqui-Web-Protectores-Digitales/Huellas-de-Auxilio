package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Rol;
import com.upc.huellasdeauxilio.entidades.Usuario;
import com.upc.huellasdeauxilio.repositorios.RolRepositorio;
import com.upc.huellasdeauxilio.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

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

        return usuarioRepositorio.save(usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }

    public Usuario iniciarSesion(String correo, String contraseña) {
        return usuarioRepositorio.findByCorreoAndContraseña(
                correo,
                contraseña
        );
    }

    public Usuario cambiarContrasena(
            String correo,
            String nuevaContrasena) {

        Usuario usuario =
                usuarioRepositorio.findByCorreo(correo);

        if (usuario != null) {
            usuario.setContraseña(nuevaContrasena);
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

        if (usuario != null &&
                usuario.getCorreo().equals(correo) &&
                usuario.getContraseña().equals(contraseña)) {

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

        if (!usuario.getContraseña().equals(contrasenaActual)) {
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