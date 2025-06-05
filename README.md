# Sistema de Gestión Contable para PYMEs0 (Consola)

Este proyecto es una aplicación de consola en Java que implementa un sistema de gestión contable utilizando el patrón MVC. Permite la administración de clientes, proveedores, ingresos, gastos y la generación de reportes contables.

## Estructura del Proyecto

- **model/**: Clases de dominio (`Client`, `Supplier`, `Transaction`, `Income`, `Expense`).
- **repository/**: Repositorios en memoria para simular persistencia (`ClientRepository`, `SupplierRepository`, `TransactionRepository`).
- **controller/**: Lógica de negocio para cada entidad (`ClientController`, `SupplierController`, `ReportController`).
- **view/**: Menús interactivos por consola y utilidades de entrada (`ConsoleMenu`, `ClientsMenu`, `SuppliersMenu`, `IncomesMenu`, `ExpensesMenu`, `ReportsMenu`, `InputUtils`).
- **App.java**: Punto de entrada de la aplicación.

## Funcionalidades

### 1. Gestión de Clientes
- Registrar, actualizar, eliminar, buscar y listar clientes.

### 2. Gestión de Proveedores
- Registrar, actualizar, eliminar, buscar y listar proveedores.

### 3. Ingresos
- Registrar ingresos asociados a clientes (valida existencia del cliente).
- Eliminar ingresos.
- Listar ingresos de un cliente.

### 4. Gastos
- Registrar gastos asociados a proveedores (valida existencia del proveedor).
- Eliminar gastos.
- Listar gastos de un proveedor.

### 5. Reportes
- Consultar historial de ingresos y gastos.
- Generar reporte de balance por rango de fechas.

## Uso

1. Compila el proyecto:

```sh
javac -d bin src/**/*.java
```

2. Ejecuta la aplicación:

```sh
java -cp bin App
```

3. Navega por el menú principal y los submenús para operar sobre clientes, proveedores, ingresos, gastos y reportes.

## Notas
- En esta versión, los datos se almacenan en memoria (listas) y se pierden al cerrar la aplicación.
- El sistema valida la existencia de clientes y proveedores antes de asociar ingresos o gastos.
- El diseño permite eventualmente conectar con una base de datos (MySQL) modificando los repositorios.