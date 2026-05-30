# Flujo General del Sistema (Navegación)

El flujo de usuario (UX) y la navegación entre pantallas de la aplicación han sido implementados utilizando **Jetpack Compose Navigation**. Toda la navegación se concentra de manera estructurada en un solo Activity (`MainActivity`), asegurando una experiencia fluida (Single-Activity Architecture).

## Grafo de Navegación (NavGraph)

La estructura de las rutas es la siguiente:

1. **`login` (Destino Inicial)**
   - **Pantalla:** `LoginScreen`
   - **Flujo:** Es un formulario simulado. Una vez el usuario "inicia sesión", el grafo de navegación elimina (`popUpTo`) el login del historial de pantallas para que el usuario no pueda regresar con el botón de "Atrás".

2. **`tickets`**
   - **Pantalla:** `TicketListScreen`
   - **Flujo:** Muestra el listado de incidencias actual.
   - **Acciones posibles:**
     - Si la Feature Flag `isTicketCreationEnabled` está activa, el usuario puede presionar el FAB (Floating Action Button) navegando a la ruta `create`.
     - Si el usuario toca un ticket, la aplicación navega a `detail/{ticketId}`.

3. **`detail/{ticketId}`**
   - **Pantalla:** `TicketDetailScreen`
   - **Flujo:** La pantalla recibe el `ticketId` por la URL de navegación. Se comunica con el `TicketViewModel` para extraer toda la información del ticket. 
   - **Acciones posibles:** Modificar el Estado o modificar la Prioridad (sólo si la Feature Flag está activa). Presionar "Atrás" hace un `popBackStack()`.

4. **`create`**
   - **Pantalla:** `CreateTicketScreen`
   - **Flujo:** Un formulario para registrar el ticket. Al finalizar, invoca el guardado en el ViewModel y hace `popBackStack()` para regresar automáticamente al listado. Gracias a la estrategia basada en eventos, el nuevo ticket ya estará renderizado en la pantalla anterior al volver.

## Inyección de Dependencias del ViewModel
Para facilitar la reactividad entre pantallas sin necesidad de un inyector complejo como Hilt/Dagger (por ser una PoC), el `TicketViewModel` se inicializa a nivel del `NavHost` dentro del `MainActivity` y se inyecta su instancia a las distintas pantallas. Esto asegura que la lista, el detalle y la creación trabajen exactamente sobre la misma fuente de verdad en memoria.
