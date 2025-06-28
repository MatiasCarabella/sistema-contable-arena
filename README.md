
# Sistema de Gestión Contable para PYMEs

Este proyecto es una aplicación de consola en Java que implementa un sistema de gestión contable utilizando el patrón MVC y persistencia en MySQL mediante JDBC. Permite la administración de clientes, proveedores, ingresos, gastos y la generación de reportes contables.

**Versión de Java utilizada:** 24.0.1
**Base de datos:** MySQL 8+ (ver `sistema_contable_arena.sql` para el esquema y datos de prueba)

## Estructura del Proyecto

- **model/**: Clases de dominio (`Client`, `Supplier`, `Transaction`, `Income`, `Expense`).
- **repository/**: Repositorios JDBC para acceso y persistencia en MySQL (`ClientRepository`, `SupplierRepository`, `TransactionRepository`).
- **controller/**: Lógica de negocio y orquestación de operaciones para cada entidad (`ClientController`, `SupplierController`, `TransactionController`, `ReportController`).
- **view/**: Menús interactivos por consola y utilidades de entrada (`ConsoleMenu`, `ClientsMenu`, `SuppliersMenu`, `IncomesMenu`, `ExpensesMenu`, `ReportsMenu`, `InputUtils`).
- **util/**: Utilidades generales, incluyendo la gestión de la conexión JDBC (`DBConnection`).
- **App.java**: Punto de entrada de la aplicación.

## Arquitectura y Persistencia

El sistema sigue una arquitectura en capas (MVC):

- **Modelo:** Representa las entidades del dominio.
- **Repositorio:** Encapsula el acceso a datos usando JDBC y MySQL.
- **Controlador:** Gestiona la lógica de negocio y coordina las operaciones entre vistas y repositorios.
- **Vista:** Proporciona menús y entrada/salida por consola.

La persistencia se realiza en una base de datos MySQL. El archivo `sistema_contable_arena.sql` contiene el esquema, relaciones y datos de prueba. La conexión se gestiona mediante la clase `DBConnection` y el driver JDBC de MySQL.

## Funcionalidades

### 1. Gestión de Clientes
- Registrar, actualizar, eliminar, buscar y listar clientes.

### 2. Gestión de Proveedores
- Registrar, actualizar, eliminar, buscar y listar proveedores.

### 3. Ingresos
- Registrar ingresos asociados a clientes.
- Eliminar ingresos.
- Listar ingresos de un cliente.

### 4. Gastos
- Registrar gastos asociados a proveedores.
- Eliminar gastos.
- Listar gastos de un proveedor.

### 5. Reportes
- Consultar historial de ingresos y gastos.
- Generar reporte de balance por rango de fechas.

## Instalación y Ejecución

1. **Configurar la base de datos MySQL:**
   - Ejecutar el script `sistema_contable_arena.sql` para crear la base y poblarla con datos de prueba.
2. **Configurar el driver JDBC:**
   - Asegurarse de que `mysql-connector-j-9.3.0.jar` esté en la carpeta `lib/` y en el classpath.
3. **Compilar y ejecutar:**
   - Compilar los archivos `.java` de `src/` y ejecutar `App.java`.

## Notas
- El sistema valida la existencia de clientes y proveedores antes de asociar ingresos o gastos.
- La persistencia es permanente en MySQL.
- El código está organizado en capas para facilitar el mantenimiento y futuras extensiones.