package com.upc.huellasdeauxilio.servicios;

import com.upc.huellasdeauxilio.entidades.ActualizarReporte;
import com.upc.huellasdeauxilio.repositorios.ActualizarReporteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActualizarReporteServicio {

    @Autowired
    private ActualizarReporteRepositorio actualizarReporteRepositorio;

    public ActualizarReporte insertar(ActualizarReporte actualizarReporte) {

        actualizarReporte.setFechaActualizacion(LocalDateTime.now());

        return actualizarReporteRepositorio.save(actualizarReporte);
    }

    public List<ActualizarReporte> listarPorReporte(Long idReporte) {
        return actualizarReporteRepositorio.findByReporte_IdReporteOrderByFechaActualizacionAsc(idReporte);
    }


}
