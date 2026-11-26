package poo.examenpractico.excepciones;

/**
 * Se lanza cuando una coordenada no se puede interpretar (por ejemplo "5A")
 * o cuando apunta a una posición que no existe en el tablero (por ejemplo "A11").
 */
public class CoordenadaInvalidaException extends BuscaminasException {

    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepción con un mensaje descriptivo.
     *
     * @param mensaje explicación del error
     */
    public CoordenadaInvalidaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea la excepción con un mensaje y la causa original.
     *
     * @param mensaje explicación del error
     * @param causa   excepción original (por ejemplo, un
     *                {@code ArrayIndexOutOfBoundsException})
     */
    public CoordenadaInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
