public class reservasLogias {
    private static String[][]horas= new String[10][6];

    public static void main(String[] args) {
        llenarDias();
        llenarHoras();
    }
    public static void llenarDias(){
        horas[0][1]="lunes";
        horas[0][2]="martes";
        horas[0][3]="miercoles";
        horas[0][4]="jueves";
        horas[0][5]="viernes";
    }
    public static void llenarHoras(){
        horas[1][0]="8:30-9:30";
        horas[2][0]="9:40-10:40";
        horas[3][0]="10:50-11:50";
        horas[4][0]="12:00-13:00";
        horas[5][0]="13:10-14:10";
        horas[6][0]="14:30-15:30";
        horas[7][0]="15:40-16:40";
        horas[8][0]="16:50-17:50";
        horas[9][0]="18:00-19:00";
    }

}
