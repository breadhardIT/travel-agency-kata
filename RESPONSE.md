## Principios solid aplicados ##
SRP:

OCP: Se puede implementar cualquier base de datos sin tener que modificar la interfaz padre
CustomerRepository, por lo que Open-Closed Principle se cumple.

LSP: Se observa Liskov en la abstracción de interfaces.

ISP: Se utilizan todos los métodos de las interfaces,
por lo que se cumple este principio y no habría que dividir en más subinterfaces.

DIP: El principio de inversión de dependencias se aplica en la implementación
de las interfaces CustomersRepository y CustomersJPARepository.

## Patrones de diseño aplicados ##
Adapter Pattern: La clase AdapterPattern del paquete de persistencia aplica el
patrón adapter implementando CustomerRepository.

Builder Pattern: La clase AdapterPattern también aplica el patrón Builder mediante
anotaciones de Lombok, facilitando la creación de objetos sin el paso de una gran
cantidad de parámetros.

Singleton Pattern: La clase CustomersInMemoryRepository implementa este patrón para
no se pueda instanciar más de una vez la clase y una vez hecha, se devuelva la misma
instancia desde un punto de acceso global a todo el proyecto.