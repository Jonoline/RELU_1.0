import java.util.Scanner;

public class reservasLogias {
    private static String[][]horas= new String[10][6];
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        llenarDias();
        llenarHoras();
        llenarHorarios();
        seleccionarDiaHora();
        leerMatriz(horas);
        eliminarReserva();
        leerMatriz(horas);
    }
    public static void llenarDias(){
        horas[0][0]="";
        horas[0][1]="[1]lunes";
        horas[0][2]="[2]martes";
        horas[0][3]="[3]miercoles";
        horas[0][4]="[4]jueves";
        horas[0][5]="[5]viernes";
    }
    public static void llenarHoras(){
        horas[1][0]="[1]   8:30-9:30";
        horas[2][0]="[2]  9:40-10:40";
        horas[3][0]="[3] 10:50-11:50";
        horas[4][0]="[4] 12:00-13:00";
        horas[5][0]="[5] 13:10-14:10";
        horas[6][0]="[6] 14:30-15:30";
        horas[7][0]="[7] 15:40-16:40";
        horas[8][0]="[8] 16:50-17:50";
        horas[9][0]="[9] 18:00-19:00";
    }
    public static void llenarHorarios(){
        int filas = horas.length;
        int columnas = horas[1].length;
        for(int i = 1; i < filas ;i++){
            for(int j = 1;j < columnas;j++){
                horas[i][j] = "1";
            }
        }
    }
    public static void leerMatriz(String[][] resultado) {
        System.out.println("\nMatriz Resultante:");
        for (String[] fila : resultado) {
            for (String val : fila) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    public static int obtenerHora() {
        int hora = 0;
        System.out.println("ingrese el numero indicado en corchetes de la hora deseada");
        try {
            hora = sc.nextInt();
        } catch (NumberFormatException e) {
            System.out.println("ingrese un numero valido");
        }
        return hora;
    }
    private static boolean validarHora(int hora){
        return hora >= 1 && hora <=9;
    }
    public static int obtenerDia() {
        int dia = 0;
        System.out.println("ingrese el numero indicado en corchetes del dia deseado");
        try {
            dia = sc.nextInt();
        } catch (NumberFormatException e) {
            System.out.println("ingrese un numero valido");
        }
        return dia;
    }
    private static boolean validarDia(int dia){
        return dia >= 1 && dia <=5;
    }
    private static void seleccionarDiaHora() {
        int dia = obtenerDia();
        int hora = obtenerHora();

        while (!validarDia(dia)) {
            System.out.println("Dia fuera de rango intente de nuevo");
            dia = obtenerDia();
        }
        while (!validarHora(hora)) {
            System.out.println("Hora fuera de rango intente de nuevo");
            hora = obtenerHora();


        }

        horas[hora][dia]=Validacion.leerMatricula();

        /*switch (hora){
            case 1 -> horas[1][0]="21797495k23";
            case 2 -> horas[2][0]="cambio";
            case 3 -> horas[3][0]="cambio";
            case 4 -> horas[4][0]="cambio";
            case 5 -> horas[5][0]="cambio";
            case 6 -> horas[6][0]="cambio";
            case 7 -> horas[7][0]="cambio";
            case 8 -> horas[8][0]="cambio";
            case 9 -> horas[9][0]="cambio";
        } */
    }
    private static void eliminarReserva() {
        String matricula = Validacion.leerMatricula();
        int dia = obtenerDia();
        int hora = obtenerHora();
        if (horas[dia][hora] == matricula) {
            horas[dia][hora] = "1";
        } else {
            System.out.println("Esta reserva no es suya");
        }
    }
}
