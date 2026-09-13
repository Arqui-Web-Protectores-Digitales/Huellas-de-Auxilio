package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Reporte;
import com.upc.huellasdeauxilio.repositorios.ReporteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteServicio {

    @Autowired
    private ReporteRepositorio reporteRepositorio;

    public Reporte insertar(Reporte reporte) {
        return reporteRepositorio.save(reporte);
    }

    public Reporte actualizarEstado(Long idReporte, String nuevoEstado) {

        Reporte reporte = reporteRepositorio.findById(idReporte).orElse(null);

        if (reporte == null) {
            return null;
        }

        String estadoActual = reporte.getEstado();

        if (estadoActual.equals("R") && nuevoEstado.equals("ER")) {
            reporte.setEstado(nuevoEstado);
            return reporteRepositorio.save(reporte);
        }

        if (estadoActual.equals("ER") && nuevoEstado.equals("AT")) {
            reporte.setEstado(nuevoEstado);
            return reporteRepositorio.save(reporte);
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

}
