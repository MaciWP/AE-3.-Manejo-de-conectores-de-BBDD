package utils;

/**
 * Utility class containing all application constants and messages
 */
public class Constants {

    // Menu options
    public static final int OPTION_ADD = 1;
    public static final int OPTION_DELETE = 2;
    public static final int OPTION_FIND = 3;
    public static final int OPTION_UPDATE = 4;
    public static final int OPTION_LIST = 5;
    public static final int OPTION_PASSENGER_MENU = 6;
    public static final int OPTION_EXIT = 7;

    // Passenger menu options
    public static final int PASSENGER_OPTION_ADD = 1;
    public static final int PASSENGER_OPTION_DELETE = 2;
    public static final int PASSENGER_OPTION_FIND = 3;
    public static final int PASSENGER_OPTION_LIST = 4;
    public static final int PASSENGER_OPTION_ADD_TO_CAR = 5;
    public static final int PASSENGER_OPTION_REMOVE_FROM_CAR = 6;
    public static final int PASSENGER_OPTION_LIST_BY_CAR = 7;
    public static final int PASSENGER_OPTION_RETURN = 8;

    // Menu texts
    public static final String MENU_TITLE = "\n=== GESTIÓN DE CONCESIONARIO ===";
    public static final String MENU_ADD = "1. Añadir nuevo coche";
    public static final String MENU_DELETE = "2. Borrar coche por ID";
    public static final String MENU_FIND = "3. Consultar coche por ID";
    public static final String MENU_UPDATE = "4. Modificar coche por ID";
    public static final String MENU_LIST = "5. Listado de coches";
    public static final String MENU_PASSENGER = "6. Gestión de pasajeros";
    public static final String MENU_EXIT = "7. Terminar el programa";
    public static final String MENU_OPTION = "Seleccione una opción: ";

    // Passenger menu texts
    public static final String PASSENGER_MENU_TITLE = "\n=== GESTIÓN DE PASAJEROS ===";
    public static final String PASSENGER_MENU_ADD = "1. Añadir nuevo pasajero";
    public static final String PASSENGER_MENU_DELETE = "2. Borrar pasajero por ID";
    public static final String PASSENGER_MENU_FIND = "3. Consultar pasajero por ID";
    public static final String PASSENGER_MENU_LIST = "4. Listar todos los pasajeros";
    public static final String PASSENGER_MENU_ADD_TO_CAR = "5. Añadir pasajero a coche";
    public static final String PASSENGER_MENU_REMOVE_FROM_CAR = "6. Eliminar pasajero de un coche";
    public static final String PASSENGER_MENU_LIST_BY_CAR = "7. Listar pasajeros de un coche";
    public static final String PASSENGER_MENU_RETURN = "8. Volver al menú principal";

    // Input prompts
    public static final String PROMPT_LICENSE = "Matrícula: ";
    public static final String PROMPT_BRAND = "Marca: ";
    public static final String PROMPT_MODEL = "Modelo: ";
    public static final String PROMPT_COLOR = "Color: ";
    public static final String PROMPT_DELETE_ID = "\nIntroduce el ID del coche a borrar: ";
    public static final String PROMPT_FIND_ID = "\nIntroduce el ID del coche a consultar: ";
    public static final String PROMPT_UPDATE_ID = "\nIntroduce el ID del coche a modificar: ";
    public static final String PROMPT_PASSENGER_NAME = "Nombre del pasajero: ";
    public static final String PROMPT_PASSENGER_AGE = "Edad del pasajero: ";
    public static final String PROMPT_PASSENGER_WEIGHT = "Peso del pasajero: ";
    public static final String PROMPT_PASSENGER_ID = "\nIntroduce el ID del pasajero: ";
    public static final String PROMPT_CAR_ID = "Introduce el ID del coche: ";
    public static final String PROMPT_ID_POSITIVE = "El ID debe ser positivo";
    public static final String PROMPT_NON_NEGATIVE_VALUE = "El valor no puede ser negativo";
    public static final String PROMPT_POSITIVE_VALUE = "El valor debe ser positivo";
    public static final String PROMPT_VALID_NUMBER = "Por favor, introduzca un número válido";
    public static final String PROMPT_RESOURCES_CLOSED = "Recursos cerrados correctamente.";

