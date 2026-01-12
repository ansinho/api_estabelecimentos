# API de Estabelecimentos Georreferenciados ⚡

API REST simples para gerenciar estabelecimentos e seus tipos, com suporte a geometrias (PostGIS), documentação OpenAPI e exemplos de seed.

## � Desafio semanal

**Projeto:** API REST em Spring Boot para gerenciar estabelecimentos e seus tipos, com suporte a geometrias (PostGIS), documentação OpenAPI (Swagger) e scripts de seed para facilitar testes.

**Contexto:** Este repositório faz parte de um desafio semanal em Java Spring Boot — exercício para praticar conceitos de API.  
**Desafio:** Segunda semana.

## �🔧 Tecnologias

- Java 21
- Spring Boot (3.x)
- Spring Data JPA
- Hibernate Spatial (JTS + PostGIS)
- PostgreSQL + PostGIS (imagem: postgis/postgis)
- Maven
- Springdoc OpenAPI (Swagger UI)

## ✅ Requisitos

- Java 21
- Maven (ou use o wrapper `./mvnw` / `mvnw.cmd`)
- Docker & docker-compose (opcional, recomendado para iniciar o banco de dados)

## 🚀 Rodando a aplicação

1. Inicie o banco PostGIS com docker-compose (opcional, mas recomendado):

```bash
# a partir da raiz do projeto
docker compose up -d
```

O serviço Postgres é iniciado com as credenciais padrão definidas em `docker/docker-compose.yaml`:
- DB: `geo_db`
- Usuário: `geo_user`
- Senha: `geo_pass`
- Porta: `5432`

Os scripts em `docker/scripts` criam as extensões PostGIS e fazem seed das tabelas (`tb_tipos_estabelecimento` e `tb_estabelecimentos`).

2. Execute a aplicação:

```bash
# com mvnw (Windows)
./mvnw spring-boot:run
# ou
mvn spring-boot:run
```

A aplicação sobe na porta padrão `8080`.

> Dica: você também pode criar uma imagem OCI com `mvn spring-boot:build-image` (ver `HELP.md`).

## 📚 Documentação da API (Swagger)

A interface do Swagger UI está disponível em:

- http://localhost:8080/swagger

E a spec OpenAPI em JSON em:

- http://localhost:8080/v3/api-docs

## Endpoints principais

### Tipos de estabelecimento

- POST /tipos-estabelecimento
  - Criar um tipo
  - Payload de exemplo:

```json
{
  "codigo": "BAR",
  "descricao": "Bar e Lanchonete"
}
```

- GET /tipos-estabelecimento
  - Listar todos os tipos

- GET /tipos-estabelecimento/{codigo}
  - Buscar por código (ex.: `BAR`)

- PUT /tipos-estabelecimento/{codigo}
  - Atualizar um tipo existente

- DELETE /tipos-estabelecimento/{codigo}
  - Remover por código

### Estabelecimentos

- POST /estabelecimentos
  - Criar novo estabelecimento
  - Payload de exemplo (valores seguem validações definidas nos DTOs):

```json
{
  "nome": "Meu Bar em Brasília",
  "cnpj": "12345678000199",
  "codigoTipo": "BAR",
  "longitude": -47.8910,
  "latitude": -15.8120
}
```

- GET /estabelecimentos
  - Listar todos (retorna uma lista resumida)

- GET /estabelecimentos/geometrias
  - Retorna um FeatureCollection GeoJSON com todas as geometrias

- GET /estabelecimentos/{id}
  - Buscar por UUID

- GET /estabelecimentos/cnpj/{cnpj}
  - Buscar por CNPJ

- PUT /estabelecimentos/{id}
  - Atualizar por ID

- DELETE /estabelecimentos/{id}
  - Remover por ID

## 🎯 Validações importantes

- `cnpj` deve ter 14 caracteres válidos (há validação `@CNPJ`)
- `nome` tamanho máximo 150
- `longitude` entre -180 e 180
- `latitude` entre -90 e 90

## 🧪 Testes

Executar testes com:

```bash
mvn test
# ou
./mvnw test
```

## Scripts SQL e seed

Os scripts em `docker/scripts`:

- `01-init-postgis.sql` — cria extensões PostGIS
- `02-seed-tipos.sql` — cria tabela `tb_tipos_estabelecimento` e insere tipos padrão
- `03-seed-estabelecimentos.sql` — cria tabela `tb_estabelecimentos` e insere exemplos

> Se você não usar Docker, execute os scripts manualmente no banco Postgres/PostGIS.

## 🎯 O que você aprende

- Construir uma API REST com Spring Boot e Spring Data JPA
- Gerenciar geometrias e integrar com PostGIS usando Hibernate Spatial (JTS)
- Documentar APIs com OpenAPI/Swagger (springdoc)
- Implementar validações, DTOs e tratamento centralizado de erros
- Escrever testes automatizados e executar integrações com banco PostGIS
- Usar Docker e docker-compose para levantar dependências de desenvolvimento
- Trabalhar com GeoJSON e retornos de geometria para consumo em mapas
