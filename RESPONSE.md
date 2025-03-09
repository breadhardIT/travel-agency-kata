* Qué principios SOLID se han utilizado para codificar este proyecto e indica algún ejemplo

	Single Responsability: Las clases como CreateCustomerCommand o GetCustomerQuery realizan una única acción.
	Open Close: Tener la interfaz CustomersRepository permite extender el código con nuevas funcionalidades. Además, puedes agregar nuevos comandos sin tener que modificar el comando existente.
	Interface Segregation: Se establece una interface con los métodos suficientes para llevar a cabo lo que se requiere. Además, sólo la implementan aquellas clases que la necesitan, como CostumerRepository_CostumerJPARepository_Adapter y CustomersInMemoryRepository.
	Dependency Inversion: Al tener la interfaz de CustomerRepository dependemos de ésta en lugar de una implementación específica.

* Qué patrones de diseño has observado e indica algún ejemplo
    Adapter: Adapta la interfaz JPA al dominio.
    Builder: Para crear las entidades.
    Singleton: Como la clase CustomersInMemoryRepository.
    Command: Como la clase CreateCustomerCommand para encapsular una operación en un objeto.
