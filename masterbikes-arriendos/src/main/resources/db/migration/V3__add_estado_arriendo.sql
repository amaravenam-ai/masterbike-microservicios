-- Campo exigido por la entidad y ausente en el esquema inicial.
ALTER TABLE arriendo
    ADD COLUMN estadoarriendo VARCHAR(50) NOT NULL DEFAULT 'activo';
