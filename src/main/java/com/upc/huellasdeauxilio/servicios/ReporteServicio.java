package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.Reporte;
import com.upc.huellasdeauxilio.repositorios.ReporteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
