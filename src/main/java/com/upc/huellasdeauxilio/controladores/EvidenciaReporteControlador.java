package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.EvidenciaReporte;
import com.upc.huellasdeauxilio.servicios.EvidenciaReporteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EvidenciaReporteControlador {

    @Autowired
    private EvidenciaReporteServicio evidenciaReporteServicio;

    @PostMapping("/evidenciareporte")
    public EvidenciaReporte insertar(
            @RequestBody EvidenciaReporte evidenciaReporte) {

        return evidenciaReporteServicio.insertar(evidenciaReporte);
    }
}
