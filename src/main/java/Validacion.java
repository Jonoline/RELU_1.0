import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;

public class Validacion {
    private static final Scanner sc = new Scanner(System.in);
    static ArrayList<String> datos = new ArrayList<>(1);

    public static boolean iniciarSesion(){
        /*Datos para simular una base de datos */
        datos.add("21797495k23");
        datos.add("21437088323");
        datos.add("21697810223");
        datos.add("21717876223");

        System.out.println("====== Iniciar Sesión ======");
        boolean matriculaValida = validarMatricula(leerMatricula());
        boolean contrasenaValida = validarContrasena(leerContrasena());
        if (matriculaValida && contrasenaValida){
            System.out.println("Acesso confirmado");
            return true;
        } else {
            System.out.println("credenciales invalidas");
            return false;
        }


    }
    public static String leerMatricula(){
        System.out.println("ingrese su matrícula: ");
        return sc.nextLine();
    }
    private static String leerContrasena(){
        System.out.println("ingrese su contraseña:");
        return sc.nextLine();
    }
    public static Boolean validarMatricula(String matricula){
        String regex = "^[0-9]{8}[0-9Kk][0-9]{2}";
        boolean dato = datos.contains(matricula);

        return dato && Pattern.matches(regex,matricula);
    }
    private static Boolean validarContrasena(String contrasena){
        return true;
    }

}
