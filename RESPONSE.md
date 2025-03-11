# PATRONES DE DISEÑO APLICADO EN ESTE PROYECTO

## Patrón de diseño Singleton
Lo aplico en el Adapter porque me interesa que se instancie una sola vez y que se pueda acceder a ella desde cualquier parte del código.
## Patrón de diseño Command
Se utiliza en las clases Command y Query.
## Patrón de diseño Adapter
Se utiliza en la clase CustomerAdapter. La intención es adaptar las interfaces CustomerRepository con la interfaz CostumersJPARepository.
## Patrón de diseño Builder
En GetCustomerQuery es el ejemplo de implementación de este patrón. 



# PRINCIPIOS SOLID QUE CUMPLE ESTE PROYECTO
## Principio de responsabilidad única
Se cumple en clases como GetCustomerQuery y Customer.
## Principio de abierto/cerrado
Si quisiesemos añadir otro tipo de clientes, podríamos hacer una herencia por extensión de customers, eso haría que no tuviesemos que modificar el resto del código.
## Principio de sustitución de Liskov
Adapter implementa la interfaz CustomerRepository, por lo que se puede sustituir por cualquier clase que implemente la interfaz CustomerRepository.
## Principio de segregación de la interfaz
CustomerRepository define métodos para la gestión de clientes.
## Principio de inversión de dependencias
GetCustomerQuery depende de CustomerRepository, pero no depende de la implementación de CustomerRepository.



