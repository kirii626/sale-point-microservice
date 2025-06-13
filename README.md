# 🧾 Sale Point Microservice

## 📘 Language Directory

- [English Version](#-sale-point-microservice-english)
- [Versión en Español](#-microservicio-de-puntos-de-venta-español)

---

## 🧾 Sale Point Microservice (English)

### 📁 Project Structure

```
sale_point_service
├── config
├── controllers
├── dtos
├── exceptions
├── graph
├── models
├── repositories
├── services
│   ├── implementations
│   ├── mappers
│   └── validations
├── utils
└── SalePointServiceApplication.java
```

### 📌 Core Features

- Create, update and delete sale points.
- Register and delete connection costs between sale points.
- Find direct connections.
- Calculate the shortest path (Dijkstra).
- Uses Redis for cache and JWT for secured access.
- Eureka discovery and service registration.

### 🔐 Security

- JWT authentication.
- Token role validation for admin access via `AdminRoleInterceptor`.
- Internal communication secured with `X-Internal-Token` header.
- Cache secured data using Redis and `@EnableCaching`.

### 📡 API Endpoints

### 🧾 Sale Point Management (`/api/sale-point/admin`)

- `GET /all`  
  🔹 Returns a list of all registered sale points.  
  🔒 Requires `ADMIN` role.  
  🧾 **Response**: `ArrayList<SalePointDtoOutput>`

![](docs\images\AdminSalePointController-getAllSalePoints.png)

- `POST /add-sale-point`  
  🔹 Registers a new sale point with the given name.  
  🔒 Requires `ADMIN` role.  
  🧾 **Request Body**: `SalePointDtoInput`  
  🧾 **Response**: `SalePointDtoOutput`

![](docs\images\AdminSalePointController-addSalePoint.png)

- `PUT /edit-sale-point/{salePointId}`  
  🔹 Updates the name of an existing sale point.  
  🔒 Requires `ADMIN` role.  
  🧾 **Request Body**: `SalePointDtoInput`  
  🧾 **Response**: `SalePointDtoOutput`

![](docs\images\AdminSalePointController-updateSalePoint.png)

- `DELETE /delete-sale-point/{salePointId}`  
  🔹 Deletes a sale point and its associated cost connections.  
  🔒 Requires `ADMIN` role.  
  🧾 **Response**: `String` confirmation message

![](docs\images\AdminSalePointController-deleteSalePoint.png)
### 💲 Cost Management (`/api/sale-point/cost/admin`)

- `GET /all`  
  🔹 Retrieves all existing connections (edges) with their costs.  
  🔒 Requires `ADMIN` role.  
  🧾 **Response**: `List<CostDto>`

![](docs\images\AdminCostController-getAllCosts.png)

- `POST /create`  
  🔹 Creates a new bidirectional connection (edge) between two sale points.  
  🔒 Requires `ADMIN` role.  
  🧾 **Request Body**: `CostDto`  
  🧾 **Response**: `CostDto` (created)

![](docs\images\AdminCostController-createCost.png)

- `DELETE /{fromId}-{toId}`  
  🔹 Removes the connection between two given sale points.  
  🔒 Requires `ADMIN` role.  
  🧾 **Response**: `String` confirmation message

![](docs\images\AdminCostController-deleteCost.png)

- `GET /direct-connections/{fromId}`  
  🔹 Lists all sale points directly connected to the given one (fromId).  
  🔒 Requires `ADMIN` role.  
  🧾 **Response**: `List<CostDto>`

![](docs\images\AdminCostController-allDirectConnectionsFrom.png)

- `GET /shortest-path?from=X&to=Y`  
  🔹 Computes the shortest path between two sale points using Dijkstra’s algorithm.  
  🔒 Requires `ADMIN` role.  
  🧾 **Query Params**:
    - `from`: Source sale point ID
    - `to`: Destination sale point ID  
      🧾 **Response**: `ShortestPathResult`  
      🔁 If no path exists, returns a totalCost of `-1` and HTTP `404`.

![](docs\images\AdminCostController-getShortestPath.png)

### 🔒 Internal Access (`/api/sale-point/admin/internal-use`)

- `GET /by-id/{salePointId}`  
  🔹 Fetches internal data for a specific sale point by ID.  
  🔐 Requires the `X-Internal-Token` header to be present and valid.  
  🧾 **Response**: `SalePointDtoOutput`  
  ❌ Calls without valid token return `403 Forbidden`.

![](docs\images\InternalUseController-getSalePointById.png)

---

### 🧪 Profiles

| Profile     | Description                                                              |
|-------------|--------------------------------------------------------------------------|
| `default`   | Shared configuration for all environments.                               |
| `dev`       | Uses in-memory H2 and schema auto-creation.                              |
| `init`      | Loads schema and data on container start.                                |
| `docker`    | PostgreSQL without schema auto-generation.                               |

### 📦 Key Dependencies

- Spring Boot 3.4.4
- Spring Data JPA
- Spring Security + JWT
- Redis for cache
- Eureka Client
- Jacoco + SonarQube

### 🎯 SonarQube – Code Quality

![](docs\images\sonarqube-stats.png)

- ✅ Coverage: 82.7%
- ✅ Security: No issues
- ✅ Maintainability: Grade A

### 🚀 Run Locally

Dev profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Init profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=init
```

Docker profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
```

### 🐳 Docker or Podman

Ready for deployment on Docker as part of a larger stack (gateway, config-server, etc.). Typically runs alongside Eureka Server and PostgreSQL.

### ✅ Testing & Coverage
Coverage is managed by Jacoco and reported to SonarQube. To run tests, create an `application-test.properties` profile with all necessary environment variables and configurations, and ensure you have a SonarQube container or installation. You can generate reports locally with:

```bash
./mvnw clean verify
# See: target/site/jacoco/index.html
```

---

## 🧾 Microservicio de Puntos de Venta (Español)

### 📁 Estructura del Proyecto

```
sale_point_service
├── config
├── controllers
├── dtos
├── exceptions
├── graph
├── models
├── repositories
├── services
│   ├── implementations
│   ├── mappers
│   └── validations
├── utils
└── SalePointServiceApplication.java
```

### 📌 Funcionalidades Principales

- Crear, actualizar y eliminar puntos de venta.
- Registrar y eliminar costos de conexión entre puntos.
- Obtener conexiones directas.
- Calcular el camino más corto (Dijkstra).
- Redis para cache, JWT para autenticación.
- Descubrimiento con Eureka.

### 🔐 Seguridad

- Autenticación con JWT.
- Validación de rol para admin con `AdminRoleInterceptor`.
- Llamadas internas seguras con `X-Internal-Token`.
- Redis como backend de cache con `@EnableCaching`.

### 📡 Endpoints API

### 🧾 Gestión de Puntos de Venta (`/api/sale-point/admin`)

- `GET /all`  
  🔹 Devuelve una lista de todos los puntos de venta registrados.  
  🔒 Requiere rol `ADMIN`.  
  🧾 **Respuesta**: `ArrayList<SalePointDtoOutput>`

![](docs\images\AdminSalePointController-getAllSalePoints.png)


- `POST /add-sale-point`  
  🔹 Registra un nuevo punto de venta con el nombre proporcionado.  
  🔒 Requiere rol `ADMIN`.  
  🧾 **Cuerpo de la solicitud**: `SalePointDtoInput`  
  🧾 **Respuesta**: `SalePointDtoOutput`

![](docs\images\AdminSalePointController-addSalePoint.png)

- `PUT /edit-sale-point/{salePointId}`  
  🔹 Actualiza el nombre de un punto de venta existente.  
  🔒 Requiere rol `ADMIN`.  
  🧾 **Cuerpo de la solicitud**: `SalePointDtoInput`  
  🧾 **Respuesta**: `SalePointDtoOutput`

![](docs\images\AdminSalePointController-updateSalePoint.png)

- `DELETE /delete-sale-point/{salePointId}`  
  🔹 Elimina un punto de venta y sus conexiones asociadas.  
  🔒 Requiere rol `ADMIN`.  
  🧾 **Respuesta**: Mensaje de confirmación `String`

![](docs\images\AdminSalePointController-deleteSalePoint.png)

### 💲 Gestión de Costos (`/api/sale-point/cost/admin`)

- `GET /all`  
  🔹 Recupera todas las conexiones existentes con sus costos.  
  🔒 Requiere rol `ADMIN`.  
  🧾 **Respuesta**: `List<CostDto>`

![](docs\images\AdminCostController-getAllCosts.png)

- `POST /create`  
  🔹 Crea una nueva conexión bidireccional entre dos puntos.  
  🔒 Requiere rol `ADMIN`.  
  🧾 **Cuerpo de la solicitud**: `CostDto`  
  🧾 **Respuesta**: `CostDto` (creado)

![](docs\images\AdminCostController-createCost.png)

- `DELETE /{fromId}-{toId}`  
  🔹 Elimina la conexión entre dos puntos específicos.  
  🔒 Requiere rol `ADMIN`.  
  🧾 **Respuesta**: Mensaje de confirmación `String`

![](docs\images\AdminCostController-deleteCost.png)

- `GET /direct-connections/{fromId}`  
  🔹 Lista todas las conexiones directas desde el punto dado.  
  🔒 Requiere rol `ADMIN`.  
  🧾 **Respuesta**: `List<CostDto>`

![](docs\images\AdminCostController-allDirectConnectionsFrom.png)

- `GET /shortest-path?from=X&to=Y`  
  🔹 Calcula el camino más corto entre dos puntos usando el algoritmo de Dijkstra.  
  🔒 Requiere rol `ADMIN`.  
  🧾 **Parámetros de consulta**:
    - `from`: ID del punto de origen
    - `to`: ID del punto de destino  
      🧾 **Respuesta**: `ShortestPathResult`  
      🔁 Si no existe camino, devuelve `totalCost = -1` y HTTP `404`.

![](docs\images\AdminCostController-getShortestPath.png)


### 🔒 Acceso Interno (`/api/sale-point/admin/internal-use`)

- `GET /by-id/{salePointId}`  
  🔹 Obtiene datos internos de un punto de venta específico por ID.  
  🔐 Requiere header `X-Internal-Token` válido.  
  🧾 **Respuesta**: `SalePointDtoOutput`  
  ❌ Llamadas sin token válido retornan `403 Forbidden`.

![](docs\images\InternalUseController-getSalePointById.png)

### 🧪 Perfiles

| Perfil     | Descripción                                                                 |
|------------|-----------------------------------------------------------------------------|
| `default`  | Configuración común.                                                        |
| `dev`      | Usa H2 con auto-creación de esquema.                                        |
| `init`     | Carga esquema y datos en el arranque.                                       |
| `docker`   | PostgreSQL sin auto-creación.                                               |

### 📦 Dependencias

- Spring Boot 3.4.4
- Spring Data JPA
- Spring Security + JWT
- Redis
- Eureka Client
- Jacoco + SonarQube

### 🎯 SonarQube – Calidad de Código


![](docs\images\sonarqube-stats.png)

- ✅ Cobertura: 82.7%
- ✅ Seguridad: Sin problemas
- ✅ Mantenibilidad: Grado A

### 🚀 Ejecución local

Perfil dev:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Perfil init:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=init
```

Perfil docker:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
```

### 🐳 Docker

Este microservicio está listo para correr en entornos Docker como parte de un stack mayor (gateway, config-server, etc.). Usualmente se ejecuta en conjunto con Eureka Server y una base PostgreSQL.

### ✅ Pruebas y Cobertura

La cobertura es gestionada por Jacoco y reportada a SonarQube, por lo que si quieres ejecutar los tests, deberás crear un perfil application-test.properties y
definir todas las variables de entorno y configuraciones, además deberás tener un contenedor de SonarQube o instalarlo. Puedes generar los reportes localmente con:


```bash
./mvnw clean verify
# Ver: target/site/jacoco/index.html
```