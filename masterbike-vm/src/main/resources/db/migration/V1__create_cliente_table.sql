CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT NOT NULL AUTO_INCREMENT,
    rut VARCHAR(13) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE NULL,
    correo VARCHAR(150) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cliente_rut (rut)
);
