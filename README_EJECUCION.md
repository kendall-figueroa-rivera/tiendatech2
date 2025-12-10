# TiendaTech2 - Guía de Ejecución

## Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalado:

1. **Java JDK 17 o superior**
   - Descarga: https://www.oracle.com/java/technologies/downloads/
   - Verifica la instalación: `java -version`

2. **Maven**
   - Descarga: https://maven.apache.org/download.cgi
   - Verifica la instalación: `mvn -version`

## Formas de Ejecutar la Aplicación

### Opción 1: Script de Ejecución (Más fácil)

1. Abre una terminal en la carpeta del proyecto
2. Ejecuta el script:
   ```bash
   ejecutar.bat
   ```

### Opción 2: Comandos Maven

1. Abre una terminal en la carpeta del proyecto
2. Ejecuta los siguientes comandos:
   ```bash
   mvn clean install -DskipTests
   mvn spring-boot:run
   ```

### Opción 3: Desde un IDE (IntelliJ IDEA, Eclipse, NetBeans)

1. Abre el proyecto en tu IDE
2. Busca la clase `Tiendatech2Application.java`
3. Haz clic derecho y selecciona "Run" o "Ejecutar"

## Acceso a la Aplicación

Una vez iniciada, la aplicación estará disponible en:

- **Aplicación Web**: http://localhost:8080
- **Consola H2** (Base de datos): http://localhost:8080/h2-console
  - URL: `jdbc:h2:mem:tiendatech2`
  - Usuario: `sa`
  - Contraseña: (dejar vacío)

## Usuarios de Prueba

El sistema viene con usuarios pre-cargados:

### Administrador
- Email: `admin@tiendatech.com`
- Contraseña: `admin123`

### Usuarios Regulares
- Email: `juan@example.com` / Contraseña: `user123`
- Email: `maria@example.com` / Contraseña: `user123`

## Crear una Nueva Cuenta

1. Ve a http://localhost:8080
2. Haz clic en "Iniciar Sesión"
3. Haz clic en "¿No tienes cuenta? Regístrate"
4. Completa el formulario con:
   - Nombre completo
   - Correo electrónico
   - Contraseña (mínimo 6 caracteres, debe contener letras y números)
   - Dirección (opcional)
   - Teléfono (opcional)
5. Haz clic en "Registrarme"
6. Inicia sesión con tu nuevo usuario

## Características Principales

- **Catálogo de Productos**: Navega por laptops, smartphones, tablets, accesorios y más
- **Carrito de Compras**: Agrega productos y realiza compras
- **Gestión de Pedidos**: Sigue el estado de tus pedidos
- **Sistema de Favoritos**: Guarda tus productos preferidos
- **Comentarios y Valoraciones**: Opina sobre los productos
- **Modo Oscuro/Claro**: Cambia el tema de la interfaz
- **Panel de Administración**: Gestiona productos, usuarios y pedidos (solo administradores)

## Solución de Problemas

### Error: "Maven no encontrado"
- Asegúrate de tener Maven instalado y agregado al PATH del sistema

### Error: "Java no encontrado"
- Verifica que Java JDK esté instalado y en el PATH

### Puerto 8080 ya en uso
- Cambia el puerto en `src/main/resources/application.properties`:
  ```properties
  server.port=8081
  ```

### La aplicación no compila
- Ejecuta: `mvn clean install -U`
- Esto limpiará y descargará las dependencias nuevamente

## Base de Datos

La aplicación usa **H2 Database** en memoria:
- Los datos se cargan automáticamente al iniciar
- Los datos se pierden al cerrar la aplicación
- Se incluyen 14 productos de ejemplo
- Se crean automáticamente categorías y métodos de pago

## Detener la Aplicación

Presiona `Ctrl+C` en la terminal donde se está ejecutando la aplicación.

## Soporte

Si encuentras problemas, verifica:
1. Los logs en la consola
2. Que todas las dependencias estén instaladas
3. Que no haya conflictos de puertos
4. Que la versión de Java sea compatible (Java 17+)