    // Error messages
    public static final String ERROR_NULL_PASSENGER_DAO = "El DAO de pasajeros no puede ser nulo";
    public static final String ERROR_NULL_CAR_DAO = "El DAO de coches no puede ser nulo";
    public static final String ERROR_MAX_CAPACITY = "El coche %d ha alcanzado su capacidad máxima de %d pasajeros";
    public static final String ERROR_DUPLICATE_LICENSE = "Ya existe un coche con esta matrícula: %s";
    public static final String ERROR_DATABASE = "Error en la base de datos: %s %s";
    public static final String ERROR_NULL_NAME = "El nombre no puede ser nulo";
    public static final String ERROR_NEGATIVE_AGE = "La edad no puede ser negativa";
    public static final String ERROR_ID_NUMBER = "Error: ID debe ser un número";
    public static final String ERROR_CAR_NOT_FOUND = "No se encontró ningún coche con ese ID";
    public static final String ERROR_NO_CARS = "No hay coches registrados";
    public static final String ERROR_INVALID_OPTION = "Opción no válida";
    public static final String ERROR_UPDATE_FAILED = "Error al actualizar el coche";
    public static final String ERROR_PASSENGER_NOT_FOUND = "No se encontró ningún pasajero con ese ID";
    public static final String ERROR_NO_PASSENGERS = "No hay pasajeros registrados";
    public static final String ERROR_INVALID_AGE = "La edad debe ser un número válido";
    public static final String ERROR_INVALID_WEIGHT = "El peso debe ser un número válido";
    public static final String ERROR_MESSAGE_PREFIX = "Error: ";
    public static final String ERROR_RESOURCES_CLOSING = "Error al cerrar los recursos.";
    public static final String ERROR_INSERT = "Error al insertar coche: ";
    public static final String ERROR_UPDATE = "Error al actualizar coche: ";
    public static final String ERROR_DELETE = "Error al eliminar coche con ID: ";
    public static final String ERROR_FIND = "Error al buscar coche con ID: ";
    public static final String ERROR_FIND_ALL = "Error al recuperar todos los coches";
    public static final String ERROR_LICENSE_PLATE = "Error al comprobar matrícula: ";
    public static final String ERROR_CREATE_NO_ROWS = "Error al crear el coche, ninguna fila afectada.";
    public static final String ERROR_CREATE_NO_ID = "Error al crear el coche, no se obtuvo ID.";
    public static final String ERROR_ADD_TO_CAR = "Error al añadir pasajero %d al coche %d";
    public static final String ERROR_REMOVE_FROM_CAR = "Error al eliminar pasajero %d del coche %d";
    public static final String ERROR_FIND_BY_CAR = "Error al buscar pasajeros del coche con ID: ";
    public static final String ERROR_UNEXPECTED = "Error inesperado: %s";


    // Success messages
    public static final String SUCCESS_CAR_ADDED = "Coche añadido correctamente";
    public static final String SUCCESS_CAR_DELETED = "Coche eliminado correctamente";
    public static final String SUCCESS_CAR_UPDATED = "Coche modificado correctamente";
    public static final String SUCCESS_EXIT = "¡Hasta pronto!";
    public static final String SUCCESS_PASSENGER_ADDED = "Pasajero añadido correctamente";
    public static final String SUCCESS_PASSENGER_DELETED = "Pasajero eliminado correctamente";
    public static final String SUCCESS_PASSENGER_ADDED_TO_CAR = "Pasajero añadido al coche correctamente";
    public static final String SUCCESS_PASSENGER_REMOVED_FROM_CAR = "Pasajero eliminado del coche correctamente";

    // Section titles
    public static final String TITLE_ADD_CAR = "\n=== AÑADIR NUEVO COCHE ===";
    public static final String TITLE_UPDATE_CAR = "\n=== MODIFICAR COCHE ===";
    public static final String TITLE_LIST_CARS = "\n=== LISTADO DE COCHES ===";
    public static final String TITLE_ADD_PASSENGER = "\n=== AÑADIR NUEVO PASAJERO ===";
    public static final String TITLE_LIST_PASSENGERS = "\n=== LISTADO DE PASAJEROS ===";
    public static final String TITLE_LIST_CAR_PASSENGERS = "\n=== PASAJEROS DEL COCHE (ID: %d) ===";
    public static final String TITLE_ADD_PASSENGER_TO_CAR = "\n=== AÑADIR PASAJERO A COCHE ===";
    public static final String TITLE_REMOVE_PASSENGER_FROM_CAR = "\n=== ELIMINAR PASAJERO DE COCHE ===";

