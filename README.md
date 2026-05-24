# Barud API

## Descripción

**Barud** es una REST API para la gestión integral de un restaurante. Permite administrar mesas, empleados, productos, pedidos, cuentas, pagos y divisiones de cuenta, además de exponer consultas analíticas a través de vistas de base de datos. Está destinada a ser consumida por aplicaciones de frontend o clientes HTTP (Postman, etc.) utilizados por el personal del restaurante.

---

## Integrantes

| Nombre |
|---|
| Hana Sofía Pinilla Manrique |

---

## Requisitos previos

- Java 17 o superior (el proyecto usa Java 21)
- Maven 3.9+ (o usar el wrapper `mvnw` / `mvnw.cmd` incluido)
- PostgreSQL corriendo localmente
- IDE recomendado: VS Code o IntelliJ IDEA

---

## Instalación

1. **Clonar el repositorio**

   ```bash
   git clone https://github.com/tu-usuario/barud.git
   cd barud
   ```

2. **Cargar el esquema SQL**

   Crear la base de datos `BARUD` en PostgreSQL y ejecutar el script de creación de tablas y vistas incluido en el proyecto.

   ```sql
   CREATE DATABASE "BARUD";
   -- luego ejecutar el script SQL del proyecto
   ```

3. **Configurar `application.properties`**

   Editar `src/main/resources/application.properties` con las credenciales de tu entorno:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5433/BARUD
   spring.datasource.username=postgres
   spring.datasource.password=123456
   spring.datasource.hikari.maximum-pool-size=5
   ```

4. **Ejecutar la aplicación**

   ```bash
   # Windows
   mvnw.cmd spring-boot:run

   # macOS / Linux
   ./mvnw spring-boot:run
   ```

   La API queda disponible en: `http://localhost:8080`

---

## Diagrama ER

> _Añadir aquí una imagen o enlace al diagrama entidad-relación de la base de datos._
>
> Ejemplo: `![Diagrama ER](docs/diagrama-er.png)`

---

## Endpoints

A continuación se listan los endpoints disponibles. Para la documentación completa (body, parámetros y ejemplos) consultar [ENDPOINTS.md](ENDPOINTS.md).

| Método | URL | Descripción |
|--------|-----|-------------|
| `GET` | `/api/productos` | Lista productos con filtros y paginación |
| `GET` | `/api/productos/{id}` | Obtiene un producto por ID |
| `POST` | `/api/productos` | Crea un nuevo producto |
| `PUT` | `/api/productos/{id}` | Actualiza un producto |
| `DELETE` | `/api/productos/{id}` | Elimina un producto |
| `GET` | `/api/pedidos` | Lista pedidos con filtros y paginación |
| `GET` | `/api/pedidos/{id}` | Obtiene un pedido por ID |
| `POST` | `/api/pedidos` | Crea un nuevo pedido |
| `PUT` | `/api/pedidos/{id}` | Actualiza un pedido |
| `PUT` | `/api/pedidos/{id}/cerrar` | Cierra un pedido y libera la mesa |
| `DELETE` | `/api/pedidos/{id}` | Elimina un pedido |
| `GET` | `/api/detalles-pedido` | Lista todos los detalles de pedido |
| `GET` | `/api/detalles-pedido/{id}` | Obtiene un detalle por ID |
| `POST` | `/api/detalles-pedido` | Crea un detalle de pedido |
| `PUT` | `/api/detalles-pedido/{id}` | Actualiza un detalle de pedido |
| `DELETE` | `/api/detalles-pedido/{id}` | Elimina un detalle de pedido |
| `GET` | `/api/cuentas` | Lista cuentas con filtros y paginación |
| `GET` | `/api/cuentas/{id}` | Obtiene una cuenta por ID |
| `POST` | `/api/cuentas` | Crea una cuenta para un pedido |
| `PUT` | `/api/cuentas/{id}` | Actualiza una cuenta |
| `PUT` | `/api/cuentas/{id}/cerrar` | Cierra la cuenta y el pedido asociado |
| `DELETE` | `/api/cuentas/{id}` | Elimina una cuenta |
| `GET` | `/api/divisiones-cuenta` | Lista todas las divisiones de cuenta |
| `GET` | `/api/divisiones-cuenta/{id}` | Obtiene una división por ID |
| `POST` | `/api/divisiones-cuenta` | Crea una división de cuenta |
| `PUT` | `/api/divisiones-cuenta/{id}` | Actualiza una división de cuenta |
| `DELETE` | `/api/divisiones-cuenta/{id}` | Elimina una división de cuenta |
| `GET` | `/api/pagos` | Lista todos los pagos |
| `GET` | `/api/pagos/{id}` | Obtiene un pago por ID |
| `POST` | `/api/pagos` | Registra un nuevo pago |
| `PUT` | `/api/pagos/{id}` | Actualiza un pago |
| `DELETE` | `/api/pagos/{id}` | Elimina un pago |
| `GET` | `/api/empleados` | Lista empleados con filtros |
| `GET` | `/api/empleados/{id}` | Obtiene un empleado por ID |
| `POST` | `/api/empleados` | Crea un nuevo empleado |
| `PUT` | `/api/empleados/{id}` | Actualiza un empleado |
| `DELETE` | `/api/empleados/{id}` | Elimina un empleado |
| `GET` | `/api/mesas` | Lista todas las mesas |
| `GET` | `/api/mesas/{id}` | Obtiene una mesa por ID |
| `POST` | `/api/mesas` | Crea una nueva mesa |
| `PUT` | `/api/mesas/{id}` | Actualiza una mesa |
| `DELETE` | `/api/mesas/{id}` | Elimina una mesa |
| `GET` | `/api/vistas/detalle-cuenta-mesa` | Detalle completo de cuentas con mesa y productos |
| `GET` | `/api/vistas/ingresos-dia-semana` | Ingresos agrupados por día de la semana |
| `GET` | `/api/vistas/productos-mas-vendidos` | Productos ordenados por cantidad vendida |
| `GET` | `/api/vistas/pedidos-por-dia` | Pedidos e ingresos agrupados por fecha |

