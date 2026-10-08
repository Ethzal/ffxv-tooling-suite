# <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Telegram-Animated-Emojis/main/Activity/Sparkles.webp" alt="Sparkles" width="25" height="25" /> FFXV Data Tooling Suite

Suite de herramientas desarrollada en **Java** y **Python** para el análisis, extracción, modificación y automatización de datos internos de **Final Fantasy XV**.

El proyecto surgió como una investigación personal sobre las estructuras binarias y sistemas de configuración del juego, evolucionando posteriormente hacia herramientas propias para automatizar tareas que inicialmente requerían edición manual.

## <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Toolbox.png" alt="Toolbox" width="25" height="25" /> Objetivos del proyecto

El proyecto combina **ingeniería inversa, análisis de datos binarios, scripting y automatización** para trabajar con diferentes estructuras internas del juego.

Entre sus principales objetivos:

- Analizar estructuras y campos dentro de archivos binarios.
- Extraer y estructurar datos mediante herramientas propias.
- Automatizar modificaciones sobre bloques de datos.
- Crear herramientas para generar configuraciones personalizadas.
- Investigar sistemas de lógica y configuración basados en XML.
- Facilitar la creación y mantenimiento de mods mediante procesos reproducibles.

---

## <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Camera%20with%20Flash.png" alt="Camera with Flash" width="25" height="25" /> Proyectos y herramientas

### 🔧 Custom Stats Tool

Herramienta desarrollada en **Python** para generar configuraciones personalizadas de estadísticas del juego mediante una interfaz gráfica.

Permite automatizar la creación de configuraciones sin necesidad de modificar manualmente los valores mediante un editor hexadecimal.

### 📦 Data Block Replicator

Script desarrollado para **010 Editor** que permite copiar bloques de datos entre diferentes estructuras binarias.

Incluye funcionalidades para:

- Seleccionar rangos de datos.
- Detectar posibles valores enteros y `float`.
- Registrar valores anteriores y posteriores.
- Mostrar direcciones y posiciones de los datos.
- Automatizar operaciones repetitivas sobre estructuras binarias.

### 📊 Field Frequency Analyzer

Herramienta para analizar la frecuencia de aparición de valores dentro de campos estructurados de archivos binarios.

El proceso incluye extracción, validación, conteo de valores y búsqueda dentro de los datos analizados.

### ☕ Java Data Parser

Parser desarrollado en **Java** para extraer y estructurar información a partir de archivos binarios del juego.

El parser identifica diferentes campos y atributos de las estructuras analizadas y genera una representación estructurada de los datos para facilitar su posterior procesamiento.

### 🧩 XML Logic Analysis

Investigación y experimentación sobre sistemas de lógica basados en **nodos XML**, utilizando pruebas iterativas para identificar relaciones entre nodos, condiciones y comportamientos dentro del juego.

Este trabajo permitió desarrollar modificaciones de gameplay más complejas y comprender mejor el funcionamiento interno de determinados sistemas.

---

## <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Travel%20and%20places/Rocket.png" alt="Rocket" width="25" height="25" /> Aplicaciones prácticas

Las herramientas desarrolladas se utilizaron para crear y mantener diferentes modificaciones de Final Fantasy XV, incluyendo:

- Sistemas de invocación personalizables.
- Modificaciones de dificultad.
- Ajustes avanzados de combate.
- Configuraciones personalizadas de estadísticas.
- Herramientas de análisis y modificación de datos.

Los diferentes mods y herramientas publicados acumulan aproximadamente **7.000 descargas** entre Nexus Mods y CurseForge.

El proyecto también incluye la publicación de versiones, correcciones, actualizaciones de compatibilidad y resolución de problemas detectados por los usuarios.

---

## <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Telegram-Animated-Emojis/main/Objects/Laptop.webp" alt="Laptop" width="25" height="25" /> Stack tecnológico

| Categoría | Tecnologías / Herramientas |
|:---|:---|
| **Lenguajes** | Java · Python |
| **Análisis binario** | 010 Editor |
| **Ingeniería inversa** | Análisis de estructuras binarias · offsets · campos de datos |
| **Scripting** | 010 Editor Binary Templates |
| **Datos** | Archivos binarios · XML |
| **Automatización** | Python · Java |
| **Control de versiones** | Git · GitHub |

---

## <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Travel%20and%20places/Classical%20Building.png" alt="Classical Building" width="25" height="25" /> Estructura del repositorio

```text
src/
 └── Código fuente Java
      ├── models/
      ├── parser/
      └── util/

python_tools/
 └── Herramientas y scripts Python

010_editor_templates/
 └── Plantillas Binary Template (.bt)

data/
 └── Archivos de entrada utilizados por las herramientas

output/
 └── Datos y resultados generados durante el procesamiento

docs/
 └── Documentación técnica del proyecto
```

---

## <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Light%20Bulb.png" alt="Light Bulb" width="25" height="25" /> Aprendizajes

El proyecto permitió profundizar de forma autodidacta en:

- Ingeniería inversa aplicada a software.
- Análisis y estructuración de datos binarios.
- Desarrollo de parsers y herramientas de automatización.
- Scripting con 010 Editor.
- Interacción entre diferentes herramientas y lenguajes.
- Experimentación sistemática para comprender sistemas sin documentación.
- Desarrollo, publicación y mantenimiento de software utilizado por otros usuarios.

---
