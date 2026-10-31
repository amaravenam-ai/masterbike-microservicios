CREATE TABLE IF NOT EXISTS bicicleta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(50) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    tarifa_hora DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_bicicleta_codigo (codigo)
);
