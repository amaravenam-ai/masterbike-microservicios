"""Crea ejemplos ficticios en las API locales o verifica su persistencia."""
import argparse
import json
from pathlib import Path
import uuid
import time
import urllib.error
from http.client import HTTPException
from comprobar_apis import SOURCES, request


def wait_ready(url, timeout):
    deadline = time.monotonic() + timeout
    while True:
        try:
            code, _ = request(url)
            if code == 200:
                return
        except (urllib.error.URLError, TimeoutError, HTTPException, ConnectionError):
            pass
        if time.monotonic() >= deadline:
            raise RuntimeError(f'{url}: no disponible dentro del plazo')
        time.sleep(2)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--sources', nargs='+', choices=SOURCES, default=SOURCES)
    parser.add_argument('--snapshot', type=Path, required=True)
    parser.add_argument('--verificar-persistencia', action='store_true')
    parser.add_argument('--espera', type=int, default=180, help='Segundos máximos de espera por servicio')
    args = parser.parse_args()
    records = json.loads(args.snapshot.read_text(encoding='utf-8')) if args.snapshot.exists() else {}
    tag = uuid.uuid4().hex[:8]
    examples = {
        'clientes': dict(rut='D-'+tag, nombre='Cliente Demo', apellidos='Ficticio', fechaNacimiento='2000-01-01', correo=tag+'@example.invalid'),
        'ordenes-servicio': dict(fechaIngreso='2026-10-31', horaIngreso='10:00:00', costo=15000, idCliente=1, problema='Prueba sintética de frenos'),
        'usuarios': dict(nombre='Usuario Demo', correo=tag+'@example.invalid', rol='DEMO'),
        'bicicletas': dict(codigo='DEMO-'+tag, tipo='Urbana', estado='Disponible', tarifaHora=2000),
        'arriendos': dict(idCliente=1, idBicicleta=1, fechaInicio='2026-10-31', fechaFin='2026-11-01', costo=10000, estadoArriendo='DEMO'),
        'inventario': dict(nombreProducto='Repuesto Demo', categoria='DEMO', stock=10, stockMinimo=2),
        'ventas': dict(idCliente=1, fechaVenta='2026-10-31', total=25000, estado='DEMO'),
        'despachos': dict(idVenta=1, direccion='Dirección ficticia de demostración', estado='DEMO', fechaEstimada='2026-11-01'),
    }
    for source in args.sources:
        url = f'http://127.0.0.1:{18081 + SOURCES.index(source)}/api/v1/{source}'
        wait_ready(url, args.espera)
        if args.verificar_persistencia:
            saved = records[source]
            code, body = request(url+'/'+str(saved['id']))
            assert code == 200 and json.loads(body) == saved, (source, 'persistencia', code, body)
            print(source, 'persistencia: OK')
            continue
        code, body = request(url)
        assert code == 200 and isinstance(json.loads(body), list), (source, code, body)
        code, _ = request(url+'/999999999')
        assert code == 404, (source, 'registro inexistente', code)
        code, _ = request(url, {})
        assert code == 400, (source, 'cuerpo inválido', code)
        code, body = request(url, examples[source])
        assert code in {200, 201}, (source, code, body)
        created = json.loads(body)
        assert created['id'], created
        if source == 'ordenes-servicio':
            assert created['cliente']['id'] == 1, created
            code, _ = request(url, dict(examples[source], idCliente=999999999))
            assert code == 404, ('cliente inexistente por Feign', code)
        code, body = request(url+'/'+str(created['id']))
        assert code == 200 and json.loads(body) == created, (source, code, body)
        records[source] = created
        args.snapshot.parent.mkdir(parents=True, exist_ok=True)
        args.snapshot.write_text(json.dumps(records, indent=2, ensure_ascii=False)+'\n', encoding='utf-8')
        print(source, 'lista, 404, validación, creación y lectura: OK')


if __name__ == '__main__':
    main()
