# Panini Operational Support (PoC)

Esta es una aplicación móvil (Prueba de Concepto - PoC) desarrollada para **Panini**, orientada a gestionar incidencias operativas y de soporte técnico relacionadas con la distribución del álbum oficial de la Copa Mundial FIFA 2026.

## 📱 Descripción General
El sistema permite a los operadores:
- Visualizar un listado de incidencias (Tickets) actualizadas en tiempo real.
- Crear nuevos tickets de soporte técnico o logístico.
- Modificar el estado y la prioridad de los tickets existentes.
- Utilizar *Feature Flags* internas para habilitar o deshabilitar funcionalidades experimentales sin necesidad de desplegar nuevas versiones.

La interfaz de usuario ha sido diseñada con un enfoque **minimalista y profesional**, utilizando una paleta de colores en escala de grises (blanco, negro, gris) para asegurar máxima legibilidad y evitar distracciones visuales innecesarias.

## 🛠️ Tecnologías Utilizadas
El proyecto sigue los estándares más modernos de desarrollo Android, manteniendo la simplicidad para evitar sobreingeniería:

- **Jetpack Compose:** Framework declarativo para la construcción de la UI.
- **Compose Navigation:** Manejo de rutas y flujo de pantallas en un entorno *Single-Activity*.
- **Arquitectura MVVM:** Separación clara entre la vista (`Screens`), la lógica de presentación (`ViewModel`) y los datos (`Repository`).
- **Kotlin Coroutines & StateFlow:** Implementación de programación reactiva para manejar estados de forma asíncrona y basada en eventos.
- **Retrofit & Gson:** Capa de red estructurada mediante contratos y DTOs, preparada para una futura integración real. (Actualmente operando mediante un *Mock Engine* en memoria).

## 📂 Estructura del Repositorio

La entrega cumple con los requisitos estructurales del proyecto:

- `/app`: Código fuente del proyecto Android (Jetpack Compose).
- `/contracts`: Contratos de la API (OpenAPI/Swagger) en formato YAML, separados por endpoints para máxima modularidad.
- `/docs`: Documentación técnica detallada explicando las decisiones arquitectónicas.
- `/video`: Enlace a la demostración en video.

## ⚙️ Arquitectura Basada en Eventos
El proyecto aprovecha **StateFlow** para mantener una "Fuente Única de Verdad" (Single Source of Truth).
Cualquier acción del usuario (como crear un ticket o cambiar su prioridad) modifica el repositorio en memoria e instantáneamente emite un nuevo estado hacia la UI. El `TicketViewModel` se encarga de interceptar estos cambios y ordenar automáticamente la lista por prioridad antes de enviarla a Jetpack Compose. Esto elimina por completo la necesidad de recargar manualmente las pantallas.

## 🚩 Feature Flags
Se ha implementado un sistema básico de *Feature Flags* para demostrar adaptabilidad:
1. `isTicketCreationEnabled`: Permite deshabilitar la creación de tickets.
2. `isPriorityUpdateEnabled`: Permite bloquear la capacidad de cambiar prioridades.

Ambos *flags* pueden activarse/desactivarse en tiempo real desde el panel superior del listado de tickets.

## 🧪 Guía de Pruebas y Flujos

Para validar el funcionamiento del sistema, sigue esta secuencia de pruebas recomendada:

### 1. Autenticación (Login)
- La aplicación arranca en el `LoginScreen`.
- **Validación:** Si intentas ingresar sin datos, el sistema requerirá que completes los campos obligatorios.
- **Credenciales Mock:** Utiliza el correo **`admin@panini.com`** y la contraseña **`admin`**.
- Al iniciar sesión exitosamente, serás redirigido al listado de tickets. La navegación elimina el login del historial, impidiendo volver atrás.

### 2. Listado y Feature Flags
- La pantalla `TicketListScreen` muestra los tickets precargados mediante el `MockEngine` del repositorio, ordenados automáticamente por prioridad (HIGH primero).
- **Prueba de Feature Flags:** En la parte superior encontrarás un panel. Apaga el check de "Permitir Crear Tickets" y notarás cómo el botón flotante (FAB) desaparece en tiempo real.

### 3. Creación de Incidencias
- Con la flag activa, presiona el FAB (+) para abrir `CreateTicketScreen`.
- Ingresa datos de prueba y selecciona la prioridad y categoría.
- Al guardar, la app regresa al listado y el nuevo ticket aparecerá automáticamente sin necesidad de refrescar la pantalla (gracias a StateFlow).

### 4. Detalles y Actualización Reactiva
- Toca cualquier ticket para abrir `TicketDetailScreen`.
- Modifica el **Estado** (ej. de OPEN a IN_PROGRESS).
- Cambia la **Prioridad** (ej. de LOW a HIGH). 
- **Prueba:** Vuelve al listado y comprueba cómo el ticket escaló automáticamente a las primeras posiciones.
- Si apagas el Feature Flag "Permitir Cambiar Prioridad" desde el listado, los botones de prioridad en el detalle se bloquearán de inmediato.

## ▶️ Ejecución del Proyecto
1. Abre Android Studio.
2. Selecciona **Open** y navega hasta la carpeta raíz del repositorio o directamente a la carpeta `/app`.
3. Sincroniza Gradle (`Sync Project with Gradle Files`).
4. Ejecuta el proyecto en un emulador o dispositivo físico (API 24 o superior requerida). Al no requerir un backend en vivo, los datos simulados se cargarán automáticamente al arrancar la aplicación.