package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Reporte;
import com.upc.huellasdeauxilio.servicios.ReporteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReporteControlador {

    @Autowired
    private ReporteServicio reporteServicio;

    @PostMapping("/reporte")
    public Reporte insertar(@RequestBody Reporte reporte) {
        return reporteServicio.insertar(reporte);
    }

    @PutMapping("/reporte/estado/{idReporte}")
    public Reporte actualizarEstado(@PathVariable Long idReporte,
                                    @RequestBody Reporte reporte) {

        return reporteServicio.actualizarEstado(
                idReporte,
                reporte.getEstado()
        );
    }

    @GetMapping("/reportes/ciudadano/{idCiudadano}")
    public List<Reporte> listarPorCiudadano(@PathVariable Long idCiudadano) {
        return reporteServicio.listarPorCiudadano(idCiudadano);
    }

    @GetMapping("/reportes/ciudadano/{idCiudadano}/resumen")
    public Map<String, Long> obtenerResumen(@PathVariable Long idCiudadano) {
        return reporteServicio.obtenerResumenCiudadano(idCiudadano);
    }

    @GetMapping("/reporte/{idReporte}/ciudadano/{idCiudadano}")
    public Reporte buscarPorCodigo(@PathVariable Long idReporte, @PathVariable Long idCiudadano) {
        return reporteServicio.buscarPorCodigoYCiudadano(idReporte, idCiudadano);
    }

    @GetMapping("/reportes/ciudadano/{idCiudadano}/filtro")
    public List<Reporte> filtrarReportes(
            @PathVariable Long idCiudadano,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String urgencia,
            @RequestParam(required = false) String distrito) {

        return reporteServicio.filtrarReportes(idCiudadano, estado, urgencia, distrito);
    }
}
