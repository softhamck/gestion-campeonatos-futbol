package com.campeonato.gestioncampeonato.interfaces;

import com.campeonato.gestioncampeonato.models.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEquipo extends JpaRepository<Equipo, Long> {
    // Buscar equipos por campeonato
    List<Equipo> findByCampeonatoIdCampeonato(Long idCampeonato);
    
    // Buscar equipos por grupo
    List<Equipo> findByGrupoIdGrupo(Long idGrupo);
}