He utilizado el patrón Adaptador, pues aplicando este patrón garantizamos que podemos cambiar la implementación de la base de datos en el futuro sin afectar a la capa de aplicación ni dominio. Así, logramos traducir Customer a CustomerEntity, sin tocar ninguna capa más aparte de la de infraestructura.

En cuanto a los principios SOLID y patrones que se pueden observar en el código encuentro lo siguiente:

Patrón factoría: se puede observar en la clase CreateCoustomerCommand, que tiene un método handle() que se encarga de crear un nuevo Customer sin que el resto del código tenga que preocuparse por la creación del objeto directamente.

Patrón singleton: se puede observar en la clase CustomersInMemoryRepository, con lo que se garantiza que haya una única instancia de la clase en la aplicación.

Responsabilidad única: se cumple correctamente, pues cada clase tiene una clara responsabilidad única.

Inversión de depencias: observamos que las distintas clases CustomerController, GetCustomerQuery y CreateCustomerCommand dependen de la interfaz CustomerRespository y no de una implementación concreta.

Abierto/Cerrado: observamos que en todas las clases los atributos son privados. Gracias a implementar el patrón Adaptador hemos conseguido mejorar la aplicación de este principio solid.

Sustitución de Liskov: en la aplicación las clases derivadas o interfaces pueden sustituirse por sus clases padre sin alterar su correcto funcionamiento.

Segregación de interfaces: se cumple ya que las interfaces son lo suficientemente específicas como para que las clases que las implementan las utilicen enteramente.