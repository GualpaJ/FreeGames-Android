# 🎮 FreeGames App – Android Kotlin (V1)

**Descripción:**  
Aplicación nativa de Android desarrollada en Kotlin que muestra una lista de videojuegos gratuitos obtenidos desde una API externa (**FreeToGame API**). Permite navegar a una pantalla de detalle donde se muestra información ampliada del juego.

En esta **versión 1.0**, la app introduce navegación entre pantallas, consumo de API REST, carga de imágenes y traducción offline de descripciones usando **Google ML Kit**.

---

## 📌 Funcionalidades

### 🔹 v1.0
- 📡 Consumo de API REST (FreeToGame API)
- 📋 Lista de juegos usando RecyclerView
- 🖼️ Carga de imágenes con Picasso
- 📱 Navegación a pantalla de detalle con Intent
- 🧾 Pantalla de detalle con:
  - Título del juego  
  - Imagen principal  
  - Descripción completa  
- 🌐 Apertura de enlace externo del juego (ACTION_VIEW)
- 🌍 Traducción offline de descripciones con Google ML Kit
- ⚙️ Arquitectura separada:
  - Activities
  - Adapter (RecyclerView)
  - Modelos de datos (Game, Screenshot, SystemRequirements)
  - Service (GameService)

---

## 🛠 Tecnologías utilizadas

- Kotlin  
- Android Studio  
- RecyclerView  
- Retrofit / API REST (FreeToGame)  
- Picasso  
- Coroutines  
- ML Kit (Translation offline EN → ES)  
- Intents  
- ViewBinding  
- Material Design  

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

---

## 📌 Estado del proyecto

- v1.0 → Lista de juegos + detalle + traducción offline  
- 🚧 En evolución → mejoras de UI y nuevas funcionalidades

---

## 🧠 Lo que aprendí

- Consumo de APIs REST en Android  
- RecyclerView y Adapters  
- Navegación entre Activities con Intents  
- Uso de Coroutines (IO / Main)  
- Carga de imágenes con Picasso  
- Traducción offline con ML Kit  
- Separación de capas en el proyecto  

---

## 🚀 Cómo ejecutar el proyecto

```bash
git clone https://github.com/tuusuario/freegames_app.git
