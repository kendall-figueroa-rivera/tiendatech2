-- =========================================
-- CREAR BASE DE DATOS TIENDATECH2
-- =========================================
CREATE DATABASE IF NOT EXISTS tiendatech2
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE tiendatech2;

-- =========================================
-- NOTA IMPORTANTE:
-- =========================================
-- Este script es OPCIONAL. Spring Boot con JPA creará
-- automáticamente todas las tablas basándose en las
-- entidades (@Entity) cuando inicies la aplicación.
--
-- Solo ejecuta este script si:
-- 1. Quieres crear la base de datos manualmente
-- 2. Tienes problemas con la creación automática
-- 3. Prefieres tener control total del esquema
--
-- Con spring.jpa.hibernate.ddl-auto=update
-- las tablas se crean/actualizan automáticamente.
-- =========================================

-- El resto de tablas (roles, usuarios, productos, etc.)
-- serán creadas automáticamente por Hibernate/JPA
-- basándose en las anotaciones @Entity del código Java.

-- Los datos iniciales se cargan automáticamente mediante
-- el archivo DataInitializer.java que incluye:
-- - Roles (ROLE_ADMIN, ROLE_USER)
-- - Usuarios de prueba (admin, juan, maria)
-- - Categorías (Laptops, Smartphones, etc.)
-- - 14 Productos de ejemplo
-- - Métodos de pago

-- =========================================
-- CREDENCIALES DE ACCESO
-- =========================================
-- Admin:
--   Email: admin@tiendatech.com
--   Password: admin123
--
-- Usuario Regular:
--   Email: juan@example.com
--   Password: user123
-- =========================================
