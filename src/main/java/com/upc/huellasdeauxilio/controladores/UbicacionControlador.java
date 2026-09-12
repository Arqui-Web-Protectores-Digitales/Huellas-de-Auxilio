package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Ubicacion;
import com.upc.huellasdeauxilio.servicios.UbicacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UbicacionControlador {

    @Autowired
    private UbicacionServicio ubicacionServicio;

    @PostMapping("/ubicacion")
    public Ubicacion insertar(@RequestBody Ubicacion ubicacion) {
        return ubicacionServicio.insertar(ubicacion);
    }
}