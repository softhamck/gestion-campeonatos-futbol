package com.campeonato.gestioncampeonato.services;

import com.campeonato.gestioncampeonato.interfaces.IPartido;
import com.campeonato.gestioncampeonato.interfaces.IGrupo;
import com.campeonato.gestioncampeonato.interfaces.IEquipo;
import com.campeonato.gestioncampeonato.interfacesServices.IPartidoService;
import com.campeonato.gestioncampeonato.models.Partido;
import com.campeonato.gestioncampeonato.models.Grupo;
import com.campeonato.gestioncampeonato.models.Equipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PartidoService implements IPartidoService {
    
    @Autowired
    private IPartido repositorio;
    
    @Autowired
    private IGrupo grupoRepositorio;
    
    @Autowired
    private IEquipo equipoRepositorio;
    
    @Override
    public List<Partido> listar() {
        return repositorio.findAll();
    }
    
    @Override
    public List<Partido> listarPorGrupo(Long idGrupo) {
        return repositorio.findByGrupoIdGrupo(idGrupo);
    }
    
    @Override
    public Optional<Partido> buscarPorId(Long id) {
        return repositorio.findById(id);
    }
    
    @Override
    public Partido guardar(Partido partido) {
        return repositorio.save(partido);
    }
    
    @Override
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    @Override
    @Transactional
    public void generarCalendario(Long idGrupo) {
        Optional<Grupo> grupoOpt = grupoRepositorio.findById(idGrupo);
        if (!grupoOpt.isPresent()) {
            throw new RuntimeException("Grupo no encontrado");
        }
        
        Grupo grupo = grupoOpt.get();
        List<Equipo> equipos = equipoRepositorio.findByGrupoIdGrupo(idGrupo);
        
        if (equipos.size() < 2) {
            throw new RuntimeException("Se necesitan al menos 2 equipos en el grupo para generar partidos");
        }
        
        // Eliminar partidos existentes del grupo
        List<Partido> partidosExistentes = repositorio.findByGrupoIdGrupo(idGrupo);
        if (partidosExistentes != null && !partidosExistentes.isEmpty()) {
            repositorio.deleteAll(partidosExistentes);
        }
        
        // Generar todos contra todos (solo ida para simplificar)
        int numeroPartido = 1;
        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                Partido partido = new Partido();
                partido.setEquipoLocal(equipos.get(i));
                partido.setEquipoVisitante(equipos.get(j));
                partido.setGrupo(grupo);
                partido.setFechaHora(LocalDateTime.now().plusDays(numeroPartido));
                partido.setLugar("Por definir");
                partido.setGolesLocal(0);
                partido.setGolesVisitante(0);
                partido.setEstado("PENDIENTE");
                repositorio.save(partido);
                numeroPartido++;
            }
        }
    }
    
    @Override
    public void registrarResultado(Long idPartido, int golesLocal, int golesVisitante) {
        Optional<Partido> partidoOpt = repositorio.findById(idPartido);
        if (partidoOpt.isPresent()) {
            Partido partido = partidoOpt.get();
            partido.setGolesLocal(golesLocal);
            partido.setGolesVisitante(golesVisitante);
            partido.setEstado("JUGADO");
            repositorio.save(partido);
        }
    }
}