from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import classification_report
import pandas as pd
import joblib

# Carrega CSV já coletado anteriormente
df = pd.read_csv('output/sinais_vitais.csv')

# Features e labels
X = df[['bpm', 'spo2', 'pressao_sistolica', 'pressao_diastolica']]
y = df['risco']  # assumindo que está como "normal", "alerta", "emergência"

# Treina modelo
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)
modelo = LogisticRegression(max_iter=1000)
modelo.fit(X_train, y_train)

# Avalia
print(classification_report(y_test, modelo.predict(X_test)))

# Salva
joblib.dump(modelo, 'modelo_risco.pkl')
