# Instrucciones para ejecutar TiendaTech2

## 1. Configurar la Base de Datos

### Opción A: Usando el archivo SQL
```bash
mysql -u root -p < database.sql
```

### Opción B: Manual
```sql
CREATE DATABASE tiendatech2;
```

La aplicación creará las tablas automáticamente al iniciar.

## 2. Verificar configuración

Archivo `application.properties` ya configurado con:
- **Base de datos:** tiendatech2
- **Usuario:** root
- **Contraseña:** biblioteca
- **Puerto:** 8080

Si tu configuración es diferente, edita `src/main/resources/application.properties`

## 3. Compilar y ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

## 4. Usuarios de prueba

La aplicación crea automáticamente estos usuarios:

| Rol | Email | Contraseña |
|-----|-------|-----------|
| Admin | admin@tiendatech.com | admin123 |
| Vendedor | vendedor@tiendatech.com | vendedor123 |
| Cliente | cliente@gmail.com | cliente123 |

## 5. Acceder a la aplicación

- **URL:** http://localhost:8080
- **Login:** http://localhost:8080/auth/login

## Solución de problemas

### Error: "Access denied for user"
Verifica usuario y contraseña en `application.properties`

### Error: "Unknown database tiendatech2"
Ejecuta: `CREATE DATABASE tiendatech2;` en MySQL

### La aplicación no levanta
1. Verifica que MySQL esté corriendo
2. Revisa los logs en la consola
3. Verifica el puerto 8080 esté disponible
