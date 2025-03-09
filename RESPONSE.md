# TRAVEL AGENCY KATA - Response by Antonio De Angelis

## Principios SOLID que se han utilizado para codificar este proyecto con ejemplos

- **S - Single Responsibility Principle (SRP)**

    He podido observar que en el proyecto se usa puesto que existe una interfaz llamada [CustomerRepository](src\main\java\com\breadhardit\travelagencykata\application\port\CustomersRepository.java) que tiene la responsabilidad única de obtener objetos `Customer` y guardarlos.

- **O - Open/Closed Principle (OCP)**

    He podido observar que en el proyecto se usa porque, por ejemplo, puedes extender el código de la interfaz [CustomerRepository](src\main\java\com\breadhardit\travelagencykata\application\port\CustomersRepository.java) para añadirle nuevas funcionalidades sin tener que modificar el código existente.

- **L - Liskov Substitution Principle (LSP)**

    No se puede observar en el proyecto puesto que no hay superclases ni subclases al no haber herencias.

- **I - Interface Segregation Principle (ISP)**

    He podido observar que se podría parecer que se podría mejorar la interfaz `CustomerRepository` separándolo en dos interfaces. Una se ocuparía de obtener los objetos `Customer` por ID, pasaporte o cualquier otro tipo de atributo. La otra se ocuparía de guardar el objeto `Customer` obtenido por la otra interfaz. Lo que pienso es que esto segregaría innecesariamente las interfaces, aunque si en un futuro se decide añadir más funcionaliades a las interfaces, podría ser factible.

- **D - Dependency Inversion Principle (DIP)**

    He podido observar que se cumple puesto que tenemos una interfaz que se ocupa de guardar los objetos `Customer` en una base de datos, en vez de que los controladores lo guarden directamente por si en un futuro quisiésemos cambiar de tipo de base de datos.

## Patrones de diseño que he observado con ejemplos

- **Creacionales**
    - Singleton: Se puede observar en [CustomersInMemoryRepository](src\main\java\com\breadhardit\travelagencykata\infrastructure\persistence\repository\CustomersInMemoryRepository.java) por la anotación `@Scope("singleton")`.
    - Factory: No he podido observarlo en el proyecto. 
    - Builder: Se puede observar en [CreateCustomerCommand](src\main\java\com\breadhardit\travelagencykata\application\command\command\CreateCustomerCommand.java) porque construye objetos `Customer`.
- **Estructurales**
    - Adapter: Se puede observar que no se cumple dado que el Port [CustomersRepository](src\main\java\com\breadhardit\travelagencykata\application\port\CustomersRepository.java) 
no encaja con la interfaz [CustomersJPARepository](src/main/java/com/breadhardit/travelagencykata/infrastructure/persistence/repository/CustomersJPARepository.java). Para esto, hemos añadido la clase [CustomersRepositoryAdapter](src\main\java\com\breadhardit\travelagencykata\infrastructure\persistence\adapter\CustomersRepositoryAdapter.java) que sirve para que todos los objetos `Customer` puedan guardarse en la base de datos de PostgreSQL, a través de la conversión a objetos `CustomerEntity`. De esta forma, hemos logrado que cuando se construye un objeto `Customer` se pueda guardar en la base de datos de PostgreSQL a través de la interfaz adapter [CustomersRepositoryAdapter](src\main\java\com\breadhardit\travelagencykata\infrastructure\persistence\adapter\CustomersRepositoryAdapter.java) y cuando haga falta obtenerlo de la base de datos se pueda hacer también.
    - Composite: No he podido observarlo en el proyecto.
    - Facade: No he podido observarlo en el proyecto.
- **Comportamiento**
    - Strategy: No he podido observarlo en el proyecto.
    - Command: No he podido observarlo en el proyecto. 
    - Observer: No he podido observarlo en el proyecto.