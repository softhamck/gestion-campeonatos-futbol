package com.campeonato.gestioncampeonato.interfaces;

import com.campeonato.gestioncampeonato.models.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICampeonato extends JpaRepository<Campeonato, Long> {
    // Métodos CRUD ya incluidos por JpaRepository
}