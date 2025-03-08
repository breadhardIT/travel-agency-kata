# Solución a "CustomersRepository no encaja con la interfaz CustomersJPARepository"
He aplicado el patrón adapter en este caso ya que así consigo crear una clase intermedia que convierta CustomersJPARepository en algo que encaje con CustomersRespository
Para más comodidad he creado la carpeta adapter donde se encuentra el patrón aplicado

## Patrones que ya había anteriormente
Se podía observar el patrón Command en la clase CreateCustomerCommand.
También el patrón Builder con la librería de Lombok en casi todas las clases

## Principios SOLID aplicados al programa
### Principio OPEN/CLOSE
CustomersRepository permite que se implementen nuevas formas de persistencia, tal y como hemos hecho en este proyecto. Por ello, este principio sí se aplica.
### Principio de Segregación de interfaces
La interfaz CustomersRepository solamente define métodos que tienen que ver con el repositorio de Customers (guardar y buscar con distintos parámetros)
### Principio de Sustitución de Liskov
El proyecto es algo pequeño para ver este principio aplicado, aunque se puede "coger con pinzas" si vemos las clases CustomersInMemoryRepository y CustomersRepositoryAdapter, las cuales se pueden usar indistintamente gracias a la implementación de la interfaz CustomersRepository, implementada por las dos clases
### Principio de Inversión de Dependecias
Las dependencias son inyectadas a través de la abstracción gracias, de nuevo, a la interfaz CustomersRepository
### Principio de Responsabilidad Única
Veo que la clase de CreateCustomerCommand se debe encargar únicamente de crear el Customer. Sin embargo, veo que lo crea y además lo guarda en el repositorio. Esto se podría arreglar utilizando el patrón Factory, sin embargo el patrón Builder ayuda mucho a la hora de crear los objetos, por lo que, aunque se "viole" este principio, el build() hace que todo se simplifique más
