"""Comprueba Flyway únicamente en las ocho bases de Docker Compose del proyecto."""
import subprocess

DATABASES = {
    'mysql-cliente': 2,
    'mysql-servicio-tecnico': 2,
    'mysql-usuario': 2,
    'mysql-bicicleta': 2,
    'mysql-arriendo': 3,
    'mysql-inventario': 2,
    'mysql-venta': 2,
    'mysql-despacho': 2,
}


def main():
    query = 'SELECT version, success FROM flyway_schema_history ORDER BY installed_rank'
    for service, expected in DATABASES.items():
        result = subprocess.run([
            'docker', 'compose', 'exec', '-T', service, 'sh', '-c',
            'MYSQL_PWD="$MYSQL_PASSWORD" mysql -u "$MYSQL_USER" "$MYSQL_DATABASE" -N -e "$1"',
            'sh', query,
        ], capture_output=True, text=True, check=True)
        rows = [row.split() for row in result.stdout.splitlines() if row.strip()]
        assert rows == [[str(i), '1'] for i in range(1, expected + 1)], (service, rows)
        print(service, f'{expected} migraciones aplicadas: OK')


if __name__ == '__main__':
    main()
