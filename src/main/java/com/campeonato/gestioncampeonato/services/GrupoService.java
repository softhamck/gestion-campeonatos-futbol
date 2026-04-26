package com.campeonato.gestioncampeonato.services;

import com.campeonato.gestioncampeonato.interfaces.IGrupo;
import com.campeonato.gestioncampeonato.interfaces.ICampeonato;
import com.campeonato.gestioncampeonato.interfaces.IEquipo;
import com.campeonato.gestioncampeonato.interfaces.IPartido;
import com.campeonato.gestioncampeonato.interfacesServices.IGrupoService;
import com.campeonato.gestioncampeonato.models.Grupo;
import com.campeonato.gestioncampeonato.models.Campeonato;
import com.campeonato.gestioncampeonato.models.Equipo;
import com.campeonato.gestioncampeonato.models.Partido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GrupoService implements IGrupoService {
    
    @Autowired
    private IGrupo repositorio;
    
    @Autowired
    private ICampeonato campeonatoRepositorio;
    
    @Autowired
    private IEquipo equipoRepositorio;
    
    @Autowired
    private IPartido partidoRepositorio;
    
    @Override
    public List<Grupo> listar() {
        return repositorio.findAll();
    }
    
    @Override
    public List<Grupo> listarPorCampeonato(Long idCampeonato) {
        return repositorio.findByCampeonatoIdCampeonato(idCampeonato);
    }
    
    @Override
    public Optional<Grupo> buscarPorId(Long id) {
        return repositorio.findById(id);
    }
    
    @Override
    public Grupo guardar(Grupo grupo) {
        return repositorio.save(grupo);
    }
    
    @Override
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    
    @Override
    @Transactional
    public void generarGrupos(Long idCampeonato, int cantidadGrupos) {
        Optional<Campeonato> campeonatoOpt = campeonatoRepositorio.findById(idCampeonato);
        if (!campeonatoOpt.isPresent()) {
            throw new RuntimeException("Campeonato no encontrado");
        }
        
        Campeonato campeonato = campeonatoOpt.get();
        List<Equipo> equipos = equipoRepositorio.findByCampeonatoIdCampeonato(idCampeonato);
        
        if (equipos.isEmpty()) {
            throw new RuntimeException("No hay equipos registrados en este campeonato");
        }
        
        // PASO 1: Desasignar equipos de grupos anteriores
        List<Grupo> gruposExistentes = repositorio.findByCampeonatoIdCampeonato(idCampeonato);
        for (Grupo grupo : gruposExistentes) {
            List<Equipo> equiposDelGrupo = equipoRepositorio.findByGrupoIdGrupo(grupo.getIdGrupo());
            for (Equipo equipo : equiposDelGrupo) {
                equipo.setGrupo(null);
                equipoRepositorio.save(equipo);
            }
            
            // Eliminar partidos del grupo
            List<Partido> partidos = partidoRepositorio.findByGrupoIdGrupo(grupo.getIdGrupo());
            if (partidos != null && !partidos.isEmpty()) {
                partidoRepositorio.deleteAll(partidos);
            }
        }
        
        // PASO 2: Eliminar grupos anteriores
        repositorio.deleteAll(gruposExistentes);
        
        // PASO 3: Mezclar equipos aleatoriamente
        Collections.shuffle(equipos);
        
        // PASO 4: Crear nuevos grupos
        char letraGrupo = 'A';
        int equiposPorGrupo = (int) Math.ceil((double) equipos.size() / cantidadGrupos);
        
        for (int i = 0; i < cantidadGrupos; i++) {
            Grupo grupo = new Grupo();
            grupo.setNombre("Grupo " + (char)(letraGrupo + i));
            grupo.setCampeonato(campeonato);
            grupo = repositorio.save(grupo);
            
            // Asignar equipos al grupo
            int inicio = i * equiposPorGrupo;
            int fin = Math.min(inicio + equiposPorGrupo, equipos.size());
            
            for (int j = inicio; j < fin; j++) {
                Equipo equipo = equipos.get(j);
                equipo.setGrupo(grupo);
                equipoRepositorio.save(equipo);
            }
        }
    }
}