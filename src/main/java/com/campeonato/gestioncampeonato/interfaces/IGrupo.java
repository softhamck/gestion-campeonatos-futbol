package com.campeonato.gestioncampeonato.interfaces;

import com.campeonato.gestioncampeonato.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGrupo extends JpaRepository<Grupo, Long> {
    // Buscar grupos por campeonato
    List<Grupo> findByCampeonatoIdCampeonato(Long idCampeonato);
}