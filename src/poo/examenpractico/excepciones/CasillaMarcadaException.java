package poo.examenpractico.excepciones;

/**
 * Se lanza cuando el jugador intenta descubrir una casilla que tiene una marca.
 * <p>
 * Las marcas existen para evitar descubrir una mina por accidente, así que
 * primero hay que quitar la marca y luego descubrir la casilla.
 * </p>
 */
public class CasillaMarcadaException extends BuscaminasException {

    private static final long serialVersionUID = 1L;

    /** Crea la excepción con un mensaje por defecto. */
    public CasillaMarcadaException() {
        super("La casilla está marcada. Quite la marca antes de descubrirla.");
    }
}
