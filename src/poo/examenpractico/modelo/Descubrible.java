package poo.examenpractico.modelo;

import poo.examenpractico.excepciones.CasillaMarcadaException;
import poo.examenpractico.excepciones.CasillaYaDescubiertaException;

/**
 * Contrato de todo elemento del tablero que el jugador puede descubrir o marcar.
 * <p>
 * Se define como interfaz para separar <em>qué</em> se puede hacer con una
 * casilla de <em>cómo</em> lo hace cada tipo de casilla.
 * </p>
 */
public interface Descubrible {

    /**
     * Descubre el elemento.
     *
     * @throws CasillaYaDescubiertaException si ya estaba descubierto
     * @throws CasillaMarcadaException       si tiene una marca puesta
     */
    void descubrir() throws CasillaYaDescubiertaException, CasillaMarcadaException;

    /**
     * Pone la marca si no la tiene, o la quita si ya la tiene.
     *
     * @return {@code true} si después de la operación el elemento queda marcado
     * @throws CasillaYaDescubiertaException si el elemento ya está descubierto
     */
    boolean alternarMarca() throws CasillaYaDescubiertaException;

    /**
     * Indica si el elemento ya fue descubierto.
     *
     * @return {@code true} si está descubierto
     */
    boolean isDescubierta();

    /**
     * Indica si el elemento tiene una marca puesta por el jugador.
     *
     * @return {@code true} si está marcado
     */
    boolean isMarcada();
}
