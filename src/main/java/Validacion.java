import java.util.Scanner;
import java.util.ArrayList;


public class Validacion {
    private static final Scanner sc = new Scanner(System.in);
    static ArrayList<String> datos = new ArrayList<>(1);

    // Método que encapsula el flujo de inicio de sesión
    public static boolean iniciarSesion() {
        System.out.println("====== Iniciar Sesión ======");

        /* se añaden datos a la array como base de datos de ejemplo*/
        datos.add("21797495k23");
        datos.add("21437088323");
        datos.add("21697810223");
        datos.add("21717876223");

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
        Usuario a = new Usuario("Ana Pérez", "25673725921", "clave12345");
        Usuario b = new Usuario("Juan", "25678765922", "clave1234");

        return datos.contains(matricula);
    }

    private static boolean validarContrasena(String contrasena) {
        return true; // Simulación
    }

    private static void mensajeConfirmacion() {
        System.out.println("Acceso Confirmado ✅");
    }
}
