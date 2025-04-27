import java.util.Scanner;

public class Validacion {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        /* Aquí se está preguntando al usuario de ingresar matrícula */
        String matricula = leerMatricula();

        /* Aquí se está preguntando al usuario de ingresar contraseña */
        String contrasena = leerContrasena();

        /* Aquí se está "validando" la matrícula ingresada (simulado, siempre true) */
        final Boolean a = validarMatricula(matricula);

        /* Aquí se está "validando" la contraseña ingresada (simulado, siempre true) */
        final Boolean b = validarContrasena(contrasena);

        /* Aquí se muestra un mensaje de confirmación de acceso */
        mensajeConfirmacion();
    }

    /* Método para leer matrícula del usuario */
    private static String leerMatricula() {
        System.out.println("Ingrese su matrícula:");
        return sc.nextLine();
    }

    /* Método para leer contraseña del usuario */
    private static String leerContrasena() {
        System.out.println("Ingrese su contraseña:");
        return sc.nextLine();
    }

    /* Método para validar matrícula (siempre devuelve true porque es un prototipo) */
    private static Boolean validarMatricula(String matricula) {
        return true;
    }

    /* Método para validar contraseña (siempre devuelve true porque es un prototipo) */
    private static Boolean validarContrasena(String contrasena) {
        return true;
    }

    /* Método para mostrar un mensaje de confirmación */
    private static void mensajeConfirmacion() {
        System.out.println("Acceso Confirmado");
    }
}
