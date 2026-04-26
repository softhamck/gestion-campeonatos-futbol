package com.campeonato.gestioncampeonato.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipos")
public class Equipo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipo;
    
    @Column(length = 100, nullable = false)
    private String nombre;
    
    @Column(length = 100)
    private String ciudad;
    
    @Column(length = 100)
    private String directorTecnico;
    
    // Relación con Campeonato (Muchos equipos pertenecen a un campeonato)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCampeonato", nullable = false)
    private Campeonato campeonato;
    
    // Relación con Grupo (Muchos equipos pertenecen a un grupo)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGrupo")
    private Grupo grupo;
    
    // Partidos como local
    @OneToMany(mappedBy = "equipoLocal", fetch = FetchType.LAZY)
    private List<Partido> partidosLocal = new ArrayList<>();
    
    // Partidos como visitante
    @OneToMany(mappedBy = "equipoVisitante", fetch = FetchType.LAZY)
    private List<Partido> partidosVisitante = new ArrayList<>();
    
    // Constructores
    public Equipo() {
    }
    
    // Getters y Setters
    public Long getIdEquipo() {
        return idEquipo;
    }
    
    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public String getDirectorTecnico() {
        return directorTecnico;
    }
    
    public void setDirectorTecnico(String directorTecnico) {
        this.directorTecnico = directorTecnico;
    }
    
    public Campeonato getCampeonato() {
        return campeonato;
    }
    
    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }
    
    public Grupo getGrupo() {
        return grupo;
    }
    
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    
    public List<Partido> getPartidosLocal() {
        return partidosLocal;
    }
    
    public void setPartidosLocal(List<Partido> partidosLocal) {
        this.partidosLocal = partidosLocal;
    }
    
    public List<Partido> getPartidosVisitante() {
        return partidosVisitante;
    }
    
    public void setPartidosVisitante(List<Partido> partidosVisitante) {
        this.partidosVisitante = partidosVisitante;
    }
}