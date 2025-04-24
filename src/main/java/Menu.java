import java.util.Scanner;

public class Menu {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // TODO: Implementar la lógica inicial y llamada a los métodos necesarios.
        menu();
        sc.close();
        System.out.println("Terminado ");
    }

    public static void menu() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = obtenerOpcion(0);
            if (opcion != -1) {
                ejecutarOpcion(opcion);
            }
        } while (opcion != 6);
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
                System.out.println("Opcion 1 ");
            }
            case 2 -> System.out.println("Opcion 2 ");
            case 3 -> System.out.println("Opcion 3 ");
            case 4 -> System.out.println("Opcion 4 ");
            case 5 -> System.out.println("Opcion 5 ");
            default -> System.out.println("Opción inválida...");
        }
    }

    private static int opcionDiaSeleccionada;

    public void iniciarReserva() {
        mostrarOpcionesDias();
        int opcion = obtenerOpcionDia();
        if (validarOpcionDia(opcion)) {
            ejecutarOpcionDia(opcion);
        } else {
            System.out.println("⚠️ Día inválido, vuelve a intentarlo.");
        }

        // Aquí podrías seguir con horas:
        mostrarOpcionesHoras();
        int hora = obtenerOpcionHora();
        if (validarOpcionHora(hora)) {
            ejecutarOpcionHora(hora);
        } else {
            System.out.println("⚠️ Hora inválida, vuelve a intentarlo.");
        }
    }


    public void mostrarOpcionesDias() {

        /* Mostrar días que se pueden reservar Logias */

        System.out.println("📅 Seleccione un día:");
        System.out.println("[1] Lunes");
        System.out.println("[2] Martes");
        System.out.println("[3] Miércoles");
        System.out.println("[4] Jueves");
        System.out.println("[5] Viernes");
    }
    public int obtenerOpcionDia() {

        /* Obtener opcion de Día del usuario */

        System.out.print("Ingrese opción de día: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean validarOpcionDia(int opcion) {

        /* Validar la opcion de Día del usuario ingresada */

        return opcion >= 1 && opcion <= 5;
    }

    public void ejecutarOpcionDia(int opcion) {

        /* Ejecutar opción ingresada del día */

        opcionDiaSeleccionada = opcion;
        String dia = switch (opcion) {
            case 1 -> "Lunes";
            case 2 -> "Martes";
            case 3 -> "Miércoles";
            case 4 -> "Jueves";
            case 5 -> "Viernes";
            default -> "Desconocido";
        };
        System.out.println("✅ Día seleccionado: " + dia);
    }

    // ----- Repetimos misma estructura para horas -----

    public void mostrarOpcionesHoras() {

        /*Menu que muestra todas las horas posibles para reservar */

        System.out.println("🕒 Seleccione un horario:");
        System.out.println("[1] 08:30 - 10:00");
        System.out.println("[2] 10:00 - 11:30");
        System.out.println("[3] 11:30 - 13:00");
        System.out.println("[4] 14:30 - 16:00");
        System.out.println("[5] 16:00 - 17:30");
        System.out.println("[6] 17:30 - 19:00");
    }

    public int obtenerOpcionHora() {

            /* Aquí, después de vmostrar el menu, se la va a pedir
               al usuario que ingrese una opción */

        System.out.print("Ingrese opción de hora: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean validarOpcionHora(int opcion) {
        return opcion >= 1 && opcion <= 6;
    }

    public void ejecutarOpcionHora(int opcion) {

            /* Aquí, después de validar la opción ingresada, se va a
               mostrar la lista de opciones. */

        String hora = switch (opcion) {
            case 1 -> "08:30 - 10:00";
            case 2 -> "10:00 - 11:30";
            case 3 -> "11:30 - 13:00";
            case 4 -> "14:30 - 16:00";
            case 5 -> "16:00 - 17:30";
            case 6 -> "17:30 - 19:00";
            default -> "Desconocida";
        };
        System.out.println("✅ Hora seleccionada: " + hora);

    }
}