---

## Requerimientos funcionales

1. **Gestión de mesas** — Crear, consultar, actualizar y eliminar mesas; controlar su estado (`Disponible`, `Ocupada`, `Reservada`). Al cerrar un pedido la mesa vuelve a `Disponible` automáticamente.
2. **Gestión de pedidos y detalles** — Registrar pedidos vinculados a una mesa y un mesero; añadir, modificar y cancelar ítems (detalles) con precio unitario y cantidad.
3. **Facturación y pagos** — Generar cuentas con subtotal, impuestos y total; soportar cierre de cuenta con cambio de estado del pedido; registrar pagos con método y monto.
4. **División de cuenta** — Dividir una cuenta en partes con descripción y monto individual para grupos de comensales.
5. **Gestión de empleados y productos** — CRUD completo con filtros por rol/estado para empleados y por nombre/tipo/precio/stock para productos.
6. **Consultas analíticas** — Vistas de base de datos que exponen ingresos por día de la semana, productos más vendidos, pedidos por fecha y detalle completo de cuenta por mesa.

---

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.6 |
| Spring Data JDBC | (incluido en Boot) |
| Spring Web MVC | (incluido en Boot) |
| PostgreSQL | — |
| Lombok | — |
| HikariCP | (incluido en Boot) |

---

## Estructura del proyecto

```
src/main/java/com/barud/
├── barud/              # Clase principal (BarudApplication)
├── config/             # Configuración CORS
├── controller/         # Controladores REST
├── dto/
│   ├── request/        # DTOs de entrada
│   └── response/       # DTOs de salida
├── model/
│   ├── enums/          # Enumeraciones del dominio
│   └── *.java          # Entidades
├── repository/         # Repositorios Spring Data JDBC
└── service/            # Lógica de negocio
```

---

## CORS

La API acepta solicitudes de los siguientes orígenes (útil para desarrollo con frontend):

- `http://localhost:5173`
- `http://localhost:5174`

Métodos permitidos: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`.

---

## Enumeraciones del dominio

Los campos de tipo enumerado aceptan los valores listados a continuación (sin distinción de mayúsculas).

| Enum | Valores |
|---|---|
| `ProductoTipo` | `Bebida alcoholica`, `Bebida no alcoholica`, `Comida` |
| `PedidoEstado` | `Abierto`, `En preparacion`, `Cerrado` |
| `CuentaEstado` | `Abierta`, `Cerrada` |
| `DetallePedidoEstado` | `Activo`, `Cancelado` |
| `MesaEstado` | `Disponible`, `Ocupada`, `Reservada` |
| `EmpleadoRol` | `Mesero`, `Bartender`, `Cajero`, `Administrador` |
| `EmpleadoEstado` | `Activo`, `Inactivo` |
| `PagoMetodo` | `Efectivo`, `Tarjeta`, `Transferencia` |

---

## Endpoints

Base URL: `http://localhost:8080`

### Productos — `/api/productos`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/productos` | Lista productos con filtros opcionales (`nombre`, `tipo`, `minPrecio`, `maxPrecio`, `minStock`, `disponibles`) y paginación (`page`, `size`) |
| `GET` | `/api/productos/{id}` | Obtiene un producto por ID |
| `POST` | `/api/productos` | Crea un nuevo producto |
| `PUT` | `/api/productos/{id}` | Actualiza un producto existente |
| `DELETE` | `/api/productos/{id}` | Elimina un producto |

### Pedidos — `/api/pedidos`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/pedidos` | Lista pedidos con filtros opcionales (`idMesa`, `idMesero`, `estado`, `fechaDesde`, `fechaHasta`) y paginación |
| `GET` | `/api/pedidos/{id}` | Obtiene un pedido por ID |
| `POST` | `/api/pedidos` | Crea un nuevo pedido |
| `PUT` | `/api/pedidos/{id}` | Actualiza un pedido existente |
| `PUT` | `/api/pedidos/{id}/cerrar` | Cierra el pedido y libera la mesa asociada |
| `DELETE` | `/api/pedidos/{id}` | Elimina un pedido |

