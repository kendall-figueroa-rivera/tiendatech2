CREATE DATABASE IF NOT EXISTS tiendatech2;
USE tiendatech2;

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol_id BIGINT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    email_confirmado BOOLEAN DEFAULT FALSE,
    token_confirmacion VARCHAR(255),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);
