# Solución a la tarea: El Port CustomersRepository no encaja con la interfaz CustomersJPARepository

He aplicado el patrón Adapter creando la clase intermedia "CustomerRepositoryAdapter" para que CustomersJPARepository sea compatible con CustomersRepository.

## Principios SOLID aplicados:
### Principio de Responsabilidad Única
CustomerRepositoryAdapter se encarga únicamente de adaptar la persistencia de CustomersJPARepository a la interfaz CustomersRepository.
### Principio OPEN/CLOSE
El código está abierto a la extensión y cerrado a la modificación ya que el Port CustomerRepository permite agregar nuevas implemetaciones de persistencia sin modificar la lógica de la aplicación.
### Principio de Sustitución de Liskov
La interfaz "CustomersRepository" y sus implementaciones (CustomersInMemoryRepository, CustomerRepositoryAdapter) pueden intercambiarse sin afectar al código.
### Principio de Segregación de interfaces
CustomersRepository define únicamente los métodos esenciales para la aplicación (búsqueda y guardado de Customers).
### Principio de Inversión de Dependecias
La capa de aplicación depende de la interfaz CustomersRepository y no de la implementación concreta (CustomerRepositoryAdapter).

## Patrones que ya había anteriormente
Patrón Command en la clase CreateCustomerCommand.
Patrón Builder con Lombok en varias clases.
Patrón Singleton en CustomersInMemoryRepository.

## Patrones que he añadido además del adapter
Patrón Singleton en CustomersRepositoryAdapter.
