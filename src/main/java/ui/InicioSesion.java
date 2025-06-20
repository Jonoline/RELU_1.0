package ui;
import datos.Usuario;
import logica.GestorUsuarios;

import java.util.Scanner;

public class InicioSesion {
    Scanner sc = new Scanner(System.in);


    public void menu() {
        String opcion;
        do {
            mostrarOpciones();
            opcion = obtenerOpcion();
            ejecutarOpcion(opcion);
        } while (!opcion.equals("2"));
    }

    private void mostrarOpciones() {
        System.out.println("          LOGIN RELU     ");
        System.out.println("1. Iniciar sesión  ");
        System.out.println("2. Salir");
        System.out.println("Ingrese su opción: ");

        // TODO: Mostrar "1. Iniciar sesión", "2. Salir"
    }

    /**
     * Ejecuta la opción seleccionada por el usuario.
     *
     * @param opcion opción ingresada por el usuario
     */
    private void ejecutarOpcion(String opcion) {
        switch (opcion){
            case "1"-> {
                GestorUsuarios gestorUsuarios = new GestorUsuarios();
                Usuario intento = gestorUsuarios.iniciarSesion(pedirDatosLogin());
                ejecucionMenuPrincipal(intento);
            }

            case "2"-> System.out.println("Saliendo del programa...");
            default -> System.out.println("ingrese una opción valida");
        }
    }

    private String obtenerOpcion(){
        return sc.nextLine();
    }
    public Usuario pedirDatosLogin() {
        String matricula = obtenerUsuario();
        String contrasena = obtenerContrasenia();

        return new Usuario(matricula, contrasena); // correo vacío porque no es necesario para login
    }
    private void ejecucionMenuPrincipal(Usuario intento){
        if(intento != null){
            System.out.println("ejecutar menu");
        } else{
            System.out.println("usted no es parte de la ufro VAYASE");
        }

    }
    private String obtenerUsuario(){
        System.out.println("Ingrese su usuario: ");
        return sc.nextLine();
    }
    private String obtenerContrasenia(){
        System.out.println("Ingrese su contraseña: ");
        return sc.nextLine();
    }
}

