# Barud API

REST API para la gestión de un restaurante. Permite administrar mesas, empleados, productos, pedidos, cuentas, pagos y divisiones de cuenta, además de exponer consultas analíticas a través de vistas de base de datos.

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

## Requisitos previos

- Java 21+
- Maven 3.9+ (o usar el wrapper `mvnw` incluido)
- PostgreSQL corriendo en `localhost:5433`
- Base de datos creada con nombre `BARUD`

---

## Configuración

El archivo de configuración se encuentra en `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/BARUD
spring.datasource.username=postgres
spring.datasource.password=123456
spring.datasource.hikari.maximum-pool-size=5
```

Ajustar las credenciales y el puerto según el entorno local.

---

## Ejecución

```bash
# Con el wrapper de Maven incluido
./mvnw spring-boot:run

# O compilar y ejecutar el JAR
./mvnw clean package
java -jar target/barud-0.0.1-SNAPSHOT.jar
```

La API queda disponible en: `http://localhost:8080`

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
