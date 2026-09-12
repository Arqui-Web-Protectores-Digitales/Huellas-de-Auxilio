package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.EvidenciaReporte;
import com.upc.huellasdeauxilio.repositorios.EvidenciaReporteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvidenciaReporteServicio {

    @Autowired
    private EvidenciaReporteRepositorio evidenciaReporteRepositorio;

    public EvidenciaReporte insertar(EvidenciaReporte evidenciaReporte) {
        return evidenciaReporteRepositorio.save(evidenciaReporte);
    }
}
