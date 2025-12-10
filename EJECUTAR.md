# 🚀 CÓMO EJECUTAR TIENDATECH2 CON MYSQL

## ✅ PASO 1: Crear Base de Datos en MySQL

Abre MySQL y ejecuta:

```sql
CREATE DATABASE IF NOT EXISTS tiendatech2;
```

---

## ✅ PASO 2: Verificar Password de MySQL

Abre el archivo:
```
src/main/resources/application.properties
```

Verifica que el password sea correcto (línea 13):
```properties
spring.datasource.password=biblioteca
```

Si tu password de MySQL es diferente, cámbialo aquí.

---

## ✅ PASO 3: Ejecutar la Aplicación

### Opción A - Desde Terminal:

```bash
java -jar target/tiendatech2-0.0.1-SNAPSHOT.jar
```

### Opción B - Recompilar y Ejecutar:

```bash
"C:\Program Files\NetBeans-24\netbeans\java\maven\bin\mvn.cmd" clean package -DskipTests
java -jar target/tiendatech2-0.0.1-SNAPSHOT.jar
```

---

## ✅ PASO 4: Abrir en Navegador

```
http://localhost:8080
```

---

## 👤 CREDENCIALES

### Administrador
- Email: `admin@tiendatech.com`
- Password: `admin123`

### Usuario Regular
- Email: `juan@example.com`
- Password: `user123`

---

## 🎯 URLS DE LA APLICACIÓN

- **Inicio**: http://localhost:8080/
- **Productos**: http://localhost:8080/productos
- **Login**: http://localhost:8080/auth/login
- **Registro**: http://localhost:8080/auth/registro
- **Carrito**: http://localhost:8080/carrito
- **Admin Panel**: http://localhost:8080/admin

---

## ✅ Qué se Carga Automáticamente

Al iniciar verás en consola:
```
✅ Roles creados
✅ Usuarios creados
   Admin: admin@tiendatech.com / admin123
   User1: juan@example.com / user123
   User2: maria@example.com / user123
✅ Categorías creadas
✅ Métodos de pago creados
✅ Productos creados (14 productos)
✅ Datos iniciales cargados exitosamente!
```

---

## ⚠️ Solución de Problemas

### Error: "Access denied for user 'root'"
→ Cambia el password en `application.properties`

### Error: "Unknown database 'tiendatech2'"
→ Ejecuta: `CREATE DATABASE tiendatech2;` en MySQL

### Error: "Port 8080 already in use"
→ Cierra otras aplicaciones en puerto 8080 o cámbialo en `application.properties`

---

## 🛑 Detener la Aplicación

Presiona `Ctrl + C` en la terminal
