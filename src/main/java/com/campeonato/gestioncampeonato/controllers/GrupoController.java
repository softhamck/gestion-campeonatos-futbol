
package com.campeonato.gestioncampeonato.controllers;

import com.campeonato.gestioncampeonato.interfacesServices.IGrupoService;
import com.campeonato.gestioncampeonato.interfacesServices.ICampeonatoService;
import com.campeonato.gestioncampeonato.models.Grupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grupos")
public class GrupoController {
    
    @Autowired
    private IGrupoService servicio;
    
    @Autowired
    private ICampeonatoService campeonatoService;
    
    @GetMapping("/listar/{idCampeonato}")
    public String listar(@PathVariable Long idCampeonato, Model model) {
        model.addAttribute("grupos", servicio.listarPorCampeonato(idCampeonato));
        model.addAttribute("campeonato", campeonatoService.buscarPorId(idCampeonato).orElse(null));
        model.addAttribute("titulo", "Grupos del Campeonato");
        return "listGrupo";
    }
    
    @PostMapping("/generar")
    public String generarGrupos(@RequestParam Long idCampeonato, @RequestParam int cantidadGrupos) {
        servicio.generarGrupos(idCampeonato, cantidadGrupos);
        return "redirect:/grupos/listar/" + idCampeonato;
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, @RequestParam Long idCampeonato) {
        servicio.eliminar(id);
        return "redirect:/grupos/listar/" + idCampeonato;
    }
}