package ui;

import java.time.LocalDateTime;
import datos.*;
import logica.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuAdmin {
    private final Scanner sc = new Scanner(System.in);
    private final GestorReservas gestorReservas;
    private final GestorLogias gestorLogias = new GestorLogias();
    private final GestorAdmin gestoradmin;
    private final GestorUsuarios gestorUsuarios = new GestorUsuarios();
    private final Json json = new Json();


    public MenuAdmin(Usuario usuarioLogueado) {
        this.gestorReservas = new GestorReservas(usuarioLogueado);
        this.gestoradmin = new GestorAdmin();
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
            if (opcion == 8) {
                System.out.println("Cerrando sesión...");
                continuar = false;  // Terminar el bucle
            } else if (opcion == 9) {
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
        System.out.println("         RELU - ADMIN          ");
        System.out.println("          Opciones      ");
        System.out.println("============================= ");
        System.out.println("    [1] Agendar Logia a usuario");
        System.out.println("    [2] Eliminar Reserva de usuario");
        System.out.println("    [3] Buscar Reserva de usuario ");
        System.out.println("    [4] Agregar Usuario");
        System.out.println("    [5] Eliminar Usuario");
        System.out.println("    [6] Agregar Logia");
        System.out.println("    [7] Habilitar/Deshabilitar Logia");
        System.out.println("    [8] Cerrar sesión");
        System.out.println("    [9] Salir");


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
            case 4 -> manejarAgregarUsuario();
            case 5 -> manejarEliminarUsuario();
            case 6 -> manejarAgregarLogia();
            case 7 -> manejarHabilitarDeshabilitarLogias();
            case 8 -> System.out.println("Cerrar sesión");
            case 9 -> System.out.println("Saliendo del programa...");
            default -> System.out.println("Opción inválida...");
        }
    }

    private Logia obtenerLogia(){
        gestorLogias.mostrarLogias();
        System.out.println("Ingrese el [ID] de la logia: ");
        String id = sc.nextLine().toUpperCase().trim();
        Logia logia = gestorLogias.obtenerLogia(id);
        verificarLogia(logia, id);
        return logia;
    }


    private void manejarAgendarLogia(){
        try{
            String matricula = obtenerMatricula();
            if (gestoradmin.buscarReserva(matricula) != null) {
                throw new IllegalArgumentException("El usuario ya tiene una reserva activa");
            }
            Usuario usuario = gestorUsuarios.buscarUsuario(matricula);
            int dia = obtenerDia();
            String mes = obtenerMes();
            Logia logia = obtenerLogia();
            Integer[] horasYminutos = formarHorasYminutos(obtenerHora(logia,dia,mes));
            gestoradmin.agregarReserva(usuario,logia,FormarFecha(dia,mes,horasYminutos));
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
        Usuario usuario = gestorUsuarios.buscarUsuario(obtenerMatricula());
        if (gestoradmin.CancelarReserva(usuario)) {
            System.out.println("Reserva cancelada");
        } else {
            System.out.println("Usuario no tiene una reserva activa");
        }
    }


    private void obtenerReserva(){
        String matricula = obtenerMatricula();
        if (gestoradmin.buscarReserva(matricula) != null){
            System.out.println(gestoradmin.buscarReserva(matricula).toString());
        } else {
            System.out.println("El usuario no tiene una reserva activa");
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


    private void verificarLogia(Logia logia, String id){
        if (logia == null) {
            throw new IllegalArgumentException("La logia con ID " + id + " no existe");
        }
        // Verificar si la logia está habilitada
        if (!logia.getHabilitada()) {
            throw new IllegalArgumentException("La logia " + id + " no está habilitada actualmente");
        }

    }


    private String obtenerMatricula(){
        System.out.println("Ingrese la matricula del usuario: ");
        String matricula = sc.nextLine().trim().toLowerCase();
        if (matricula.isEmpty() || !matricula.matches("\\d{8}[0-9k]\\d{2}")) {
            throw new IllegalArgumentException("Por favor ingrese una matricula valida (ej: 12345678k02)");
        }
        return matricula;
    }


    private String obtenerCorreo(){
        System.out.println("Ingrese el correo del usuario: ");
        String correo = sc.nextLine().trim();
        if (correo.isEmpty() || !correo.matches("^[\\w.-]+@(ufromail|ufrontera|ufroadmin)\\.cl$")) {
            throw new IllegalArgumentException("Por favor ingrese un correo valido (ej: <EMAIL>)");
        }
        return correo;
    }


    private String obtenerContrasenia(){
        System.out.println("Ingrese una contraseña para el usuario: ");
        String contra = sc.nextLine().trim();
        if (contra.isEmpty()){
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        return contra;
    }


    private void manejarAgregarUsuario(){
        try{
        String matricula = obtenerMatricula();
        String correo = obtenerCorreo();
        String contrasenia = obtenerContrasenia();
        gestoradmin.agregarUsuario(matricula, correo, contrasenia);
            System.out.println("Usuario agregado correctamente");
        } catch (IllegalArgumentException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private void manejarEliminarUsuario(){
        try{
        String matricula = obtenerMatricula();
        gestoradmin.eliminarUsuario(matricula);
        System.out.println("Usuario eliminado correctamente junto con su reserva");
        } catch (IllegalArgumentException e){
            System.out.println("Error: "+e.getMessage());
        }
    }


    private int obtenerCapacidadLogia() {
        System.out.println("Ingrese la capacidad de la logia (4 o 6 personas): ");
        try {
            int capacidad = Integer.parseInt(sc.nextLine().trim());
            if (capacidad != 4 && capacidad != 6) {
                throw new IllegalArgumentException("La capacidad solo puede ser 4 o 6 personas");
            }
            return capacidad;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La capacidad debe ser un número válido");
        }
    }
    private String obtenerPisoLogia() {
        System.out.println("Ingrese el piso de la logia (ejemplo: P2): ");
        String piso = sc.nextLine().trim().toUpperCase();
        if (!piso.matches("^P[2-3]$")) {
            throw new IllegalArgumentException("El piso debe tener el formato 'P' seguido del número (P2 o P3)");
        }
        return piso;
    }

    private void manejarAgregarLogia(){
        try {
            int capacidad = obtenerCapacidadLogia();
            String piso = obtenerPisoLogia();

            gestorLogias.agregarLogiaADMIN(capacidad, piso);
            System.out.println("Logia agregada correctamente");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private String obtenerIdLogia() {
        System.out.println("Ingrese el ID de la logia (ejemplo: L1): ");
        String id = sc.nextLine().trim().toUpperCase();
        if (!id.matches("^L\\d+$")) {
            throw new IllegalArgumentException("El ID debe tener el formato 'L' seguido de números (ejemplo: L1, L2, L10)");
        }
        return id;
    }

    private void manejarHabilitarDeshabilitarLogias() {
        try {
            // Mostrar todas las logias con su estado actual
            System.out.println("\nEstado actual de las logias:");
            for (Logia l : gestorLogias.obtenerTodasLasLogias()) {
                System.out.println("[" + l.getID() + "] " +
                        " - Estado: " + (l.getHabilitada() ? "Habilitada" : "Deshabilitada")); // condicion ? valorTRUE : valorFALSE
            }
            String id = obtenerIdLogia();
            Logia logia = gestorLogias.obtenerLogia(id);
            if (logia == null) {
                throw new IllegalArgumentException("No existe una logia con el ID: " + id);
            }
            printsmanejologia(id, logia);
            switchmanejologia(obtenerOpcion(), id, logia);
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor ingrese un número válido");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printsmanejologia(String id, Logia logia ){
        System.out.println("Estado actual de la logia " + id + ": " +
                (logia.getHabilitada() ? "Habilitada" : "Deshabilitada"));
        System.out.println("\n¿Qué desea hacer?");
        System.out.println("1. Habilitar");
        System.out.println("2. Deshabilitar");
        System.out.print("Opción: ");
    }

    private void switchmanejologia(int opcion, String id, Logia logia){
        switch (opcion) {
            case 1 -> {
                if (logia.getHabilitada()) {
                    System.out.println("La logia ya está habilitada");
                } else {
                    gestorLogias.habilitarLogiaADMIN(id);
                    System.out.println("Logia " + id + " habilitada correctamente");
                }
            }
            case 2 -> {
                if (!logia.getHabilitada()) {
                    System.out.println("La logia ya está deshabilitada");
                } else {
                    gestorLogias.deshabilitarLogiaADMIN(id);
                    System.out.println("Logia " + id + " deshabilitada correctamente");
                }
            }
            default -> throw new IllegalArgumentException("Opción inválida");
        }
    }
}
