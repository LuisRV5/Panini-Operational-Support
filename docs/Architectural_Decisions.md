# Decisiones Arquitectónicas y Mantenibilidad

Este documento expone el criterio técnico para estructurar el proyecto de acuerdo con la rúbrica de evaluación, enfocado en las mejores prácticas de ingeniería de software.

## 1. Patrón Arquitectónico (MVVM)
Se adoptó **Model-View-ViewModel (MVVM)** para garantizar la separación de responsabilidades:
- **UI (Jetpack Compose):** Las pantallas (`Screens`) son "tontas", solo emiten intenciones (ej. `onClick`) al ViewModel y observan pasivamente el estado (`StateFlow`).
- **ViewModel:** Encapsula la lógica de negocio y presentación. No conoce la vista.
- **Repository:** Centraliza los datos y actúa como única fuente de la verdad (Single Source of Truth).

## 2. Naming y Terminología en Inglés
Todo el código fuente (clases, funciones, variables) y la estructura de directorios fue nombrada estrictamente en **inglés técnico** (ej. `TicketViewModel`, `updateTicketPriority`, `MockEngine`) respetando el estándar internacional de la industria del software. Únicamente los textos y etiquetas (Strings) dirigidos al usuario final están en español.

## 3. Modularidad y Organización de Paquetes
Se aplicó un enfoque de *Package by Layer / Feature* altamente modular:
- `data/remote/api`: Estructura para llamadas a red (Retrofit).
- `data/remote/model`: DTOs estructurados.
- `data/repository`: Lógica de recolección de datos y Mocks.
- `domain/model`: Lógica de negocio (Enums y Entidades puras).
- `ui/screens`: Vistas agrupadas.
Esta separación asegura que el equipo futuro de Panini no dependa de acoplamientos al cambiar una API o un componente de UI.

## 4. Reutilización y Evitando Sobreingeniería
- Componentes de UI como los "Badges" (`PriorityBadge`, `StatusBadge`) o paneles (`FeatureFlagsPanel`) se extrajeron como `@Composable` independientes para fomentar la reutilización.
- **Prevención de Sobreingeniería:** Se evitó la inclusión de librerías complejas como Dagger/Hilt para inyección de dependencias. Para el alcance de un PoC, añadir Dagger constituiría **sobreingeniería injustificada**, por lo que se utilizó instanciación nativa y el componente de Navigation Compose. No se introdujo complejidad innecesaria.
