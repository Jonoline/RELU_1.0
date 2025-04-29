import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
class reservasLogiasTest {
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //IMPORTANTE
    //CADA TEST SOLO FUNCIONA POR SEPARADO
    //ESTO DEBIDO A LA DECLARACIÓN DEL SCANNER COMO STATIC EN LA CLASE reservasLogias
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private final InputStream originalIn = System.in;

    @AfterEach
    public void restaurarSystemIn() {
        System.setIn(originalIn);
    }

    private void simularEntrada(String input) {
        ByteArrayInputStream inputSimulado = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputSimulado);
    }

    @Test
    public void entradaValidaHoras() {
        simularEntrada("5\n");
        int resultado = reservasLogias.obtenerHora();
        assertEquals(5, resultado);
    }

    @Test
    public void entradaInvalidaYLuegoValidaHoras() {
        simularEntrada("abc\n2\n");
        int resultado = reservasLogias.obtenerHora();
        assertEquals(2, resultado);
    }

    @Test
    public void entradaValidaDias() {
        simularEntrada("5\n");
        int resultado = reservasLogias.obtenerDia();
        assertEquals(5, resultado);
    }

    @Test
    public void entradaInvalidaYLuegoValidaDia() {
        simularEntrada("abc\n3\n");
        int resultado = reservasLogias.obtenerDia();
        assertEquals(3, resultado);
    }
}
