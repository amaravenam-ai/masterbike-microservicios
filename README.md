# MasterBike — ocho microservicios

Sistema académico de portafolio de Ariel Aravena para clientes, servicio técnico, usuarios, bicicletas, arriendos, inventario, ventas y despachos. Java 21, Spring Boot, Spring Data JPA, MySQL, Flyway y Docker Compose. Preparación con asistencia de Codex.

## Estado comprobado

Se verificaron las ocho imágenes Docker con Java 21 y el arranque de los ocho servicios con MySQL. Se comprobaron 17 migraciones Flyway, las ocho API con creación y lectura de datos ficticios, respuestas 400/404 y la consulta OpenFeign de servicio técnico a clientes.

Los ocho módulos se empaquetaron y las **40 pruebas Maven pasaron con JDK 21**, sin omisiones dentro de la selección ServiceTest/ControllerTest. Los resultados se registran en `evidencia/resumen.json`. El recorrido y las pruebas unitarias cubren un alcance seleccionado; no se afirma cobertura exhaustiva ni validación de producción.

## Compilar y probar

Instalar o disponer de JDK 21 y Maven 3.9.12. Desde esta raíz:

```sh
mvn -B -ntp '-Dtest=*ServiceTest,*ControllerTest' -Dsurefire.failIfNoSpecifiedTests=false verify
```

En PowerShell, mantener entre comillas los argumentos -D con puntos. La automatización adjunta fija Java 21, pero no se ha ejecutado aún en GitHub.

## Entorno aislado de demostración

Copiar `.env.example` a `.env` y completar MYSQL_ROOT_PASSWORD y MYSQL_PASSWORD con valores locales nuevos. Las ocho bases usan el usuario portfolio. No se publican puertos MySQL.

```sh
docker compose up --build -d
python scripts/comprobar_recorrido.py --snapshot .local/recorrido.json --espera 300
python scripts/verificar_migraciones.py
docker compose restart
python scripts/comprobar_recorrido.py --snapshot .local/recorrido.json --verificar-persistencia --espera 300
docker compose stop
```

| Servicio | Carpeta | Puerto local |
|---|---|---|
| Clientes | masterbike-vm | 18081 |
| Servicio técnico | masterbike-vm-atencion | 18082 |
| Usuarios | masterbikes-usuarios | 18083 |
| Bicicletas | masterbikes-bicicletas | 18084 |
| Arriendos | masterbikes-arriendos | 18085 |
| Inventario | masterbikes-inventario | 18086 |
| Ventas | masterbikes-ventas | 18087 |
| Despachos | masterbikes-despachos | 18088 |

## Seguridad y limitaciones

Los servicios son una demostración sin autenticación completa; un campo rol no implementa control de acceso. No exponerlos a Internet. Las relaciones entre bases son referencias de aplicación, no claves foráneas distribuidas.
Los clientes y usuarios seed son ficticios, con correo example.invalid; los códigos DEMO no son RUT válidos. No se promete validación tributaria. No se muestran datos reales.
Se añadieron validaciones de campos obligatorios y pruebas HTTP en usuarios/despachos. Se corrigió una prueba obsoleta de bicicletas y el método eliminar que faltaba en arriendos.
El nombre del proyecto Compose es portfolio-masterbike; no reutilizarlo para bases de otros proyectos. Los volúmenes se conservan al detener. No usar down -v si necesitas conservar esos datos.

## Evidencia y alcance

El script espera a que estén disponibles las API, crea un registro ficticio por servicio y guarda sus respuestas completas para compararlas después del reinicio. No borra registros existentes. El snapshot es local y está excluido de Git.
La primera inicialización de las ocho bases puede tomar varios minutos. El control de salud consulta MySQL con el usuario de demostración por TCP; no confunde el servidor temporal de inicialización con una base lista. El entorno se verificó con aproximadamente 8 GB asignados a Docker, manteniendo servicios ajenos intactos.
Ver [arquitectura, contratos y límites](docs/ARQUITECTURA.md). La automatización tiene pruebas Maven y un trabajo separado de integración Docker; todavía no se ha ejecutado en GitHub para este candidato.
Ver `evidencia/resumen.json`, `TERCEROS.md` y `AVISO_DERECHOS.md`.
