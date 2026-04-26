package com.campeonato.gestioncampeonato.services;

import com.campeonato.gestioncampeonato.interfaces.ICampeonato;
import com.campeonato.gestioncampeonato.interfacesServices.ICampeonatoService;
import com.campeonato.gestioncampeonato.models.Campeonato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampeonatoService implements ICampeonatoService {
    
    @Autowired
    private ICampeonato repositorio;
    
    @Override
    public List<Campeonato> listar() {
        return repositorio.findAll();
    }
    
    @Override
    public Optional<Campeonato> buscarPorId(Long id) {
        return repositorio.findById(id);
    }
    
    @Override
    public Campeonato guardar(Campeonato campeonato) {
        return repositorio.save(campeonato);
    }
    
    @Override
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}