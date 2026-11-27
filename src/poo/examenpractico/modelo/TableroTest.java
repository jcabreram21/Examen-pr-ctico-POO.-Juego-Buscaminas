package poo.examenpractico.modelo;

import poo.examenpractico.excepciones.CasillaMarcadaException;
import poo.examenpractico.excepciones.CasillaYaDescubiertaException;
import poo.examenpractico.excepciones.CoordenadaInvalidaException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TableroTest {

    /**
     * Tablero de prueba 4x4 con una sola mina en A1:
     * <pre>
     *   M 1 0 0
     *   1 1 0 0
     *   0 0 0 0
     *   0 0 0 0
     * </pre>
     */
    private Tablero tableroConMinaEnA1() {
        return Tablero.conMinasEn(4, 4, new Coordenada(0, 0));
    }

    private int contarMinas(Tablero t) throws Exception {
        int minas = 0;
        for (int f = 0; f < t.getFilas(); f++) {
            for (int c = 0; c < t.getColumnas(); c++) {
                if (t.getCasilla(new Coordenada(f, c)).esMina()) {
                    minas++;
                }
            }
        }
        return minas;
    }

    // ---------- Inicialización ----------

    @Test
    void tableroPorDefectoEs10x10Con10Minas() throws Exception {
        Tablero t = new Tablero();
        assertEquals(10, t.getFilas());
        assertEquals(10, t.getColumnas());
        assertEquals(10, t.getTotalMinas());
        assertEquals(10, contarMinas(t));
        assertEquals(90, t.getTotalSeguras());
    }

    @Test
    void siempreColocaLaCantidadExactaDeMinas() throws Exception {
        for (int intento = 0; intento < 200; intento++) {
            assertEquals(10, contarMinas(new Tablero()));
        }
    }

    @Test
    void conLaMismaSemillaElTableroEsIgual() throws Exception {
        Tablero a = new Tablero(10, 10, 10, new Random(42));
        Tablero b = new Tablero(10, 10, 10, new Random(42));
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 10; c++) {
                Coordenada pos = new Coordenada(f, c);
                assertEquals(a.getCasilla(pos).esMina(), b.getCasilla(pos).esMina());
            }
        }
    }

    @Test
    void rechazaConfiguracionesInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> new Tablero(0, 10, 5));
        assertThrows(IllegalArgumentException.class, () -> new Tablero(27, 10, 5));
        assertThrows(IllegalArgumentException.class, () -> new Tablero(10, 100, 5));
        assertThrows(IllegalArgumentException.class, () -> new Tablero(10, 10, 0));
        assertThrows(IllegalArgumentException.class, () -> new Tablero(10, 10, 100));
    }

    @Test
    void conMinasEnRechazaMinasFueraOrepetidas() {
        assertThrows(IllegalArgumentException.class,
                () -> Tablero.conMinasEn(3, 3, new Coordenada(3, 0)));
        assertThrows(IllegalArgumentException.class,
                () -> Tablero.conMinasEn(3, 3, new Coordenada(0, 0), new Coordenada(0, 0)));
    }

    // ---------- Conteo de minas vecinas ----------

    @Test
    void cuentaMinasVecinasIncluyendoDiagonales() throws Exception {
        Tablero t = tableroConMinaEnA1();
        assertEquals(1, t.getCasilla(new Coordenada(0, 1)).getMinasAdyacentes()); // A2 (lado)
        assertEquals(1, t.getCasilla(new Coordenada(1, 0)).getMinasAdyacentes()); // B1 (lado)
        assertEquals(1, t.getCasilla(new Coordenada(1, 1)).getMinasAdyacentes()); // B2 (diagonal)
        assertEquals(0, t.getCasilla(new Coordenada(2, 2)).getMinasAdyacentes()); // C3 (lejos)
    }

    @Test
    void casillaConDosMinasVecinasMuestraDos() throws Exception {
        Tablero t = Tablero.conMinasEn(3, 3, new Coordenada(0, 0), new Coordenada(0, 2));
        assertEquals(2, t.getCasilla(new Coordenada(0, 1)).getMinasAdyacentes());
        assertEquals(2, t.getCasilla(new Coordenada(1, 1)).getMinasAdyacentes());
    }

    @Test
    void cuentaCorrectamenteEnEsquinasYBordes() throws Exception {
        Tablero t = Tablero.conMinasEn(3, 3, new Coordenada(2, 2));
        assertEquals(1, t.getCasilla(new Coordenada(1, 1)).getMinasAdyacentes());
        assertEquals(1, t.getCasilla(new Coordenada(2, 1)).getMinasAdyacentes());
        assertEquals(0, t.getCasilla(new Coordenada(0, 0)).getMinasAdyacentes());
    }

    // ---------- Descubrir ----------

    @Test
    void descubrirUnaMinaDevuelveTrue() throws Exception {
        Tablero t = tableroConMinaEnA1();
        assertTrue(t.descubrir(new Coordenada(0, 0)));
    }

    @Test
    void descubrirUnaCasillaConNumeroSoloAbreEsaCasilla() throws Exception {
        Tablero t = tableroConMinaEnA1();
        assertFalse(t.descubrir(new Coordenada(0, 1)));
        assertEquals(1, t.contarDescubiertasSeguras());
        assertEquals('1', t.getSimbolo(0, 1));
    }

    @Test
    void descubrirUnaCasillaVaciaAbreEnCascada() throws Exception {
        Tablero t = tableroConMinaEnA1();
        assertFalse(t.descubrir(new Coordenada(3, 3))); // D4: vacía, lejos de la mina
        // Deben quedar abiertas todas las seguras (15 en total)
        assertEquals(15, t.contarDescubiertasSeguras());
        assertTrue(t.todasLasSegurasDescubiertas());
        // La mina sigue cubierta
        assertEquals(Casilla.SIMBOLO_CUBIERTA, t.getSimbolo(0, 0));
    }

    @Test
    void laCascadaSeDetieneEnLosNumeros() throws Exception {
        // Mina en C3 de un tablero 5x5. Descubrir A1 abre mucho, pero no pasa de los números.
        Tablero t = Tablero.conMinasEn(5, 5, new Coordenada(2, 2));
        t.descubrir(new Coordenada(0, 0));
        // Los 8 vecinos de la mina son números: deben estar abiertos
        assertEquals('1', t.getSimbolo(1, 1));
        // La mina no se abre nunca por cascada
        assertEquals(Casilla.SIMBOLO_CUBIERTA, t.getSimbolo(2, 2));
        // Se abrieron todas las seguras: 24
        assertEquals(24, t.contarDescubiertasSeguras());
    }

    @Test
    void laCascadaNoAbreCasillasMarcadas() throws Exception {
        Tablero t = tableroConMinaEnA1();
        t.marcar(new Coordenada(3, 0)); // D1 marcada (el jugador cree que hay mina)
        t.descubrir(new Coordenada(3, 3));
        assertEquals(Casilla.SIMBOLO_MARCADA, t.getSimbolo(3, 0));
        assertFalse(t.todasLasSegurasDescubiertas());
    }

    @Test
    void descubrirDosVecesLanzaExcepcion() throws Exception {
        Tablero t = tableroConMinaEnA1();
        t.descubrir(new Coordenada(0, 1));
        assertThrows(CasillaYaDescubiertaException.class, () -> t.descubrir(new Coordenada(0, 1)));
    }

    @Test
    void descubrirUnaCasillaMarcadaLanzaExcepcion() throws Exception {
        Tablero t = tableroConMinaEnA1();
        t.marcar(new Coordenada(0, 1));
        assertThrows(CasillaMarcadaException.class, () -> t.descubrir(new Coordenada(0, 1)));
    }

    @Test
    void descubrirFueraDelTableroLanzaExcepcion() {
        Tablero t = tableroConMinaEnA1();
        assertThrows(CoordenadaInvalidaException.class, () -> t.descubrir(new Coordenada(4, 0)));
        assertThrows(CoordenadaInvalidaException.class, () -> t.descubrir(new Coordenada(0, 4)));
    }

    // ---------- Marcar ----------

    @Test
    void marcarYDesmarcarCambiaElContador() throws Exception {
        Tablero t = tableroConMinaEnA1();
        assertTrue(t.marcar(new Coordenada(0, 0)));
        assertEquals(1, t.contarMarcadas());
        assertFalse(t.marcar(new Coordenada(0, 0)));
        assertEquals(0, t.contarMarcadas());
    }

    @Test
    void marcarFueraDelTableroLanzaExcepcion() {
        Tablero t = tableroConMinaEnA1();
        assertThrows(CoordenadaInvalidaException.class, () -> t.marcar(new Coordenada(9, 9)));
    }

    // ---------- Fin de partida ----------

    @Test
    void revelarTodoDescubreTodasLasCasillas() throws Exception {
        Tablero t = tableroConMinaEnA1();
        t.revelarTodo();
        for (int f = 0; f < 4; f++) {
            for (int c = 0; c < 4; c++) {
                assertTrue(t.getCasilla(new Coordenada(f, c)).isDescubierta());
            }
        }
        assertEquals('X', t.getSimbolo(0, 0));
    }

    @Test
    void getSimbolosEntregaUnaCopiaDelEstadoVisible() throws Exception {
        Tablero t = tableroConMinaEnA1();
        t.descubrir(new Coordenada(0, 1)); // A2 tiene número 1
        t.marcar(new Coordenada(3, 3));    // D4 marcada

        char[][] simbolos = t.getSimbolos();
        assertEquals(4, simbolos.length);
        assertEquals(4, simbolos[0].length);
        assertEquals('1', simbolos[0][1]);
        assertEquals(Casilla.SIMBOLO_MARCADA, simbolos[3][3]);
        assertEquals(Casilla.SIMBOLO_CUBIERTA, simbolos[0][0]);

        simbolos[0][0] = 'Z'; // modificar la copia no afecta al tablero
        assertEquals(Casilla.SIMBOLO_CUBIERTA, t.getSimbolo(0, 0));
    }

    @Test
    void mensajeDeErrorIndicaElRangoValido() {
        Tablero t = new Tablero();
        CoordenadaInvalidaException e = assertThrows(CoordenadaInvalidaException.class,
                () -> t.getCasilla(new Coordenada(10, 0)));
        assertTrue(e.getMessage().contains("A-J"), e.getMessage());
        assertTrue(e.getCause() instanceof ArrayIndexOutOfBoundsException);
    }
}
