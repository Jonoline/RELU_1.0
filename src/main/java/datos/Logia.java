package datos;

public enum Logia {
    LOGIA1(4), LOGIA2(4), LOGIA3(4), LOGIA4(4),
    LOGIA5(4), LOGIA6(4), LOGIA7(4), LOGIA8(4),
    LOGIA9(6), LOGIA10(6), LOGIA11(6), LOGIA12(6),
    LOGIA13(6), LOGIA14(6), LOGIA15(6), LOGIA16(6);

    private final int capacidad;

    Logia(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    @Override
    public String toString() {
        return name() + " (capacidad: " + capacidad + ")";
    }
}
