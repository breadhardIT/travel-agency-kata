<h1 align="center">Kata Respuesta</h1>
<p align="center">Hugo Alvarez Ajenjo</p>

## Explicación de los Cambios Realizados

El problema inicial es que tenemos que hacer una migración de una implementación de base de datos en memoria (como lo
tenemos actualmente). A una base de datos H2 mediante la que será usada mediante Spring Jpa.

El problema que tenemos es que las interfaces que pide la capa de aplicación no es la misma que la que provee
JpaRepository.

Solución: Crear una clase adaptadora en medio. **Patrón Adapter**.

Para ello, esta nueva clase implementará la interfaz _CustomersRepository_ que es la que pide la capa de aplicación.
Esta clase tiene que convertir los datos del tipo _Customer_ al tipo _CustomerEntity_ que es la que utiliza Hibernate
para guardar los datos.

Para mayor limpieza he creado un **Mapper** separado de esta clase para mayor limpieza en
`infrastructure/persistence/mapper` que me convierte los datos de un tipo al otro.

Me he encontrado el problema que no acepta nulos los valores de `aceptable` y de `enrrollementDate` por lo que a falta
de información y de acciones en la implementación anterior, he decidido inicializarlos con `true` y `LocalDate.now()`
respectivamente.

## Principios SOLID Identificados en el Proyecto y Patrones

Se han identificado los siguientes patrones de diseño:

- Adapter (El que acabo de crear)
- Singleton: `infrastructure/persistence/repository/CustomerMemoryRepository` Es singleton (Si o si, ya que es lo que
  hace de DB)
- Builder: Muchas clases usan la anotación de `@Builder` de Lombok

Los principios SOLID identificados son:

- Principio de Una sola Responsabilidad: Cada entidad software (función/clase/módulo) solo debe hacer una cosa y bien.
    - Las clases de datos como Customer no tienen más función que representar a un cliente
    - El antiguo _CustomersInMemoryRepository_ simplemente sirve para acceder a la base de datos...
- Principio Open-Close de Bertrand Meyer: una entidad de software debe quedarse abierta para su extensión, pero cerrada
  para su modificación
    - Se puede cambiar perfectamente la base de datos sin tocar antiguo código.
    - Añadir nuevas funciones al software mediante la creación de nuevos controllers, querys commands...
- Principio de Sustitución de Bárbara Liskov: Cada clase que hereda de otra puede usarse como su padre sin necesidad de
  conocer las diferencias entre ellas.
    - No se encuentra ningún tipo de herencia
    - Una posible violación de este principio puede considerarse que la nueva implementación de la base de datos al
      tener el problema que no me aceptara nulos por lo que una tiene precondiciones más restrictivas (esto depende de
      las reglas de negocio, podría haber puesto que sean nulables en el entity)
- Segregación de Interfaces
    - Considero que las interfaces tienen buena cohesión y no hace falta separarlas más (_CustomersRepository_).
- Inversión de dependencias
    - Se usan abstracciones en vez de las clases como se puede ver con los repositorios y el puerto.
    - No estoy familiarizado con la arquitectura hexagonal, asi que asumo que los comandos y query que son clases de
      datos al fin y al cabo no hace falta, aunque se puede abstraer un poco más mediante una interfaz en sus métodos `handle`.

>[!important]
> No he detectado ningún antipatrón SOLID a parte de los señalados previamente, por lo que no he cambiado ninguna parte del codigo