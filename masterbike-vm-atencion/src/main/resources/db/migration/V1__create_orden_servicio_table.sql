CREATE TABLE IF NOT EXISTS orden_servicio (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fecha_ingreso DATE NOT NULL,
    hora_ingreso TIME NOT NULL,
    costo DECIMAL(10,2) NOT NULL,
    id_cliente BIGINT NOT NULL,
    problema VARCHAR(500),
    PRIMARY KEY (id)
);
