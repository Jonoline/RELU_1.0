import java.util.Scanner;

public class Menu {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Primero validar usuario
        boolean accesoPermitido = Validacion.iniciarSesion();
        // TODO: Implementar la lógica inicial y llamada a los métodos necesarios.
        if (accesoPermitido) {
            // Si accede correctamente, continuar
            System.out.println("Ejecutando reservasLogias...");
            menu();
            reservasLogias.main(new String[0]);
        } else {
            System.out.println("Acceso Denegado. 🚫 Saliendo del sistema.");
        }

    }

    public static void menu() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = obtenerOpcion(0);
            if (opcion != -1) {
                ejecutarOpcion(opcion);
            }
        } while (opcion != 5);
    }

    public static void mostrarOpciones() {
        System.out.println("=============================");
        System.out.println("            RELU            ");
        System.out.println("          Opciones      ");
        System.out.println("============================= ");
        System.out.println("    [1] Agendar Reserva de Logias");
        System.out.println("    [2] Eliminar Reserva de Logias");
        System.out.println("    [3] Ver Logias Reservadas");
        System.out.println("    [4] Ver Historial Logias Reservadas");
        System.out.println("    [5] Salir");


        System.out.println("============================= ");
        System.out.print("      Opcion: ");
    }

    public static int obtenerOpcion(int opcion) {
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un numero valido");
            return -1; // Devolver un valor inválido para que no ejecute ninguna opción
        }
        return opcion;
    }

    public static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> {
                reservasLogias.iniciarReserva();
            }
            case 2 -> System.out.println("Opcion 2 ");
            case 3 -> System.out.println("Opcion 3 ");
            case 4 -> System.out.println("Opcion 4 ");
            case 5 -> System.out.println("Opcion 5 ");
            default -> System.out.println("Opción inválida...");
        }
    }

    public static void mostrarOpcionesDias() {

        /* Mostrar días que se pueden reservar Logias */

        System.out.println("📅 Seleccione un día:");
        System.out.println("[1] Lunes");
        System.out.println("[2] Martes");
        System.out.println("[3] Miércoles");
        System.out.println("[4] Jueves");
        System.out.println("[5] Viernes");
    }


    // ----- Repetimos misma estructura para horas -----

    public static void mostrarOpcionesHoras() {

        /*Menu que muestra todas las horas posibles para reservar */

        System.out.println("🕒 Seleccione un horario:");
        System.out.println("[1] 08:30 - 10:00");
        System.out.println("[2] 10:00 - 11:30");
        System.out.println("[3] 11:30 - 13:00");
        System.out.println("[4] 14:30 - 16:00");
        System.out.println("[5] 16:00 - 17:30");
        System.out.println("[6] 17:30 - 19:00");
    }
}
