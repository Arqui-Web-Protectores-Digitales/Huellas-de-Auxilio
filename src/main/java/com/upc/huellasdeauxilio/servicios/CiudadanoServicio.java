package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Ciudadano;
import com.upc.huellasdeauxilio.entidades.Usuario;
import com.upc.huellasdeauxilio.repositorios.CiudadanoRepositorio;
import com.upc.huellasdeauxilio.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CiudadanoServicio {

    @Autowired
    private CiudadanoRepositorio ciudadanoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Ciudadano insertar(Ciudadano ciudadano) {

        if (ciudadano == null
                || ciudadano.getUsuario() == null
                || ciudadano.getUsuario().getIdUsuario() == null) {
            return null;
        }

        Long idUsuario = ciudadano.getUsuario().getIdUsuario();

        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        ciudadano.setUsuario(usuario);

        return ciudadanoRepositorio.save(ciudadano);
    }

    public Ciudadano buscarPorDni(String dni) {
        return ciudadanoRepositorio.findByDni(dni);
    }

    public Ciudadano buscarPorUsuario(Long idUsuario) {
        return ciudadanoRepositorio.findByUsuario_IdUsuario(idUsuario);
    }

    public Ciudadano obtenerPerfil(Long idCiudadano) {
        if (idCiudadano == null) {
            return null;
        }

        return ciudadanoRepositorio.findById(idCiudadano).orElse(null);
    }

    @Transactional
    public Ciudadano actualizarPerfil(
            Long idCiudadano,
            Ciudadano datos
    ) {
        //validar los datos obligatorios
        if (datos == null
                || datos.getNombreCompleto() == null
                || datos.getNombreCompleto().isBlank()
                || datos.getDistrito() == null
                || datos.getDistrito().isBlank()
                || datos.getUsuario() == null
                || datos.getUsuario().getCorreo() == null
                || datos.getUsuario().getCorreo().isBlank()
                || datos.getUsuario().getTelefono() == null
                || datos.getUsuario().getTelefono().isBlank()) {

            return null;
        }

        Ciudadano ciudadano = obtenerPerfil(idCiudadano);

        if (ciudadano == null || ciudadano.getUsuario() == null) {
            return null;
        }

        Usuario usuario = ciudadano.getUsuario();

        String correo = datos.getUsuario().getCorreo().trim();


        //actualizar únicamente los campos permitidos
        ciudadano.setNombreCompleto(datos.getNombreCompleto().trim());
        ciudadano.setDistrito(datos.getDistrito().trim());

        usuario.setCorreo(correo);
        usuario.setTelefono(datos.getUsuario().getTelefono().trim());

        usuarioRepositorio.save(usuario);

        return ciudadanoRepositorio.save(ciudadano);
    }
}