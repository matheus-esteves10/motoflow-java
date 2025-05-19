# 📍 Mapeamento Inteligente de Pátio com Visão Computacional

## 🚀 Visão Geral

Este projeto tem como objetivo desenvolver uma solução baseada em **visão computacional** para otimizar a gestão de pátios de motos da Mottu, empresa referência no aluguel de motocicletas para entregadores de aplicativo. A proposta automatiza a alocação, localização e remoção de motos em tempo real, aumentando a eficiência, escalabilidade e precisão da operação.

## ❗ O Problema

Com mais de 100 filiais espalhadas por diferentes localidades e tamanhos, a Mottu enfrenta desafios críticos relacionados à operação manual de seus pátios:

- Localização manual das motos gera erros e retrabalho;
- Baixa produtividade dos operadores;
- Falta de padronização e visibilidade em tempo real;
- Atrasos na operação e riscos de segurança;
- Dificuldade de escalar o processo para novas filiais.

## 🎯 Objetivos da Solução

A solução idealizada busca resolver os problemas acima com os seguintes pilares:

### ✅ Localização Inteligente

- Identificação automática das motos por imagem e leitura de placas;
- Mapeamento digital do pátio com exibição em tempo real da posição das motos.

### ✅ Automação de Processos

- Alocação automática de motos em vagas disponíveis;
- Remoção de motos do pátio ao serem alugadas, com simples leitura de placa.

### ✅ Interface Visual e Escalável

- Plataforma web ou aplicativo com interface intuitiva para os operadores;
- Compatível com qualquer layout de pátio, permitindo fácil adaptação.

## 🔄 Fluxo da Aplicação

O fluxo operacional da aplicação ocorre da seguinte forma:

1. **Upload da Imagem da Placa**  
   O operador utiliza o aplicativo mobile para capturar e enviar a imagem da placa da moto por meio da interface da aplicação.

2. **Processamento com Visão Computacional**  
   Um script em Python consome essa imagem e utiliza um modelo de OCR (por exemplo, a API Plate Recognizer) para identificar automaticamente a placa da moto.

3. **Envio ao Backend**  
   Após a leitura, o script envia para o backend em Java os dados da placa juntamente com a tarefa a ser executada (como o cadastro e posicionamento de uma nova moto no pátio).

4. **Persistência dos Dados**  
   O backend processa a solicitação, atualiza o banco de dados com as informações recebidas e associa a moto a uma posição no pátio.

5. **Visualização na Interface**  
   A interface web ou mobile exibe a nova posição da moto em tempo real no mapa do pátio, permitindo acompanhamento completo pelos operadores.


## 🧠 Tecnologias Envolvidas

- Visão Computacional para leitura da iamgem das placas (API Plater Recognizer)
- Backend com integração de câmeras/sensores
- Frontend Web/App com visualização do pátio em tempo real
- Banco de dados georreferenciado/localização

## 🧩 Benefícios Esperados

- Redução de erros e retrabalho manual
- Aumento da produtividade e agilidade operacional
- Operação mais segura e escalável
- Suporte tecnológico ao crescimento da Mottu

---

> **Status**: Em desenvolvimento  
> **Equipe**: [Matheus Esteves, Gabriel Falanga e Arthur Spedine]  
> **Cliente/Desafio**: Mottu – Challenge  
> **Instruções**: Somente rodar o projeto e fazer os requests no endpoint (swagger ou arquivo do insomnia que foi adicionado ao repositório)

