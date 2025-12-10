# ⚠️ IMPORTANTE - LEER ANTES DE EJECUTAR

## ✅ Cambios Realizados

Se ha configurado la aplicación para usar **MySQL** en lugar de H2.

---

## 🔧 CONFIGURACIÓN REQUERIDA

### PASO 1: Crear la Base de Datos

Abre **MySQL Workbench** o la línea de comandos de MySQL y ejecuta:

```sql
CREATE DATABASE IF NOT EXISTS tiendatech2;
```

---

### PASO 2: Configurar Password de MySQL

Abre el archivo:
```
src/main/resources/application.properties
```

En la **línea 13**, cambia el password por el de tu MySQL:

```properties
spring.datasource.password=TU_PASSWORD_AQUI
```

**IMPORTANTE**: Si el password actual (`biblioteca`) es diferente al tuyo, cámbialo.

---

### PASO 3: Ejecutar la Aplicación

```bash
java -jar target/tiendatech2-0.0.1-SNAPSHOT.jar
```

Si necesitas recompilar:
```bash
"C:\Program Files\NetBeans-24\netbeans\java\maven\bin\mvn.cmd" clean package -DskipTests
java -jar target/tiendatech2-0.0.1-SNAPSHOT.jar
```

---

### PASO 4: Abrir en Navegador

```
http://localhost:8080
```

---

## 📋 Archivos Modificados

1. ✅ `pom.xml` - Habilitado MySQL, deshabilitado H2
2. ✅ `application.properties` - Configurado para MySQL
3. ✅ `DataInitializer.java` - Carga datos automáticamente

---

## 🎯 ¿Qué se Carga Automáticamente?

Al iniciar la aplicación, Spring Boot hará:

1. **Crear todas las tablas** en MySQL automáticamente
2. **Cargar datos iniciales**:
   - ✅ 2 Roles (ADMIN, USER)
   - ✅ 3 Usuarios (admin, juan, maria)
   - ✅ 8 Categorías
   - ✅ 14 Productos con imágenes
   - ✅ 5 Métodos de pago

**NO necesitas ejecutar scripts SQL manualmente.**

---

## 👤 Credenciales de Acceso

### Administrador
- **Email**: `admin@tiendatech.com`
- **Password**: `admin123`

### Usuarios Regulares
- **Email**: `juan@example.com` | **Password**: `user123`
- **Email**: `maria@example.com` | **Password**: `user123`

---

## ✅ Verificar que Funcionó

En la consola deberías ver:

```
Started Tiendatech2Application in X.XXX seconds
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

### ❌ Error: "Access denied for user 'root'"
**Solución**: Cambia el password en `application.properties` línea 13

### ❌ Error: "Unknown database 'tiendatech2'"
**Solución**: Ejecuta en MySQL:
```sql
CREATE DATABASE tiendatech2;
```

### ❌ Error: "Communications link failure"
**Solución**: Asegúrate que MySQL Server esté corriendo

### ❌ Error: "Port 8080 already in use"
**Solución**: Cierra otras aplicaciones en puerto 8080

---

## 📚 Archivos de Ayuda

- `EJECUTAR.md` - Instrucciones rápidas de ejecución
- `CONFIGURAR_MYSQL.md` - Guía detallada de MySQL
- `database/schema.sql` - Información sobre el esquema

---

## 🎬 ¡Listo para Grabar el Video!

Una vez que la aplicación esté corriendo en http://localhost:8080, ya puedes comenzar a grabar tu demostración.

Las funcionalidades disponibles son:
- ✅ Catálogo de productos
- ✅ Carrito de compras
- ✅ Sistema de pedidos
- ✅ Favoritos
- ✅ Comentarios
- ✅ Panel de administración
- ✅ Login/Registro
- ✅ Gestión de ofertas
- ✅ Y mucho más...

---

**¿Preguntas?** Lee los archivos `EJECUTAR.md` o `CONFIGURAR_MYSQL.md` para más detalles.
