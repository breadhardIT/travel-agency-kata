# SOLUCIÓN DEL EJERCICIO

Para solucionar este ejercicio, se puede aplicar el patrón de diseño **Adapter**.
Este patrón permite que dos interfaces incompatibles trabajen juntas. En este caso, necesitamos que la interfaz
`CustomersRepository` sea compatible con `CustomersJPARepository`, por lo que se ha desarrollado una clase
`CustomersRepositoryAdapter` que implementa la interfaz `CustomersRepository` y se comunica con la clase
`CustomersJPARepository`. Al añadirle la anotación `@Primary` a la clase `CustomersRepositoryAdapter`, Spring Boot
inyectará esta clase en lugar de `CustomersInMemoryRepository` cuando se necesite un bean de tipo `CustomersRepository`.

De esta forma se consigue que la aplicación funcione con la base de datos sin necesidad de modificar las capas de
aplicación y dominio.

# PRINCIPIOS SOLID ENCONTRADOS

- **Principio de responsabilidad única**: Las clases tienen una única responsabilidad y, por tanto, una única razón
para ser modificadas. Un ejemplo de esto son las clases POJO como `Customer`, que solo contienen los atributos y
métodos necesarios para representar un cliente.
- **Principio de abierto/cerrado**: Las clases están abiertas a la extensión pero cerradas a la modificación, como
en el caso de los comandos, los cuales se pueden añadir sin modificar el código existente.
- **Principio de sustitución de Liskov**: Los objetos de un programa deberían ser reemplazables por instancias de sus
subtipos sin alterar el correcto funcionamiento del programa, por ejemplo, en el caso de `CustomersRepositoryAdapter`
y `CustomersInMemoryRepository`.
- **Principio de segregación de la interfaz**: Las interfaces deben ser específicas y evitar interfaces genéricas
con métodos innecesarios. Las interfaces del proyecto cumplen con este principio.
- **Principio de inversión de dependencias**: Se debe depender de abstracciones, no de implementaciones. Esto puede
observarse en los comandos, que dependen de la interfaz `CustomersRepository` y no de sus implementaciones.

