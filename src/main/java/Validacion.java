import java.util.Scanner;

public class Validacion {
    private static final Scanner sc = new Scanner(System.in);

    // Método que encapsula el flujo de inicio de sesión
    public static boolean iniciarSesion() {
        System.out.println("====== Iniciar Sesión ======");

        /* Aquí se está preguntando al usuario de ingresar matrícula */
        String matricula = leerMatricula();

        /* Aquí se está preguntando al usuario de ingresar contraseña */
        String contrasena = leerContrasena();

        /* Aquí se está "validando" la matrícula ingresada (simulado, siempre true) */
        boolean matriculaValida = validarMatricula(matricula);

        /* Aquí se está "validando" la contraseña ingresada (simulado, siempre true) */
        boolean contrasenaValida = validarContrasena(contrasena);

        if (matriculaValida && contrasenaValida) {
            /* Aquí se muestra un mensaje de confirmación de acceso */
            mensajeConfirmacion();
            return true;
        } else {
            System.out.println("Credenciales inválidas 🚫");
            return false;
        }
    }

    private static String leerMatricula() {
        System.out.println("Ingrese su matrícula:");
        return sc.nextLine();
    }

    private static String leerContrasena() {
        System.out.println("Ingrese su contraseña:");
        return sc.nextLine();
    }

    private static boolean validarMatricula(String matricula) {
        return true; // Simulación
    }

    private static boolean validarContrasena(String contrasena) {
        return true; // Simulación
    }

    private static void mensajeConfirmacion() {
        System.out.println("Acceso Confirmado ✅");
    }
}
