import tempfile
import unittest
from pathlib import Path
from unittest.mock import Mock

import pandas as pd
import requests

from analise import autenticar, buscar_chamados, gerar_graficos


def resposta(payload):
    r = Mock()
    r.json.return_value = payload
    return r


class AnalyticsTests(unittest.TestCase):
    def test_autenticacao(self):
        session = Mock()
        session.post.return_value = resposta({"token": "token-teste"})
        autenticar(session, "teste@example.com", "senha-teste")
        session.headers.update.assert_called_once_with({"Authorization": "Bearer token-teste"})

    def test_token_ausente(self):
        session = Mock()
        session.post.return_value = resposta({})
        with self.assertRaises(ValueError):
            autenticar(session, "teste@example.com", "senha-teste")

    def test_paginacao(self):
        session = Mock()
        session.get.side_effect = [resposta({"content": [{"id": 1}], "last": False}), resposta({"content": [{"id": 2}], "last": True})]
        df = buscar_chamados(session)
        self.assertEqual(df["id"].tolist(), [1, 2])
        self.assertEqual([call.kwargs["params"]["page"] for call in session.get.call_args_list], [0, 1])

    def test_pagina_vazia(self):
        session = Mock()
        session.get.return_value = resposta({"content": [], "last": True})
        self.assertTrue(buscar_chamados(session).empty)

    def test_resposta_invalida(self):
        session = Mock()
        session.get.return_value = resposta({"content": []})
        with self.assertRaises(ValueError):
            buscar_chamados(session)

    def test_erro_http(self):
        session = Mock()
        session.get.return_value.raise_for_status.side_effect = requests.HTTPError("401")
        with self.assertRaises(requests.HTTPError):
            buscar_chamados(session)

    def test_graficos(self):
        df = pd.DataFrame([{"status": "ABERTO", "prioridade": "ALTA", "tecnico": {"nome": "Técnico fictício"}}, {"status": "FECHADO", "prioridade": "MEDIA", "tecnico": None}])
        with tempfile.TemporaryDirectory() as d:
            gerar_graficos(df, Path(d))
            self.assertEqual(len(list(Path(d).glob("*.png"))), 3)


if __name__ == "__main__":
    unittest.main()
