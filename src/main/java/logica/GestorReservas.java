package logica;
import datos.Json;
import datos.Reserva;

import java.util.List;
public class GestorReservas {
    private static final String archivo = "reservas.json";
    private List<Reserva> reservas;

    public GestorReservas() {
        Json json = new Json();
        reservas = new Json().CargarReservas();
    }

}
