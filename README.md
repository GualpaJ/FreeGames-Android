# 📱 FreeGames App – Android Kotlin

**Descripción:**  
Aplicación nativa de Android desarrollada en Kotlin que consume la [FreeToGame API](https://www.freetogame.com/api-doc) para mostrar un catálogo de juegos gratuitos, con lista, detalle, búsqueda, galería de imágenes y diseño moderno con Material Design.

El proyecto se ha desarrollado en varias fases, incorporando consumo de API, galería de capturas, sistema de traducción de descripciones y mejora de la experiencia de usuario.

---

## 📌 Funcionalidades

### 🔹 v1.0
- Lista de juegos usando **RecyclerView**
- Consumo de API REST con **Retrofit**
- Navegación a pantalla de detalle con paso de datos (`Intent`)
- Carga y visualización de imágenes con **Picasso**
- Pantalla de detalle con:
  - Título, thumbnail, descripción
  - Plataforma, género, desarrollador, editor
- Layout responsivo con **ConstraintLayout**

### 🔹 v1.1
- 🔍 Búsqueda local de juegos en tiempo real (**SearchView**)
- 🖼️ Galería de capturas de pantalla en el detalle (horizontal)
- ⚙️ Scrollview en pantalla de detalle para mejor navegación
- 📱 Mejora de UI con Material Design Components

### 🔹 v1.2
- 🌐 **Traducción automática** de la descripción del inglés al español mediante API externa
- 💾 Caché de la traducción para evitar llamadas repetidas
- ⚡ Mejora de rendimiento y gestión de estado

---

## 🛠 Tecnologías utilizadas

- **Kotlin**
- **Android Studio** (Ladybug)
- **Retrofit** (consumo de API REST)
- **RecyclerView** + Adaptadores personalizados
- **Picasso** (carga de imágenes)
- **Intents** (navegación entre Activities)
- **SearchView** (filtrado en tiempo real)
- **Material Design Components**
- **ConstraintLayout / ScrollView**
- **API de traducción externa** (MyMemory)

---

## 📷 Capturas de pantalla

### 🟢 v1.0 – Lista y detalle
<p align="center">
  <img src="screenshots/list.png" width="250">
  <img src="screenshots/detail.png" width="250">
</p>

### 🔵 v1.1 – Búsqueda y galería
<p align="center">
  <img src="screenshots/search.png" width="250">
  <img src="screenshots/gallery.png" width="250">
</p>

---

## 📌 Estado del proyecto

El proyecto ha evolucionado en tres fases principales:

| Versión | Estado | Funcionalidades |
|---------|--------|-----------------|
| v1.0 | ✅ Completado | Lista + Detalle + API + Picasso |
| v1.1 | ✅ Completado | Búsqueda + Galería de capturas |
| v1.2 | ✅ Completado | Traducción automática de descripciones |
| v2.0 | 🚧 Planificado | Favoritos locales (Room / DataStore) |

---

## 📝 Lo que aprendí

- Consumo de APIs REST con **Retrofit** y manejo de respuestas asíncronas
- Implementación de **RecyclerView** con diferentes tipos de vistas
- Navegación entre Activities con **Intents** y paso de objetos
- Carga eficiente de imágenes con **Picasso**
- Creación de **SearchView** para filtrado local en tiempo real
- Uso de **ScrollView** para pantallas con contenido extenso
- Integración de APIs externas (traducción) con Retrofit
- Gestión de caché simple para optimizar peticiones
- Organización de código en capas (Network, Data, UI)

---

## 🚀 Cómo ejecutar

1. Clona el repositorio:

```bash
git clone https://github.com/GualpaJ/FreeGames-Android.git
