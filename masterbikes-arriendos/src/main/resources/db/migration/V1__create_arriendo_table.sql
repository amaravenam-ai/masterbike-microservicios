CREATE TABLE IF NOT EXISTS arriendo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_cliente BIGINT NOT NULL,
    id_bicicleta BIGINT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    costo DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id)
);
