## Solución:
Para adaptar el jpa a la interfaz CustomerRepository he usado el patrón adapter,
para ello he creado la clase DatabaseAdapter.

## Bug:
Había un bug en la clase GetCustomerQuery(se llamaba al método de
buscar por pasaporte pasándole el id en vez del pasaporte, por lo que
nunca devolvía nada) que he corregido
para que funcione correctamente aunque cabe destacar que antes de corregir 
esto pasaba los tests, por lo que puede que sean necesarios más tests. 

## Patrones observados:

- builder: en practicamente todas las clases menos controlador e interfaces
- singleton: en CustomersInMemoryRepository y en nueva clase introducida DatabaseAdapter
- command: en CreateCustomerCommand