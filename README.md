# Sistema de Gestión Escolar

Este proyecto tiene como objetivo ofrecer una solución para la gestión de datos escolares, permitiendo realizar operaciones CRUD sobre entidades como estudiantes, profesores, grados y asignaciones de estudiantes a grados.
## Tecnologías utilizadas

- Backend: Spring Boot con JPA (Hibernate
- Base de datos: PostgreSQL
- Frontend: Vue 3 (en desarrollo)

##  Configuración inicial
### Requisitos
- Java 11+
- Maven
- PostgreSQL 13+
- Pasos para la configuración

## Configuración de la Base de Datos:

- Instala y configura PostgreSQL en tu máquina.
- Crea una base de datos llamada viaro.
- Configura el usuario, la contraseña y el puerto para tu base de datos. Asegúrate de que estos valores estén reflejados en tu archivo application.properties de Spring Boot.

#### Para crear la base de datos puedes utilizar el siguiente script SQL:

```sql
CREATE DATABASE viaro;
```
Nota: Si prefieres usar un archivo para crear la base de datos, puedes colocar este script en la carpeta resources de tu proyecto y luego ejecutarlo usando psql o cualquier cliente de PostgreSQL.

## Configuración del proyecto:

- Clona el repositorio en tu máquina local.

- Navega al directorio del proyecto y ejecuta el siguiente comando para compilar y empaquetar la aplicación:


```bash 
 mvn clean package
```


- Una vez empaquetado, puedes ejecutar la aplicación con:


```bash 
java -jar target/portfolio-0.0.1-SNAPSHOT.jar
```

Nota: Reemplaza nombre-del-artifacto.jar con el nombre actual del JAR generado en la carpeta target.

## Verificación:

- Una vez que la aplicación esté en ejecución, puedes probar el endpoint de estudiantes usando:

```bash 
http://localhost:8080/api/students
```

Esto debería devolver una lista vacía al inicio, ya que aún no hay datos en la base de datos.


