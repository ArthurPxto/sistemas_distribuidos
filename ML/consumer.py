import csv
import json
from confluent_kafka import Consumer
import os

# Configurações do consumidor Kafka
conf = {
    'bootstrap.servers': 'localhost:9092',
    'group.id': 'grupo-salvador',
    'auto.offset.reset': 'latest'
}

consumer = Consumer(conf)
consumer.subscribe(['sinais-processados'])  # tópico onde o pré-processador publica os dados

# Caminho do arquivo CSV
csv_path = "/home/pxto/code/sistemas_distribuidos/ML/output/sinais_vitais.csv"

# Garante que o cabeçalho seja escrito apenas uma vez
write_header = not os.path.exists(csv_path)

with open(csv_path, mode='a', newline='') as csvfile:
    fieldnames = ['paciente_id', 'bpm', 'spo2', 'pressao_sistolica', 'pressao_diastolica', 'timestamp', 'risco']
    writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

    if write_header:
        writer.writeheader()

    print("Aguardando mensagens do Kafka...")
    try:
        while True:
            msg = consumer.poll(1.0)
            if msg is None:
                continue
            if msg.error():
                print("Erro: {}".format(msg.error()))
                continue

            try:
                dados = json.loads(msg.value().decode('utf-8'))
                writer.writerow(dados)
                csvfile.flush()
                print(f"Salvo: {dados}")
            except json.JSONDecodeError:
                print("Mensagem inválida:", msg.value())
    except KeyboardInterrupt:
        print("Encerrando consumidor...")
    finally:
        consumer.close()
