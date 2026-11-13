# Actividad 4: API REST Segura con Spring Boot, Spring Security y JWT

API REST desarrollada en **Spring Boot** que implementa autenticación basada en **JWT (JSON Web Token)**, validación estricta de datos mediante **DTOs**, control de códigos de estado HTTP y un CRUD completo protegido. El proyecto incluye su colección de pruebas documentada en **Bruno** y se encuentra desplegado en un servidor VPS en la nube.

---

## 👨‍💻 Autor
* **Nombre:** Jonathan René Cruz Gutiérrez
* **Carrera:** Ingeniería en Sistemas Computacionales

---

## 🚀 Tecnologías y Herramientas Utilizadas
* **Backend:** Java 17, Spring Boot, Spring Security, Spring Data JPA, Hibernate.
* **Seguridad:** JJWT (`0.11.5`) para la generación y validación de tokens.
* **Base de Datos:** MySQL (XAMPP local / MySQL en VPS).
* **Pruebas de API:** Bruno (Colección integrada en el repositorio).
* **Control de Versiones:** Git y GitHub.
* **Despliegue:** Ubuntu VPS (DigitalOcean - IP: `157.230.89.112`, Puerto: `8086`).

---

## 📂 Estructura del Proyecto
El proyecto está organizado en paquetes limpios siguiendo buenas prácticas de arquitectura:
* `com.jrcg.act4_t4.models`: Entidades JPA (`Usuario`, `Escuela`).
* `com.jrcg.act4_t4.repositories`: Interfaces de persistencia (`UsuarioRepository`, `EscuelaRepository`).
* `com.jrcg.act4_t4.dtos`: Objetos de transferencia de datos con validaciones (`AuthRequestDTO`, `AuthResponseDTO`, `EscuelaDTO`).
* `com.jrcg.act4_t4.controllers`: Controladores REST públicos y protegidos (`AuthController`, `EscuelaController`).
* `com.jrcg.act4_t4.security`: Configuración de seguridad, filtros JWT y utilidades de tokens (`SecurityConfig`, `JwtUtil`, `JwtRequestFilter`, `CustomUserDetailsService`).

---

## 🔒 Endpoints de la API

### 1. Autenticación (Públicos)
* `POST /api/auth/register` - Registra un nuevo usuario en el sistema (encriptando contraseña con BCrypt).
* `POST /api/auth/login` - Autentica al usuario y devuelve el **Token JWT** de acceso.

### 2. CRUD de Escuelas (Protegidos - Requiere Header `Authorization: Bearer <TOKEN>`)
* `GET /api/escuelas` - Obtiene la lista de todas las escuelas (**200 OK**).
* `GET /api/escuelas/{id}` - Obtiene una escuela por su ID (**200 OK** o **404 Not Found**).
* `POST /api/escuelas` - Crea una nueva escuela aplicando validaciones DTO (**201 Created**).
* `PUT /api/escuelas/{id}` - Actualiza una escuela existente (**200 OK** o **404 Not Found**).
* `DELETE /api/escuelas/{id}` - Elimina una escuela por ID (**204 No Content** o **404 Not Found**).

---

## 🧪 Pruebas con Bruno
La colección de pruebas se encuentra guardada dentro de la carpeta **`API_Act4`** en la raíz de este repositorio. Para probarla:
1. Abre **Bruno** y selecciona *Open Collection*, buscando la carpeta del proyecto.
2. Ejecuta primero la petición de **Login** para obtener el token JWT.
3. Copia el token y pégalo en la pestaña **Auth -> Bearer Token** de las peticiones del CRUD de escuelas.

---

## ☁️ Despliegue en el VPS (Sin afectar actividades previas)
* **IP del Servidor:** `157.230.89.112`
* **Puerto:** `8086` *(Configurado de manera aislada en `application.properties` como `server.port=8086` para no interferir con los puertos de prácticas anteriores).*
* **Estado:** Corriendo en segundo plano mediante `nohup` y persistido con un archivo de logs (`app.log`).

---

## 📷 Capturas de Evidencia

### 1. Configuración del Puerto en el VPS (`application.properties`)


### 2. Pruebas de Registro y Login exitosas en Bruno


### 3. Prueba del CRUD protegido con Token Bearer (201 Created)
