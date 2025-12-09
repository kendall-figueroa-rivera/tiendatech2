# 🛒 TiendaTech2

Aplicación web de comercio electrónico desarrollada con Spring Boot para la venta de productos tecnológicos. Proyecto del curso de Desarrollo Web y Patrones.

## 📋 Descripción

TiendaTech2 es una plataforma completa de e-commerce que permite a los usuarios:
- Explorar y buscar productos tecnológicos
- Gestionar carrito de compras y favoritos
- Realizar pedidos y seguimiento de compras
- Comentar y calificar productos
- Comunicarse con soporte mediante chat en tiempo real
- Administrar productos, usuarios y pedidos (panel de administración)

## ✨ Características Principales

### 👤 Gestión de Usuarios
- Registro y autenticación segura
- Confirmación de email
- Perfiles de usuario personalizables
- Sistema de roles (Admin, Vendedor, Cliente)

### 🛍️ Catálogo de Productos
- Búsqueda y filtrado de productos
- Categorización de productos
- Gestión de stock y alertas de inventario bajo
- Ofertas y promociones especiales
- Comentarios y valoraciones de productos

### 🛒 Carrito y Compras
- Carrito de compras persistente
- Lista de favoritos
- Proceso de checkout completo
- Historial de pedidos
- Seguimiento de estado de pedidos

### 💬 Comunicación
- Chat en tiempo real con soporte
- Sistema de mensajería

### 👨‍💼 Panel de Administración
- Gestión de usuarios y roles
- Administración de productos y categorías
- Gestión de pedidos
- Control de ofertas y promociones
- Dashboard con estadísticas

## 🛠️ Tecnologías Utilizadas

- **Backend:**
  - Java 23
  - Spring Boot 3.5.7
  - Spring Security (Autenticación y autorización)
  - Spring Data JPA (Persistencia)
  - Spring WebSocket (Chat en tiempo real)
  - Spring Mail (Notificaciones por email)

- **Base de Datos:**
  - MySQL 8.0+
  - Hibernate (ORM)

- **Frontend:**
  - Thymeleaf (Motor de plantillas)
  - HTML5, CSS3, JavaScript
  - Diseño responsive

- **Herramientas:**
  - Maven (Gestión de dependencias)
  - Lombok (Reducción de código boilerplate)
  - Spring Boot DevTools (Desarrollo)

## 📦 Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalado:

- **Java 23** o superior
- **Maven 3.6+** (o usar un IDE con Maven integrado)
- **MySQL 8.0+** (servidor de base de datos)
- **Git** (opcional, para clonar el repositorio)

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone <url-del-repositorio>
cd tiendatech2
```

### 2. Configurar la Base de Datos

#### Opción A: Usando el archivo SQL
```bash
mysql -u root -p < database.sql
```

#### Opción B: Crear manualmente
```sql
CREATE DATABASE tiendatech2;
```

> **Nota:** La aplicación creará automáticamente las tablas necesarias al iniciar gracias a la configuración `spring.jpa.hibernate.ddl-auto=update`.

### 3. Configurar Credenciales de MySQL

Edita el archivo `src/main/resources/application.properties` y ajusta las credenciales según tu configuración:

```properties
spring.datasource.username=root
spring.datasource.password=tu_contraseña
```

**Configuración por defecto:**
- Usuario: `root`
- Contraseña: `biblioteca`
- Base de datos: `tiendatech2`
- Puerto: `3306`

### 4. (Opcional) Configurar Email

Si deseas habilitar el envío de emails de confirmación, agrega en `application.properties`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-contraseña-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

> **Nota:** Si no configuras el email, la aplicación funcionará normalmente pero solo mostrará los tokens en la consola.

## ▶️ Ejecución

### Opción 1: Usando Maven (Terminal)

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

### Opción 2: Usando un IDE

1. Abre el proyecto en **IntelliJ IDEA**, **Eclipse** o **NetBeans**
2. Espera a que Maven descargue las dependencias automáticamente
3. Ejecuta la clase `Tiendatech2Application.java`
4. La aplicación estará disponible en `http://localhost:8080`

### Opción 3: Ejecutar el JAR

