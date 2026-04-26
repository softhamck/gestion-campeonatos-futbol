package com.campeonato.gestioncampeonato.controllers;

import com.campeonato.gestioncampeonato.interfacesServices.IEquipoService;
import com.campeonato.gestioncampeonato.interfacesServices.ICampeonatoService;
import com.campeonato.gestioncampeonato.models.Equipo;
import com.campeonato.gestioncampeonato.models.Campeonato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/equipos")
public class EquipoController {
    
    @Autowired
    private IEquipoService servicio;
    
    @Autowired
    private ICampeonatoService campeonatoService;
    
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("equipos", servicio.listar());
        model.addAttribute("titulo", "Gestión de Equipos");
        return "listEquipo";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("equipo", new Equipo());
        model.addAttribute("campeonatos", campeonatoService.listar());
        model.addAttribute("titulo", "Nuevo Equipo");
        return "formEquipo";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Equipo equipo, @RequestParam Long idCampeonato) {
        Optional<Campeonato> campeonatoOpt = campeonatoService.buscarPorId(idCampeonato);
        if (campeonatoOpt.isPresent()) {
            equipo.setCampeonato(campeonatoOpt.get());
            servicio.guardar(equipo);
        }
        return "redirect:/equipos/listar";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Equipo> equipo = servicio.buscarPorId(id);
        if (equipo.isPresent()) {
            model.addAttribute("equipo", equipo.get());
            model.addAttribute("campeonatos", campeonatoService.listar());
            model.addAttribute("titulo", "Editar Equipo");
            return "formEquipo";
        }
        return "redirect:/equipos/listar";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return "redirect:/equipos/listar";
    }
}