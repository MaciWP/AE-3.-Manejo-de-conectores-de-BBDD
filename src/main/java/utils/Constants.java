package utils;

/**
 * Utility class containing all application constants and messages
 */
public class Constants {
    // Files
    public static final String DATA_FILE = "coches.dat";
    public static final String CSV_FILE = "coches.csv";
    public static final String CSV_HEADER = "ID;Matricula;Marca;Modelo;Color";

    // Menu options
    public static final int OPTION_ADD = 1;
    public static final int OPTION_DELETE = 2;
    public static final int OPTION_FIND = 3;
    public static final int OPTION_LIST = 4;
    public static final int OPTION_EXPORT = 5;
    public static final int OPTION_EXIT = 6;

    // Menu texts
    public static final String MENU_TITLE = "\n=== GESTIÓN DE CONCESIONARIO ===";
    public static final String MENU_ADD = "1. Añadir nuevo coche";
    public static final String MENU_DELETE = "2. Borrar coche por ID";
    public static final String MENU_FIND = "3. Consultar coche por ID";
    public static final String MENU_LIST = "4. Listado de coches";
    public static final String MENU_EXPORT = "5. Exportar coches a CSV";
    public static final String MENU_EXIT = "6. Terminar el programa";
    public static final String MENU_OPTION = "Seleccione una opción: ";

    // Input prompts
    public static final String PROMPT_LICENSE = "Matrícula: ";
    public static final String PROMPT_BRAND = "Marca: ";
    public static final String PROMPT_MODEL = "Modelo: ";
    public static final String PROMPT_COLOR = "Color: ";
    public static final String PROMPT_DELETE_ID = "\nIntroduce el ID del coche a borrar: ";
    public static final String PROMPT_FIND_ID = "\nIntroduce el ID del coche a consultar: ";

    // Error messages
    public static final String ERROR_ID_DUPLICATE = "Ya existe un coche con ese ID";
    public static final String ERROR_LICENSE_DUPLICATE = "Ya existe un coche con esa matrícula";
    public static final String ERROR_ID_NUMBER = "Error: ID debe ser un número";
    public static final String ERROR_CAR_NOT_FOUND = "No se encontró ningún coche con ese ID";
    public static final String ERROR_NO_CARS = "No hay coches registrados";
    public static final String ERROR_INVALID_OPTION = "Opción no válida";

    // Exception messages
    public static final String ERROR_SAVE_DATA = "Error al guardar los datos: ";
    public static final String ERROR_EXPORT_CSV = "Error al exportar a CSV: ";
    public static final String ERROR_LOAD_DATA = "Error al cargar los datos ";

    // Success messages
    public static final String SUCCESS_CAR_ADDED = "Coche añadido correctamente";
    public static final String SUCCESS_CAR_DELETED = "Coche eliminado correctamente";
    public static final String SUCCESS_CSV_EXPORT = "Coches exportados a CSV correctamente";
    public static final String SUCCESS_SAVE = "Datos guardados correctamente al cerrar el programa.";
    public static final String SUCCESS_EXIT = "¡Hasta pronto!";

    // Section titles
    public static final String TITLE_ADD_CAR = "\n=== AÑADIR NUEVO COCHE ===";
    public static final String TITLE_LIST_CARS = "\n=== LISTADO DE COCHES ===";

    // Validation messages
    public static final String ERROR_NULL_CAR = "El coche no puede ser null";
    public static final String ERROR_NULL_ID = "El ID no puede ser null";
    public static final String ERROR_NULL_LICENSE = "La matrícula no puede ser null o vacía";

    // Format patterns
    public static final String FORMAT_TO_STRING = "ID: %d, Matrícula: %s, Marca: %s, Modelo: %s, Color: %s";
    public static final String FORMAT_CSV = "%d;%s;%s;%s;%s";

    private Constants() {
    }
}