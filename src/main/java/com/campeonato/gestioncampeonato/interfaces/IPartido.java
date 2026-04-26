package com.campeonato.gestioncampeonato.interfaces;

import com.campeonato.gestioncampeonato.models.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPartido extends JpaRepository<Partido, Long> {
    // Buscar partidos por grupo
    List<Partido> findByGrupoIdGrupo(Long idGrupo);
    
    // Buscar partidos por equipo (local o visitante)
    List<Partido> findByEquipoLocalIdEquipoOrEquipoVisitanteIdEquipo(Long idLocal, Long idVisitante);
}