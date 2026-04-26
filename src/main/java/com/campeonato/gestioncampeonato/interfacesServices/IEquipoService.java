package com.campeonato.gestioncampeonato.interfacesServices;

import com.campeonato.gestioncampeonato.models.Equipo;
import java.util.List;
import java.util.Optional;

public interface IEquipoService {
    List<Equipo> listar();
    List<Equipo> listarPorCampeonato(Long idCampeonato);
    Optional<Equipo> buscarPorId(Long id);
    Equipo guardar(Equipo equipo);
    void eliminar(Long id);
}