## Principios SOLID:
- Cada clase solo tiene una sola responsabilidad = Single Responsibility
- CustomerRepository al ser interfaz permite añadir nuevas implementaciones sin modificar el código existente = Open/Closed
- CustomerRepository no obliga a las implementaciones de los métodos a depender de otros no usados = Segregación de Interfaces

## Patrones:
- En CustomersInMemoryRepository se aplica el patrón Singleton
- En CreateCustomerCommand y GetCustomerQuery tenemos el patrón Comando
- Además, la solución que hemos realizado para el ejercicio aplica el patrón Adaptador