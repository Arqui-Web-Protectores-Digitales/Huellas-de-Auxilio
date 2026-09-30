package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.dtos.ActualizarReporteDTO;
import com.upc.huellasdeauxilio.entidades.ActualizarReporte;
import com.upc.huellasdeauxilio.servicios.ActualizarReporteServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ActualizarReporteControlador {

    @Autowired
    private ActualizarReporteServicio actualizarReporteServicio;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/actualizarreporte")
    public ActualizarReporteDTO insertar(@RequestBody ActualizarReporteDTO dto) {
        ActualizarReporte actualizarReporte = modelMapper.map(dto, ActualizarReporte.class);
        actualizarReporte = actualizarReporteServicio.insertar(actualizarReporte);
        return modelMapper.map(actualizarReporte, ActualizarReporteDTO.class);
    }

    @GetMapping("/actualizarreporte/reporte/{idReporte}")
    public List<ActualizarReporteDTO> listarPorReporte(@PathVariable Long idReporte) {
        List<ActualizarReporte> historial = actualizarReporteServicio.listarPorReporte(idReporte);
        return historial.stream()
                .map(h -> modelMapper.map(h, ActualizarReporteDTO.class))
                .collect(Collectors.toList());
    }

}
