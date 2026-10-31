CREATE TABLE IF NOT EXISTS despacho (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_venta BIGINT NOT NULL,
    direccion VARCHAR(250) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_estimada DATE NOT NULL,
    PRIMARY KEY (id)
);
