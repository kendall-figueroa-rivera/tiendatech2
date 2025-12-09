# 🗄️ Configuración de MySQL para TiendaTech2

## 📋 Pasos para Configurar MySQL

### 1️⃣ Crear la Base de Datos

Abre MySQL Workbench o línea de comandos de MySQL y ejecuta:

```sql
CREATE DATABASE IF NOT EXISTS tiendatech2
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

**¡Eso es todo!** No necesitas crear tablas manualmente.

---

### 2️⃣ Verificar Credenciales en `application.properties`

El archivo ya está configurado para MySQL con estas credenciales:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tiendatech2
spring.datasource.username=root
spring.datasource.password=biblioteca
```

**Si tu password de MySQL es diferente**, cámbialo en:
```
src/main/resources/application.properties
```

---

### 3️⃣ Recompilar y Ejecutar

```bash
# Recompilar el proyecto
"C:\Program Files\NetBeans-24\netbeans\java\maven\bin\mvn.cmd" clean package -DskipTests

# Ejecutar la aplicación
java -jar target/tiendatech2-0.0.1-SNAPSHOT.jar
```

---

## ✅ ¿Qué Sucede Automáticamente?

Cuando inicies la aplicación, **Spring Boot hará todo por ti**:

1. ✅ **Crea todas las tablas** automáticamente basándose en las entidades Java
2. ✅ **Carga datos iniciales** mediante `DataInitializer.java`:
   - 2 Roles (ADMIN, USER)
   - 3 Usuarios de prueba
   - 8 Categorías
   - 14 Productos con imágenes
   - 5 Métodos de pago

---

## 📊 Estructura de Tablas (creadas automáticamente)

- ✅ `roles` - Roles del sistema
- ✅ `usuarios` - Usuarios registrados
- ✅ `categorias` - Categorías de productos
- ✅ `productos` - Catálogo de productos
- ✅ `carrito` - Carrito de compras
- ✅ `pedidos` - Pedidos realizados
- ✅ `items_pedido` - Detalle de cada pedido
- ✅ `favoritos` - Productos favoritos
- ✅ `comentarios` - Comentarios en productos
- ✅ `ofertas` - Ofertas y descuentos
- ✅ `metodos_pago` - Métodos de pago disponibles
- ✅ `chats` - Sistema de chat
- ✅ `mensajes` - Mensajes del chat
- ✅ `devoluciones` - Solicitudes de devolución

---

## 👤 Credenciales de Prueba

### Administrador
- **Email**: admin@tiendatech.com
- **Password**: admin123

### Usuarios Regulares
- **Email**: juan@example.com | **Password**: user123
- **Email**: maria@example.com | **Password**: user123

---

## 🔍 Verificar las Tablas

Después de ejecutar la aplicación por primera vez, puedes ver las tablas en MySQL:

```sql
USE tiendatech2;
SHOW TABLES;
SELECT * FROM productos;
SELECT * FROM usuarios;
```

---

## ⚠️ Notas Importantes

### Modo de Actualización: `update`
```properties
spring.jpa.hibernate.ddl-auto=update
```

Este modo:
- ✅ Crea tablas si no existen
- ✅ Actualiza tablas si hay cambios en las entidades
- ✅ **NO borra datos** entre reinicios
- ✅ Los datos persisten

### Si quieres reiniciar la base de datos:
```sql
DROP DATABASE tiendatech2;
CREATE DATABASE tiendatech2;
```

Luego reinicia la aplicación y los datos iniciales se volverán a cargar.

---

## 🆚 Diferencia con H2

| Característica | H2 (Memoria) | MySQL |
|---------------|--------------|-------|
| Instalación | No requiere | Requiere MySQL instalado |
| Persistencia | Datos se borran al reiniciar | Datos persisten |
| Velocidad | Muy rápido | Rápido |
| Uso | Desarrollo/Pruebas | Producción |
| Configuración | Automática | Requiere crear BD |

---

## 🚀 ¡Listo!

Una vez configurado MySQL, tu aplicación guardará todos los datos de forma permanente.

Perfecto para:
- ✅ Producción
- ✅ Demostraciones largas
- ✅ Desarrollo continuo
- ✅ Pruebas con datos reales
