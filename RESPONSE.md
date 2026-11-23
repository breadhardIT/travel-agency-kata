**SOLID**: principios de diseño (Single Responsibility, Open to extend closed to modify, liskov substitution, interface segregation, dependency inversion)-> generalidades a como organizar el codigo limpio.

**- Single Responsibility**: las entidades solo deberian tener una razon de cambio. es decir no enlazar demasiado dos funcionalidades distintas, aislar las funcionalidades de manera logica, que un cambio en una no afecte a la otra.
Como ejemplo en el repositorio, se divide la aplicación en una Arquitectura Hexagonal, con lo cual ya tienen bastante aisladas las distintas funcionalidades.
Además, cada clase tiene claramente un solo objetivo, por ejemplo: _Customer_ se encarga de la entidad lógica, mientras que _CustomerRepository_ se encarga de la tabla de los clientes. Las clases _GetCustomerPTO_ y _PutCustomerPTO_ también demuestran la separación de las responsabilidades.
Sin embargo, la clase _CustomerController_ podría considerarse como que rompe este principio, ya que se encarga de muchas funcionalidades, como recibir HTTP, contruir comandos y mapear a DTO.

**- Open to extend, closed to modify**: se pueden añadir funcionalidades, pero si la funcionalidad existente ya funciona NO se modifica.
Como ejemplo en el repositorio, si quisieramos añadir una nueva manera de guardar los clientes se puede crear una nueva implementación de _CustomerRepository_ sin cambiar los casos de uso de la clase que ya está implementada.

**- Liskov Substitution**: clases derivadas o interfaces se pueden sustituir por clases padres sin alterar el funcionamiento correcto. 
Como ejemplo, _CustomersInMemoryRepository_ y _CustomerRepositoryAdapter_ son intercambiables. _CreateCustomerCommand_ y _GetCustomerQuery_ funcionan igual con cualquiera de las dos, sin saber cuál reciben.

**- Interface segregation**: no obligar a implementar una interfaz que no se use, ser muy especifico.
Como ejemplo, la interfaz _CustomersRepository_ tiene pocos casos de usos, con solo tres métodos, que se realizan para cualquier implementación que se busque.
La aplicación no ve la interfaz mucho más grande de JpaRepository, que queda oculta tras el adaptador.

**- Dependency inversion**: entidades dependen de abstracciones. es decir no depender de implementaciones concretas.
Los casos de uso dependen de la abstracción CustomersRepository, no de JPA. La infraestructura depende de la aplicación, y no al revés. La inyección es por constructor, como en CustomerRepositoryAdapter(CustomersJPARepository jpaRepository).


**PATRONES DE DISEÑO**
En el repositorio se pueden observar varios patrones de diseño,

- Arquitectura hexagonal (puertos y adaptadores): es el patrón arquitectónico del proyecto. De esta manera se aislan las distintas funcionalidades, de manera que sea más matenible en el futuro.
- Adapter: CustomerRepositoryAdapter adapta la interfaz del puerto (saveCustomer, getCustomerById...) a la de Spring Data (save, findById...). Este es el patrón que se ha implementado para el taller, ya que había que juntar dos interfaces que no eran compatibles inicialmente.
- Builder: Lombok @Builder en Customer, CustomerEntity, los DTO y los comandos, por ejemplo Customer.builder().id(...).build(). Tambien existe una propia clase _CustomerBuilder_ que se encarga de crear a las entidades de tipo _Customer_ sin que las clases que necesiten instanciarlos sepan los detalles de implementación.
- Singleton: CustomersInMemoryRepository lleva @Scope("singleton"), y los beans de Spring son singletons por defecto. Esto significa que solo puede haber una instancia de la clase a la vez.
