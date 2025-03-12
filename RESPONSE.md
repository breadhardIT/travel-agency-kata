- Principios SOLID presentes en el código:
  -
    - ##### Single Responsibility:
        - CustomerEntity solo cambia si cambia algo de los datos que deben guardarse de un cliente, no de su manejo.
    - ##### Open/Closed:
      - CustomersInMemoryRepository implementa CustomersRepository.
    - ##### Liskov Sustitution:
      - CustomersJPARepository y CustomersInMemoryRepository implementan CustomersRepository, lo que permite 
        intercambiarlos sin afectar el sistema
    - ##### Interface Segregation:(No he encontrado nada)
    - ##### Dependency Inversion:
      - El CustomersController y la GetCustomerQuery solo acceden a los Customers mediante la interfaz CustomersRepository,
        rebajando así el nivel de acoplamiento.

- Patrones de diseño:
  - 
  - ##### Creacionales:
    - **Singleton**: En CustomersInMemoryRepository y en CustomersRepository
    - **Builder** (aunque solo se utiliza la llamada @builder de Lombok, no es un patrón como tal, ya que no tenemos una clase 
      auxiliar para la creación de los objetos, sin embargo, sí que delegamos la creación): 
      GetCustomerQuery, CreateCustomerCommand, Customer, CustomerEntity, GetCustomerDTO, PutCustomerDTO
  - ##### Estructurales:
  - ##### De comportamiento:
    - **Comando**: CreateCustomerCommand 
  
