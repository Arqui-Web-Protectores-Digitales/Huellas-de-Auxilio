package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.EvidenciaReporte;
import com.upc.huellasdeauxilio.servicios.EvidenciaReporteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/evidenciareporte/reporte/{idReporte}")
    public List<EvidenciaReporte> buscarPorReporte(@PathVariable Long idReporte) {
        return evidenciaReporteServicio.buscarPorReporte(idReporte);
    }

    @DeleteMapping("/evidenciareporte/{idEvidencia}")
    public void eliminar(@PathVariable Long idEvidencia) {
        evidenciaReporteServicio.eliminar(idEvidencia);
    }


}
