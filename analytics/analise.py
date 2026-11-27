"""Consome a API Helpdesk autenticada e gera indicadores de chamados."""
import os
from pathlib import Path

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
import pandas as pd
import requests

URL = os.getenv("HELPDESK_URL", "http://localhost:8080").rstrip("/")
DIR = Path(__file__).resolve().parent / "output"
TIMEOUT = 30


def autenticar(session, email, senha):
    response = session.post(f"{URL}/auth/login", json={"email": email, "senha": senha}, timeout=TIMEOUT)
    response.raise_for_status()
    token = response.json().get("token")
    if not isinstance(token, str) or not token:
        raise ValueError("A API não retornou um token válido.")
    session.headers.update({"Authorization": f"Bearer {token}"})


def buscar_chamados(session):
    registros = []
    pagina = 0
    while True:
        response = session.get(f"{URL}/chamados", params={"page": pagina, "size": 100, "sort": "id,asc"}, timeout=TIMEOUT)
        response.raise_for_status()
        dados = response.json()
        if not isinstance(dados, dict) or not isinstance(dados.get("content"), list) or not isinstance(dados.get("last"), bool):
            raise ValueError("Resposta inesperada: esperava uma página de chamados.")
        registros.extend(dados["content"])
        if dados["last"]:
            break
        if not dados["content"]:
            raise ValueError("Página vazia antes do fim da paginação.")
        pagina += 1
    return pd.DataFrame(registros)


def salvar_grafico(contagem, nome, titulo, tipo="bar", pasta=DIR):
    fig, ax = plt.subplots(figsize=(9, 5))
    try:
        if tipo == "pie":
            contagem.plot(kind="pie", autopct="%1.1f%%", ax=ax)
            ax.set_ylabel("")
        else:
            contagem.plot(kind="bar", ax=ax)
            ax.set_ylabel("Quantidade")
            ax.tick_params(axis="x", labelrotation=15)
        ax.set_title(titulo)
        fig.tight_layout()
        pasta.mkdir(parents=True, exist_ok=True)
        fig.savefig(pasta / nome, dpi=150)
    finally:
        plt.close(fig)


def gerar_graficos(df, pasta=DIR):
    if df.empty:
        return
    obrigatorias = {"status", "prioridade", "tecnico"}
    if not obrigatorias.issubset(df.columns):
        raise ValueError("Campos ausentes na resposta de chamados.")
    tecnicos = df["tecnico"].map(lambda t: t.get("nome", "Sem nome") if isinstance(t, dict) else "Sem técnico")
    salvar_grafico(df["status"].fillna("Não informado").value_counts(), "chamados_por_status.png", "Chamados por status", pasta=pasta)
    salvar_grafico(df["prioridade"].fillna("Não informada").value_counts(), "chamados_por_prioridade.png", "Chamados por prioridade", "pie", pasta=pasta)
    salvar_grafico(tecnicos.value_counts(), "chamados_por_tecnico.png", "Chamados por técnico", pasta=pasta)


def main():
    email = os.getenv("HELPDESK_EMAIL")
    senha = os.getenv("HELPDESK_PASSWORD")
    if not email or not senha:
        print("Defina HELPDESK_EMAIL e HELPDESK_PASSWORD antes de executar.")
        return 1
    try:
        with requests.Session() as session:
            autenticar(session, email, senha)
            df = buscar_chamados(session)
        if df.empty:
            print("Nenhum chamado cadastrado. Nenhum gráfico novo foi gerado.")
            return 0
        gerar_graficos(df)
        print(f"{len(df)} chamados processados. Gráficos em {DIR}.")
        return 0
    except (requests.RequestException, ValueError, OSError) as exc:
        # Evita imprimir credenciais ou o corpo de respostas da API.
        print(f"Falha na análise: {type(exc).__name__}. Verifique a API, as credenciais e os dados.")
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