### Detalles de Pedido — `/api/detalles-pedido`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/detalles-pedido` | Lista todos los detalles de pedido |
| `GET` | `/api/detalles-pedido/{id}` | Obtiene un detalle por ID |
| `POST` | `/api/detalles-pedido` | Agrega un ítem a un pedido |
| `PUT` | `/api/detalles-pedido/{id}` | Actualiza un ítem de pedido |
| `DELETE` | `/api/detalles-pedido/{id}` | Elimina un ítem de pedido |

### Cuentas — `/api/cuentas`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/cuentas` | Lista cuentas con filtros opcionales (`idPedido`, `estado`, `minTotal`, `maxTotal`) y paginación |
| `GET` | `/api/cuentas/{id}` | Obtiene una cuenta por ID |
| `POST` | `/api/cuentas` | Crea una cuenta para un pedido (`409` si ya existe una cuenta abierta) |
| `PUT` | `/api/cuentas/{id}` | Actualiza una cuenta existente |
| `PUT` | `/api/cuentas/{id}/cerrar` | Cierra la cuenta y el pedido asociado |
| `DELETE` | `/api/cuentas/{id}` | Elimina una cuenta |

### Divisiones de Cuenta — `/api/divisiones-cuenta`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/divisiones-cuenta` | Lista todas las divisiones de cuenta |
| `GET` | `/api/divisiones-cuenta/{id}` | Obtiene una división por ID |
| `POST` | `/api/divisiones-cuenta` | Crea una nueva división de cuenta |
| `PUT` | `/api/divisiones-cuenta/{id}` | Actualiza una división de cuenta |
| `DELETE` | `/api/divisiones-cuenta/{id}` | Elimina una división de cuenta |

### Pagos — `/api/pagos`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/pagos` | Lista todos los pagos |
| `GET` | `/api/pagos/{id}` | Obtiene un pago por ID |
| `POST` | `/api/pagos` | Registra un nuevo pago |
| `PUT` | `/api/pagos/{id}` | Actualiza un pago existente |
| `DELETE` | `/api/pagos/{id}` | Elimina un pago |

### Empleados — `/api/empleados`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/empleados` | Lista empleados con filtros opcionales (`nombre`, `rol`, `estado`, `fechaDesde`, `fechaHasta`) |
| `GET` | `/api/empleados/{id}` | Obtiene un empleado por ID |
| `POST` | `/api/empleados` | Crea un nuevo empleado |
| `PUT` | `/api/empleados/{id}` | Actualiza un empleado existente |
| `DELETE` | `/api/empleados/{id}` | Elimina un empleado |

### Mesas — `/api/mesas`

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/api/mesas` | Lista todas las mesas |
| `GET` | `/api/mesas/{id}` | Obtiene una mesa por ID |
| `POST` | `/api/mesas` | Crea una nueva mesa |
| `PUT` | `/api/mesas/{id}` | Actualiza una mesa existente |
| `DELETE` | `/api/mesas/{id}` | Elimina una mesa |

### Vistas / Consultas analíticas — `/api/vistas`

Todos los endpoints son `GET` de solo lectura, sin parámetros.

| Ruta | Descripción |
|---|---|
| `/api/vistas/detalle-cuenta-mesa` | Detalle completo de cuentas por mesa (productos, subtotal, impuestos, total) |
| `/api/vistas/ingresos-dia-semana` | Ingresos totales y promedio agrupados por día de la semana |
| `/api/vistas/productos-mas-vendidos` | Ranking de productos por cantidad vendida e ingresos generados |
| `/api/vistas/pedidos-por-dia` | Cantidad de pedidos e ingresos agrupados por fecha |
| `/api/vistas/productos-disponibles` | Productos con stock mayor a cero |
| `/api/vistas/detalle-pedido-completo` | Detalle completo de ítems de pedido con mesa, producto, cantidad y estado |
| `/api/vistas/pedidos-activos` | Pedidos activos con información de mesa y mesero |
| `/api/vistas/pedidos-mesero` | Todos los pedidos con información del mesero asignado |
| `/api/vistas/detalle-pedido-mesero` | Ítems de pedido con producto, cantidad, precio unitario y subtotal |
| `/api/vistas/cuenta-mesa` | Resumen de cuentas con mesa, subtotal, impuestos, total y estado |
| `/api/vistas/division-cuenta-mesa` | Divisiones de cuenta con número de mesa, descripción y monto |
| `/api/vistas/bebidas-alcoholicas-mas-vendidas` | Bebidas alcohólicas cuyas ventas superan el promedio propio |
| `/api/vistas/comidas-no-pedidas` | Comidas del menú que nunca han sido incluidas en ningún pedido |

> Para la documentación completa de request/response bodies ver [ENDPOINTS.md](ENDPOINTS.md).

---

## Códigos de respuesta comunes

| Código | Significado |
|---|---|
| `200 OK` | Operación exitosa |
| `204 No Content` | Eliminación exitosa |
| `404 Not Found` | Recurso no encontrado |
| `409 Conflict` | Conflicto de estado (ej: ya existe una cuenta abierta para ese pedido) |
