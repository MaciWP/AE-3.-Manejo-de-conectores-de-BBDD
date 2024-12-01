# 🚗 Sistema de Gestión de Concesionario

![Java Badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Lombok Badge](https://img.shields.io/badge/Lombok-FF0000?style=for-the-badge&logo=lombok&logoColor=white)

## 📑 Índice
1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Características](#características)
3. [Arquitectura](#arquitectura)
4. [Uso](#uso)
5. [Persistencia de Datos](#persistencia-de-datos)
6. [Estructura del Proyecto](#estructura-del-proyecto)
7. [Tecnologías Utilizadas](#tecnologías-utilizadas)
8. [Consideraciones de Diseño](#consideraciones-de-diseño)

## Descripción del Proyecto
El Sistema de Gestión de Concesionario es una aplicación Java diseñada para administrar el inventario de vehículos en un concesionario.

## Características

### Gestión de Vehículos
- ✨ Alta de nuevos vehículos con validación completa
- 🔍 Búsqueda de vehículos por ID
- 📋 Listado completo del inventario
- 🗑️ Eliminación de vehículos por ID
- 📤 Exportación de datos a formato CSV
- 💾 Persistencia automática de datos

### Validaciones Implementadas
- Control de IDs duplicados
- Verificación de matrículas únicas
- Validación de campos obligatorios
- Comprobación de formato de datos

## Arquitectura

### Capas del Sistema
1. **Capa de Presentación**
    - Interfaz de línea de comandos
    - Gestión de entrada/salida de usuario
    - Manejo de menús y opciones

2. **Capa de Servicio**
    - Lógica de negocio
    - Validaciones de datos
    - Gestión de operaciones CRUD

3. **Capa de Modelo**
    - Entidades de datos
    - Serialización para persistencia

4. **Capa de Utilidades**
    - Constantes del sistema
    - Mensajes centralizados
    - Configuración común

### Componentes Principales
- **Main.java**: Punto de entrada y UI
- **CarService.java**: Servicios y lógica de negocio
- **Car.java**: Modelo de datos con Lombok
- **Constants.java**: Configuración y mensajes


## Uso

### Menú Principal
El sistema ofrece las siguientes opciones:
1. Añadir nuevo coche
2. Borrar coche por ID
3. Consultar coche por ID
4. Listado de coches
5. Exportar coches a CSV
6. Terminar el programa

### Operaciones Principales
- **Alta de Vehículos**: Introduce datos completos del vehículo
- **Consulta**: Búsqueda por ID con resultados detallados
- **Listado**: Visualización de todo el inventario
- **Eliminación**: Borrado seguro con confirmación
- **Exportación**: Generación de archivo CSV

## Persistencia de Datos

### Almacenamiento Binario
- Archivo: `coches.dat`
- Serialización Java
- Carga automática al inicio
- Guardado automático al cerrar

### Exportación CSV
- Archivo: `coches.csv`
- Formato: ID;Matrícula;Marca;Modelo;Color
- Separador configurable
- Cabeceras incluidas

## Estructura del Proyecto

### Organización de Paquetes
```
src/
├── main/
│   ├── model/
│   │   └── Car.java
│   ├── service/
│   │   └── CarService.java
│   ├── utils/
│   │   └── Constants.java
│   └── Main.java
```

## Tecnologías Utilizadas

### Core
- Lombok para reducción de código boilerplate
- Serialización Java para persistencia

### Bibliotecas Destacadas
1. **Lombok**
    - @Data para getters/setters automáticos
    - @Log para logging incorporado
    - @AllArgsConstructor/@NoArgsConstructor

2. **Java Standard**
    - java.io para operaciones de archivo
    - java.util para colecciones
    - Scanner para entrada de usuario

## Consideraciones de Diseño

### Patrones Implementados
1. **Patrón Servicio**
    - Encapsulamiento de lógica de negocio
    - Separación de responsabilidades
    - Facilidad de mantenimiento

2. **Singleton Implícito**
    - Instancia única de CarService
    - Gestión centralizada de datos

---