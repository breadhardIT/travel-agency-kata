# RESPONSE

## Principios SOLID observados
- **SRP**: los comandos/queries (`CreateCustomerCommand`, `GetCustomerQuery`) solo orquestan su caso de uso; la entidad de dominio `Customer` solo modela datos.
- **OCP**: los casos de uso no se tocan al añadir un repositorio nuevo; basta con registrar otra implementación de `CustomersRepository` (memoria, JPA) sin tocar Application/Domain.
- **LSP**: las implementaciones de `CustomersRepository` son intercambiables para controller y queries; todas cumplen el mismo contrato.
- **ISP**: el puerto `CustomersRepository` expone solo lo necesario (guardar, buscar por id o pasaporte) evitando dependencias forzadas.
- **DIP**: controllers y casos de uso dependen de la abstracción `CustomersRepository`, no de detalles concretos; Spring inyecta la implementación.

## Patrones de diseño observados
- **Arquitectura hexagonal / Ports & Adapters**: `CustomersRepository` como puerto; el nuevo `C2ustomersInMemoryRepository` (adaptador JPA) y `CustomersInMemoryRepository` (memoria) como adaptadores concretos.
- **Repository pattern**: `CustomersJPARepository` encapsula el acceso a la BBDD y el puerto lo expone al dominio.
- **Adapter / Mapper**: el adaptador JPA traduce `Customer` a `CustomerEntity` y viceversa en `adaptCustomer*`.
- **Builder**: Lombok genera builders en DTOs (`PutCustomerDTO`), dominio (`Customer`) y entidad (`CustomerEntity`) para construcción legible.
- **Dependency Injection**: Spring gestiona la creación de controladores y repositorios, desacoplando wiring del uso.

## Refactors realizados
- Se añadió el adaptador JPA (`C2ustomersInMemoryRepository`) para usar la BBDD H2/PostgreSQL manteniendo Application/Domain intactos.
- `CustomerEntity` ahora tiene constructor por defecto (requisito de Hibernate) y en el adaptador se rellenan valores no nulos para cumplir restricciones de BBDD.

## Tests
- `mvn test` pasa tras los cambios, validando que se persiste y recupera desde la base de datos.
