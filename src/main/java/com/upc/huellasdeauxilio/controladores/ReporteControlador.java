package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Reporte;
import com.upc.huellasdeauxilio.servicios.ReporteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReporteControlador {

    @Autowired
    private ReporteServicio reporteServicio;

    @PostMapping("/reporte")
    public Reporte insertar(@RequestBody Reporte reporte) {
        return reporteServicio.insertar(reporte);
    }
}
