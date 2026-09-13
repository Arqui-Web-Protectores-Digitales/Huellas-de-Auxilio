package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Ciudadano;
import com.upc.huellasdeauxilio.entidades.Mascota;
import com.upc.huellasdeauxilio.entidades.Notificacion;
import com.upc.huellasdeauxilio.entidades.Solicitud;
import com.upc.huellasdeauxilio.repositorios.CiudadanoRepositorio;
import com.upc.huellasdeauxilio.repositorios.MascotaRepositorio;
import com.upc.huellasdeauxilio.repositorios.NotificacionRepositorio;
import com.upc.huellasdeauxilio.repositorios.SolicitudRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class SolicitudServicio {
    @Autowired
    private SolicitudRepositorio solicitudRepositorio;

    @Autowired
    private CiudadanoRepositorio ciudadanoRepositorio;

    @Autowired
    private MascotaServicio mascotaServicio;

    @Autowired
    private NotificacionServicio notificacionServicio;

    @Transactional
    public Solicitud insertar(
            Long idCiudadano,
            Solicitud datos
    ) {
        // Validar los campos obligatorios.
        if (datos == null
                || datos.getMascota() == null
                || datos.getMascota().getIdMascota() == null
                || datos.getTipoVivienda() == null
                || datos.getTipoVivienda().isBlank()
                || datos.getMotivo() == null
                || datos.getMotivo().isBlank()
                || datos.getExperiencia() == null) {

            return null;
        }

        //aqui se valida que el ciudadano exista y tenga un usuario
        Ciudadano ciudadano = buscarCiudadano(idCiudadano);

        if (ciudadano == null || ciudadano.getUsuario() == null) {
            return null;
        }

        //validar que la mascota exista y esté disponible
        Mascota mascota = mascotaServicio.buscarPorId(
                datos.getMascota().getIdMascota()
        );

        if (mascota == null
                || !Boolean.TRUE.equals(mascota.getEstado())) {

            return null;
        }

        Solicitud solicitud = new Solicitud();

        solicitud.setCiudadano(ciudadano);
        solicitud.setMascota(mascota);
        solicitud.setTipoVivienda(datos.getTipoVivienda().trim());
        solicitud.setMotivo(datos.getMotivo().trim());
        solicitud.setExperiencia(datos.getExperiencia());
        solicitud.setFechaSolicitud(LocalDateTime.now());
        solicitud.setEstadoSolicitud("ENVIADA");

        Solicitud solicitudGuardada =
                solicitudRepositorio.save(solicitud);

        // Crear la notificación para el ciudadano.
        Notificacion notiCiudadano = new Notificacion();

        notiCiudadano.setSolicitud(solicitudGuardada);
        notiCiudadano.setUsuario(ciudadano.getUsuario());
        notiCiudadano.setTitulo("Solicitud enviada");
        notiCiudadano.setDescripcion(
                "Tu solicitud para adoptar a "
                        + mascota.getNombre()
                        + " fue enviada correctamente."
        );
        notiCiudadano.setFechaNotificacion(LocalDateTime.now());

        notificacionServicio.insertar(notiCiudadano);

        return solicitudGuardada;
    }

    public List<Solicitud> listarPorCiudadano(Long idCiudadano) {
        Ciudadano ciudadano = buscarCiudadano(idCiudadano);

        if (ciudadano == null) {
            return null;
        }

        return solicitudRepositorio
                .findByCiudadano_IdCiudadanoOrderByFechaSolicitudDesc(
                        idCiudadano
                );
    }

    public Solicitud buscarPorCodigoYCiudadano(
            Long idSolicitud,
            Long idCiudadano
    ) {
        if (idSolicitud == null || idCiudadano == null) {
            return null;
        }

        return solicitudRepositorio
                .findByIdSolicitudAndCiudadano_IdCiudadano(
                        idSolicitud,
                        idCiudadano
                );
    }

    //esta es una funcion extra para quitar especios extra y transformar
    //mayusculas a minusculas y facilitar la filtracion
    private String normalizarFiltro(String valor) {
        if (valor == null || valor.isBlank()) {
            return "todos";
        }

        return valor.trim().toLowerCase(Locale.ROOT);
    }

    public List<Solicitud> filtrarSolicitudes(
            Long idCiudadano,
            String nombreMascota,
            String estado
    ) {
        Ciudadano ciudadano = buscarCiudadano(idCiudadano);

        if (ciudadano == null) {
            return null;
        }


        String estadoFiltro = normalizarFiltro(estado);

        if (!estadoFiltro.equals("todos")
                && !estadoFiltro.equals("enviada")
                && !estadoFiltro.equals("en_revision")
                && !estadoFiltro.equals("aprobada")
                && !estadoFiltro.equals("rechazada")) {

            return null;
        }

        return solicitudRepositorio.filtrarSolicitudes(
                idCiudadano,
                normalizarFiltro(nombreMascota),
                estadoFiltro
        );
    }

    private Ciudadano buscarCiudadano(Long idCiudadano) {
        if (idCiudadano == null) {
            return null;
        }

        return ciudadanoRepositorio
                .findById(idCiudadano)
                .orElse(null);
    }


}