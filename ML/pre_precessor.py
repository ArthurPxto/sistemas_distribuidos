# processamento_sinais.py
from pyspark.sql import SparkSession
from pyspark.sql.functions import from_json, col, udf
from pyspark.sql.types import *
from pyspark.sql.functions import to_json, struct
# Define o schema do JSON recebido
schema = StructType([
    StructField("paciente_id", IntegerType()),
    StructField("bpm", IntegerType()),
    StructField("spo2", IntegerType()),
    StructField("pressao_sistolica", IntegerType()),
    StructField("pressao_diastolica", IntegerType()),
    StructField("timestamp", DoubleType())
])

# Inicializa Spark
spark = SparkSession.builder \
    .appName("AnalisadorDeSinaisVitais") \
    .getOrCreate()

# Lê do Kafka
df_raw = spark.readStream.format("kafka") \
    .option("kafka.bootstrap.servers", "localhost:9092") \
    .option("subscribe", "sinais-vitais") \
    .option("startingOffsets", "latest") \
    .load()

# Converte o payload para JSON
df_json = df_raw.selectExpr("CAST(value AS STRING)") \
    .select(from_json(col("value"), schema).alias("dados")) \
    .select("dados.*")

# Função de classificação simples
def classificar_risco(bpm, spo2, sistolica, diastolica):
    if bpm > 120 or spo2 < 90 \
     or sistolica > 160  or diastolica > 110:
        return "emergência"
    elif bpm > 100 or spo2 < 95:
        return "alerta"
    else:
        return "normal"

# UDF para aplicar no DataFrame
risco_udf = udf(classificar_risco, StringType())

# Adiciona coluna com classificação
df_classificado = df_json.withColumn(
    "risco",
    risco_udf(
        col("bpm"),
        col("spo2"),
        col("pressao_sistolica"),
        col("pressao_diastolica")
    )
)

# Empacota os dados em JSON
df_para_kafka = df_classificado.select(to_json(struct("*")).alias("value"))

query = df_para_kafka.writeStream \
    .format("kafka") \
    .option("kafka.bootstrap.servers", "localhost:9092") \
    .option("topic", "sinais-processados") \
    .option("checkpointLocation", "/home/pxto/code/sistemas_distribuidos/ML/checkpoint-kafka") \
    .option("failOnDataLoss", "false") \
    .start()

query.awaitTermination()