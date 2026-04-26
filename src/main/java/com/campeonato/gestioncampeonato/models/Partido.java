package com.campeonato.gestioncampeonato.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "partidos")
public class Partido implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartido;
    
    @Column(nullable = true)
    private LocalDateTime fechaHora;
    
    @Column(length = 100)
    private String lugar;
    
    @Column(nullable = false)
    private Integer golesLocal = 0;
    
    @Column(nullable = false)
    private Integer golesVisitante = 0;
    
    @Column(length = 20)
    private String estado; // PENDIENTE, JUGADO
    
    // Relación con Equipo Local
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEquipoLocal", nullable = false)
    private Equipo equipoLocal;
    
    // Relación con Equipo Visitante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEquipoVisitante", nullable = false)
    private Equipo equipoVisitante;
    
    // Relación con Grupo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGrupo", nullable = false)
    private Grupo grupo;
    
    // Constructores
    public Partido() {
        this.estado = "PENDIENTE";
    }
    
    // Método para calcular resultado
    public String getResultadoEquipoLocal() {
        if ("JUGADO".equals(estado)) {
            if (golesLocal > golesVisitante) return "Victoria";
            if (golesLocal.equals(golesVisitante)) return "Empate";
            return "Derrota";
        }
        return "Pendiente";
    }
    
    public String getResultadoEquipoVisitante() {
        if ("JUGADO".equals(estado)) {
            if (golesVisitante > golesLocal) return "Victoria";
            if (golesVisitante.equals(golesLocal)) return "Empate";
            return "Derrota";
        }
        return "Pendiente";
    }
    
    public int getPuntosLocal() {
        if ("JUGADO".equals(estado)) {
            if (golesLocal > golesVisitante) return 3;
            if (golesLocal.equals(golesVisitante)) return 1;
            return 0;
        }
        return 0;
    }
    
    public int getPuntosVisitante() {
        if ("JUGADO".equals(estado)) {
            if (golesVisitante > golesLocal) return 3;
            if (golesVisitante.equals(golesLocal)) return 1;
            return 0;
        }
        return 0;
    }
    
    // Getters y Setters
    public Long getIdPartido() {
        return idPartido;
    }
    
    public void setIdPartido(Long idPartido) {
        this.idPartido = idPartido;
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    
    public String getLugar() {
        return lugar;
    }
    
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    
    public Integer getGolesLocal() {
        return golesLocal;
    }
    
    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }
    
    public Integer getGolesVisitante() {
        return golesVisitante;
    }
    
    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Equipo getEquipoLocal() {
        return equipoLocal;
    }
    
    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }
    
    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }
    
    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }
    
    public Grupo getGrupo() {
        return grupo;
    }
    
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}