    // Generic system messages
    public static final String NO_PASSENGERS_IN_CAR = "No hay pasajeros en este coche";
    public static final String CAR_PASSENGERS_HEADER = "Pasajeros:";
    public static final String AVAILABLE_CARS_TITLE = "\nCoches disponibles:";
    public static final String AVAILABLE_PASSENGERS_TITLE = "\nPasajeros disponibles:";

    // Validation constants
    public static final int MAX_PASSENGERS_PER_CAR = 5;
    public static final int MIN_AGE = 0;
    public static final double MIN_WEIGHT = 0.1;
    public static final String LICENSE_PLATE_REGEX = "^[0-9]{4}[A-Z]{3}$";

    // Database column names
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LICENSE_PLATE = "license_plate";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_WEIGHT = "weight";

    // Entities and fields
    public static final String ENTITY_CAR = "Coche";
    public static final String ENTITY_PASSENGER = "Pasajero";
    public static final String FIELD_LICENSE_PLATE = "matrícula";
    public static final String FIELD_ID = "ID";
    public static final String FIELD_BRAND = "marca";
    public static final String FIELD_MODEL = "modelo";
    public static final String FIELD_COLOR = "color";
    public static final String FIELD_NAME = "nombre";

    // Operations for messages
    public static final String OPERATION_ADD = "al añadir";
    public static final String OPERATION_UPDATE = "al actualizar";
    public static final String OPERATION_DELETE = "al eliminar";
    public static final String OPERATION_FIND = "al buscar";
    public static final String OPERATION_LIST = "al listar";
    public static final String OPERATION_ADD_TO_CAR = "al añadir al coche";
    public static final String OPERATION_REMOVE_FROM_CAR = "al eliminar del coche";

    // Validation error messages
    public static final String ERROR_NULL_CAR = "El coche no puede ser null";
    public static final String ERROR_NULL_ID = "El ID no puede ser null";
    public static final String ERROR_NULL_LICENSE = "La matrícula no puede ser null o vacía";
    public static final String ERROR_NULL_PASSENGER = "El pasajero no puede ser null";
    public static final String ERROR_NULL_BRAND = "La marca no puede ser nula";
    public static final String ERROR_NULL_MODEL = "El modelo no puede ser nulo";
    public static final String ERROR_NULL_COLOR = "El color no puede ser nulo";
    public static final String ERROR_EMPTY_NAME = "El nombre del pasajero no puede estar vacío";
    public static final String ERROR_NULL_DAO = "El DAO de coches no puede ser nulo";
    public static final String ERROR_NULL_FIELD = "El %s no puede ser nulo";
    public static final String ERROR_EMPTY_FIELD = "El %s no puede estar vacío";
    public static final String ERROR_INVALID_ID = "El ID debe ser positivo";
    public static final String ERROR_INVALID_LICENSE = "Formato de matrícula inválido. Debe ser 4 números seguidos de 3 letras en mayúsculas. NNNNLLL";

    // Display messages
    public static final String DISPLAY_AVAILABLE_CARS = "\nCoches disponibles:";
    public static final String DISPLAY_CURRENT_CAR_DATA = "\nDatos actuales del coche:";
    public static final String DISPLAY_NEW_CAR_DATA = "\nIntroduzca los nuevos datos:";
    public static final String DISPLAY_PASSENGER_INDENT = "  %s";
    public static final String DISPLAY_CAR_DETAILS = "\nCoche: %s";


    // Database connection messages
    public static final String DATABASE_CONNECTION_ESTABLISHED = "Conexión a base de datos establecida";
    public static final String DATABASE_CONNECTION_CLOSED = "Conexión a base de datos cerrada";
    public static final String DATABASE_CONNECTION_ERROR = "Error al cerrar la conexión: %s";

    private Constants() {
        // Prevent instantiation
    }
}
