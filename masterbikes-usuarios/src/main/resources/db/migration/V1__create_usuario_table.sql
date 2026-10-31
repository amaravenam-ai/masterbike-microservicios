CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuario_correo (correo)
);
