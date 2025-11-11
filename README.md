Link do vídeo da solução completa: www.youtube.com/shorts/DjFR82GLHUM?feature=share

# 🛵 Mottu - Localização Inteligente de Motos no Pátio
#
Solução inteligente para gerenciamento e localização de motos em pátios de locadoras, utilizando tecnologia BLE com beacons (prototipado com **ESP32**) e visão computacional para preenchimento automático de dados.

## 🌐 Visão Geral

Este projeto foi desenvolvido com o objetivo de **automatizar e facilitar a movimentação e localização de motos no pátio** da Mottu, especialmente durante o processo de retirada e devolução dos veículos.

A aplicação conta com:

- Um **sistema de localização baseado em sinal BLE (RSSI)** para encontrar motos no pátio;
- Preenchimento automático de **placa** e **tipo da moto** por meio de uma foto;
- Interface amigável para operadores, com **visualização em tempo real da posição das motos**.

---

## 📲 Funcionalidades

### 📍 Localização com Beacon BLE (ESP32)
- Cada moto possui um **beacon ESP32** que emite sinais BLE constantemente.
- A aplicação mobile usa a **intensidade do sinal (RSSI)** captado pelo smartphone para estimar a **distância até a moto**.
- Isso permite que o operador encontre rapidamente a moto no pátio, mesmo sem saber sua posição exata.

### 🧠 Preenchimento Automático via Visão Computacional
- Durante o cadastro, o operador tira uma foto da placa da moto.
- Um modelo de visão computacional (API Plate Recognizer e Modelo desenvolvido por nós) **identifica automaticamente a placa e o tipo da moto**.
- Essas informações são então enviadas ao backend para finalização do cadastro.

---

## 🔄 Fluxo da Aplicação

1. **Captura da Placa**
   - O operador tira uma foto da moto via app mobile.

2. **Leitura Automática**
   - A imagem é processada pelo modelo para extração da placa e do tipo da moto.

3. **Cadastro**
   - Os dados extraídos são enviados ao backend e armazenados no banco de dados.

4. **Localização**
   - O app detecta o beacon BLE especifico da moto que está sendo buscada e calcula a distância com base no RSSI.
   - A interface mostra, em tempo real, a posição da moto no pátio.

5. **Visualização**
   - O operador visualiza e interage com a posição das motos via aplicativo ou sistema web.

---

## 🧪 Tecnologias Utilizadas

| Área                      | Tecnologia                           |
|---------------------------|--------------------------------------|
| Localização BLE           | ESP32 + BLE + RSSI                   |
| Visão Computacional       | API Plate Recognizer + Modelo ML     |
| Backend                   | Java + Spring Boot                   |
| Aplicativo Mobile         | React Native/Kotlin + Biblioteca BLE |
| Frontend Web              | Thymeleaf                            |
| Banco de Dados            | PostgreSQL                           |

---

## 🎯 Benefícios Esperados

- 🚀 Redução de tempo na localização de motos
- 🔍 Precisão no cadastro com leitura automatizada
- 🛠️ Menos erros e retrabalho manual
- 📈 Escalabilidade para grandes pátios
- 🔐 Mais segurança e controle operacional

---

## 🚧 Status do Projeto

> 🧪 **Em desenvolvimento (MVP funcional com ESP32 e RSSI)**  
> 👨‍💻 **Equipe**: Matheus Esteves, Gabriel Falanga, Arthur Spedine  
> 🏁 **Desafio**: Mottu – Challenge  
> 📄 **Instruções**: Execute o projeto e utilize os endpoints via Swagger ou Insomnia (arquivo incluído no repositório)
