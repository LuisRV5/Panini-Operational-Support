# Explicación Técnica de Contratos API (YAML)

## Contexto
Como parte de la prueba de concepto (PoC) para el soporte operativo de Panini, se han definido los contratos de la API en formato OpenAPI/Swagger (YAML). Aunque el backend no será implementado en esta etapa, estos contratos sirven como la única fuente de verdad para la integración futura entre el equipo móvil y el equipo de backend.

## Estructura de Contratos
Por recomendación arquitectónica y para mantener una alta granularidad, se ha decidido separar los contratos por endpoint o dominio de acción en lugar de mantener un solo archivo monolítico. Esto facilita la mantenibilidad, evita conflictos de control de versiones y permite iterar cada endpoint de forma independiente.

Los archivos generados en `/contracts` son:

1. **`auth-login-api.yaml`**: Define el inicio de sesión del operador y el retorno del JWT.
2. **`get-tickets-api.yaml`**: Define el listado de incidencias y soporte básico de arreglos.
3. **`get-ticket-detail-api.yaml`**: Detalla el payload completo de un ticket, incluyendo los datos anidados del proveedor.
4. **`create-ticket-api.yaml`**: Payload de entrada (Request) necesario para reportar un incidente (ej. Faltante de inventario).
5. **`update-ticket-status-api.yaml`**: Actualización parcial (PATCH) exclusiva para el cambio de estado.
6. **`update-ticket-priority-api.yaml`**: Actualización parcial (PATCH) exclusiva para el cambio de prioridad, evento que dispara la reactividad en el listado móvil.

## Consideraciones de Diseño
- **Estándar RESTful:** Se utilizaron métodos correctos (GET, POST, PATCH) y URIs semánticas. Se eligió `PATCH` en lugar de `PUT` para actualizaciones parciales de estado y prioridad.
- **Códigos HTTP Exhaustivos:** Todos los contratos definen escenarios más allá del `200 OK` o `201 Created`, incluyendo `400 Bad Request`, `401 Unauthorized`, `404 Not Found` y `500 Internal Server Error`, garantizando que la aplicación móvil esté diseñada para manejar estados de error y no solo "Happy Paths".
- **Esquemas JSON (DTOs):** Los esquemas definidos en los componentes (ej. `TicketSummary`, `TicketDetail`, `CreateTicketRequest`) son la base exacta que el equipo de Android utilizará para mapear las clases DTOs usando Retrofit y Gson/Moshi.
