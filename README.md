# 🎮 FreeGames App – Android Kotlin

**Descripción:**  
Aplicación nativa de Android desarrollada en Kotlin que consume una API de videojuegos gratuitos para mostrar un catálogo dinámico de juegos.  
Permite visualizar una lista de juegos, acceder a un detalle completo con información técnica, galería de imágenes y enlaces externos.

El proyecto ha evolucionado hasta una versión estable (v2.0), incorporando mejoras visuales, galería de screenshots, UI más moderna con Material Design y optimización de la pantalla de detalle.

---

## 📌 Funcionalidades

### 🔹 v1.0
- 📡 Consumo de API REST (FreeToGame) con **Retrofit**
- 📋 Lista de juegos usando **RecyclerView**
- 🔗 Navegación a pantalla de detalle mediante `Intent`
- 🧠 Modelo de datos estructurado con Gson (`@SerializedName`)
- 🖼️ Carga de imágenes con **Picasso**
- 📄 Pantalla de detalle con:
  - Título del juego
  - Imagen principal
  - Descripción corta
  - Link externo al juego

---

### 🔹 v2.0
- 🖼️ Sistema de **galería de imágenes (screenshots)**
- 📱 Implementación de **RecyclerView en grid (GridLayoutManager)**
- 🎯 Layout dinámico para adaptar última imagen (spanSizeLookup)
- 🎨 Mejora completa del UI con Material Design:
  - Cards redondeadas
  - Chips para género y plataforma
  - Dark theme moderno
- ⚙️ Sección de **System Requirements**:
  - OS
  - Processor
  - Memory
  - Graphics
  - Storage
- 🔄 Manejo seguro de datos nulos (`?: "Not available"`)
- 📡 Optimización de carga de imágenes con Picasso
- 🧭 UI más estructurada y jerárquica en detalle

---

## 🛠 Tecnologías utilizadas

- Kotlin  
- Android Studio  
- Retrofit2 (API REST)  
- Gson (deserialización JSON)  
- RecyclerView  
- GridLayoutManager  
- Material Design Components  
- Picasso (carga de imágenes)  
- Coroutines (Dispatchers IO/Main)  
- ViewBinding  
- Intent (navegación entre Activities)

---

## 📷 Capturas de pantalla

### 🟢 Lista de juegos
<p align="center">
  <img src="list.png" width="250">
</p>

### 🔵 Detalle del juego
<p align="center">
  <img src="detail.png" width="250">
</p>

### 🟣 Galería de screenshots
<p align="center">
  <img src="gallery.png" width="250">
</p>

---

## 📌 Estado del proyecto

- v1.0 → Lista básica de juegos + detalle simple  
- v2.0 → Galería, UI mejorada, system requirements y diseño moderno  

🚧 Proyecto finalizado (base sólida para ampliaciones futuras como favoritos, filtros o login)

---

## 🧠 Lo que aprendí

- Consumo de APIs REST con Retrofit  
- Modelado de datos complejos con JSON anidado  
- Uso avanzado de RecyclerView (Grid + SpanSizeLookup)  
- Diseño de UI moderna con Material Design  
- Manejo de imágenes con Picasso  
- Arquitectura básica con Activities + separación de capas  
- Gestión de null safety en Kotlin  
- Mejora progresiva de UX/UI en Android

---

## 🚀 Cómo ejecutar el proyecto

1. Clona el repositorio:

```bash
git clone https://github.com/GualpaJ/FreeGames-Android.git
