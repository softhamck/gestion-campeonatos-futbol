package com.campeonato.gestioncampeonato.interfacesServices;

import com.campeonato.gestioncampeonato.models.Grupo;
import java.util.List;
import java.util.Optional;

public interface IGrupoService {
    List<Grupo> listar();
    List<Grupo> listarPorCampeonato(Long idCampeonato);
    Optional<Grupo> buscarPorId(Long id);
    Grupo guardar(Grupo grupo);
    void eliminar(Long id);
    void generarGrupos(Long idCampeonato, int cantidadGrupos);
}