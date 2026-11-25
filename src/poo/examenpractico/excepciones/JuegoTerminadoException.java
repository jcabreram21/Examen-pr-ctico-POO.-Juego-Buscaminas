package poo.examenpractico.excepciones;

/**
 * Se lanza cuando se intenta jugar (descubrir o marcar) en una partida
 * que ya terminó, ya sea por victoria o por derrota.
 */
public class JuegoTerminadoException extends BuscaminasException {

    private static final long serialVersionUID = 1L;

    /** Crea la excepción con un mensaje por defecto. */
    public JuegoTerminadoException() {
        super("La partida ya terminó. Inicie una nueva partida para seguir jugando.");
    }
}
