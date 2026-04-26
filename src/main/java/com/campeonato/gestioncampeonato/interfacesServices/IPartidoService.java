package com.campeonato.gestioncampeonato.interfacesServices;

import com.campeonato.gestioncampeonato.models.Partido;
import java.util.List;
import java.util.Optional;

public interface IPartidoService {
    List<Partido> listar();
    List<Partido> listarPorGrupo(Long idGrupo);
    Optional<Partido> buscarPorId(Long id);
    Partido guardar(Partido partido);
    void eliminar(Long id);
    void generarCalendario(Long idGrupo);
    void registrarResultado(Long idPartido, int golesLocal, int golesVisitante);
}