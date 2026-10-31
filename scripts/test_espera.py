"""Regresión del reinicio TCP observado en el runner Linux de GitHub."""
from http.client import RemoteDisconnected
import unittest
from unittest.mock import patch
from comprobar_recorrido import wait_ready


class ReadinessTest(unittest.TestCase):
    def test_transient_reset_and_closed_connection_recover(self):
        for failure in [ConnectionResetError(104, 'Connection reset by peer'),
                        RemoteDisconnected('Remote end closed connection')]:
            with self.subTest(error=type(failure).__name__):
                with patch('comprobar_recorrido.request', side_effect=[failure, (200, '[]')]) as request:
                    with patch('comprobar_recorrido.time.sleep'):
                        wait_ready('http://127.0.0.1:18081/api/v1/clientes', 10)
                self.assertEqual(request.call_count, 2)

    def test_unavailable_service_still_fails_at_deadline(self):
        with patch('comprobar_recorrido.request', side_effect=ConnectionResetError(104, 'reset')):
            with self.assertRaises(RuntimeError):
                wait_ready('http://127.0.0.1:18081/api/v1/clientes', 0)


if __name__ == '__main__':
    unittest.main()
