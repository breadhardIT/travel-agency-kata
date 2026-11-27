# Principios SOLID y patrones de diseño

## SOLID

- **Responsabilidad única.** `CustomersController` recibe las peticiones HTTP y construye las respuestas. `GetCustomerDTO.from` convierte el cliente al formato de salida y evita repetir ese mapeo en las dos consultas. El adaptador JPA traduce entre el modelo de dominio y la entidad de base de datos.
- **Abierto/cerrado.** El cambio de almacenamiento se hace incorporando `CustomersJpaAdapter`, una implementación del puerto `CustomersRepository`. El comando de alta puede seguir usando el mismo contrato al cambiar de repositorio.
- **Sustitución de Liskov.** El adaptador JPA y el repositorio en memoria se pueden usar a través de `CustomersRepository`. Ambos permiten recuperar clientes por identificador o pasaporte y devuelven `Optional.empty()` cuando la búsqueda no encuentra un cliente.
- **Segregación de interfaces.** `CustomersRepository` expone las operaciones de clientes que necesita la aplicación. La paginación y las consultas de Spring Data quedan dentro de infraestructura.
- **Inversión de dependencias.** `CreateCustomerCommand` y `GetCustomerQuery` dependen del puerto definido en la aplicación. `CustomersJpaAdapter` implementa ese puerto y recibe `CustomersJPARepository` por constructor.

## Patrones

- **Adapter.** `CustomersJpaAdapter` implementa el puerto y delega en Spring Data JPA. Convierte entre `Customer` y `CustomerEntity` y usa una consulta por ejemplo para buscar por pasaporte.
- **Builder.** Lombok genera los constructores paso a paso para objetos como `Customer` y `CreateCustomerCommand`. Cada llamada indica qué campo se está asignando antes de llamar a `build()`.
- **Command.** `CreateCustomerCommand` reúne los datos del alta y la ejecuta con `handle()`. La lectura se modela aparte en `GetCustomerQuery`.
- **Repository.** `CustomersRepository` representa las operaciones para guardar y recuperar clientes. `CustomersJPARepository` proporciona acceso a las filas de la tabla mediante JPA.

## Cambios en infraestructura

La API usa H2 en modo PostgreSQL y Flyway crea el esquema. Al guardar sin fecha de alta ni estado, el adaptador aplica la fecha actual y el estado inactivo. Si hay dos altas con el mismo pasaporte, la búsqueda devuelve la última registrada.

La consulta REST por pasaporte llama al puerto con el valor recibido. `GetCustomerQuery.handle()` pasa el campo `id` a `getCustomerByPassport` en esa rama, por lo que la corrección se hizo en el controlador para respetar la restricción del enunciado sobre la capa de aplicación.

## Ejercicios de patrones

- **Composite.** Los alimentos y platos implementan `CalorieItem`. Un plato suma las calorías de sus ingredientes y el total del menú se calcula con la misma operación para platos y alimentos sueltos.
- **Métodos de fábrica.** `Movement` ofrece un método de creación por operación. Cada tipo calcula su efecto en el saldo y la cuenta usa ese resultado para registrar el movimiento.
- **Strategy.** Cada tipo de documento selecciona su operación de escaneo. El trayecto determina qué documento se requiere.
- **Observer.** El repositorio de empleados avisa a los suscriptores al registrar un alta nueva. El saludo se envía al recibir ese aviso.
