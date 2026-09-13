package com.upc.huellasdeauxilio.controladores;

import com.upc.huellasdeauxilio.entidades.Ciudadano;
import com.upc.huellasdeauxilio.servicios.CiudadanoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CiudadanoControlador
{

    @Autowired
    private CiudadanoServicio ciudadanoServicio;

    @PostMapping("/ciudadano")
    public Ciudadano insertar(@RequestBody Ciudadano ciudadano) {
        return ciudadanoServicio.insertar(ciudadano);
    }

    @GetMapping("/ciudadano/dni/{dni}")
    public Ciudadano buscarPorDni(@PathVariable String dni) {
        return ciudadanoServicio.buscarPorDni(dni);
    }

    @GetMapping("/ciudadano/usuario/{idUsuario}")
    public Ciudadano buscarPorUsuario(@PathVariable Long idUsuario) {
        return ciudadanoServicio.buscarPorUsuario(idUsuario);
    }

    @GetMapping("/ciudadano/perfil/{idCiudadano}")
    public Ciudadano obtenerPerfil(@PathVariable Long idCiudadano)
    {
        return ciudadanoServicio.obtenerPerfil(idCiudadano);
    }

    @PutMapping("/ciudadano/perfil/{idCiudadano}")
    public String actualizarPerfil(@PathVariable Long idCiudadano, @RequestBody Ciudadano ciudadano)
    {
        Ciudadano actualizado = ciudadanoServicio.actualizarPerfil(idCiudadano, ciudadano);

        return "Perfil actualizado correctamente";
    }
}

