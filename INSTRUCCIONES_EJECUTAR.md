# 🚀 Instrucciones para Ejecutar TiendaTech2 en Local

## 📋 Requisitos Previos

1. **Java 23 o superior** instalado
   - Verificar versión: `java --version`

2. **Maven** instalado (o usar el que viene con NetBeans)
   - NetBeans ya incluye Maven en: `C:\Program Files\NetBeans-24\netbeans\java\maven\bin\mvn.cmd`

## 🔧 Pasos para Ejecutar

### Opción 1: Usando Maven desde línea de comandos

1. **Abrir terminal** en la carpeta del proyecto:
   ```
   cd C:\Users\Fabia\.claude-worktrees\tiendatech2\funny-kepler
   ```

2. **Limpiar y compilar** el proyecto:
   ```
   "C:\Program Files\NetBeans-24\netbeans\java\maven\bin\mvn.cmd" clean package -DskipTests
   ```

3. **Ejecutar la aplicación**:
   ```
   java -jar target/tiendatech2-0.0.1-SNAPSHOT.jar
   ```

4. **Abrir navegador** en:
   ```
   http://localhost:8080
   ```

### Opción 2: Usando NetBeans

1. Abrir NetBeans
2. File → Open Project
3. Seleccionar la carpeta del proyecto
4. Click derecho en el proyecto → "Run"
5. Esperar a que compile y ejecute
6. Abrir navegador en: http://localhost:8080

### Opción 3: Ejecutar directamente el JAR (si ya está compilado)

1. **Abrir terminal** en la carpeta del proyecto
2. **Ejecutar**:
   ```
   java -jar target/tiendatech2-0.0.1-SNAPSHOT.jar
   ```
3. **Abrir navegador** en: http://localhost:8080

## 🔍 Verificar que está corriendo

Cuando la aplicación esté lista, verás en la consola:
```
Started Tiendatech2Application in X.XXX seconds
✅ Datos iniciales cargados exitosamente!
```

## 👤 Credenciales de Prueba

### Usuario Administrador
- **Email**: admin@tiendatech.com
- **Password**: admin123
- **Acceso**: Panel de administración completo

### Usuarios Regulares
- **Email**: juan@example.com | **Password**: user123
- **Email**: maria@example.com | **Password**: user123

## 🌐 URLs Importantes

- **Inicio**: http://localhost:8080/
- **Productos**: http://localhost:8080/productos
- **Login**: http://localhost:8080/auth/login
- **Carrito**: http://localhost:8080/carrito
- **Panel Admin**: http://localhost:8080/admin
- **Base de datos H2**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:tiendatech2`
  - Usuario: `sa`
  - Password: (dejar vacío)

## ⚠️ Solución de Problemas

### El puerto 8080 está en uso
Si ves el error "Port 8080 is already in use":
1. Cerrar cualquier otra aplicación en el puerto 8080
2. O cambiar el puerto en `src/main/resources/application.properties`:
   ```
   server.port=8081
   ```

### Error "mvn command not found"
Usar la ruta completa de Maven:
```
"C:\Program Files\NetBeans-24\netbeans\java\maven\bin\mvn.cmd" clean package
```

### La aplicación no carga datos
La base de datos es H2 en memoria, se crea automáticamente al iniciar.
Si no ves productos, verifica en la consola que aparezca:
```
✅ Datos iniciales cargados exitosamente!
```

## 🛑 Detener la Aplicación

- En terminal: Presionar `Ctrl + C`
- En NetBeans: Botón de Stop (cuadrado rojo)

## 📦 Contenido de la Base de Datos

Al iniciar, se cargan automáticamente:
- ✅ 2 Roles (ADMIN, USER)
- ✅ 3 Usuarios (1 admin, 2 usuarios regulares)
- ✅ 8 Categorías
- ✅ 14 Productos
- ✅ 5 Métodos de pago

## 🎬 Listo para Grabar

Una vez que la aplicación esté corriendo y veas la página en http://localhost:8080,
ya puedes comenzar a grabar tu video demostrativo.

¡Disfruta explorando TiendaTech2! 🚀
