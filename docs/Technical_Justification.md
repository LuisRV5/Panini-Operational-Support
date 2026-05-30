# Justificación Técnica: Capa de Datos y Networking

Este documento explica las decisiones de diseño adoptadas para la capa de datos de la Prueba de Concepto (PoC) del sistema operativo de Panini.

## 1. Arquitectura MVVM y Separación de Responsabilidades
El proyecto sigue estrictamente el patrón **MVVM (Model-View-ViewModel)**. Para mantener la cohesión y el bajo acoplamiento, la capa de datos se ha estructurado en:
- **`domain.model`**: Modelos puros en Kotlin (ej. `Ticket`, `TicketPriority`) que representan las reglas de negocio, agnósticos a la red o base de datos.
- **`data.remote.model`**: Objetos de Transferencia de Datos (DTOs) que mapean exactamente con los contratos JSON esperados de una API real.
- **`data.remote.api`**: Interfaces de Retrofit (`ApiService`) que declaran las operaciones HTTP estándar.
- **`data.repository`**: El `TicketRepository` actúa como el mediador central (Single Source of Truth) para la aplicación.

## 2. Implementación del "MockEngine" Interno
Debido a la ausencia de un backend funcional para esta PoC y priorizando evitar la **sobreingeniería**, se tomó la decisión de que el `TicketRepository` maneje una estructura de datos simulada en memoria (Mocking).

### Evitando Complejidad Innecesaria
Inicialmente, se podría haber utilizado la API de Corrutinas `delay()` para simular latencia de red, o interceptores (Mock Interceptors) dentro de OkHttp. Sin embargo, dado que el objetivo principal de la PoC es validar la arquitectura base (Event-Driven y Feature Flags) sin enfocarnos en refinamientos cosméticos de UX (como loaders prolongados), los datos se devuelven de forma **síncrona e inmediata**.

### Mocks Realistas (Contexto Panini)
Los datos simulados en el repositorio no utilizan contenido genérico (ej. "Lorem Ipsum"), sino que reflejan incidentes reales de distribución logística del Mundial 2026:
- Faltantes de cajas de sobres.
- Retrasos en camiones de "Logística Express".
- Sobres mal sellados por parte de imprentas.

## 3. Configuración de Retrofit Preparada para el Futuro
Aunque los datos actuales provienen del MockEngine en memoria, se ha inicializado `RetrofitClient` y `ApiService` con `GsonConverterFactory`. Esto cumple con el requerimiento técnico de "dejar preparada una estructura de networking coherente para futuras integraciones". El día que el backend real esté disponible, el `TicketRepository` simplemente cambiará su origen de datos de la lista en memoria al cliente de Retrofit, sin alterar ninguna capa superior de la aplicación (ViewModel o View).
