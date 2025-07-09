from confluent_kafka import Consumer
import json
import pandas as pd
from sklearn.linear_model import LogisticRegression
import joblib

# Carrega o modelo previamente treinado
modelo = joblib.load('modelo_risco.pkl')  # Treinado com as features bpm, spo2, sistólica, diastólica

# Configura o consumidor Kafka
consumer = Consumer({
    'bootstrap.servers': 'localhost:9092',
    'group.id': 'ia-consumer',
    'auto.offset.reset': 'latest'
})
consumer.subscribe(['sinais-processados'])  # Este é o tópico onde o Spark envia os dados

print("🔁 Aguardando dados do Spark...")

try:
    while True:
        msg = consumer.poll(1.0)

        if msg is None:
            continue
        if msg.error():
            print("⚠️ Erro:", msg.error())
            continue

        # Lê os dados do Spark (JSON)
        try:
            data = json.loads(msg.value().decode('utf-8'))
            paciente_id = data.get("paciente_id", "desconhecido")

            # Converte em DataFrame e extrai features
            df = pd.DataFrame([data])
            X = df[['bpm', 'spo2', 'pressao_sistolica', 'pressao_diastolica']]

            # Prediz risco
            pred = modelo.predict(X)[0]

            print(f"👤 Paciente {paciente_id}: risco previsto -> {pred.upper()}")

        except Exception as e:
            print("❌ Erro ao processar mensagem:", e)

except KeyboardInterrupt:
    print("\nEncerrando consumidor...")

finally:
    consumer.close()
