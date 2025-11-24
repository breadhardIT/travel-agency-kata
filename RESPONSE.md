# Patrones
Se usa patron builder en:
- CreateCustomerCommand 
- GetCustomerQuery
- Customer
- CustomerEntity
- GetCustomerDTO
- PutCustomerDTO

Patron singleton en CustomersInMemoryRepository

Patron factory en la creacion de Customers

Habra que usar patron adapter para que CustomersRepository encaje con CustomersJPARepository

# Principios SOLID

En general se cumplen los principios solid, ya que:
- Las clases tienen una única responsabilidad y, por tanto, un motivo para cambiar
- Las clases estan abiertas a extensión pero cerradas a modificación
- Ningun módulo de alto nivel depende directamente de módulos de bajo nivel
- No hay ninguna herencia por extension, asi que no se puede violar el LSP
- No hay ninguna clase que implemente metodos que no usa
