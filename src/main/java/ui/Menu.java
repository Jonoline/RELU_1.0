package ui;

import java.time.LocalDateTime;
import datos.*;
import logica.GestorLogias;
import logica.GestorReservas;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final GestorReservas gestorReservas;
    private final GestorLogias gestorLogias = new GestorLogias();
    private final Json json = new Json();
    private final Usuario usuarioLoqueado;


    public Menu(Usuario usuarioLogueado) {
        this.gestorReservas = new GestorReservas(usuarioLogueado);
        this.usuarioLoqueado = usuarioLogueado;
    }

    public void iniciar() {
        System.out.println("\n=== BIENVENIDO A RELU ===  ");
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
                json.crearBackups();
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
        System.out.println("    [3] Ver Reserva actual de Logia");
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
            case 1 -> {
                manejarAgendarLogia();
                delay(2000);

            }
            case 2 -> {
                cancelarReserva();
                delay();
            }
            case 3 -> {
                obtenerReserva();
                delay(4000);
            }
            case 4 -> System.out.println("Cerrar sesión");
            case 5 -> System.out.println("Saliendo del programa...");
            default -> System.out.println("Opción inválida...");
        }
    }

    private Logia obtenerLogia(){
        gestorLogias.mostrarLogias();
        System.out.println("Ingrese el [ID] de la logia: ");
        String id = sc.nextLine().toUpperCase().trim();
        Logia logia = gestorLogias.obtenerLogia(id);
        gestorLogias.verificarLogia(logia, id);
        return logia;
    }

    private void manejarAgendarLogia(){
        JavaTimeUtils timeUtils = new JavaTimeUtils();
        if (gestorReservas.getReservaUsuario() != null) {
            System.out.println("Usted ya tiene una reserva activa, si desea realizar otra debe cancelar la actual.");
            return;
        }

        try{
            String mes = obtenerMes();
            int dia = obtenerDia();
            Logia logia = obtenerLogia();
            Integer[] horasYminutos = timeUtils.formarHorasYminutos(obtenerHora(logia, dia, mes));
            LocalDateTime fecha = timeUtils.FormarFecha(dia,mes,horasYminutos);
            gestorReservas.agregarReserva(usuarioLoqueado,logia,fecha);
            System.out.println("Reserva realizada con éxito");
            } catch (IllegalArgumentException e){
                System.out.println("Error: "+e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado");
        }
    }

    private String obtenerMes() {
        System.out.println("Ingrese el mes de la reserva como texto (ej:junio): ");
        String mesIngresado = sc.nextLine().trim();
        return Mes.obtenerMes(mesIngresado).getNumeroMes();
    }

    private int obtenerDia(){
        System.out.println("Ingrese el número del dia de la reserva (ej: 2): ");
        int dia;
        try {
            dia = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Se debe ingresar el número del día que desea reservar");
        }
        return dia;
    }

    private String obtenerHora(Logia logia, int dia, String mes){
        System.out.println("🕒 Bloques horarios disponibles:");
        ArrayList<Horario> bloquesDisponibles = gestorReservas.obtenerBloquesDisponibles(logia, dia, mes);
        if (bloquesDisponibles.isEmpty()) {
            throw new IllegalArgumentException("No hay bloques horarios disponibles para esta logia en la fecha seleccionada");
        }
        for (Horario h : bloquesDisponibles) {
            System.out.println(h.toString());
        }
        int bloque;
        try {
            bloque = Integer.parseInt(sc.nextLine());
            Horario horarioSeleccionado = Horario.obtenerHorario(bloque);
            if (horarioSeleccionado == null || !bloquesDisponibles.contains(horarioSeleccionado)) {
                throw new IllegalArgumentException("Bloque no válido");
            }
            return horarioSeleccionado.getHoraInicio();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingrese un número válido");
        }
    }

    private void cancelarReserva() {
        if (gestorReservas.CancelarReserva(usuarioLoqueado)) {
            System.out.println("Reserva cancelada correctamente");
        } else {
            System.out.println("Usted no tiene una reserva activa para cancelar");
        }
    }

    private void obtenerReserva(){
        if (gestorReservas.getReservaUsuario() != null){
            System.out.println(gestorReservas.getReservaUsuario());
        } else {
            System.out.println("Usted no tiene una reserva activa");
        }
    }

    public void delay(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void delay(){
        delay(1000);
    }
}
