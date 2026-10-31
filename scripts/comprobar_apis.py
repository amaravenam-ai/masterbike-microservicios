"""Smoke local sobre las ocho API de la copia, sin conectarse a otros puertos."""
import json, urllib.request, urllib.error
SOURCES=['clientes','ordenes-servicio','usuarios','bicicletas','arriendos','inventario','ventas','despachos']
def request(url, body=None):
    data=None if body is None else json.dumps(body).encode()
    req=urllib.request.Request(url,data=data,headers={'Content-Type':'application/json'})
    try:
        with urllib.request.urlopen(req,timeout=10) as response:return response.status,response.read().decode()
    except urllib.error.HTTPError as error:return error.code,error.read().decode()
if __name__=='__main__':
    for i,source in enumerate(SOURCES):
        url=f'http://127.0.0.1:{18081+i}/api/v1/{source}'
        code,body=request(url);assert code==200,(source,code);assert isinstance(json.loads(body),list)
        code,_=request(url+'/999999999');assert code==404,(source,'registro inexistente',code)
        code,_=request(url,{});assert code==400,(source,'cuerpo inválido',code)
        print(source,'lista, inexistente y entrada inválida: OK')
