# fx-erp-system

ERP system with a backend written in Spring Boot and a frontend written using JavaFX. Technologies used in the project:
Spring Boot: Web (RestControllers), Test, Data JPA, DevTools, Java 21, MySQL/MariaDB, JUnit Unit Tests, SpringBootTest
Integration Tests, Lombok, JavaFX, Maven, Git, GitHub, IntelliJ Ultimate.

## Main commands

```bash
# go to directory
$ cd backend

# image creation
$ docker build -f Dockerfile -t fx-erp-system-backend .

# starting the image
$ docker run -d -p 8080:8080 fx-erp-system-backend

# image composing
$ docker-compose up -d
```
