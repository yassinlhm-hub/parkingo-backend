# ParkinGo — Backend

API REST + WebSocket para la app de valet parking bajo demanda ParkinGo.
Spring Boot 3 · Java 17 · PostgreSQL · JWT · WebSocket (STOMP).

## Arrancar en local

1. Base de datos:
   ```
   docker compose up -d
   ```
2. Compilar y arrancar (necesitas JDK 17 y Maven, o usa el wrapper si lo generas con `mvn -N wrapper:wrapper`):
   ```
   mvn spring-boot:run
   ```
3. La API queda en `http://localhost:8080`. Las migraciones (Flyway) se aplican solas al arrancar.

## Variables de entorno relevantes

| Variable | Default | Descripción |
|---|---|---|
| `DB_HOST` / `DB_PORT` / `DB_NAME` / `DB_USER` / `DB_PASSWORD` | ver `application.yml` | Conexión a PostgreSQL |
| `JWT_SECRET` | valor de ejemplo | **Cámbialo en producción** — mínimo 32 caracteres |

## Endpoints principales

- `POST /auth/register` — registro (rol `CUSTOMER` o `VALET`)
- `POST /auth/login`
- `POST /vehicles` · `GET /vehicles/mine`
- `POST /requests` — el cliente crea una solicitud de valet
- `GET /requests/pending` — solicitudes disponibles para un valet
- `POST /requests/{id}/accept` — el valet acepta
- `PATCH /requests/{id}/status` — cambia estado (ACCEPTED → IN_PROGRESS → PARKED → RETRIEVING → COMPLETED)
- `POST /requests/{id}/location` — el valet publica su ubicación (también se emite por WebSocket)
- `GET /requests/{id}/location` — histórico de ubicaciones
- `GET /requests/history/customer` · `GET /requests/history/valet`
- `POST /payments/charge` — cobro (simulado; ver `PaymentService` para conectar un proveedor real)

## Tiempo real

WebSocket STOMP en `/ws`. El cliente se suscribe a:
- `/topic/requests/{requestId}/location`
- `/topic/requests/{requestId}/status`

## Pendiente antes de producción

- Conectar un proveedor de pago real (Stripe/Redsys) en `PaymentService`
- Añadir refresh tokens (ahora mismo el JWT dura 24h fijas)
- Rate limiting en `/auth/**`
- Tests de integración (Testcontainers + PostgreSQL)
- Notificaciones push para nuevas solicitudes disponibles
