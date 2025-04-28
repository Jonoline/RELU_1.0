import java.util.Scanner;

public class Menu {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        reservasLogias.main(new String[0]);
        // Primero validar usuario
        boolean accesoPermitido = Validacion.iniciarSesion();
        // TODO: Implementar la lógica inicial y llamada a los métodos necesarios.
        if (accesoPermitido) {
            menu();
        } else {
            System.out.println("Acceso Denegado. 🚫 Saliendo del sistema.");
        }

    }

    public static void menu() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = obtenerOpcion(0);
            if (opcion==4) {
                ejecutarOpcion(opcion);
                break;
            }
            else if (opcion !=-1) {
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
        System.out.println("    [4] Cerrar sesión");
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
            case 1 -> reservasLogias.iniciarReserva();
            case 2 -> reservasLogias.eliminarReserva(Validacion.matriculaGuardada);
            case 3 -> System.out.println("Opcion 3 ");
            case 4 -> cerrarSesion();
            case 5 -> System.out.println("saliendo del programa...");
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
        System.out.println("[1]   8:30-9:30\n" +
                "[2]   9:40-10:40\n" +
                "[3]  10:50-11:50\n" +
                "[4]  12:00-13:00\n" +
                "[5]  13:10-14:10\n" +
                "[6]  14:30-15:30\n" +
                "[7]  15:40-16:40\n" +
                "[8]  16:50-17:50\n" +
                "[9]  18:00-19:00");
    }
    private static void cerrarSesion(){
        boolean accesoPermitido = Validacion.iniciarSesion();
        if (accesoPermitido) {
            menu();
        } else {
            System.out.println("Acceso Denegado. 🚫 Saliendo del sistema.");
        }
    }
}
