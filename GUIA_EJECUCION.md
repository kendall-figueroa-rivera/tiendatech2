# 🚀 Guía Rápida para Ejecutar TiendaTech2

## 📋 Requisitos Previos

Antes de ejecutar la aplicación, necesitas tener instalado:

1. **Java 23 o superior** ✅ (Ya tienes Java 24 instalado)
2. **Maven** (Gestor de dependencias)
3. **MySQL** (Base de datos)
4. **Base de datos `tiendatech2` creada**

---

## 🔧 Opción 1: Usar un IDE (MÁS FÁCIL)

### IntelliJ IDEA / Eclipse / NetBeans

1. **Abre el proyecto** en tu IDE
2. **Espera** a que Maven descargue las dependencias automáticamente
3. **Configura la base de datos:**
   - Asegúrate de que MySQL esté corriendo
   - Crea la base de datos `tiendatech2`:
     ```sql
     CREATE DATABASE tiendatech2;
     ```
   - O ejecuta el script: `database.sql`
4. **Verifica `application.properties`:**
   - Usuario: `root`
   - Contraseña: `biblioteca`
   - Si tu MySQL tiene otra contraseña, cámbiala en `src/main/resources/application.properties`
5. **Ejecuta la aplicación:**
   - Busca el archivo `Tiendatech2Application.java`
   - Haz clic derecho → "Run" o presiona `Shift+F10`

---

## 💻 Opción 2: Desde la Terminal (Requiere Maven)

### Paso 1: Instalar Maven

**Windows:**
1. Descarga Maven desde: https://maven.apache.org/download.cgi
2. Extrae el archivo ZIP
3. Agrega Maven al PATH del sistema:
   - Ve a Variables de Entorno
   - Agrega `C:\ruta\a\apache-maven-3.x.x\bin` al PATH
4. Verifica: `mvn --version`

### Paso 2: Configurar MySQL

1. **Inicia MySQL** (si no está corriendo)
2. **Crea la base de datos:**
   ```sql
   CREATE DATABASE tiendatech2;
   ```
   O ejecuta:
   ```bash
   mysql -u root -p < database.sql
   ```

### Paso 3: Verificar Configuración

Edita `src/main/resources/application.properties` si es necesario:
- Usuario MySQL: `root`
- Contraseña MySQL: `biblioteca` (cámbiala si es diferente)

### Paso 4: Compilar y Ejecutar

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

---

## 🌐 Acceder a la Aplicación

Una vez que la aplicación esté corriendo:

- **URL principal:** http://localhost:8080
- **Login:** http://localhost:8080/auth/login

---

## 👤 Usuarios de Prueba

La aplicación crea automáticamente estos usuarios al iniciar:

| Rol | Email | Contraseña |
|-----|-------|-----------|
| **Admin** | admin@tiendatech.com | admin123 |
| **Vendedor** | vendedor@tiendatech.com | vendedor123 |
| **Cliente** | cliente@gmail.com | cliente123 |

---

## ❗ Solución de Problemas

### Error: "Maven no encontrado"
- Instala Maven o usa un IDE que lo tenga integrado

### Error: "Access denied for user 'root'"
- Verifica usuario y contraseña en `application.properties`
- Asegúrate de que MySQL esté corriendo

### Error: "Unknown database tiendatech2"
- Crea la base de datos: `CREATE DATABASE tiendatech2;`
- O ejecuta: `mysql -u root -p < database.sql`

### Error: "Port 8080 already in use"
- Cierra la aplicación que está usando el puerto 8080
- O cambia el puerto en `application.properties`: `server.port=8081`

### La aplicación no inicia
1. Verifica que MySQL esté corriendo
2. Revisa los logs en la consola
3. Verifica que el puerto 8080 esté disponible
4. Asegúrate de que Java 23+ esté instalado

---

## 📝 Notas Importantes

- La aplicación crea las tablas automáticamente al iniciar (gracias a `spring.jpa.hibernate.ddl-auto=update`)
- Los usuarios de prueba se crean automáticamente al iniciar por primera vez
- El servidor corre en el puerto **8080** por defecto