```bash
mvn clean package
java -jar target/tiendatech2-0.0.1-SNAPSHOT.jar
```

## 🌐 Acceso a la Aplicación

Una vez que la aplicación esté corriendo:

- **URL Principal:** http://localhost:8080
- **Login:** http://localhost:8080/auth/login
- **Registro:** http://localhost:8080/auth/registro
- **Panel Admin:** http://localhost:8080/admin (requiere rol ADMIN)

## 👥 Usuarios de Prueba

La aplicación crea automáticamente estos usuarios al iniciar por primera vez:

| Rol | Email | Contraseña | Descripción |
|-----|-------|-----------|-------------|
| **Admin** | admin@tiendatech.com | admin123 | Acceso completo al sistema |
| **Vendedor** | vendedor@tiendatech.com | vendedor123 | Gestión de productos |
| **Cliente** | cliente@gmail.com | cliente123 | Usuario estándar |

## 📁 Estructura del Proyecto

```
tiendatech2/
├── src/
│   ├── main/
│   │   ├── java/tiendatech2/
│   │   │   ├── config/          # Configuraciones (Security, DataInitializer)
│   │   │   ├── controller/       # Controladores REST y MVC
│   │   │   │   ├── api/         # Endpoints API REST
│   │   │   │   └── ...          # Controladores de vistas
│   │   │   ├── model/           # Entidades JPA
│   │   │   ├── repository/      # Repositorios Spring Data
│   │   │   ├── service/         # Lógica de negocio
│   │   │   │   └── impl/        # Implementaciones de servicios
│   │   │   └── Tiendatech2Application.java
│   │   └── resources/
│   │       ├── static/          # Archivos estáticos (CSS, JS, imágenes)
│   │       ├── templates/       # Plantillas Thymeleaf
│   │       └── application.properties
│   └── test/                     # Pruebas unitarias
├── database.sql                  # Script de creación de BD
├── pom.xml                       # Configuración Maven
└── README.md                     # Este archivo
```

## 🔐 Seguridad

- Autenticación basada en Spring Security
- Contraseñas encriptadas con BCrypt
- Protección CSRF (deshabilitada para desarrollo, habilitar en producción)
- Autorización basada en roles
- Confirmación de email para nuevos usuarios

## 🐛 Solución de Problemas

### Error: "Access denied for user"
- Verifica que las credenciales en `application.properties` sean correctas
- Asegúrate de que MySQL esté corriendo
- Verifica que el usuario tenga permisos en la base de datos

### Error: "Unknown database tiendatech2"
```sql
CREATE DATABASE tiendatech2;
```

### Error: "Port 8080 already in use"
- Cierra la aplicación que está usando el puerto 8080
- O cambia el puerto en `application.properties`:
  ```properties
  server.port=8081
  ```

### Error: "Maven no encontrado"
- Instala Maven y agrégalo al PATH del sistema
- O usa un IDE con Maven integrado (IntelliJ IDEA, Eclipse)

### La aplicación no inicia
1. Verifica que MySQL esté corriendo
2. Revisa los logs en la consola para errores específicos
3. Verifica que Java 23+ esté instalado: `java -version`
4. Asegúrate de que el puerto 8080 esté disponible

### Los emails no se envían
- Esto es normal si no configuraste SMTP
- Los tokens de confirmación se mostrarán en la consola
- Para habilitar emails, configura las propiedades de `spring.mail` en `application.properties`

## 📝 Notas de Desarrollo

- La aplicación usa `spring.jpa.hibernate.ddl-auto=update` para crear/actualizar tablas automáticamente
- Los usuarios de prueba se crean automáticamente solo si la base de datos está vacía
- El servicio de email está configurado para no fallar si no hay configuración SMTP
- El modo desarrollo está habilitado (`spring.thymeleaf.cache=false`)

## 🤝 Contribuir

Este es un proyecto académico. Para contribuir:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es parte de un curso académico de Desarrollo Web y Patrones.

## 👨‍💻 Autor

Desarrollado como parte del curso de Desarrollo Web y Patrones - Avance 2

---

**¿Necesitas ayuda?** Revisa el archivo `GUIA_EJECUCION.md` para instrucciones detalladas paso a paso.
