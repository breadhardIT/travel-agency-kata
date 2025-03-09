# Respuesta - Alonso Moros

## ¿Qué principios SOLID se han utilizado para codificar este proyecto?

- **Single Responsibility Principle:** Se ha utilizado en la creación de las clases, cada una tiene una única
  responsabilidad como debería ser. <br />
  <br />
  **·** *Como por ejemplo:* El CustomerController que se ocupa de manejar las peticiones o la clase Customer que representa el modelo. <br />
  <br />
- **Open/Closed Principle:** Se ha utilizado en la creación de las clases, estas son abiertas para la extensión y
  cerradas para la modificación como indica el principio. <br />
  <br />
  **·** *Como por ejemplo:* Las clases de los builders, se pueden extender para agregar más funcionalidades. <br />
  <br />
- **Interface Segregation Principle:** Se ha utilizado en la creación de las clases, estas tienen interfaces específicas
  para cada tipo de clase. <br />
  <br />
  **·** *Como por ejemplo:* CustomersRepository o CustomersJPARepository. <br />
  <br />
- **Dependency Inversion Principle:** Se ha utilizado en la creación de las clases, ya que las clases dependen de otras
  y no hay instanciaciones en incoherentes unas de otras. <br />
  <br />
  **·** *Como por ejemplo:* El controller y un poco el resto. <br />

## ¿Qué patrones de diseño has observado?
<br />

- **Singleton:** En la clase CustomersInMemoryRepository
- **Builder:** En las clases de los builders (CreateCustomerCommand, Customer, CustomerDTO, CustomerEntity, etc.)
- **Command:** En application/command -> CreateCustomerCommand
- Y diría un **Factory** en el controller quizás por el hecho de que devuelve un Optional.empty() o el objeto que se busca, pero para mí que no lo es.