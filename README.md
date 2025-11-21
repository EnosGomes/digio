## Teste Técnico Digio

Este projeto é um microserviço desenvolvido para integrações externas para obtenção de compras, clientes, produtos e recomendações.
Não utiliza banco de dados local, pois todas as informações são consumidas via API externa oriundas do Vercel.

🚀 Tecnologias Utilizadas

Java 17,Spring Boot 3, Feign Client, Lombok

📦 Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado: Java 17 e o Maven 3.8+

🔧 Como rodar o projeto localmente

1️⃣ Clonar o repositório
git clone https://github.com/SEU_USUARIO/seu-repo.git

cd seu-repo

Depois rodar o comando:

mvn spring-boot:run

🧪 Testando a API

Caso queira usar o swagger entre no link: http://localhost:8080/swagger-ui/index.html#

GET api/v1/compras (sem paginação, retornando as 37 compras de uma vez)

GET api/v1/compras?page=0&size=5 (com paginação)

GET api/v1/clientes-fieis

GET api/v1/maior-compra/{ano}

GET api/v1/recomendacao/cliente/tipo/{cpf}






