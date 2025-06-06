import logica.Validacion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionTest {

    @Test
    void testMatriculaValida(){
        assertTrue(Validacion.validarMatricula("21797495k23"));
    }

    @Test
    void testMatriculaInvalida(){
        assertFalse(Validacion.validarMatricula("zdxfcgvbjkml,ñ"));
    }
    @Test
    void testMatriculaVacio(){
        assertFalse(Validacion.validarMatricula(""));
    }

}