# Estrategia Basada en Eventos (Event-Driven Communication)

Esta prueba de concepto (PoC) implementa un modelo de programación reactiva para manejar las actualizaciones de la interfaz de usuario de forma completamente autónoma, evitando las recargas manuales (Pull-to-Refresh) o las llamadas imperativas para actualizar la lista.

## El Problema
Tradicionalmente, tras modificar un recurso (crear un ticket, o cambiar su estado), la vista se encargaba de pedir al servidor o a la base de datos la lista fresca de elementos (`fetchTickets()`). Esto acopla fuertemente las acciones y puede conducir a estados inconsistentes si ocurre un error.

## Solución: Kotlin Flow y Arquitectura Unidireccional
Hemos aprovechado la potencia de `StateFlow` dentro de Kotlin Coroutines para observar la capa de datos en tiempo real.

1. **`TicketRepository` (Fuente de Verdad):** Mantiene un `MutableStateFlow` con la lista de tickets. Cada vez que el repositorio agrega o modifica un ticket, *emite* un nuevo valor a través de este Flow.
2. **`TicketViewModel` (El Observador):** Durante su ciclo de vida (`init`), recolecta pasivamente (mediante `collectLatest`) los cambios que vengan del repositorio. Cuando llega una lista nueva, el ViewModel la recibe, le aplica lógica de negocio (como **ordenar la lista por prioridad**) y se la pasa a la Vista.
3. **Jetpack Compose (La Vista Reactiva):** La UI simplemente lee `uiState.collectAsState()`. Cada vez que el ViewModel emite, la UI se recompone instantáneamente con los datos más recientes y ordenados.

### Escenarios de Prueba Implementados
- **Creación de Tickets:** Al presionar "Guardar" en la vista de creación, solo se notifica al repositorio. El flujo reactivo se encarga de rellenar la pantalla del listado.
- **Actualización de Prioridad:** Al cambiar la prioridad de "LOW" a "HIGH", el repositorio notifica el cambio, el ViewModel reordena la lista de inmediato (las prioridades `HIGH` van primero), provocando que el ticket suba automáticamente a la parte superior de la vista.
