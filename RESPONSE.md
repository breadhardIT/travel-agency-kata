Aunque he observado todos los principios **SOLID**, aquí pongo los más visibles:

* Single Responsibility Principle:
Cada clase tiene una tarea específica y no hace más de lo que debe, como por ejemplo CustomersRepository.


*  Liskov Substitution Principle:
CustomersInMemoryRepository y CustomersJPARepository implementan CustomersRepository. Podemos usar una u otra sin que el código deje de funcionar.


* Interface Segregation Principle:
La interfaz CustomersRepository solo incluye los métodos que realmente se necesitan.

  

**Patrones de Diseño** que he visto:

* Adapter Pattern - (el usado en la solución)
CustomerDataAccessAdapter actúa como un puente entre CustomersRepository y CustomersJPARepository, convirtiendo entre CustomerEntity y Customer.


* Command Pattern
Por ejemplo en CreateCustomerCommand que implementa el patrón Command, usa el encapsulamiento en objetos y exponen un método handle.


* Singleton Pattern
En CustomersInMemoryRepository tenemos un @Scope("singleton"), que nos indica que la instancia sea única.
