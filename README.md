# FFXV Data Tooling Suite: Herramientas de Análisis y Automatización de Datos

## 🚀 Descripción General
Este repositorio contiene una suite de herramientas desarrolladas en **Java** y **Python** para realizar **ingeniería inversa, análisis y modificación programática** de los archivos de datos binarios y de configuración del videojuego Final Fantasy XV.

El objetivo principal fue construir un *pipeline* de automatización para facilitar la creación de contenido avanzado (como nuevos sistemas de invocación) y la localización de recursos del juego.

---

## 🛠️ Stack Tecnológico y Componentes

| Categoría | Tecnologías y Herramientas |
| :--- | :--- |
| **Lenguajes Base** | Java, Python |
| **Herramientas de Análisis** | 010 Editor, Git & GitHub |
| **Conceptos Clave** | Ingeniería Inversa, Scripting, Automatización, Análisis de Binarios, Arquitectura de Datos |

---

## 📂 Estructura del Repositorio

El proyecto está organizado en módulos lógicos para demostrar buenas prácticas de modularidad:

*   **`/src/`**: Contiene todo el código fuente Java.
    *   `/com/ethzal/ffxv_tooling/models/`: Clase `Enemy.java`.
    *   `/com/ethzal/ffxv_tooling/parser/`: Clase principal `EnemyDataReader.java`.
    *   `/com/ethzal/ffxv_tooling/util/`: Clase `FileUtils.java` con utilidades estáticas.
*   **`/data/`**: Contiene los archivos de *input* necesarios para ejecutar las herramientas (ej: `enemies.bin`, `unknown_enemy_ids.txt`).
*   **`/output/`**: Carpeta donde se guardan los archivos generados por la herramienta (ej: `010_elements.txt`).
*   // Siguientes actualizaciones:
*   **`/python_tools/`**: Contiene el script de Web Scraping en Python.
*   **`/010_editor_templates/`**: Contiene las plantillas `.bt` utilizadas para el análisis de bajo nivel.
*   **`/docs/`**: Documentación detallada sobre las decisiones de ingeniería y el proceso de refactorización.

---

## 💡 Próximos Pasos y Aprendizajes (Visión de Futuro)

Este proyecto es una demostración de capacidad técnica autodidacta. El siguiente paso sería:

1.  **Implementar Testing:** Añadir tests unitarios (JUnit/Mockito) a la clase `EnemyDataReader` para validar la lógica de parsing.
2.  **Formalizar el Pipeline:** Integrar el script de Python en el flujo principal de Java para una automatización completa.
3.  **Mejorar la Documentación Técnica:** Profundizar en el `Engineering_PostMortem.md` explicando el impacto de la optimización del caché y la refactorización realizada.
