# Primeira etapa: Construção do projeto
FROM ubuntu:latest AS build

# Atualiza a lista de pacotes e instala o JDK do OpenJDK 21 e Maven
RUN apt-get update && apt-get install -y openjdk-21-jdk maven

# Define o diretório de trabalho
WORKDIR /app

# Copia todos os arquivos do diretório atual para o diretório de trabalho no contêiner
COPY . .

# Executa o comando Maven para limpar e construir o projeto
RUN mvn clean install

# Segunda etapa: Imagem final
FROM openjdk:21-jdk-slim

# Expõe a porta 8080
EXPOSE 8080

# Copia os artefatos construídos da primeira etapa para a segunda etapa
COPY --from=build /app/target/gestao_vagas-0.0.1-SNAPSHOT.jar /app.jar

# Define o ponto de entrada para a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]