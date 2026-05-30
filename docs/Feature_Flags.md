# Manejo de Feature Flags (Funcionalidades Experimentales)

Debido al requisito de mantener la agilidad durante pruebas internas (testing A/B o despliegues controlados), la aplicación integra un sistema básico de **Feature Flags** (Interruptores de características).

## Implementación
Las Feature Flags se han integrado directamente en el `TicketUiState` para asegurar que todo componente visual que dependa de ellas sea recomposto si la flag cambia en tiempo de ejecución. 

En un escenario de producción real, estos valores se inicializarían llamando a un backend (ej. Firebase Remote Config). Para efectos de esta PoC, se han añadido métodos en el `TicketViewModel` que permiten hacer un "toggle" temporal desde un panel de pruebas integrado en la UI principal.

### Flags Implementadas

1. **`isTicketCreationEnabled`**
   - **Propósito:** Determina si los usuarios de la aplicación tienen los permisos o la capacidad temporal de crear nuevos tickets de soporte.
   - **Comportamiento Reactivo:** Si se deshabilita, el botón flotante (FAB) u opción de navegación para crear tickets desaparecerá completamente de la interfaz `TicketListScreen`, impidiendo la creación. Además, la capa ViewModel también verifica la flag, garantizando seguridad si por algún error el evento se dispara.

2. **`isPriorityUpdateEnabled`**
   - **Propósito:** Controla si los operadores tienen permitido modificar la criticidad (prioridad) de un ticket existente.
   - **Comportamiento Reactivo:** Al deshabilitarse esta opción, el selector de prioridades (Dropdown) dentro de `TicketDetailScreen` se bloqueará y se mostrará como solo-lectura, previniendo alteraciones y cambios bruscos en el reordenamiento automático del listado.
