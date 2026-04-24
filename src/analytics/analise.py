import os
import requests
import pandas as pd
import matplotlib.pyplot as plt

URL = "http://localhost:8080"
DIR = os.path.dirname(os.path.abspath(__file__))

def buscar_chamados():
    """
    Busca os chamados cadastrados na API
    :return: DataFrame contendo os chamados cadastrados
    """
    response = requests.get(f"{URL}/chamados")
    return pd.DataFrame(response.json())

def buscar_usuarios():
    """
    Busca os usuários cadastrados na API
    :return: DataFrame contendo os usuários cadastrados
    """
    response = requests.get(f"{URL}/usuarios")
    return pd.DataFrame(response.json())

def grafico_chamados_por_status(df):
    """
    Conta a quantidade de chamados por status e gera um gráfico em barra.
    :param df: DataFrame de chamados
    """
    contagem = df['status'].value_counts()
    contagem.plot(kind='bar', color=['green', 'orange', 'red'], title='Chamados por Status')

    plt.xlabel("Status")
    plt.ylabel('Quantidade')
    plt.xticks(rotation=0)
    plt.tight_layout()

    plt.savefig(os.path.join(DIR, 'chamados_por_status.png'))
    plt.show()
    print("Gráfíco 'Chamados por Status' criado com sucesso.")

def grafico_chamados_por_prioridade(df):
    """
    Conta a quantidade de chamados por prioridade e gera um gráfico de pizza
    :param df: DataFrame de chamados
    """
    contagem = df['prioridade'].value_counts()
    contagem.plot(kind='pie', autopct='%1.1f%%', title='Chamados por Prioridade')

    plt.ylabel('')
    plt.xticks(rotation=0)

    plt.savefig(os.path.join(DIR, 'chamados_por_prioridade.png'))
    plt.show()
    print("Gráfíco 'Chamados por Prioridade' criado com sucesso.")

def grafico_chamados_por_tecnico(df):
    """
    Conta a quantidade de chamados por técnico e gera um gráfico em barra
    :param df: DataFrame de chamados
    """
    df['tecnico_nome'] = df['tecnico'].apply(lambda t: t['nome'] if t else "Sem técnico")
    contagem = df['tecnico_nome'].value_counts()
    contagem.plot(kind='bar', title='Chamados por Técnico')

    plt.xlabel('Técnico')
    plt.ylabel('Quantidade')
    plt.xticks(rotation=0)
    plt.tight_layout()

    plt.savefig(os.path.join(DIR, 'chamados_por_tecnico.png'))
    plt.show()
    print("Gráfico 'Chamados por Técnico' criado com sucesso.")

def main():
    df_chamados = buscar_chamados()

    if df_chamados.empty:
        print("Nenhum chamado cadastrado.")
        return

    print(f"{len(df_chamados)} chamados encontrados.\n")

    grafico_chamados_por_status(df_chamados)
    grafico_chamados_por_prioridade(df_chamados)
    grafico_chamados_por_tecnico(df_chamados)
    print("Gráficos gerados!")

if __name__ == "__main__":
    main()