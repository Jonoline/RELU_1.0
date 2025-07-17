package datos;

public enum Mes {
    ENERO("01"),
    FEBRERO("02"),
    MARZO("03"),
    ABRIL("04"),
    MAYO("05"),
    JUNIO("06"),
    JULIO("07"),
    AGOSTO("08"),
    SEPTIEMBRE("09"),
    OCTUBRE("10"),
    NOVIEMBRE("11"),
    DICIEMBRE("12");

    private final String numeroMes;

    Mes(String numeroMes) {
        this.numeroMes = numeroMes;
    }

    public String getNumeroMes() {
        return numeroMes;
    }

    public static Mes obtenerMes(String nombreMes) {
        try {
            return valueOf(nombreMes.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ingrese un mes válido");
        }
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}