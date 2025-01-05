# 🚗 Sistema de Gestión de Concesionario con MySQL

![Java Badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL Badge](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)

## 📑 Índice
1. [Descripción y Objetivos](#-descripción-y-objetivos)
2. [Requisitos del Sistema](#-requisitos-del-sistema)
3. [Arquitectura y Diseño](#-arquitectura-y-diseño)
4. [Funcionalidades Implementadas](#-funcionalidades-implementadas)
5. [Decisiones Técnicas](#-decisiones-técnicas)
6. [Base de Datos](#-base-de-datos)
7. [Instalación y Configuración](#-instalación-y-configuración)
8. [Uso del Sistema](#-uso-del-sistema)

## 📋 Descripción y Objetivos
Sistema de gestión para concesionario que permite la administración completa de coches y sus pasajeros, implementado como proyecto educativo para la asignatura de Acceso a Datos. El sistema permite gestionar el registro de vehículos y controlar la capacidad de pasajeros por coche.



### 🎯 Objetivos Principales
- Gestión de coches y pasajeros: Implementar CRUD completo para cada entidad.
- Control de relaciones: Gestionar la asociación N:M entre coches y pasajeros.
- Validaciones de datos: Asegurar que los datos ingresados cumplan con los formatos y reglas esperadas.
- Interfaz de usuario
- Manejo errores

## 🏗 Arquitectura y Diseño

### 📐 Patrones Implementados
- **Patrón DAO**: Abstracción del acceso a datos
- **Servicios**: Centralización de lógica de negocio
- **Singleton**: Gestión de conexiones a BD
- **MVC Modificado**: Separación de responsabilidades

### 🔄 Estructura del Sistema

```ascii
Usuario
   ↓
Main (UI)
   ↓
Services ←→ Validaciones
   ↓
DAO Layer ←→ Conexión BD
   ↓
MySQL DB
```

### 📚 Capas del Sistema
1. **Interfaz de Usuario (Main)**
   - Menús interactivos
   - Gestión de entrada/salida
   - Control de flujo

2. **Capa de Servicio**
   - Lógica de negocio
   - Validaciones
   - Gestión de transacciones

3. **Capa DAO**
   - Operaciones CRUD
   - Mapeo objeto-relacional
   - Gestión de conexiones

## ⚡ Funcionalidades Implementadas

### 🚙 Gestión de Coches
| Operación | Endpoint | Validaciones |
|-----------|----------|--------------|
| Añadir | `CarService.add()` | Matrícula única, formato NNNNLLL |
| Modificar | `CarService.update()` | Existencia, datos válidos |
| Eliminar | `CarService.delete()` | Existencia |
| Consultar | `CarService.findById()` | ID válido |
| Listar | `CarService.findAll()` | N/A |

### 👥 Gestión de Pasajeros
| Operación | Endpoint | Validaciones |
|-----------|----------|--------------|
| Añadir | `PassengerService.add()` | Datos completos |
| Modificar | `PassengerService.update()` | Existencia, datos válidos |
| Eliminar | `PassengerService.delete()` | Existencia |
| Consultar | `PassengerService.findById()` | ID válido |
| Asignar a Coche | `PassengerService.addToCar()` | Capacidad <= 5 |
| Eliminar de Coche | `PassengerService.removeFromCar()` | Existencia relación |

### ✅ Validaciones Implementadas
- **Coches**
   - Matrícula: formato NNNNLLL
   - Marca/Modelo: no vacíos
   - ID: positivo

- **Pasajeros**
   - Edad: no negativa
   - Peso: > 0
   - Nombre: no vacío

- **Relaciones**
   - Máximo 5 pasajeros por coche
   - Control de existencia previa
   - Validación de IDs

## ⚙️ Decisiones Técnicas

### 🛠 Características Técnicas
- Excepciones personalizadas
- Manejo de recursos con try-with-resources
- Validaciones centralizadas
- Mensajes de error constantes

## 💾 Base de Datos

### 📊 Modelo de Datos
```sql
CREATE TABLE cars (
    id INT PRIMARY KEY AUTO_INCREMENT,
    license_plate VARCHAR(7) UNIQUE,
    brand VARCHAR(50),
    model VARCHAR(50),
    color VARCHAR(30)
);

CREATE TABLE passengers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    age INT,
    weight DOUBLE
);

CREATE TABLE car_passengers (
    car_id INT,
    passenger_id INT,
    PRIMARY KEY (car_id, passenger_id),
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (passenger_id) REFERENCES passengers(id)
);
```

### 🔐 Configuración de Conexión
```properties
url=jdbc:mysql://localhost:3306/concesionario
user=root
password=
```

## 📥 Instalación y Configuración

1. Clonar el repositorio
```bash
git clone https://github.com/MaciWP/AE-3. Manejo de conectores de BBDD.git
```

2. Configurar la base de datos
```bash
mysql -u root -p < script.sql
```

3. Compilar el proyecto
```bash
mvn clean install
```

4. Ejecutar la aplicación
```bash
java -jar target/concesionario.jar
```

## 📖 Uso del Sistema

### 🔄 Flujo Principal
1. Ejecutar la aplicación
2. Seleccionar opción del menú principal
3. Seguir las instrucciones en pantalla
4. Las operaciones confirmarán su éxito/fracaso

### ⌨️ Comandos Disponibles
```
1. Añadir nuevo coche
2. Borrar coche por ID
3. Consultar coche por ID
4. Modificar coche por ID
5. Listado de coches
6. Gestión de pasajeros
7. Terminar el programa
```

### 🎛️ Submenú de Pasajeros
```
1. Añadir nuevo pasajero
2. Borrar pasajero por ID
3. Consultar pasajero por ID
4. Listar todos los pasajeros
5. Añadir pasajero a coche
6. Eliminar pasajero de coche
7. Listar pasajeros de un coche
8. Volver al menú principal
```

---

## ✒️ Autores
* **Oriol Macias Badosa** - *Desarrollador* - [MaciWP](https://github.com/MaciWP)