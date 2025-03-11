
Aunque he encontrado todos los **principios SOLID** implementados, algunos ejemplos son:

* Single Responsibility Principle:

La única función de la clase GetCustomerQuery es recuperar un cliente.

* Segregación de interfaces

En la clase CustomersRepository observamos que sus métodos son solo sobre clientes

* Inversión de dependencias

CustomersController usa la interfaz CustomersRepository en vez de CustomersRepositoryAdaptador.

En cuanto a los **patrones de diseño**:

* Patrón Adapter

Lo he implementado en CustomersRepositoryAdaptador

* Patrón Singleton

Observamos que la clase CustomersInMemoryRepository tiene @Scope("singleton")

* Patrón Builder

Lo utilizamos en varias clases, como por ejemplo en las clase Customer con @Builder
