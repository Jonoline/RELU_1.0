package datos;

public enum Horario {
    BLOQUE_1("08:30", "10:00"),
    BLOQUE_2("10:00", "11:30"),
    BLOQUE_3("11:30", "13:00"),
    BLOQUE_4("13:00", "14:30"),
    BLOQUE_5("14:30", "16:00"),
    BLOQUE_6("16:00", "17:30"),
    BLOQUE_7("17:30", "19:00");

    private final String horaInicio;
    private final String horaFin;

    Horario(String horaInicio, String horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public String getHoraInicio() {
        return horaInicio;
    }
    private int getNumeroBloque() {
        return Integer.parseInt(name().substring(7));
    }
    public static Horario obtenerHorario(int bloque) {
        for (Horario h : Horario.values()) {
            if (h.getNumeroBloque() == bloque) {
                return h;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "[" + getNumeroBloque() + "] " + horaInicio + "-" + horaFin;
    }
}