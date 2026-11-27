package poo.examenpractico.modelo;

import poo.examenpractico.excepciones.CasillaMarcadaException;
import poo.examenpractico.excepciones.CasillaYaDescubiertaException;

import java.io.Serializable;

/**
 * Clase base abstracta de todas las casillas del tablero.
 * <p>
 * Guarda lo que tienen en común todas las casillas (si están descubiertas o
 * marcadas) y deja a las subclases lo que cambia entre ellas: si son mina,
 * cuántas minas tienen alrededor y qué símbolo muestran al descubrirse.
 * Esto es herencia y polimorfismo: el tablero trata a todas como
 * {@code Casilla} sin saber de qué tipo concreto es cada una.
 * </p>
 * Subclases: {@link CasillaMina}, {@link CasillaVacia} y {@link CasillaNumerica}.
 */
public abstract class Casilla implements Descubrible, Serializable {

    private static final long serialVersionUID = 1L;

    /** Símbolo de una casilla que todavía no se ha descubierto. */
    public static final char SIMBOLO_CUBIERTA = '#';
    /** Símbolo de una casilla marcada por el jugador. */
    public static final char SIMBOLO_MARCADA = 'M';
    /** Símbolo de una mina descubierta. */
    public static final char SIMBOLO_MINA = 'X';
    /** Símbolo de una casilla descubierta sin minas alrededor. */
    public static final char SIMBOLO_VACIA = 'V';

    private boolean descubierta;
    private boolean marcada;

    /**
     * {@inheritDoc}
     * <p>Se revisa primero si ya estaba descubierta y luego si está marcada.</p>
     */
    @Override
    public void descubrir() throws CasillaYaDescubiertaException, CasillaMarcadaException {
        if (descubierta) {
            throw new CasillaYaDescubiertaException();
        }
        if (marcada) {
            throw new CasillaMarcadaException();
        }
        descubierta = true;
    }

    /**
     * Descubre la casilla sin hacer validaciones y le quita la marca.
     * <p>
     * Se usa en dos casos: cuando el tablero abre automáticamente las casillas
     * vecinas y cuando la partida termina y se muestra todo el tablero.
     * </p>
     */
    public void revelar() {
        descubierta = true;
        marcada = false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean alternarMarca() throws CasillaYaDescubiertaException {
        if (descubierta) {
            throw new CasillaYaDescubiertaException("No se puede marcar una casilla ya descubierta.");
        }
        marcada = !marcada;
        return marcada;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isDescubierta() {
        return descubierta;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isMarcada() {
        return marcada;
    }

    /**
     * Devuelve el símbolo que la vista debe dibujar para esta casilla en su
     * estado actual: cubierta, marcada o, si está descubierta, el símbolo
     * propio de cada tipo de casilla.
     *
     * @return el carácter a mostrar
     */
    public final char getSimbolo() {
        if (descubierta) {
            return getSimboloDescubierta();
        }
        return marcada ? SIMBOLO_MARCADA : SIMBOLO_CUBIERTA;
    }

    /**
     * Símbolo que muestra la casilla una vez descubierta. Cada subclase
     * decide el suyo (polimorfismo).
     *
     * @return el carácter a mostrar cuando la casilla está descubierta
     */
    protected abstract char getSimboloDescubierta();

    /**
     * Indica si la casilla contiene una mina.
     *
     * @return {@code true} si es una mina
     */
    public abstract boolean esMina();

    /**
     * Cantidad de minas en las casillas vecinas (incluidas las diagonales).
     *
     * @return número de minas vecinas; 0 en una casilla mina, donde no aplica
     */
    public abstract int getMinasAdyacentes();

    /**
     * Indica si al descubrir esta casilla deben abrirse automáticamente sus
     * vecinas. Solo ocurre con las casillas sin minas alrededor.
     *
     * @return {@code true} si se debe expandir
     */
    public abstract boolean seExpande();
}
