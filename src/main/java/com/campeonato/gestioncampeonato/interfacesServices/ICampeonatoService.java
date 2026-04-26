package com.campeonato.gestioncampeonato.interfacesServices;

import com.campeonato.gestioncampeonato.models.Campeonato;
import java.util.List;
import java.util.Optional;

public interface ICampeonatoService {
    List<Campeonato> listar();
    Optional<Campeonato> buscarPorId(Long id);
    Campeonato guardar(Campeonato campeonato);
    void eliminar(Long id);
}