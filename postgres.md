markdown
Copy code

#  Comandos bach postgres



## 1. Iniciar el servidor de PostgreSQL:
```bash
 pg_ctl start -D /Volumes/ExtremePro/dbs
```


## 2. Ingresar a postgres:
```bash
 psql -U postgres
```

## 3. Crear base de datos: 
```sql
CREATE DATABASE [nombre_de_la_base_de_datos];
```

# Comandos SQL Básicos

A continuación se proporciona una lista de comandos SQL básicos y comunes:

## Navegación y Visualización

### 1. Ver todas las bases de datos:
En `psql`:
```sql 
\l
```

### 2. Seleccionar una base de datos:
En `psql`:
```sql 
\c [nombre_de_la_base_de_datos]
```

### 3. Ver todas las tablas:
En `psql`:
```sql 
\dt
```
### 4. Ver estructura de una tabla:
En `psql`:
```sql 
\d [nombre_de_la_tabla]
```


## Operaciones CRUD

### 5. Seleccionar/Leer datos:
```sql 
SELECT * FROM [nombre_de_la_tabla];
```


### 6. Insertar un nuevo registro:

```sql 
INSERT INTO [nombre_de_la_tabla] ([columna1], [columna2], ...)
VALUES ([valor1], [valor2], ...);
```


### 7. Actualizar registros:
```sql 
UPDATE [nombre_de_la_tabla]
SET [columna1] = [valor_nuevo1], [columna2] = [valor_nuevo2]
WHERE [condición];
```

### 8. Eliminar registros:
```sql 
DELETE FROM [nombre_de_la_tabla]
WHERE [condición];
```

## Administración de Tablas

### 9. Crear una nueva tabla:
```sql 
CREATE TABLE [nombre_de_la_tabla] (
[nombre_columna1] [tipo_dato1],
[nombre_columna2] [tipo_dato2],
...
);
```


### 10. Eliminar una tabla:
```sql 
DROP TABLE [nombre_de_la_tabla];
```


### 11. Añadir columna:
```sql 
ALTER TABLE [nombre_de_la_tabla]
ADD [nombre_nueva_columna] [tipo_dato];
```


### 12. Eliminar columna:
```sql 
ALTER TABLE [nombre_de_la_tabla]
DROP COLUMN [nombre_columna];
```



> Nota: Estos comandos son básicos y estándares de SQL, pero pueden variar ligeramente según el DBMS. Consulta siempre la documentación oficial del sistema que estés utilizando.
Puedes copiar este texto y guardarlo en un archivo con extensión .md para visualizarlo correctamente en cualquier lector de Markdown o plataforma que soporte este formato, como GitHub, GitLab, entre otros.