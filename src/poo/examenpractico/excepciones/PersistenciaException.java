package poo.examenpractico.excepciones;

/**
 * Se lanza cuando falla el guardado o la carga de una partida
 * (archivo inexistente, dañado, sin permisos, etc.).
 */
public class PersistenciaException extends BuscaminasException {

    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepción con un mensaje descriptivo.
     *
     * @param mensaje explicación del error
     */
    public PersistenciaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea la excepción con un mensaje y la causa original.
     *
     * @param mensaje explicación del error
     * @param causa   excepción original (normalmente una {@code IOException})
     */
    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
