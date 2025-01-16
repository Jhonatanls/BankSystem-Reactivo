# Sistema bancario Reactivo
## Descripción del proyecto
Este proyecto es la continuación del sistema bancario que utiliza programación imperativa en todo su proceso de creación,
la idea de este nuevo repositorio es agregarle la funcionalidad de gestión de transacciones reactivas para visualización 
de saldo en tiempo real y auditoría de transacciones con sus diferentes datos relacionados.

- **Saldo:** Visualización en tiempo real del saldo
- **Auditoría de transacciones:** Registro de los datos importantes de cada transacción
    - Saldo inicial
    - Saldo final
    - Usuario
    - Monto a transaccionar
    - Fecha de transacción
    - Estado de transacción
- **Persistencia en base de datos MongoDB:** Implementación de la persistencia en base de datos NoSql y no bloqueante que soporte la    reactividad para almacenamiento de las auditorías generadas en las transacciones.
- **Pruebas:** Implementación de pruebas unitarias a funciones claves dentro del proceso de auditorías y pruebas de integración para la comunicación entre APIs.

## Tecnologías usadas
- Java 17
- Spring Boot 3.0
- Spring Data Reactive
- Mongo DB (Atlas)
- Gradle
- JUnit y Web Test Client
- Swagger
- Intellij IDEA Community Edition

## Pruebas
### Pruebas unitarias

### Pruebas de integración

### API Test Postman

## Orgnización de commits del proyecto
1. Creación del Readme.md y primeros pasos para la app reactiva.
2. Configuración de Swagger para su correcto funcionamiento, organización del proyecto por paquetes, creación de entidades en MongoDB
3. Creación de modelos, repositorios, servicios y controller necesarios para el funcionamiento de la entidad cuenta que será usada para la auditoria de transacciones. 