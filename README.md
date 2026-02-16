## MeetAPI (Leia-se 'Metapi' ou Encontro)

### Objetivo do projeto
O presente projeto visa utilizar todos os recursos aprendidos durante a ministração
das aulas da disciplina de Desenvolvimento de Sistemas Corporativos. Onde nos foi apresentado as 
ferramentas que fazem parte do sistema corporativo, melhores práticas e utilidades. O Java, Spring Framework e todo o ecossistema Spring (Security, Data...) são ferramentas robustas e 
aplicáveis em diversas soluções corporativas reais.

Pensamos em desenvolver uma API para gestão de eventos:
- Gerenciar eventos
- Gerenciar atividades de eventos
- Gerenciar inscrições em eventos
- Gerenciar usuários
- Gerenciar certificados (:warning: Em andamento)

## Tecnologias 
- Spring 3.4.5
- Java 21 - Coretto
- Maven 3.9.11
- Flyway (Gerenciamento de migrations)

### Instale os requisitos - Java 21 and Maven 3.9


Easy install with SDKMAN
https://sdkman.io/

```bash
curl -s "https://get.sdkman.io" | bash
```

```bash
sdk install java 21.0.8-amzn
```

```bash
sdk install maven 3.9.11
```

**Instale as dependências**
```bash
mvn clean install
```

**Rodando o projeto**
```bash
mvn spring-boot:run
```

### Documentação
Documentação com springdoc-swagger em:
http://localhost:8080/swagger-ui/index.html