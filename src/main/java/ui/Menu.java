package ui;

import datos.Usuario;
import logica.GestorReservas;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final GestorReservas gestorReservas;
    private final Usuario usuarioLogueado;


    public Menu(Usuario usuarioLogueado) {
        this.gestorReservas = new GestorReservas(usuarioLogueado);
        this.usuarioLogueado = usuarioLogueado;
    }

    public void iniciar() {
        System.out.println("\n=== BIENVENIDO A RELU, " + usuarioLogueado.getUfromail() + " ===");
        menu();
    }

        public void menu() {
        int opcion;
        boolean continuar = true;
        do {
            mostrarOpciones();
            opcion = obtenerOpcion();
            if (opcion == 4) {
                System.out.println("Cerrando sesión...");
                continuar = false;  // Terminar el bucle
            } else if (opcion == 5) {
                System.out.println("Saliendo del programa...");
                System.exit(0);  // Terminar el programa
            } else if (opcion != -1) {
                ejecutarOpcion(opcion);
            }
        } while (continuar);
    }


    public void mostrarOpciones() {
        System.out.println("=============================");
        System.out.println("            RELU            ");
        System.out.println("          Opciones      ");
        System.out.println("============================= ");
        System.out.println("    [1] Agendar Logia");
        System.out.println("    [2] Eliminar Reserva de Logia");
        System.out.println("    [3] Ver Logias");
        System.out.println("    [4] Cerrar sesión");
        System.out.println("    [5] Salir");


        System.out.println("============================= ");
        System.out.print("      Opcion: ");
    }

    public int obtenerOpcion() {
        int opcion;
        try {
            opcion = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un numero valido");
            return -1; // Devolver un valor inválido para que no ejecute ninguna opción
        }
        return opcion;
    }
    public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            /* OPCIONES NO IMPLEMENADAS CAMBIANDO TODO EL RATO */
            case 1 -> System.out.println("agendar logia");
            case 2 -> System.out.println("ver detalle");
            case 3 -> System.out.println("cancelar reserva");
            case 4 -> System.out.println("cerrar sesión");
            case 5 -> System.out.println("Saliendo del programa...");
            default -> System.out.println("Opción inválida...");
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


    // ----- Repetimos misma estructura para horas -----

    public void mostrarOpcionesHoras() {

        //Menu que muestra todas las horas posibles para reservar */

        System.out.println("🕒 Seleccione un horario:");
        System.out.println("""
                [1]   8:30-9:30
                [2]   9:40-10:40
                [3]  10:50-11:50
                [4]  12:00-13:00
                [5]  13:10-14:10
                [6]  14:30-15:30
                [7]  15:40-16:40
                [8]  16:50-17:50
                [9]  18:00-19:00""");
    }

}
