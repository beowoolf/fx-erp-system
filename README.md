# fx-erp-system

![Java](https://img.shields.io/badge/Java-25-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.16-6DB33F?logo=springboot&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-17.0.6-FF6B00)
![Maven](https://img.shields.io/badge/Maven-3.8.7-C71A36?logo=apachemaven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![Version](https://img.shields.io/badge/version-0.0.1--SNAPSHOT-blue)

Desktop ERP client (JavaFX) backed by a Spring Boot REST API and MySQL. Operators sign in, then manage employees and warehouse stock.

## Table of contents

- [Project description](#project-description)
- [Tech stack](#tech-stack)
- [Getting started locally](#getting-started-locally)
- [Available scripts](#available-scripts)
- [Project scope](#project-scope)
- [Project status](#project-status)
- [License](#license)

## Project description

**fx-erp-system** is a two-module ERP application:

| Module | Path | Role |
| --- | --- | --- |
| Backend | `backend` | Spring Boot REST API (`pl.erp.backend`). Persists employees, operators, warehouses, items, and quantity types with Spring Data JPA. |
| Frontend | `frontend` | JavaFX desktop client (`pl.erp.fx`). Talks to the API at `http://localhost:8080` through `RestTemplate`. |

The UI starts at an undecorated login window (`600×400`). After credential check it opens the main shell with two modules: **Employees** (default) and **Warehouse**. Logout returns to the login screen.

There is no additional documentation in this repository. API paths and screens are defined in the controllers and FXML files listed under [Project scope](#project-scope).

## Tech stack

**Backend** (`backend/pom.xml`)

- Java 25 (`maven.compiler.source` / `target`)
- Spring Boot 3.4.16 (`spring-boot-starter-parent`)
- Spring Web (`RestController`)
- Spring Data JPA and Hibernate (`ddl-auto=update`, `MySQLDialect`)
- MySQL Connector/J
- Lombok
- Spring Boot DevTools (runtime, optional)
- `spring-boot-starter-test` (JUnit Vintage excluded; JUnit 5)
- Maven Wrapper 3.8.7 (`backend/mvnw`, `backend/mvnw.cmd`)
- Docker multi-stage image (`backend/Dockerfile`) and Compose (`backend/docker-compose.yml`)

**Frontend** (`frontend/pom.xml`)

- Java 25
- Spring Boot parent 3.4.16 and `spring-boot-starter-web` (used for `RestTemplate`, not as a web server)
- JavaFX 17.0.6: `javafx-controls`, `javafx-fxml`, `javafx-graphics`, `javafx-base`
- Lombok
- FXML views and CSS under `frontend/src/main/resources`

**Data store**

- MySQL database `erp` (Compose service `mysql`, plus phpMyAdmin and Adminer)

## Getting started locally

### Prerequisites

- JDK 25
- Maven 3.8+ (or the backend Maven Wrapper; it does not need a global Maven install)
- Docker and Docker Compose, if you use the bundled MySQL and backend image
- A desktop environment that can show JavaFX (the client is not a browser app)

### 1. Database

From `backend`, start MySQL (and the optional admin UIs):

```bash
cd backend
docker compose up -d mysql
```

Defaults match `application.properties` and `docker-compose.yml`:

| Setting | Value |
| --- | --- |
| Host | `localhost` (Compose hostname `mysql` inside the backend container) |
| Port | `3306` |
| Database | `erp` |
| User | `root` |
| Password | `toor` |

Override any of these with `MYSQL_HOST`, `MYSQL_PORT`, `MYSQL_DB_NAME`, `MYSQL_USER`, and `MYSQL_PASSWORD`. Hibernate creates and updates tables on startup (`spring.jpa.hibernate.ddl-auto=update`).

Optional tools from the same Compose file:

- phpMyAdmin: [http://localhost:8081](http://localhost:8081)
- Adminer: [http://localhost:8082](http://localhost:8082)

### 2. Backend

```bash
cd backend
./mvnw spring-boot:run
```

On Windows:

```bat
cd backend
mvnw.cmd spring-boot:run
```

The API listens on port **8080**.

Create an operator before using the desktop login (passwords are stored and compared as plain text):

```bash
curl -X POST http://localhost:8080/operators ^
  -H "Content-Type: application/json" ^
  -d "{\"login\":\"admin\",\"password\":\"admin\"}"
```

On bash, use `\` for line continuation and the same JSON body.

### 3. Frontend

The client expects the API at `http://localhost:8080`. `frontend/pom.xml` has no JavaFX or exec plugin, so compile with Maven and launch `pl.erp.fx.Main` from the IDE (the original project was set up in IntelliJ IDEA):

```bash
cd frontend
mvn clean compile
```

Run the main class `pl.erp.fx.Main`. Sign in with the operator created above, then open **Employees** or **Warehouse** from the menu.

### Docker (backend and MySQL together)

`backend/Dockerfile` is a multi-stage build (`./mvnw clean package -DskipTests`, then `java -jar app.jar`). Compose builds that image as `erp-backend` and waits for MySQL.

```bash
cd backend
docker compose up -d
```

Equivalent image-only flow:

```bash
cd backend
docker build -f Dockerfile -t fx-erp-system-backend .
docker run -d -p 8080:8080 \
  -e MYSQL_HOST=host.docker.internal \
  -e MYSQL_PORT=3306 \
  -e MYSQL_DB_NAME=erp \
  -e MYSQL_USER=root \
  -e MYSQL_PASSWORD=toor \
  fx-erp-system-backend
```

The Dockerfile base images are Eclipse Temurin **21**, while both POMs compile for Java **25**. A local `./mvnw` run needs JDK 25; the container build still uses JDK 21 and may fail until the Dockerfile is updated.

## Available scripts

Run backend goals from `backend` with the wrapper (`./mvnw` or `mvnw.cmd`). Frontend goals use a local Maven install (`mvn`) because that module has no wrapper.

| Command | Module | What it does |
| --- | --- | --- |
| `./mvnw spring-boot:run` | backend | Starts the API on port 8080 |
| `./mvnw clean package` | backend | Builds `target/*.jar` (tests included) |
| `./mvnw test` | backend | Runs `BackendApplicationTests` (`@SpringBootTest`, context load) |
| `./mvnw clean package -DskipTests` | backend | Same package goal used by the Dockerfile |
| `mvn clean compile` | frontend | Compiles the JavaFX client |
| `docker compose up -d` | backend | Starts MySQL, phpMyAdmin, Adminer, and the API |
| `docker compose up -d mysql` | backend | Starts only MySQL |
| `docker build -f Dockerfile -t fx-erp-system-backend .` | backend | Builds the API image |
| `docker run -d -p 8080:8080 fx-erp-system-backend` | backend | Runs the image (set `MYSQL_*` if the database is not on the default host) |

There is no npm/script runner and no GitHub Actions workflow in this repository.

## Project scope

### Desktop client

- **Login** (`login.fxml`): `POST /verify_operator_credentials`. Unknown login or wrong password stays unauthenticated.
- **App shell** (`app.fxml`): menu for the employee module, warehouse module, logout, and exit.
- **Employees** (`employee.fxml` and add/view/edit/delete dialogs): table of first name, last name, and salary; refresh, create, view, update, delete.
- **Warehouse** (`warehouse.fxml` and item dialogs): warehouse picker and item table; add, view, edit, and delete items (name, quantity, quantity type) for the selected warehouse.

### REST API

Base URL: `http://localhost:8080`. No global `/api` prefix and no auth token on later calls; only the login screen checks credentials.

| Method | Path | Purpose |
| --- | --- | --- |
| `GET` | `/employees` | List employees |
| `GET` | `/employees/{idEmployee}` | One employee (handler sleeps 500 ms) |
| `POST` | `/employees` | Create or update (body `EmployeeDto`; update when `idEmployee` is set) |
| `DELETE` | `/employees/{idEmployee}` | Delete employee |
| `GET` | `/operators` | List operators |
| `POST` | `/operators` | Create operator |
| `DELETE` | `/operators` | Delete operator (id in the body) |
| `POST` | `/verify_operator_credentials` | Check login and password |
| `GET` | `/warehouses` | List warehouses |
| `POST` | `/warehouses` | Create warehouse |
| `DELETE` | `/warehouses` | Delete warehouse (id in the body) |
| `GET` | `/warehouse_module_data` | Warehouse module payload; optional `idWarehouse` query param |
| `GET` | `/items` | List items |
| `GET` | `/items/{idItem}` | One item |
| `GET` | `/item_edit_data/{idItem}` | Item plus quantity types for the edit form |
| `POST` | `/items` | Create or update item (`ItemSaveDto`) |
| `DELETE` | `/items/{idItem}` | Delete item |
| `GET` | `/quantity_types` | List quantity types |
| `POST` | `/quantity_types` | Create quantity type |
| `DELETE` | `/quantity_types` | Delete quantity type (id in the body) |

### Domain

- **Employee** — first name, last name, salary; optional one-to-one **Operator** (login, password).
- **Warehouse** — name; one-to-many **Item**.
- **Item** — name, quantity, **QuantityType**, warehouse.
- **QuantityType** — unit name shared by items.

## Project status

Both modules are version **0.0.1-SNAPSHOT**. The employee and warehouse flows used by the desktop client are implemented. This is not a production release.

Current limits:

- Automated tests are a single Spring Boot context-load test. It needs a reachable MySQL instance.
- Operator passwords are plain text. The API does not protect endpoints after login.
- The JavaFX module has no run plugin; start it from the IDE.
- `backend/Dockerfile` uses JDK 21 while the POMs target Java 25.
- No CI configuration and no license file are checked in.
- Several REST error paths throw if a record is missing instead of returning HTTP 404.

## License

No `LICENSE` file is present. All rights reserved by the copyright holder until a license is added.
