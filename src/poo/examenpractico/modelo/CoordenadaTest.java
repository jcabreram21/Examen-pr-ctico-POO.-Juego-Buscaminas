package poo.examenpractico.modelo;

import poo.examenpractico.excepciones.CoordenadaInvalidaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordenadaTest {

    @Test
    void convierteA5EnFila0Columna4() throws Exception {
        Coordenada c = Coordenada.desdeTexto("A5");
        assertEquals(0, c.getFila());
        assertEquals(4, c.getColumna());
    }

    @Test
    void aceptaMinusculasYEspacios() throws Exception {
        assertEquals(Coordenada.desdeTexto("J10"), Coordenada.desdeTexto("  j10 "));
    }

    @Test
    void toStringDevuelveElFormatoDelJugador() throws Exception {
        assertEquals("C7", Coordenada.desdeTexto("c7").toString());
    }

    @Test
    void rechazaTextoNulo() {
        assertThrows(CoordenadaInvalidaException.class, () -> Coordenada.desdeTexto(null));
    }

    @Test
    void rechazaFormatosIncorrectos() {
        String[] malos = {"", "A", "5A", "AA5", "A0", "A-1", "A123", "hola", "A 5"};
        for (String malo : malos) {
            assertThrows(CoordenadaInvalidaException.class,
                    () -> Coordenada.desdeTexto(malo), "Debía rechazar: \"" + malo + "\"");
        }
    }

    @Test
    void rechazaIndicesNegativos() {
        assertThrows(IllegalArgumentException.class, () -> new Coordenada(-1, 0));
    }

    @Test
    void igualdadYHashCode() {
        assertEquals(new Coordenada(2, 3), new Coordenada(2, 3));
        assertEquals(new Coordenada(2, 3).hashCode(), new Coordenada(2, 3).hashCode());
        assertNotEquals(new Coordenada(2, 3), new Coordenada(3, 2));
    }
}
