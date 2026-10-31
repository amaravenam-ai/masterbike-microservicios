CREATE TABLE IF NOT EXISTS inventario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre_producto VARCHAR(150) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    stock INT NOT NULL,
    stock_minimo INT NOT NULL,
    PRIMARY KEY (id)
);
