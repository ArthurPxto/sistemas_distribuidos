# Responsavel por gerar dados em tempo real para serem consumidos 
# gerador.py
from confluent_kafka import Producer
import json
import time
import random

producer = Producer({'bootstrap.servers': 'localhost:9092'})

def gerar_sinais():
    return {
        "paciente_id": random.randint(1, 10),
        "bpm": random.randint(50, 180), #180
        "spo2": random.randint(85, 100), #85
        "pressao_sistolica": random.randint(90, 180), #180
        "pressao_diastolica": random.randint(60, 120), #120
        "timestamp": time.time()
    }

while True:
    dados = gerar_sinais()
    producer.produce("sinais-vitais", json.dumps(dados).encode("utf-8"))
    producer.flush()
    print("Enviado:", dados)
    time.sleep(1)
