package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Usuario;
import com.upc.huellasdeauxilio.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Usuario insertar(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo);
    }

    public Usuario iniciarSesion(String correo, String contraseña) {
        return usuarioRepositorio.findByCorreoAndContraseña(correo, contraseña);
    }

    public Usuario cambiarContrasena(String correo, String nuevaContrasena) {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo);

        if (usuario != null) {
            usuario.setContraseña(nuevaContrasena);
            return usuarioRepositorio.save(usuario);
        }

        return null;
    }

    //ELIMINADO LOGICO DE USUARIO
    public String eliminadoLogico(String correo, String contraseña) {

        Usuario usuario = usuarioRepositorio.findByCorreoAndContraseña(correo, contraseña);
        if (usuario != null) {
            usuario.setEstadoUsuario(true);
            usuarioRepositorio.save(usuario);
            return "Usuario encontrado";
        } else {
            return "Usuario no encontrado";
        }
    }

    public Usuario cambiarContrasenaDesdePerfil(
            String correo,
            String contrasenaActual,
            String nuevaContrasena,
            String confirmarContrasena)
    {
        if (correo == null || correo.isBlank() ||

                contrasenaActual == null || contrasenaActual.isBlank() ||

                nuevaContrasena == null || nuevaContrasena.isBlank() ||

                confirmarContrasena == null || confirmarContrasena.isBlank()) {
            return null;
        }

        Usuario usuario = usuarioRepositorio.findByCorreo(correo);

        if (usuario == null ||
                usuario.getContraseña() == null) {
            return null;
        }

        if (!usuario.getContraseña().equals(contrasenaActual)) {
            return null;
        }

        return cambiarContrasena(correo, nuevaContrasena);
    }
}