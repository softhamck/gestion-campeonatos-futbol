package com.campeonato.gestioncampeonato.api;

import com.campeonato.gestioncampeonato.interfaces.IEquipo;
import com.campeonato.gestioncampeonato.models.Equipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen
public class EquipoRestController {
    
    @Autowired
    private IEquipo equipoRepositorio;
    
    // ENDPOINT 1: Listar todos los equipos
    @GetMapping("/listar")
    public List<EquipoDTO> listarTodos() {
        List<Equipo> equipos = equipoRepositorio.findAll();
        return equipos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // ENDPOINT 2: Buscar por nombre (CONSULTA CON FILTRO)
    @GetMapping("/buscarPorNombre")
    public List<EquipoDTO> buscarPorNombre(@RequestParam String nombre) {
        List<Equipo> equipos = equipoRepositorio.findAll();
        return equipos.stream()
                .filter(e -> e.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // ENDPOINT 3: Buscar por ciudad (CONSULTA CON FILTRO)
    @GetMapping("/buscarPorCiudad")
    public List<EquipoDTO> buscarPorCiudad(@RequestParam String ciudad) {
        List<Equipo> equipos = equipoRepositorio.findAll();
        return equipos.stream()
                .filter(e -> e.getCiudad() != null && 
                       e.getCiudad().toLowerCase().contains(ciudad.toLowerCase()))
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // ENDPOINT 4: Buscar por campeonato
    @GetMapping("/buscarPorCampeonato")
    public List<EquipoDTO> buscarPorCampeonato(@RequestParam Long idCampeonato) {
        List<Equipo> equipos = equipoRepositorio.findByCampeonatoIdCampeonato(idCampeonato);
        return equipos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // ENDPOINT 5: Buscar por ID
    @GetMapping("/{id}")
    public EquipoDTO buscarPorId(@PathVariable Long id) {
        return equipoRepositorio.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }
    
    // Método para convertir Entidad a DTO (evita problemas de serialización)
    private EquipoDTO convertirADTO(Equipo equipo) {
        EquipoDTO dto = new EquipoDTO();
        dto.setId(equipo.getIdEquipo());
        dto.setNombre(equipo.getNombre());
        dto.setCiudad(equipo.getCiudad());
        dto.setDirectorTecnico(equipo.getDirectorTecnico());
        if (equipo.getCampeonato() != null) {
            dto.setNombreCampeonato(equipo.getCampeonato().getNombre());
        }
        if (equipo.getGrupo() != null) {
            dto.setNombreGrupo(equipo.getGrupo().getNombre());
        }
        return dto;
    }
}

// DTO (Data Transfer Object) - Clase interna
class EquipoDTO {
    private Long id;
    private String nombre;
    private String ciudad;
    private String directorTecnico;
    private String nombreCampeonato;
    private String nombreGrupo;
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public String getDirectorTecnico() { return directorTecnico; }
    public void setDirectorTecnico(String directorTecnico) { this.directorTecnico = directorTecnico; }
    
    public String getNombreCampeonato() { return nombreCampeonato; }
    public void setNombreCampeonato(String nombreCampeonato) { this.nombreCampeonato = nombreCampeonato; }
    
    public String getNombreGrupo() { return nombreGrupo; }
    public void setNombreGrupo(String nombreGrupo) { this.nombreGrupo = nombreGrupo; }
}