package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Entidad;
import com.upc.huellasdeauxilio.entidades.Notificacion;
import com.upc.huellasdeauxilio.entidades.Reporte;
import com.upc.huellasdeauxilio.entidades.Ubicacion;
import com.upc.huellasdeauxilio.repositorios.ReporteRepositorio;
import com.upc.huellasdeauxilio.repositorios.UbicacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteServicio {

    @Autowired
    private ReporteRepositorio reporteRepositorio;

    @Autowired
    private NotificacionServicio notificacionServicio;

    @Autowired
    private UbicacionRepositorio ubicacionRepositorio;

    @Autowired
    private EntidadServicio entidadServicio;

    public Reporte insertar(Reporte reporte) {

        Ubicacion ubicacion = ubicacionRepositorio.findById(reporte.getUbicacion().getIdUbicacion()).orElse(null);

        if (ubicacion != null && ubicacion.getLatitud() != null && ubicacion.getLongitud() != null) {
            Entidad entidadCercana = entidadServicio.obtenerEntidadMasCercana(ubicacion.getLatitud(), ubicacion.getLongitud());

            reporte.setEntidad(entidadCercana);
        }

        Reporte reporteGuardado = reporteRepositorio.save(reporte);


        Notificacion notiCiudadano = new Notificacion();
        notiCiudadano.setReporte(reporteGuardado);

        notiCiudadano.setUsuario(reporteGuardado.getCiudadano().getUsuario());
        notiCiudadano.setTitulo("Reporte Registrado");
        notiCiudadano.setDescripcion("Tu reporte ha sido registrado y enviado exitosamente.");
        notificacionServicio.insertar(notiCiudadano);

        if(reporteGuardado.getEntidad() != null) {
            Notificacion notiEntidad = new Notificacion();
            notiEntidad.setReporte(reporteGuardado);

            notiEntidad.setUsuario(reporteGuardado.getEntidad().getUsuario());
            notiEntidad.setTitulo("Nuevo Reporte Asignado");
            notiEntidad.setDescripcion("Se te ha asignado un nuevo caso de maltrato animal.");
            notificacionServicio.insertar(notiEntidad);
        }

        return reporteGuardado;
    }

    public Reporte actualizarEstado(Long idReporte, String nuevoEstado) {
        Reporte reporte = reporteRepositorio.findById(idReporte).orElse(null);

        if (reporte == null) {
            return null;
        }

        String estadoActual = reporte.getEstado();


        if ((estadoActual.equals("R") && nuevoEstado.equals("ER")) ||
                (estadoActual.equals("ER") && nuevoEstado.equals("AT"))) {

            reporte.setEstado(nuevoEstado);
            Reporte reporteActualizado = reporteRepositorio.save(reporte);

            Notificacion notiCiudadano = new Notificacion();
            notiCiudadano.setReporte(reporteActualizado);
            notiCiudadano.setUsuario(reporteActualizado.getCiudadano().getUsuario());
            notiCiudadano.setTitulo("Actualización de Reporte");
            notiCiudadano.setDescripcion("Tu reporte ha cambiado al estado: " + nuevoEstado);
            notificacionServicio.insertar(notiCiudadano);

            return reporteActualizado;
        }

        return null;
    }

    // para Ciudadano
    public List<Reporte> listarPorCiudadano(Long idCiudadano) {
        return reporteRepositorio.findByCiudadano_IdCiudadano(idCiudadano);
    }

    public Reporte buscarPorCodigoYCiudadano(Long idReporte, Long idCiudadano) {
        return reporteRepositorio.findByIdReporteAndCiudadano_IdCiudadano(idReporte, idCiudadano);
    }

    public Map<String, Long> obtenerResumenCiudadano(Long idCiudadano) {
        long total = reporteRepositorio.countByCiudadano_IdCiudadano(idCiudadano);
        long enRevision = reporteRepositorio.countByCiudadano_IdCiudadanoAndEstado(idCiudadano, "ER");
        long atendidos = reporteRepositorio.countByCiudadano_IdCiudadanoAndEstado(idCiudadano, "AT");

        Map<String, Long> resumen = new HashMap<>();
        resumen.put("total", total);
        resumen.put("enRevision", enRevision);
        resumen.put("atendidos", atendidos);
        return resumen;
    }

    public List<Reporte> filtrarReportes(Long idCiudadano, String estado, String urgencia, String distrito) {
        return reporteRepositorio.filtrarReportes(idCiudadano, estado, urgencia, distrito);
    }

    // para  Entidad
    public List<Reporte> listarPorEntidad(Long idEntidad) {
        return reporteRepositorio.findByEntidad_IdEntidadOrderByFechaReporteDesc(idEntidad);
    }

    public Map<String, Long> obtenerResumenEntidad(Long idEntidad) {
        long total = reporteRepositorio.countByEntidad_IdEntidad(idEntidad);
        long enRevision = reporteRepositorio.countByEntidad_IdEntidadAndEstado(idEntidad, "ER");
        long atendidos = reporteRepositorio.countByEntidad_IdEntidadAndEstado(idEntidad, "AT");

        Map<String, Long> resumen = new HashMap<>();
        resumen.put("total", total);
        resumen.put("enRevision", enRevision);
        resumen.put("atendidos", atendidos);
        return resumen;
    }

    public List<Reporte> filtrarReportesEntidad(Long idEntidad, String estado, String urgencia, String distrito) {
        return reporteRepositorio.filtrarReportesPorEntidad(idEntidad, estado, urgencia, distrito);
    }

    //Buscar Por Id cuando una entidad requiera de visualizar una entidads
    public Reporte buscarPorId(Long Id){

        return reporteRepositorio.findById(Id).orElse(null);
    }
}
