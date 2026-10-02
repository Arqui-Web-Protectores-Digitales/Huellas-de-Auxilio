package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.dtos.EvidenciaReporteDTO;
import com.upc.huellasdeauxilio.entidades.EvidenciaReporte;
import com.upc.huellasdeauxilio.servicios.EvidenciaReporteServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EvidenciaReporteControlador {

    @Autowired
    private EvidenciaReporteServicio evidenciaReporteServicio;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/evidenciareporte")
    @PreAuthorize("hasRole('CIUDADANO')")
    public EvidenciaReporteDTO insertar(@RequestBody EvidenciaReporteDTO evidenciaDTO) {
        EvidenciaReporte evidencia = modelMapper.map(evidenciaDTO, EvidenciaReporte.class);

        evidencia = evidenciaReporteServicio.insertar(evidencia);

        return modelMapper.map(evidencia, EvidenciaReporteDTO.class);
    }

    @GetMapping("/evidenciareporte/reporte/{idReporte}")
    @PreAuthorize("hasAnyRole('CIUDADANO', 'ENTIDAD')")
    public List<EvidenciaReporteDTO> buscarPorReporte(@PathVariable Long idReporte) {
        List<EvidenciaReporte> evidencias = evidenciaReporteServicio.buscarPorReporte(idReporte);

        return evidencias.stream()
                .map(evidencia -> modelMapper.map(evidencia, EvidenciaReporteDTO.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/evidenciareporte/{idEvidencia}")
    @PreAuthorize("hasRole('CIUDADANO')")
    public void eliminar(@PathVariable Long idEvidencia) {
        evidenciaReporteServicio.eliminar(idEvidencia);
    }

}
