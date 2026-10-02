package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.dtos.ReporteDTO;
import com.upc.huellasdeauxilio.entidades.Reporte;
import com.upc.huellasdeauxilio.servicios.ReporteServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ReporteControlador {

    @Autowired
    private ReporteServicio reporteServicio;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/reporte")
    @PreAuthorize("hasRole('CIUDADANO')")
    public ReporteDTO insertar(@RequestBody ReporteDTO reporteDTO) {
        Reporte reporte = modelMapper.map(reporteDTO, Reporte.class);
        reporte = reporteServicio.insertar(reporte);
        return modelMapper.map(reporte, ReporteDTO.class);
    }

    @PutMapping("/reporte/estado/{idReporte}")
    @PreAuthorize("hasRole('ENTIDAD')")
    public ReporteDTO actualizarEstado(@PathVariable Long idReporte,
                                       @RequestBody ReporteDTO reporteDTO) {
        Reporte reporteActualizado = reporteServicio.actualizarEstado(idReporte, reporteDTO.getEstado());
        return (reporteActualizado != null) ? modelMapper.map(reporteActualizado, ReporteDTO.class) : null;
    }

    @GetMapping("/reportes/ciudadano/{idCiudadano}")
    @PreAuthorize("hasRole('CIUDADANO')")
    public List<ReporteDTO> listarPorCiudadano(@PathVariable Long idCiudadano) {
        return reporteServicio.listarPorCiudadano(idCiudadano).stream()
                .map(r -> modelMapper.map(r, ReporteDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/reportes/ciudadano/{idCiudadano}/resumen")
    public Map<String, Long> obtenerResumen(@PathVariable Long idCiudadano) {
        return reporteServicio.obtenerResumenCiudadano(idCiudadano);
    }

    @GetMapping("/reporte/{idReporte}/ciudadano/{idCiudadano}")
    public ReporteDTO buscarPorCodigo(@PathVariable Long idReporte, @PathVariable Long idCiudadano) {
        Reporte reporte = reporteServicio.buscarPorCodigoYCiudadano(idReporte, idCiudadano);
        return (reporte != null) ? modelMapper.map(reporte, ReporteDTO.class) : null;
    }

    @GetMapping("/reportes/ciudadano/{idCiudadano}/filtro")
    public List<ReporteDTO> filtrarReportes(
            @PathVariable Long idCiudadano,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String urgencia,
            @RequestParam(required = false) String distrito) {

        return reporteServicio.filtrarReportes(idCiudadano, estado, urgencia, distrito).stream()
                .map(r -> modelMapper.map(r, ReporteDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/reportes/entidad/{idEntidad}")
    @PreAuthorize("hasRole('ENTIDAD')")
    public List<ReporteDTO> listarPorEntidad(@PathVariable Long idEntidad) {
        return reporteServicio.listarPorEntidad(idEntidad).stream()
                .map(r -> modelMapper.map(r, ReporteDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/reportes/entidad/{idEntidad}/resumen")
    public Map<String, Long> obtenerResumenEntidad(@PathVariable Long idEntidad) {
        return reporteServicio.obtenerResumenEntidad(idEntidad);
    }

    @GetMapping("/reportes/entidad/{idEntidad}/filtro")
    public List<ReporteDTO> filtrarReportesEntidad(
            @PathVariable Long idEntidad,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String urgencia,
            @RequestParam(required = false) String distrito) {

        return reporteServicio.filtrarReportesEntidad(idEntidad, estado, urgencia, distrito).stream()
                .map(r -> modelMapper.map(r, ReporteDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/reportes_1/{Id}")
    @PreAuthorize("hasRole('ENTIDAD')")
    public ReporteDTO buscarPorId(@PathVariable Long Id){
        Reporte reporte = reporteServicio.buscarPorId(Id);
        return (reporte != null) ? modelMapper.map(reporte, ReporteDTO.class) : null;
    }

}
