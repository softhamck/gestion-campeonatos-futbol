package com.campeonato.gestioncampeonato.controllers;

import com.campeonato.gestioncampeonato.interfacesServices.IPartidoService;
import com.campeonato.gestioncampeonato.interfacesServices.IGrupoService;
import com.campeonato.gestioncampeonato.models.Grupo;
import com.campeonato.gestioncampeonato.models.Partido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/partidos")
public class PartidoController {
    
    @Autowired
    private IPartidoService servicio;
    
    @Autowired
    private IGrupoService grupoService;
    
    @GetMapping("/listar/{idGrupo}")
    public String listar(@PathVariable Long idGrupo, Model model) {
        List<Partido> partidos = servicio.listarPorGrupo(idGrupo);
        Optional<Grupo> grupoOpt = grupoService.buscarPorId(idGrupo);
        
        model.addAttribute("partidos", partidos);
        model.addAttribute("grupo", grupoOpt.orElse(null));
        model.addAttribute("titulo", "Partidos del Grupo");
        return "listPartido";
    }
    
    @PostMapping("/generarCalendario")
    public String generarCalendario(@RequestParam Long idGrupo) {
        servicio.generarCalendario(idGrupo);
        return "redirect:/partidos/listar/" + idGrupo;
    }
    
    @GetMapping("/registrarResultado/{id}")
    public String mostrarFormularioResultado(@PathVariable Long id, Model model) {
        Optional<Partido> partido = servicio.buscarPorId(id);
        if (partido.isPresent()) {
            model.addAttribute("partido", partido.get());
            model.addAttribute("titulo", "Registrar Resultado");
            return "formPartido";
        }
        return "redirect:/partidos/listar/1";
    }
    
    @PostMapping("/guardarResultado")
    public String guardarResultado(@RequestParam Long idPartido, 
                                   @RequestParam int golesLocal, 
                                   @RequestParam int golesVisitante) {
        servicio.registrarResultado(idPartido, golesLocal, golesVisitante);
        Optional<Partido> partido = servicio.buscarPorId(idPartido);
        if (partido.isPresent()) {
            return "redirect:/partidos/listar/" + partido.get().getGrupo().getIdGrupo();
        }
        return "redirect:/campeonatos/listar";
    }
    
    @GetMapping("/tablaPosiciones/{idGrupo}")
    public String tablaPosiciones(@PathVariable Long idGrupo, Model model) {
        List<Partido> partidos = servicio.listarPorGrupo(idGrupo);
        
        // Mapa para calcular estadísticas
        Map<String, Map<String, Object>> estadisticas = new LinkedHashMap<>();
        
        for (Partido partido : partidos) {
            if ("JUGADO".equals(partido.getEstado())) {
                // Estadísticas equipo local
                String nombreLocal = partido.getEquipoLocal().getNombre();
                estadisticas.putIfAbsent(nombreLocal, new HashMap<>());
                Map<String, Object> statsLocal = estadisticas.get(nombreLocal);
                statsLocal.putIfAbsent("PJ", 0);
                statsLocal.putIfAbsent("PG", 0);
                statsLocal.putIfAbsent("PE", 0);
                statsLocal.putIfAbsent("PP", 0);
                statsLocal.putIfAbsent("GF", 0);
                statsLocal.putIfAbsent("GC", 0);
                statsLocal.putIfAbsent("PTS", 0);
                
                statsLocal.put("PJ", (int)statsLocal.get("PJ") + 1);
                statsLocal.put("GF", (int)statsLocal.get("GF") + partido.getGolesLocal());
                statsLocal.put("GC", (int)statsLocal.get("GC") + partido.getGolesVisitante());
                
                if (partido.getGolesLocal() > partido.getGolesVisitante()) {
                    statsLocal.put("PG", (int)statsLocal.get("PG") + 1);
                    statsLocal.put("PTS", (int)statsLocal.get("PTS") + 3);
                } else if (partido.getGolesLocal().equals(partido.getGolesVisitante())) {
                    statsLocal.put("PE", (int)statsLocal.get("PE") + 1);
                    statsLocal.put("PTS", (int)statsLocal.get("PTS") + 1);
                } else {
                    statsLocal.put("PP", (int)statsLocal.get("PP") + 1);
                }
                
                // Estadísticas equipo visitante
                String nombreVisitante = partido.getEquipoVisitante().getNombre();
                estadisticas.putIfAbsent(nombreVisitante, new HashMap<>());
                Map<String, Object> statsVisitante = estadisticas.get(nombreVisitante);
                statsVisitante.putIfAbsent("PJ", 0);
                statsVisitante.putIfAbsent("PG", 0);
                statsVisitante.putIfAbsent("PE", 0);
                statsVisitante.putIfAbsent("PP", 0);
                statsVisitante.putIfAbsent("GF", 0);
                statsVisitante.putIfAbsent("GC", 0);
                statsVisitante.putIfAbsent("PTS", 0);
                
                statsVisitante.put("PJ", (int)statsVisitante.get("PJ") + 1);
                statsVisitante.put("GF", (int)statsVisitante.get("GF") + partido.getGolesVisitante());
                statsVisitante.put("GC", (int)statsVisitante.get("GC") + partido.getGolesLocal());
                
                if (partido.getGolesVisitante() > partido.getGolesLocal()) {
                    statsVisitante.put("PG", (int)statsVisitante.get("PG") + 1);
                    statsVisitante.put("PTS", (int)statsVisitante.get("PTS") + 3);
                } else if (partido.getGolesVisitante().equals(partido.getGolesLocal())) {
                    statsVisitante.put("PE", (int)statsVisitante.get("PE") + 1);
                    statsVisitante.put("PTS", (int)statsVisitante.get("PTS") + 1);
                } else {
                    statsVisitante.put("PP", (int)statsVisitante.get("PP") + 1);
                }
            }
        }
        
        // Ordenar por puntos (mayor a menor), diferencia de goles, goles a favor
        List<Map.Entry<String, Map<String, Object>>> listaOrdenada = new ArrayList<>(estadisticas.entrySet());
        listaOrdenada.sort((e1, e2) -> {
            int pts1 = (int)e1.getValue().get("PTS");
            int pts2 = (int)e2.getValue().get("PTS");
            if (pts2 != pts1) return pts2 - pts1;
            
            int dif1 = (int)e1.getValue().get("GF") - (int)e1.getValue().get("GC");
            int dif2 = (int)e2.getValue().get("GF") - (int)e2.getValue().get("GC");
            if (dif2 != dif1) return dif2 - dif1;
            
            int gf1 = (int)e1.getValue().get("GF");
            int gf2 = (int)e2.getValue().get("GF");
            return gf2 - gf1;
        });
        
        model.addAttribute("estadisticas", listaOrdenada);
        model.addAttribute("grupo", grupoService.buscarPorId(idGrupo).orElse(null));
        model.addAttribute("titulo", "Tabla de Posiciones");
        return "tablaPosiciones";
    }
}