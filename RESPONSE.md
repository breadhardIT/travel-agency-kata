Principios SOLID en el proyecto:

Single Responsability Principle: Ninguna clase tiene una responsabilidad por el cual pueda ser necesario en un futuro 
tener que editarla
Open/Close Principle: Podemos implementar cualquier tipo de base de datos sin tener que modificar la interfaz de 
CustomerRepository
Liskov Subtitution Principle: No se puede observar, ya que no hay ninguna jerarquía en el proyecto
Interface Segregation Principle: Se utilizan todos los métodos de las interfaces sin tener que utilizar excepciones, 
por lo que se cumple este principio
Dependency Inversion Principle: La interfaz CustomerRepository es la que permite que se cumpla este principio, 
desacoplando la base de datos del controlador

Patrones de diseño en el proyecto:

Singleton: La clase CustomersInMemoryRepository era una base de datos de la cual solo existía una única estancia
Builder: CustomerEntity y Customer utilizan @Builder para generar sus constructores
Adapter: CustomerJPARepositoryAdapter es el adaptador necesario para que la CustomersJPARepository funcione con 
CustomersRepository
Command: La clase CreateCustomerCommand se encarga de crear al Customer y guardarlo en la base de datos