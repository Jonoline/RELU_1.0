import java.util.Scanner;

public class reservasLogias {
    private static final String[][] horas = new String[10][6];
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        llenarDias();
        llenarHoras();
        llenarHorarios();
        leerMatriz(horas);
    }

    public static void llenarDias() {
        horas[0][0] = "";
        horas[0][1] = "lunes";
        horas[0][2] = "martes";
        horas[0][3] = "miercoles";
        horas[0][4] = "jueves";
        horas[0][5] = "viernes";
    }

    public static void llenarHoras() {
        horas[1][0] = "8:30-9:30";
        horas[2][0] = "9:40-10:40";
        horas[3][0] = "10:50-11:50";
        horas[4][0] = "12:00-13:00";
        horas[5][0] = "13:10-14:10";
        horas[6][0] = "14:30-15:30";
        horas[7][0] = "15:40-16:40";
        horas[8][0] = "16:50-17:50";
        horas[9][0] = "18:00-19:00";
    }

    public static void llenarHorarios() {
        int filas = horas.length;
        int columnas = horas[1].length;
        for (int i = 1; i < filas; i++) {
            for (int j = 1; j < columnas; j++) {
                horas[i][j] = "1";
            }
        }
    }

    public static void leerMatriz(String[][] resultado) {
        System.out.println("\nMatriz Resultante:");
        for (String[] fila : resultado) {
            for (String val : fila) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    public static void iniciarReserva() {
        Menu.mostrarOpcionesDias();
        int opcion = obtenerOpcionDia();
        if (validarOpcionDia(opcion)) {
            ejecutarOpcionDia(opcion);
        } else {
            System.out.println("⚠️ Día inválido, vuelve a intentarlo.");
            return; // 🚫 No continúa si el día no es válido
        }

        // Solo llega aquí si el día fue válido
        Menu.mostrarOpcionesHoras();
        int hora = obtenerOpcionHora();
        if (validarOpcionHora(hora)) {
            ejecutarOpcionHora(hora);
        } else {
            System.out.println("⚠️ Hora inválida, vuelve a intentarlo.");
            return; // 🚫 No continúa si la hora no es válida
        }

        // Aquí puedes continuar con la lógica de reserva (guardar, confirmar, etc.)
        System.out.println("✅ Reserva registrada con éxito.");
    }


    public static int obtenerOpcionDia() {

        /* Obtener opcion de Día del usuario */

        System.out.print("Ingrese opción de día: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean validarOpcionDia(int opcion) {
        return opcion >= 1 && opcion <= 5;
    }


    public static void ejecutarOpcionDia(int opcion) {

        /* Ejecutar opción ingresada del día */
        int opcionDiaSeleccionada = opcion;
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

    public static int obtenerOpcionHora() {

            /* Aquí, después de mostrar el menu, se la va a pedir
               al usuario que ingrese una opción */

        System.out.print("Ingrese opción de hora: ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean validarOpcionHora(int opcion) {

        return opcion >= 1 && opcion <= 6;
    }

    public static void ejecutarOpcionHora(int opcion) {

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
