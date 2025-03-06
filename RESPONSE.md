## Solución:
Para adaptar el jpa a la interfaz CustomerRepository he usado el patrón adapter,
para ello he creado la clase DatabaseAdapter.

## Bug:
Había un bug en la clase GetCustomerQuery(se llamaba al método de
buscar por pasaporte pasándole el id en vez del pasaporte) que he corregido
para que funcione correctamente aunque cabe destacar que antes de corregir 
esto pasaba los tests, por lo que puede que sean necesarios más tests. 

## Patrones observados:

- builder
- factory