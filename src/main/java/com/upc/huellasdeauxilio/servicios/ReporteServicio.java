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
}
