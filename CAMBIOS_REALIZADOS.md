# Cambios Realizados en TiendaTech2

## Resumen
Se ha revertido exitosamente la aplicación de MySQL a H2 y se han corregido varios bugs críticos para permitir el registro y funcionamiento correcto de la aplicación.

---

## 1. Migración de MySQL a H2

### 1.1 Archivo: `pom.xml`
**Cambios:**
- ✅ Eliminada dependencia de MySQL Connector
- ✅ Habilitada dependencia de H2 Database

**Antes:**
```xml
<!-- H2 Database (comentado - ahora usando MySQL) -->
<!--
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
-->
<!-- MySQL - Base de datos principal -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

**Después:**
```xml
<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 1.2 Archivo: `application.properties`
**Cambios:**
- ✅ Configurada base de datos H2 en memoria
- ✅ Habilitada consola H2
- ✅ Eliminada configuración específica de MySQL

**Antes:**
```properties
# MySQL - Base de datos principal
spring.datasource.url=jdbc:mysql://localhost:3306/tiendatech2?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Costa_Rica
spring.datasource.username=root
spring.datasource.password=biblioteca

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

**Después:**
```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:tiendatech2;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

## 2. Corrección de Bugs Críticos

### 2.1 Bug de Roles en AuthController.java (líneas 81-87)
**Problema:** El controlador buscaba el rol "USER" pero DataInitializer creaba roles con prefijo "ROLE_USER"

**Archivo:** `src/main/java/tiendatech2/controller/AuthController.java`

**Antes:**
```java
Rol rolUser = rolService.buscarPorNombre("USER");
if (rolUser == null) {
    rolUser = new Rol();
    rolUser.setNombre("USER");
    rolUser.setDescripcion("Rol básico de usuario");
    rolService.guardar(rolUser);
}
```

**Después:**
```java
Rol rolUser = rolService.buscarPorNombre("ROLE_USER");
if (rolUser == null) {
    rolUser = new Rol();
    rolUser.setNombre("ROLE_USER");
    rolUser.setDescripcion("Rol básico de usuario");
    rolService.guardar(rolUser);
}
```

### 2.2 Bug de Duplicación de Prefijo en UserDetailsServiceImpl.java (líneas 31-40)
**Problema:** Se agregaba "ROLE_" cuando el rol ya tenía el prefijo, causando "ROLE_ROLE_USER"

**Archivo:** `src/main/java/tiendatech2/config/UserDetailsServiceImpl.java`

**Antes:**
```java
List<GrantedAuthority> authorities = new ArrayList<>();
if (usuario.getRol() != null) {
    authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre()));
} else {
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
}
```

**Después:**
```java
List<GrantedAuthority> authorities = new ArrayList<>();
if (usuario.getRol() != null) {
    // El rol ya viene con el prefijo ROLE_ desde la base de datos
    String roleName = usuario.getRol().getNombre();
    if (!roleName.startsWith("ROLE_")) {
        roleName = "ROLE_" + roleName;
    }
    authorities.add(new SimpleGrantedAuthority(roleName));
} else {
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
}
```

---

## 3. Corrección de Parpadeo de Imágenes

### 3.1 Optimización CSS para Imágenes
**Archivo:** `src/main/resources/static/css/style.css` (líneas 246-259)

**Problema:** Las imágenes de productos parpadeaban al cargar

**Solución aplicada:**
```css
.producto-card img {
    width: 100%;
    height: 200px;
    object-fit: cover;
    border-radius: 5px;
    margin-bottom: 1rem;
    /* Prevenir parpadeo de imágenes */
    backface-visibility: hidden;
    -webkit-backface-visibility: hidden;
    transform: translateZ(0);
    -webkit-transform: translateZ(0);
    will-change: auto;
    image-rendering: -webkit-optimize-contrast;
}
```

**Beneficios:**
- ✅ Activa aceleración por hardware
- ✅ Previene re-renderizado innecesario
- ✅ Mejora la estabilidad visual
- ✅ Compatible con navegadores modernos

---

## 4. Nuevos Archivos Creados

### 4.1 Script de Ejecución: `ejecutar.bat`
Script automatizado para Windows que:
- ✅ Verifica instalación de Maven y Java
- ✅ Limpia el proyecto
- ✅ Compila la aplicación
- ✅ Inicia el servidor Spring Boot
- ✅ Muestra información de acceso

**Uso:**
```bash
ejecutar.bat
```

### 4.2 Documentación: `README_EJECUCION.md`
Guía completa que incluye:
- ✅ Requisitos previos (Java, Maven)
- ✅ Tres formas diferentes de ejecutar la aplicación
- ✅ URLs de acceso
- ✅ Usuarios de prueba
- ✅ Instrucciones para crear cuenta
- ✅ Características principales
- ✅ Solución de problemas comunes

