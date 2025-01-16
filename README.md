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

## Orgnización de commits del proyecto
1. Creación del Readme.md y primeros pasos para la app reactiva.
2. Configuración de Swagger para su correcto funcionamiento, organización del proyecto por paquetes, creación de entidades en MongoDB
3. Creación de modelos, repositorios, servicios y controller necesarios para el funcionamiento de la entidad cuenta que será usada para la auditoria de transacciones.
4. - Implementación de lógica necesaria para creación, obtención y eliminación de cuentas con userId quemado y accountId quemado para no alargar su desarrollo y sus respectivos, services y controllers.
   - Implementación de métodos para la creación de transacciones y su posterior auditoría. 
5. Implementación de la logica de creación de transacciones para programación reactiva con DTOs:
   - Entrada y salida customizada de transacciones para mayor seguridad
   - Salida del balance de cada cuenta por accountId
   - Mapper para pasar de entidad a DTO y viceversa
6. Creación de carpeta para manejar mejor las excepciones y lanzar errores personalizados:
   - Excepción para cuenta no encontrada por accountId
   - Excepción para manejar los montos en las transacciones
   - Global Handler para el manejo global de las excepciones mejoradas
7. Cambios en archivos relacionados a Account para generar el balance en tiempo real, generación de Test unitarios y de integración para AccountService y TransactionService