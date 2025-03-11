## Principios SOLID

En este código se aplican siempre los principios SOLID y se pueden apreciar en diferentes partes del código. Estos son algunos ejemplos donde se aplican entre otros:

- En la capa application se separa la operación de escritura CreateCustomerCommand de la de lectura GetCustomerQuery, haciendo que cada clase tenga una responsabilidad única (Single responsabilty).
- Otro ejemplo, tanto la clase CustomersInMemoryRepository como CustomersJPARepository implementan la interfaz CustomersRepository, por lo que puedes usar una implementación u otra sin afectar el código, cumpliendo así la sustitución de Liskov (Y con los requisitos del cliente).
- Esto también permite cumplir el principio OpenClose, ya que para extender el código (si queremos añadir una implementación diferente) no haría falta modificar el código, sino crear otra clase que extienda de CustomersRepository.

## Patrones de diseño observados

- Se puede ver el patrón builder usado constantemente en la creación de objetos con @Builder como por ejemplo para CustomerCommand en CustomerController:
    ```java
    CreateCustomerCommand command = CreateCustomerCommand.builder()
        .name(customer.getName())
        .surnames(customer.getSurnames())
        .birthDate(customer.getBirthDate())
        .passportNumber(customer.getPassportNumber())
        .customersRepository(customersRepository)
        .build();
    ```
- Aplicación de singleton para crear una única instancia del repositorio con @Scope("singleton") en la clase CustomersInMemoryRepository:
    ```java
    @Repository
    @Scope("singleton")
    public class CustomersInMemoryRepository implements CustomersRepository {
        //(...)
    }
    ````

- Además del patrón adapter que acabamos de añadir.