package poo.examenpractico.modelo;

import poo.examenpractico.excepciones.CasillaMarcadaException;
import poo.examenpractico.excepciones.CasillaYaDescubiertaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CasillaTest {

    @Test
    void unaCasillaNuevaEstaCubierta() {
        Casilla c = new CasillaVacia();
        assertFalse(c.isDescubierta());
        assertFalse(c.isMarcada());
        assertEquals(Casilla.SIMBOLO_CUBIERTA, c.getSimbolo());
    }

    @Test
    void polimorfismoCadaTipoMuestraSuSimbolo() throws Exception {
        Casilla mina = new CasillaMina();
        Casilla vacia = new CasillaVacia();
        Casilla numerica = new CasillaNumerica(3);
        mina.descubrir();
        vacia.descubrir();
        numerica.descubrir();

        assertEquals('X', mina.getSimbolo());
        assertEquals('V', vacia.getSimbolo());
        assertEquals('3', numerica.getSimbolo());
    }

    @Test
    void soloLaMinaEsMina() {
        assertTrue(new CasillaMina().esMina());
        assertFalse(new CasillaVacia().esMina());
        assertFalse(new CasillaNumerica(1).esMina());
    }

    @Test
    void soloLaVaciaSeExpande() {
        assertTrue(new CasillaVacia().seExpande());
        assertFalse(new CasillaMina().seExpande());
        assertFalse(new CasillaNumerica(2).seExpande());
    }

    @Test
    void numericaGuardaSuCantidad() {
        assertEquals(5, new CasillaNumerica(5).getMinasAdyacentes());
    }

    @Test
    void numericaRechazaValoresFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> new CasillaNumerica(0));
        assertThrows(IllegalArgumentException.class, () -> new CasillaNumerica(9));
    }

    @Test
    void descubrirDosVecesLanzaExcepcion() throws Exception {
        Casilla c = new CasillaVacia();
        c.descubrir();
        assertThrows(CasillaYaDescubiertaException.class, c::descubrir);
    }

    @Test
    void marcarYDesmarcar() throws Exception {
        Casilla c = new CasillaMina();
        assertTrue(c.alternarMarca());
        assertEquals(Casilla.SIMBOLO_MARCADA, c.getSimbolo());
        assertFalse(c.alternarMarca());
        assertEquals(Casilla.SIMBOLO_CUBIERTA, c.getSimbolo());
    }

    @Test
    void noSePuedeDescubrirUnaCasillaMarcada() throws Exception {
        Casilla c = new CasillaVacia();
        c.alternarMarca();
        assertThrows(CasillaMarcadaException.class, c::descubrir);
        assertFalse(c.isDescubierta());
    }

    @Test
    void noSePuedeMarcarUnaCasillaDescubierta() throws Exception {
        Casilla c = new CasillaVacia();
        c.descubrir();
        assertThrows(CasillaYaDescubiertaException.class, c::alternarMarca);
    }

    @Test
    void revelarQuitaLaMarca() throws Exception {
        Casilla c = new CasillaMina();
        c.alternarMarca();
        c.revelar();
        assertTrue(c.isDescubierta());
        assertFalse(c.isMarcada());
    }
}
