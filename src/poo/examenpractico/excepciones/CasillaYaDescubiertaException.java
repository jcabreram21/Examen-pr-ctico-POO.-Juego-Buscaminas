package poo.examenpractico.excepciones;

/**
 * Se lanza cuando el jugador intenta descubrir (o marcar) una casilla
 * que ya había sido descubierta.
 */
public class CasillaYaDescubiertaException extends BuscaminasException {

    private static final long serialVersionUID = 1L;

    /** Crea la excepción con un mensaje por defecto. */
    public CasillaYaDescubiertaException() {
        super("Esa casilla ya fue descubierta.");
    }

    /**
     * Crea la excepción con un mensaje personalizado.
     *
     * @param mensaje explicación del error
     */
    public CasillaYaDescubiertaException(String mensaje) {
        super(mensaje);
    }
}
