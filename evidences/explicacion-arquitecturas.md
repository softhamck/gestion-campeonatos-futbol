# Tipos de Arquitecturas que Intervienen en esta Actividad

## 1. Arquitectura MVC (Modelo - Vista - Controlador)

### ¿Dónde se aplica?
En la aplicación principal (gestioncampeonato)

### Características:
- **Modelo:** Entidades JPA que representan las tablas de la base de datos
- **Vista:** Plantillas Thymeleaf que renderizan HTML
- **Controlador:** Clases @Controller que reciben peticiones HTTP y retornan vistas

### Diferencias con otras arquitecturas:
- Separa la lógica de presentación (Vista) de la lógica de negocio (Modelo)
- El controlador actúa como intermediario
- Ideal para aplicaciones web tradicionales con interfaz de usuario

---

## 2. Arquitectura REST (Representational State Transfer)

### ¿Dónde se aplica?
En la API REST expuesta por la aplicación principal (EquipoRestController)

### Características:
- **@RestController:** Expone endpoints que retornan JSON
- **Sin estado (Stateless):** Cada petición contiene toda la información necesaria
- **Recursos:** Cada entidad es un recurso accesible mediante URL
- **Métodos HTTP:** GET para consultas, POST para crear, PUT para actualizar, DELETE para eliminar

### Diferencias con MVC tradicional:
- No retorna vistas HTML, retorna datos (JSON/XML)
- No mantiene sesión de usuario
- Permite que cualquier cliente (web, móvil, escritorio) consuma los datos
- Es una arquitectura orientada a servicios

---

## 3. Arquitectura Cliente-Servidor

### ¿Dónde se aplica?
En la comunicación entre la aplicación cliente y la aplicación principal

### Características:
- **Servidor:** Aplicación principal (localhost:8080) que expone la API REST
- **Cliente:** Aplicación cliente (localhost:8081) que consume la API
- **Comunicación:** HTTP/HTTPS con formato JSON
- **WebClient:** Librería reactiva de Spring para consumir APIs

### Diferencias:
- El cliente y el servidor son aplicaciones independientes
- Pueden estar en diferentes máquinas o puertos
- El cliente solo necesita conocer la interfaz (API), no la implementación
- Permite escalabilidad: múltiples clientes pueden consumir el mismo servidor

---

## 4. Arquitectura en Capas (Layered Architecture)

### ¿Dónde se aplica?
En la estructura interna de ambas aplicaciones

### Capas en la aplicación principal:
1. **Capa de Presentación:** Controladores MVC + API REST
2. **Capa de Negocio:** Servicios (@Service)
3. **Capa de Persistencia:** Repositorios JPA (@Repository)
4. **Capa de Datos:** Base de datos MySQL

### Diferencias:
- Cada capa tiene una responsabilidad específica
- Las capas superiores dependen de las inferiores, no al revés
- Facilita el mantenimiento y las pruebas
- Permite cambiar una capa sin afectar las demás

---

## CUADRO COMPARATIVO

| Arquitectura | Propósito | Salida | Comunicación |
|-------------|-----------|--------|--------------|
| MVC | Aplicación web con interfaz | HTML | Navegador ↔ Servidor |
| REST | Exponer datos como servicio | JSON/XML | Cliente HTTP ↔ API |
| Cliente-Servidor | Separar frontend y backend | Variable | HTTP entre aplicaciones |
| En Capas | Organizar código internamente | Interna | Entre capas del mismo sistema |

---

## ¿CÓMO SE DIFERENCIAN?

### MVC vs REST:
- MVC retorna VISTAS (HTML), REST retorna DATOS (JSON)
- MVC usa @Controller, REST usa @RestController
- MVC mantiene estado de sesión, REST es sin estado

### REST vs Cliente-Servidor:
- REST es el ESTILO de la API, Cliente-Servidor es la ARQUITECTURA del sistema
- REST define cómo se exponen los datos, Cliente-Servidor define cómo se distribuyen

### En Capas vs MVC:
- MVC es una arquitectura de PRESENTACIÓN (cómo se muestra la información)
- En Capas es una arquitectura de SISTEMA (cómo se organiza todo el código)
- MVC puede ser parte de una arquitectura en capas