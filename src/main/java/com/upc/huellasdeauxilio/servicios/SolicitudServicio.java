package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.dtos.SolicitudDTO;
import com.upc.huellasdeauxilio.entidades.Ciudadano;
import com.upc.huellasdeauxilio.entidades.Mascota;
import com.upc.huellasdeauxilio.entidades.Notificacion;
import com.upc.huellasdeauxilio.entidades.Solicitud;
import com.upc.huellasdeauxilio.repositorios.CiudadanoRepositorio;
import com.upc.huellasdeauxilio.repositorios.MascotaRepositorio;
import com.upc.huellasdeauxilio.repositorios.SolicitudRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Transactional
    public SolicitudDTO insertar(Long idCiudadano, SolicitudDTO solicitudDTO)
    {
        if (idCiudadano == null
                || solicitudDTO == null
                || solicitudDTO.getMascotaIdMascota() == null
                || solicitudDTO.getTipoVivienda() == null
                || solicitudDTO.getTipoVivienda().isBlank()
                || solicitudDTO.getMotivo() == null
                || solicitudDTO.getMotivo().isBlank()
                || solicitudDTO.getExperiencia() == null) {

            return null;
        }

        Ciudadano ciudadano = ciudadanoRepositorio
                .findById(idCiudadano)
                .orElse(null);

        if (ciudadano == null || ciudadano.getUsuario() == null) {
            return null;
        }

        Mascota mascota = mascotaRepositorio
                .findById(solicitudDTO.getMascotaIdMascota())
                .orElse(null);

        if (mascota == null
                || !Boolean.TRUE.equals(mascota.getEstado())) {

            return null;
        }

        // Convertir DTO a entidad.
        Solicitud solicitud = modelMapper.map(
                solicitudDTO,
                Solicitud.class
        );

        // No aceptar estos valores desde el cliente.
        solicitud.setIdSolicitud(null);
        solicitud.setCiudadano(ciudadano);
        solicitud.setMascota(mascota);
        solicitud.setFechaSolicitud(LocalDateTime.now());
        solicitud.setEstadoSolicitud("ENVIADA");

        solicitud.setTipoVivienda(
                solicitudDTO.getTipoVivienda().trim()
        );
        solicitud.setMotivo(solicitudDTO.getMotivo().trim());
        solicitud.setExperiencia(solicitudDTO.getExperiencia());

        Solicitud solicitudGuardada =
                solicitudRepositorio.save(solicitud);

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

        // Convertir la entidad guardada a DTO.
        SolicitudDTO respuesta = modelMapper.map(
                solicitudGuardada,
                SolicitudDTO.class
        );

        respuesta.setMascotaIdMascota(mascota.getIdMascota());
        respuesta.setCiudadanoIdCiudadano(
                ciudadano.getIdCiudadano()
        );

        return respuesta;
    }

    public List<SolicitudDTO> listarPorCiudadano(Long idCiudadano)
    {

        if (idCiudadano == null) {
            return null;
        }

        Ciudadano ciudadano = ciudadanoRepositorio
                .findById(idCiudadano)
                .orElse(null);

        if (ciudadano == null) {
            return null;
        }

        return solicitudRepositorio
                .findByCiudadano_IdCiudadanoOrderByFechaSolicitudDesc(idCiudadano).stream()
                .map(solicitud ->
                {SolicitudDTO dto = modelMapper.map(solicitud, SolicitudDTO.class);
                    dto.setMascotaIdMascota(solicitud.getMascota().getIdMascota());
                    dto.setCiudadanoIdCiudadano(solicitud.getCiudadano().getIdCiudadano());
                    return dto;
                }).collect(Collectors.toList());
    }

    public SolicitudDTO buscarPorCodigoYCiudadano(Long idSolicitud, Long idCiudadano)
    {
        if (idSolicitud == null || idCiudadano == null)
        {
            return null;
        }

        Solicitud solicitud = solicitudRepositorio.findByIdSolicitudAndCiudadano_IdCiudadano(idSolicitud, idCiudadano);

        if (solicitud == null)
        {
            return null;
        }

        SolicitudDTO dto = modelMapper.map(solicitud, SolicitudDTO.class);
        dto.setMascotaIdMascota(solicitud.getMascota().getIdMascota());
        dto.setCiudadanoIdCiudadano(solicitud.getCiudadano().getIdCiudadano());
        return dto;
    }

    //esta es una funcion extra para quitar especios extra y transformar
    //mayusculas a minusculas y facilitar la filtracion
    private String normalizarFiltro(String valor) {
        if (valor == null || valor.isBlank()) {
            return "todos";
        }

        return valor.trim().toLowerCase(Locale.ROOT);
    }

    public List<SolicitudDTO> filtrarSolicitudes(Long idCiudadano, String nombreMascota, String estado)
    {
        Ciudadano ciudadano = buscarCiudadano(idCiudadano);

        if (ciudadano == null)
        {
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

        return solicitudRepositorio.filtrarSolicitudes(idCiudadano, normalizarFiltro(nombreMascota), estadoFiltro)
                .stream()
                .map(solicitud ->
                {
                    SolicitudDTO dto = modelMapper.map(solicitud, SolicitudDTO.class);
                    dto.setMascotaIdMascota(solicitud.getMascota().getIdMascota());
                    dto.setCiudadanoIdCiudadano(solicitud.getCiudadano().getIdCiudadano());
                    return dto;
                }).collect(Collectors.toList());

    }

    public List<SolicitudDTO> listarPorMascota(Long idMascota) {
        if (idMascota == null) {
            return null;
        }
        Mascota mascota = mascotaRepositorio.findById(idMascota).orElse(null);

        if (mascota == null)
        {
            return null;
        }

        return solicitudRepositorio.findByMascota_IdMascotaOrderByFechaSolicitudDesc(idMascota).stream()
                .map(solicitud -> {SolicitudDTO dto = modelMapper.map(solicitud, SolicitudDTO.class);
                    dto.setMascotaIdMascota(solicitud.getMascota().getIdMascota());
                    dto.setCiudadanoIdCiudadano(solicitud.getCiudadano().getIdCiudadano());
                    return dto;}).collect(Collectors.toList());
    }

    public SolicitudDTO buscarPorSolicitudYMascota(Long idSolicitud, Long idMascota) {
        if (idSolicitud == null || idMascota == null)
        {
            return null;
        }

        Mascota mascota = mascotaRepositorio.findById(idMascota).orElse(null);

        if (mascota == null)
        {
            return null;
        }

        Solicitud solicitud = solicitudRepositorio.findByIdSolicitudAndMascota_IdMascota(idSolicitud, idMascota);

        if (solicitud == null)
        {
            return null;
        }

        SolicitudDTO dto = modelMapper.map(solicitud, SolicitudDTO.class);
        dto.setMascotaIdMascota(solicitud.getMascota().getIdMascota());
        dto.setCiudadanoIdCiudadano(solicitud.getCiudadano().getIdCiudadano());
        return dto;
    }

    public List<Solicitud> listarPorEntidad(Long idEntidad) {

        if (idEntidad == null) {
            return null;
        }

        return solicitudRepositorio
                .findByMascota_Entidad_IdEntidadOrderByFechaSolicitudDesc(idEntidad);
    }

    public List<SolicitudDTO> filtrarSolicitudesPorMascota(Long idMascota, String codigo, String estado) {
        if (idMascota == null) {
            return null;
        }

        Mascota mascota = mascotaRepositorio
                .findById(idMascota)
                .orElse(null);

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

        return solicitudRepositorio.filtrarSolicitudesPorMascota(idMascota, normalizarFiltro(codigo), estadoFiltro).stream()
                .map(solicitud -> {SolicitudDTO dto = modelMapper.map(solicitud, SolicitudDTO.class);
                    dto.setMascotaIdMascota(solicitud.getMascota().getIdMascota());dto.setCiudadanoIdCiudadano(solicitud.getCiudadano().getIdCiudadano());
                    return dto;
                }).collect(Collectors.toList());
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

    private SolicitudDTO convertirADTO(Solicitud solicitud) {
        SolicitudDTO dto = modelMapper.map(
                solicitud,
                SolicitudDTO.class
        );

        if (solicitud.getMascota() != null) {
            dto.setMascotaIdMascota(
                    solicitud.getMascota().getIdMascota()
            );
        }

        if (solicitud.getCiudadano() != null) {
            dto.setCiudadanoIdCiudadano(
                    solicitud.getCiudadano().getIdCiudadano()
            );
        }

        return dto;
    }
}