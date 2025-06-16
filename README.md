# Proyecto-Final
## 🎮 Breakout! – Proyecto Final de Desarrollo de Aplicaciones Web

Breakout es una aplicación web que combina el clásico juego de romper bloques con una comunidad de usuarios donde cualquiera puede jugar, compartir, comentar y descubrir nuevos videojuegos publicados por otros.

---

## ✨ Características principales

- Juego Breakout jugable desde el navegador
- Guardado de partidas y ranking global
- Registro/Login y acceso como invitado
- Publicaciones con imágenes, vídeo, descripción y etiquetas
- Valoraciones con estrellas, favoritos y comentarios
- Búsqueda, filtrado y ordenación de listados
- Perfiles de usuario con historial de partidas y favoritos
- Panel de administración para gestionar usuarios

---

## 🛠️ Tecnologías utilizadas

### Backend
- Spring Boot 3
- Spring MVC + Security
- Maven
- JPA / Hibernate
- Cloudinary (archivos multimedia)
- EmailSender (SMTP para feedback)

### Frontend
- Thymeleaf
- SCSS + Bootstrap 5
- JavaScript (juego y efectos)
- JQuery + Swiper.js (carruseles)
- PrettyTime

### Base de datos
- MySQL
- DBeaver (modelado)
- Relaciones normalizadas (users, listings, comments, etc.)

### Pruebas
- JUnit + Mockito
- Selenium (flujo de usuario)
- Codacy (calidad y limpieza de código)

### Despliegue
- Docker
- Render (backend)
- Railway (base de datos)

---

## 🧪 Cómo probar el proyecto

1. Clona el repositorio:
```bash
git clone https://github.com/aarcosb664/Proyecto-Final.git
```

2. Configura `src/main/resources/application.properties` con tus datos (MySQL, Cloudinary...).

3. Ejecuta el proyecto:
```bash
./mvnw spring-boot:run
```

4. Visita [https://proyecto-final-aarcosb.onrender.com/](https://proyecto-final-aarcosb.onrender.com/) para ver la aplicación.

---

## 🧠 Arquitectura general

- Modelo Vista Controlador (MVC)
- Controladores REST + plantillas Thymeleaf
- Servicios desacoplados de la lógica de persistencia
- Estructura modular con separación clara de capas

---

## 🚀 Posibles mejoras futuras

- Modo multijugador, nuevos niveles y más tipos de bloques
- Sistema de notificaciones y respuestas a comentarios
- Recuperación de contraseña y autenticación OAuth
- Internacionalización (multi-idioma)
- Mejoras en la experiencia móvil y rendimiento del juego

---

## 👨‍💻 Autor

**Alejandro Arcos Bellido**  
Proyecto Final del CFGS de Desarrollo de Aplicaciones Web  
Curso 2024–2025  
IES Ruiz Gijón
