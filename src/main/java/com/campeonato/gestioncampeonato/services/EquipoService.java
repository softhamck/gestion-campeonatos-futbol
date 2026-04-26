package com.campeonato.gestioncampeonato.services;

import com.campeonato.gestioncampeonato.interfaces.IEquipo;
import com.campeonato.gestioncampeonato.interfaces.ICampeonato;
import com.campeonato.gestioncampeonato.interfacesServices.IEquipoService;
import com.campeonato.gestioncampeonato.models.Equipo;
import com.campeonato.gestioncampeonato.models.Campeonato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService implements IEquipoService {
    
    @Autowired
    private IEquipo repositorio;
    
    @Autowired
    private ICampeonato campeonatoRepositorio;
    
    @Override
    public List<Equipo> listar() {
        return repositorio.findAll();
    }
    
    @Override
    public List<Equipo> listarPorCampeonato(Long idCampeonato) {
        return repositorio.findByCampeonatoIdCampeonato(idCampeonato);
    }
    
    @Override
    public Optional<Equipo> buscarPorId(Long id) {
        return repositorio.findById(id);
    }
    
    @Override
    public Equipo guardar(Equipo equipo) {
        if (equipo.getCampeonato() != null && equipo.getCampeonato().getIdCampeonato() != null) {
            Optional<Campeonato> campeonato = campeonatoRepositorio.findById(equipo.getCampeonato().getIdCampeonato());
            if (campeonato.isPresent()) {
                equipo.setCampeonato(campeonato.get());
                return repositorio.save(equipo);
            }
        }
        return null;
    }
    
    @Override
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}