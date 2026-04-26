package com.campeonato.gestioncampeonato.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupos")
public class Grupo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrupo;
    
    @Column(length = 50, nullable = false)
    private String nombre; // Grupo A, Grupo B, etc.
    
    // Relación con Campeonato
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCampeonato", nullable = false)
    private Campeonato campeonato;
    
    // Un grupo tiene muchos equipos
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
    private List<Equipo> equipos = new ArrayList<>();
    
    // Un grupo tiene muchos partidos
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Partido> partidos = new ArrayList<>();
    
    // Constructores
    public Grupo() {
    }
    
    // Getters y Setters
    public Long getIdGrupo() {
        return idGrupo;
    }
    
    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Campeonato getCampeonato() {
        return campeonato;
    }
    
    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }
    
    public List<Equipo> getEquipos() {
        return equipos;
    }
    
    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
    
    public List<Partido> getPartidos() {
        return partidos;
    }
    
    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }
}