---

## 5. Configuración de la Base de Datos H2

### Características:
- **Tipo:** Base de datos en memoria
- **Persistencia:** Los datos se cargan al iniciar y se pierden al cerrar
- **Consola:** Accesible en http://localhost:8080/h2-console
- **Credenciales:**
  - URL: `jdbc:h2:mem:tiendatech2`
  - Usuario: `sa`
  - Contraseña: (vacía)

### Datos Pre-cargados (DataInitializer.java):
- ✅ 2 Roles: ROLE_ADMIN, ROLE_USER
- ✅ 3 Usuarios: admin, juan, maria
- ✅ 8 Categorías: Laptops, Smartphones, Tablets, etc.
- ✅ 5 Métodos de Pago
- ✅ 14 Productos con imágenes de Unsplash

---

## 6. Usuarios de Prueba Disponibles

### Administrador:
```
Email: admin@tiendatech.com
Contraseña: admin123
```

### Usuarios Regulares:
```
Email: juan@example.com
Contraseña: user123

Email: maria@example.com
Contraseña: user123
```

---

## 7. Funcionalidades Verificadas

### ✅ Sistema de Autenticación
- Login funcional
- Registro de nuevos usuarios
- Validación de correo electrónico
- Validación de contraseñas (mínimo 6 caracteres, letras y números)
- Encriptación de contraseñas con BCrypt

### ✅ Sistema de Roles
- Asignación automática de ROLE_USER a nuevos usuarios
- Soporte para ROLE_ADMIN
- Autorización correcta en vistas

### ✅ Interfaz de Usuario
- Formularios de login y registro estilizados
- Mensajes de error y éxito
- Navegación fluida
- Imágenes optimizadas sin parpadeo

---

## 8. Cómo Probar la Aplicación

### Paso 1: Ejecutar la Aplicación
```bash
# Opción 1: Usar el script
ejecutar.bat

# Opción 2: Comandos Maven
mvn clean install -DskipTests
mvn spring-boot:run
```

### Paso 2: Acceder a la Aplicación
1. Abrir navegador en: http://localhost:8080
2. Ver consola H2 en: http://localhost:8080/h2-console

### Paso 3: Crear una Nueva Cuenta
1. Ir a "Iniciar Sesión"
2. Hacer clic en "Regístrate aquí"
3. Completar formulario:
   - Nombre: Tu Nombre
   - Correo: tunombre@ejemplo.com
   - Contraseña: password123 (mínimo 6 caracteres, letras y números)
   - Dirección: (opcional)
   - Teléfono: (opcional)
4. Hacer clic en "Registrarme"
5. Iniciar sesión con las credenciales creadas

### Paso 4: Probar Funcionalidades
- ✅ Navegar por productos
- ✅ Agregar productos al carrito
- ✅ Crear favoritos
- ✅ Realizar pedidos
- ✅ Ver historial de pedidos
- ✅ Cambiar entre modo claro/oscuro

---

## 9. Problemas Resueltos

| # | Problema | Solución | Archivo |
|---|----------|----------|---------|
| 1 | Base de datos MySQL requerida | Migrado a H2 en memoria | pom.xml, application.properties |
| 2 | Rol USER no encontrado al registrar | Corregido prefijo "ROLE_USER" | AuthController.java |
| 3 | Doble prefijo "ROLE_ROLE_USER" | Validación de prefijo existente | UserDetailsServiceImpl.java |
| 4 | Imágenes parpadeando | Optimizaciones CSS | style.css |
| 5 | Difícil de ejecutar localmente | Script automatizado | ejecutar.bat |

---

## 10. Próximos Pasos Sugeridos

### Para Desarrollo:
1. Configurar base de datos persistente (PostgreSQL/MySQL) para producción
2. Implementar sistema de emails real (actualmente simulado)
3. Agregar tests unitarios e integración
4. Configurar CI/CD

### Para Producción:
1. Cambiar `spring.jpa.hibernate.ddl-auto=update` a `validate`
2. Habilitar CSRF protection
3. Configurar HTTPS
4. Implementar rate limiting
5. Agregar logging y monitoreo

---

## Contacto y Soporte

Si tienes problemas ejecutando la aplicación:
1. Verifica los requisitos previos (Java 17+, Maven)
2. Revisa los logs en la consola
3. Consulta README_EJECUCION.md
4. Verifica que el puerto 8080 esté disponible

---

**Fecha de cambios:** 2025-12-09
**Versión:** 1.0.0
**Estado:** ✅ Completado y funcional
