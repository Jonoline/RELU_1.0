package logica;

import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;

public class Validacion {
    private static final Scanner sc = new Scanner(System.in);
    static ArrayList<String> datos = new ArrayList<>(1);

    public static String matriculaGuardada;

    static{/*Datos para simular una base de datos */
        datos.add("21797495k23");
        datos.add("21437088323");
        datos.add("21697810223");
        datos.add("21717876223");
    }

    public static boolean iniciarSesion() {
        matriculaGuardada = leerMatricula();
        // si NO valida la matricula "matricula invalida"
        if (!validarMatricula(matriculaGuardada)) {
            System.out.println("Matricula invalida");
            return false;
        }
        if (!validarContrasena(leerContrasena())) {
            System.out.println("Contraseña invalida");
            return false;
        }
        System.out.println("Acceso confirmado \n");
        return true;
    }
    public static String leerMatricula(){
        System.out.println("Ingrese su matrícula: ");
        return sc.nextLine();
    }
    private static String leerContrasena(){
        System.out.println("Ingrese su contraseña:");
        return sc.nextLine();
    }
    public static Boolean validarMatricula(String matricula){
        String regex = "^[0-9]{8}[0-9kK][0-9]{2}";
        boolean dato = datos.contains(matricula.toLowerCase());

        return dato && Pattern.matches(regex,matricula);
    }
    private static Boolean validarContrasena(String contrasena){
        return true;
    }
}
