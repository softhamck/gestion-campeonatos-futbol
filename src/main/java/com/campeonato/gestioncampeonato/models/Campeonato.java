package com.campeonato.gestioncampeonato.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campeonatos")
public class Campeonato implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCampeonato;
    
    @Column(length = 100, nullable = false, unique = true)
    private String nombre;
    
    @Column(nullable = false)
    private LocalDate fechaInicio;
    
    @Column(nullable = false)
    private LocalDate fechaFin;
    
    @Column(length = 50)
    private String estado; // PENDIENTE, EN_CURSO, FINALIZADO
    
    // Un campeonato tiene muchos equipos
    @OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Equipo> equipos = new ArrayList<>();
    
    // Un campeonato tiene muchos grupos
    @OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Grupo> grupos = new ArrayList<>();
    
    // Constructores
    public Campeonato() {
        this.estado = "PENDIENTE";
    }
    
    // Getters y Setters
    public Long getIdCampeonato() {
        return idCampeonato;
    }
    
    public void setIdCampeonato(Long idCampeonato) {
        this.idCampeonato = idCampeonato;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public List<Equipo> getEquipos() {
        return equipos;
    }
    
    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
    
    public List<Grupo> getGrupos() {
        return grupos;
    }
    
    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }
}