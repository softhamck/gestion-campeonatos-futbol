# Diagrama de Arquitectura - API REST + Cliente

```mermaid
flowchart LR

    Usuario["Usuario / Navegador"]

    subgraph Cliente["Aplicación Cliente - localhost:8081"]
        VistaCliente["Capa Vista Thymeleaf`<br/>`buscarEquipos.html`<br/>`resultadoEquipos.html`<br/>`index.html"]
        ClienteController["ClienteController`<br/>`/cliente/buscarPorNombre`<br/>`/cliente/buscarPorCiudad`<br/>`/cliente/buscarPorCampeonato"]
        ApiService["ApiService`<br/>`WebClient`<br/>`listarEquipos()`<br/>`buscarPorNombre()`<br/>`buscarPorCiudad()`<br/>`buscarPorCampeonato()"]
    end

    subgraph Principal["Aplicación Principal - localhost:8080"]
        VistaPrincipal["Capa Vista Thymeleaf`<br/>`listCampeonato.html`<br/>`listEquipo.html`<br/>`tablaPosiciones.html"]
        MVC["Controladores MVC`<br/>`CampeonatoController`<br/>`EquipoController`<br/>`GrupoController`<br/>`PartidoController"]
        RestAPI["API REST`<br/>`EquipoRestController`<br/>`GET /api/equipos/listar`<br/>`GET /api/equipos/buscarPorNombre`<br/>`GET /api/equipos/buscarPorCiudad`<br/>`GET /api/equipos/buscarPorCampeonato"]
        Servicios["Capa Servicio`<br/>`CampeonatoService`<br/>`EquipoService`<br/>`GrupoService`<br/>`PartidoService"]
        Repositorios["Capa Repositorio JPA`<br/>`ICampeonato`<br/>`IEquipo`<br/>`IGrupo`<br/>`IPartido"]
        BD[("Base de Datos MySQL`<br/>`campeonato_db`<br/>`campeonatos`<br/>`equipos`<br/>`grupos`<br/>`partidos")]
    end

    Usuario --> VistaCliente
    VistaCliente --> ClienteController
    ClienteController --> ApiService

    ApiService -->|"HTTP GET / JSON"| RestAPI

    RestAPI --> Servicios
    Servicios --> Repositorios
    Repositorios --> BD

    MVC --> Servicios
    VistaPrincipal --> MVC
```

# Flujo de información

1. **Cliente (Navegador)** solicita búsqueda en `localhost:8081`.
2. **ClienteController** recibe la petición.
3. **ApiService** usa `WebClient` para llamar a la API REST.
4. Se realiza una petición HTTP GET a:

   ```http
   GET localhost:8080/api/equipos/buscarPorNombre?nombre=X
   ```
5. **EquipoRestController** procesa la petición.
6. **IEquipo (JPA)** consulta la base de datos MySQL.
7. **MySQL** retorna los datos.
8. **EquipoRestController** convierte la respuesta a JSON.
9. **HTTP Response** devuelve un JSON con los equipos encontrados.
10. **ApiService** recibe y deserializa el JSON.
11. **ClienteController** envía los datos a la vista Thymeleaf.
12. **Cliente (Navegador)** muestra los resultados en una tabla HTML.
