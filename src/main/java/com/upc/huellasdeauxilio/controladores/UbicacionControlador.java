package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.dtos.UbicacionDTO;
import com.upc.huellasdeauxilio.entidades.Ubicacion;
import com.upc.huellasdeauxilio.servicios.UbicacionServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UbicacionControlador {

    @Autowired
    private UbicacionServicio ubicacionServicio;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/ubicacion")
    public UbicacionDTO insertar(
            @RequestBody UbicacionDTO ubicacionDTO) {

        Ubicacion ubicacion =
                modelMapper.map(
                        ubicacionDTO,
                        Ubicacion.class
                );

        ubicacion =
                ubicacionServicio.insertar(ubicacion);

        return modelMapper.map(
                ubicacion,
                UbicacionDTO.class
        );
    }
}