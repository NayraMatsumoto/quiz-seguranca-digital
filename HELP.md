# Deploy no Render — Guia rápido

1. Criar repositório no GitHub (já configurado como remoto em `origin`).
2. No painel do Render, criar um novo **Database > PostgreSQL** (ou usar `render.yaml`).
   - Anote a `DATABASE_URL` ou `JDBC_DATABASE_URL` gerada.
3. Criar um novo **Web Service** no Render:
   - Environment: `Docker` (ou `Native` se preferir).
   - Build Command: `./mvnw -DskipTests package`
   - Start Command: `java -Dserver.port=$PORT -jar target/quiz-seguranca-digital-1.0.0.jar`
   - Adicionar Environment Variables:
     - `SPRING_PROFILES_ACTIVE=prod`
     - Caso a Render forneça `DATABASE_URL` no formato `postgres://user:pass@host:port/db`, não é necessário alterar (o app faz o parsing em `prod`).
     - Alternativamente defina `JDBC_DATABASE_URL` com o valor `jdbc:postgresql://...` e `DB_USERNAME` / `DB_PASSWORD`.
4. Health check: use `/actuator/health` (attractor precisa estar habilitado — adicionamos Actuator).
5. Push para o GitHub e acione o deploy.

Comandos locais úteis:

```powershell
./mvnw -DskipTests package
java -jar target/quiz-seguranca-digital-1.0.0.jar
```

Possíveis erros comuns:
- `Invalid DATABASE_URL format`: verifique se `DATABASE_URL` está no formato `postgres://user:pass@host:port/db`.
- Erro ao iniciar: verifique se o JAR gerado tem o nome `quiz-seguranca-digital-1.0.0.jar`; caso contrário atualize o `startCommand`.
# Quiz de Segurança Digital para Escolas

Aplicação Spring Boot 3 com Thymeleaf e H2 para um quiz corporativo com sorteio de 5 perguntas dentre um banco de até 20 questões.


