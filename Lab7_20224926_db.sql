CREATE DATABASE IF NOT EXISTS Lab7_20224926;
USE Lab7_20224926;

CREATE TABLE proveedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    razon_social VARCHAR(100) NOT NULL,
    nombre_comercial VARCHAR(100),
    ruc VARCHAR(11) NOT NULL,
    telefono VARCHAR(15),
    correo_electronico VARCHAR(100),
    sitio_web VARCHAR(100),
    direccion_fisica VARCHAR(150),
    pais VARCHAR(50) NOT NULL,
    representante_legal VARCHAR(100) NOT NULL,
    dni_representante_legal VARCHAR(8) NOT NULL,
    tipo_proveedor VARCHAR(20) NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    facturacion_anual_dolares DECIMAL(15,2),
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ultima_actualizacion DATETIME,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_ruc UNIQUE (ruc)
);



# Insertaremos algo de información para que se puedan realizar las primeras consultas con facilidad
INSERT INTO proveedor (razon_social, nombre_comercial, ruc, telefono, correo_electronico, sitio_web, direccion_fisica, pais, representante_legal, dni_representante_legal, tipo_proveedor, categoria, facturacion_anual_dolares, ultima_actualizacion, estado) VALUES
('Proveedor A', 'Prov A', '12345678901', '987654321', 'contacto@proveedora.com', 'http://proveedora.com', 'Av. Principal 123, Lima', 'Perú', 'Juan Pérez', '12345678', 'Nacional', 'Servicios', 50000.00, NULL, TRUE),
('Proveedor B', 'Prov B', '98765432109', '912345678', 'contacto@proveedorb.com', 'http://proveedorb.com', 'Calle Secundaria 456, Santiago', 'Chile', 'María Gómez', '87654321', 'Internacional', 'Tecnología', 75000.00, NULL, TRUE),
('Proveedor C', NULL, '11122233344', '923456789', 'contacto@proveedorc.com', NULL, 'Jr. Independencia 789, Bogotá', 'Colombia', 'Carlos López', '45678912', 'Internacional', 'Productos', 30000.00, NULL, TRUE),
('Proveedor D', 'Prov D', '44455566677', '934567890', NULL, 'http://proveedord.com', 'Av. Central 101, Buenos Aires', 'Argentina', 'Ana Rodríguez', '34567890', 'Nacional', 'Otros', 20000.00, NULL, FALSE);