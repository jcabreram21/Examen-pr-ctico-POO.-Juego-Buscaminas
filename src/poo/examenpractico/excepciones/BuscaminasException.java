package poo.examenpractico.excepciones;

/**
 * Excepción base de todos los errores propios del juego Buscaminas.
 * <p>
 * Todas las excepciones personalizadas heredan de esta clase. Así, el
 * controlador puede capturar cada error por separado o, si no necesita
 * distinguirlos, capturar solo {@code BuscaminasException}.
 * </p>
 */
public class BuscaminasException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepción con un mensaje descriptivo.
     *
     * @param mensaje explicación del error, pensada para mostrarse al jugador
     */
    public BuscaminasException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea la excepción con un mensaje y la causa original del error.
     *
     * @param mensaje explicación del error
     * @param causa   excepción original que provocó este error
     */
    public BuscaminasException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
