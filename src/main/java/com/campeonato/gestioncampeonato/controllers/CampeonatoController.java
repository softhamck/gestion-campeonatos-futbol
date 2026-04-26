package com.campeonato.gestioncampeonato.controllers;

import com.campeonato.gestioncampeonato.interfacesServices.ICampeonatoService;
import com.campeonato.gestioncampeonato.models.Campeonato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/campeonatos")
public class CampeonatoController {
    
    @Autowired
    private ICampeonatoService servicio;
    
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("campeonatos", servicio.listar());
        model.addAttribute("titulo", "Gestión de Campeonatos");
        return "listCampeonato";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("campeonato", new Campeonato());
        model.addAttribute("titulo", "Nuevo Campeonato");
        return "formCampeonato";
    }
    
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Campeonato campeonato) {
        servicio.guardar(campeonato);
        return "redirect:/campeonatos/listar";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Campeonato> campeonato = servicio.buscarPorId(id);
        if (campeonato.isPresent()) {
            model.addAttribute("campeonato", campeonato.get());
            model.addAttribute("titulo", "Editar Campeonato");
            return "formCampeonato";
        }
        return "redirect:/campeonatos/listar";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return "redirect:/campeonatos/listar";
    }
}