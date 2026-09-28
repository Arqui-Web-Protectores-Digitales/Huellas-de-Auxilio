package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Ciudadano;
import com.upc.huellasdeauxilio.entidades.Mascota;
import com.upc.huellasdeauxilio.entidades.Notificacion;
import com.upc.huellasdeauxilio.entidades.Solicitud;
import com.upc.huellasdeauxilio.repositorios.CiudadanoRepositorio;
import com.upc.huellasdeauxilio.repositorios.SolicitudRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        solicitud.setEstadoSolicitud("EN_REVISION");

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

        if (mascota.getEntidad() != null) {
            Notificacion notiEntidad = new Notificacion();
            notiEntidad.setSolicitud(solicitudGuardada);
            notiEntidad.setUsuario(mascota.getEntidad().getUsuario());
            notiEntidad.setTitulo("Nueva Solicitud de Adopción");
            notiEntidad.setDescripcion("Tienes una nueva solicitud de adopción para la mascota: " + mascota.getNombre());
            notiEntidad.setFechaNotificacion(LocalDateTime.now());
            notificacionServicio.insertar(notiEntidad);
        }

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
    public List<Solicitud> listarPorMascota(Long idMascota) {

        if (idMascota == null) {
            return null;
        }

        Mascota mascota = mascotaServicio.buscarPorId(idMascota);

        if (mascota == null) {
            return null;
        }

        return solicitudRepositorio
                .findByMascota_IdMascotaOrderByFechaSolicitudDesc(idMascota);
    }

    public Solicitud buscarPorSolicitudYMascota(
            Long idSolicitud,
            Long idMascota
    ) {
        if (idSolicitud == null || idMascota == null) {
            return null;
        }

        Mascota mascota = mascotaServicio.buscarPorId(idMascota);

        if (mascota == null) {
            return null;
        }

        return solicitudRepositorio
                .findByIdSolicitudAndMascota_IdMascota(
                        idSolicitud,
                        idMascota
                );
    }

    public List<Solicitud> listarPorEntidad(Long idEntidad) {

        if (idEntidad == null) {
            return null;
        }

        return solicitudRepositorio
                .findByMascota_Entidad_IdEntidadOrderByFechaSolicitudDesc(idEntidad);
    }

    public List<Solicitud> filtrarSolicitudesPorMascota(
            Long idMascota,
            String codigo,
            String estado
    ) {
        if (idMascota == null) {
            return null;
        }

        Mascota mascota = mascotaServicio.buscarPorId(idMascota);

        if (mascota == null) {
            return null;
        }

        String estadoFiltro = normalizarFiltro(estado);

        if (!estadoFiltro.equals("todos")
                && !estadoFiltro.equals("en_revision")
                && !estadoFiltro.equals("rechazada")
                && !estadoFiltro.equals("aprobada")) {
            return null;
        }

        return solicitudRepositorio.filtrarSolicitudesPorMascota(
                idMascota,
                normalizarFiltro(codigo),
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

    public List<Solicitud> filtrarSolicitudesPorEntidad(
            Long idEntidad,
            String codigo,
            String estado,
            String especie
    ) {
        if (idEntidad == null) {
            return null;
        }

        String codigoFiltro = normalizarFiltro(codigo);
        String estadoFiltro = normalizarFiltro(estado);
        String especieFiltro = normalizarFiltro(especie);

        if (!estadoFiltro.equals("todos")
                && !estadoFiltro.equals("en_revision")
                && !estadoFiltro.equals("aprobada")
                && !estadoFiltro.equals("rechazada")) {
            return null;
        }

        if (!especieFiltro.equals("todos")
                && !especieFiltro.equals("perro")
                && !especieFiltro.equals("gato")) {
            return null;
        }

        return solicitudRepositorio.filtrarSolicitudesPorEntidad(
                idEntidad,
                codigoFiltro,
                estadoFiltro,
                especieFiltro
        );
    }

    @Transactional
    public Solicitud rechazarSolicitud(Long idSolicitud) {

        if (idSolicitud == null) {
            return null;
        }

        Solicitud solicitud = solicitudRepositorio
                .findById(idSolicitud)
                .orElse(null);

        if (solicitud == null) {
            return null;
        }

        //solo se puede gestionar una solicitud que está en revisión
        if (!"EN_REVISION".equals(solicitud.getEstadoSolicitud())) {
            return null;
        }

        solicitud.setEstadoSolicitud("RECHAZADA");

        Solicitud solicitudActualizada =
                solicitudRepositorio.save(solicitud);

        //notificar al ciudadano solicitante
        if (solicitud.getCiudadano() != null
                && solicitud.getCiudadano().getUsuario() != null) {

            Notificacion notificacion = new Notificacion();

            notificacion.setSolicitud(solicitudActualizada);
            notificacion.setUsuario(
                    solicitud.getCiudadano().getUsuario()
            );
            notificacion.setTitulo("Solicitud rechazada");
            notificacion.setDescripcion(
                    "Tu solicitud de adopción para "
                            + solicitud.getMascota().getNombre()
                            + " ha sido rechazada."
            );
            notificacion.setFechaNotificacion(LocalDateTime.now());

            notificacionServicio.insertar(notificacion);
        }

        return solicitudActualizada;
    }

    @Transactional
    public Solicitud aprobarSolicitud(Long idSolicitud) {

        if (idSolicitud == null) {
            return null;
        }

        Solicitud solicitud = solicitudRepositorio
                .findById(idSolicitud)
                .orElse(null);

        if (solicitud == null) {
            return null;
        }

        //solo se puede gestionar una solicitud que está en revisión
        if (!"EN_REVISION".equals(solicitud.getEstadoSolicitud())) {
            return null;
        }

        Mascota mascota = solicitud.getMascota();

        if (mascota == null) {
            return null;
        }

        //aprobar la solicitud seleccionada
        solicitud.setEstadoSolicitud("APROBADA");

        Solicitud solicitudAprobada =
                solicitudRepositorio.save(solicitud);

        //buscar las demás solicitudes que siguen en revisión
        List<Solicitud> solicitudesPendientes =
                solicitudRepositorio
                        .findByMascota_IdMascotaAndEstadoSolicitud(
                                mascota.getIdMascota(),
                                "EN_REVISION"
                        );

        for (Solicitud otraSolicitud : solicitudesPendientes) {

            otraSolicitud.setEstadoSolicitud("RECHAZADA");

            Solicitud solicitudRechazada =
                    solicitudRepositorio.save(otraSolicitud);

            //notificar al ciudadano cuya solicitud fue rechazada automáticamente
            if (otraSolicitud.getCiudadano() != null
                    && otraSolicitud.getCiudadano().getUsuario() != null) {

                Notificacion notificacionRechazo = new Notificacion();

                notificacionRechazo.setSolicitud(solicitudRechazada);
                notificacionRechazo.setUsuario(
                        otraSolicitud.getCiudadano().getUsuario()
                );
                notificacionRechazo.setTitulo("Solicitud rechazada");
                notificacionRechazo.setDescripcion(
                        "Tu solicitud de adopción para "
                                + mascota.getNombre()
                                + " ha sido rechazada."
                );
                notificacionRechazo.setFechaNotificacion(LocalDateTime.now());

                notificacionServicio.insertar(notificacionRechazo);
            }
        }

        //la mascota deja de estar disponible para adopción
        mascotaServicio.cambiarEstado(
                mascota.getIdMascota(),
                false
        );

        //notificar al ciudadano cuya solicitud fue aprobada
        if (solicitud.getCiudadano() != null
                && solicitud.getCiudadano().getUsuario() != null) {

            Notificacion notificacion = new Notificacion();

            notificacion.setSolicitud(solicitudAprobada);
            notificacion.setUsuario(
                    solicitud.getCiudadano().getUsuario()
            );
            notificacion.setTitulo("Solicitud aprobada");
            notificacion.setDescripcion(
                    "Tu solicitud de adopción para "
                            + mascota.getNombre()
                            + " ha sido aprobada."
            );
            notificacion.setFechaNotificacion(LocalDateTime.now());

            notificacionServicio.insertar(notificacion);
        }

        return solicitudAprobada;
    }

}