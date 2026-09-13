package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.ActualizarReporte;
import com.upc.huellasdeauxilio.servicios.ActualizarReporteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ActualizarReporteControlador {

    @Autowired
    private ActualizarReporteServicio actualizarReporteServicio;

    @PostMapping("/actualizarreporte")
    public ActualizarReporte insertar(
            @RequestBody ActualizarReporte actualizarReporte) {

        return actualizarReporteServicio.insertar(actualizarReporte);
    }

    @GetMapping("/actualizarreporte/reporte/{idReporte}")
    public List<ActualizarReporte> listarPorReporte(@PathVariable Long idReporte) {
        return actualizarReporteServicio.listarPorReporte(idReporte);
    }


}
