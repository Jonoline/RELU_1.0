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


    public Menu(Usuario usuarioLogueado) {
        this.gestorReservas = new GestorReservas(usuarioLogueado);
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
            /* OPCIONES NO IMPLEMENADAS CAMBIANDO TODO EL RATO */
            case 1 -> manejarAgendarLogia();
            case 2 -> cancelarReserva();
            case 3 -> obtenerReserva();
            case 4 -> System.out.println("cerrar sesión");
            case 5 -> System.out.println("Saliendo del programa...");
            default -> System.out.println("Opción inválida...");
        }
    }

    private Logia obtenerLogia(){
        System.out.println("Ingrese el ID de la logia: ");
        gestorLogias.mostrarLogias();
        String id = sc.nextLine();
        return gestorLogias.obtenerLogia(id.toUpperCase());
    }

    private void manejarAgendarLogia(){
        try{
            int dia = obtenerDia();
            String mes = obtenerMes();
            Logia logia = obtenerLogia();
            Integer[] horasYminutos = formarHorasYminutos(obtenerHora(logia,dia,mes));
            gestorReservas.agregarReserva(logia,FormarFecha(dia,mes,horasYminutos));
            System.out.println("Reserva realizada con éxito");
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
    }

    private String obtenerMes() {
        System.out.println("Ingrese el mes de la reserva: ");
        String mesIngresado = sc.nextLine().trim();
        return Mes.obtenerMes(mesIngresado).getNumeroMes();
    }

    private int obtenerDia(){
        System.out.println("Ingrese el dia de la reserva: ");
        int dia;
        try {
            dia = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingrese un numero valido");
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

    private Integer[] formarHorasYminutos(String horaInicio){
        Integer[] partes = new Integer[2];
        String[] partesHora = horaInicio.split(":");
        partes[0] = Integer.parseInt(partesHora[0]);
        partes[1] = Integer.parseInt(partesHora[1]);
        return partes;
    }

    private LocalDateTime FormarFecha(int dia, String mes, Integer[] horasYminutos){
        LocalDateTime fecha = LocalDateTime.of(LocalDateTime.now().getYear(), Integer.parseInt(mes), dia, horasYminutos[0],horasYminutos[1]);
        if (fecha.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("La fecha seleccionada no puede ser anterior a la fecha actual");
        }
        return fecha;
    }

    private void cancelarReserva() {
        if (gestorReservas.CancelarReserva()) {
            System.out.println("Reserva cancelada");
        } else {
            System.out.println("Usted no tiene una reserva activa");
        }
    }

    private void obtenerReserva(){
        if (gestorReservas.getReservaUsuario() != null){
            System.out.println(gestorReservas.getReservaUsuario());
        } else {
            System.out.println("Usted no tiene una reserva activa");
        }
    }
}
