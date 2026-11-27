package poo.examenpractico.modelo;

import poo.examenpractico.excepciones.CasillaMarcadaException;
import poo.examenpractico.excepciones.CasillaYaDescubiertaException;
import poo.examenpractico.excepciones.CoordenadaInvalidaException;
import poo.examenpractico.excepciones.JuegoTerminadoException;

import java.io.Serializable;

/**
 * Una partida de Buscaminas: reúne al {@link Jugador}, el {@link Tablero} y
 * el {@link EstadoJuego}, y aplica las reglas de victoria y derrota.
 * <p>
 * Es la clase que usa el controlador: la vista nunca toca el tablero
 * directamente para jugar, siempre pasa por aquí. Es {@link Serializable}
 * para poder guardar la partida completa en un archivo.
 * </p>
 */
public class Juego implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Jugador jugador;
    private final Tablero tablero;
    private EstadoJuego estado;
    private int jugadas;

    /**
     * Crea una partida nueva con un tablero de 10x10 y 10 minas aleatorias.
     *
     * @param jugador quien juega la partida
     * @throws IllegalArgumentException si el jugador es nulo
     */
    public Juego(Jugador jugador) {
        this(jugador, new Tablero());
    }

    /**
     * Crea una partida con un tablero ya preparado (útil para pruebas).
     *
     * @param jugador quien juega la partida
     * @param tablero tablero con el que se juega
     * @throws IllegalArgumentException si el jugador o el tablero son nulos
     */
    public Juego(Jugador jugador, Tablero tablero) {
        if (jugador == null || tablero == null) {
            throw new IllegalArgumentException("El jugador y el tablero son obligatorios.");
        }
        this.jugador = jugador;
        this.tablero = tablero;
        this.estado = EstadoJuego.EN_CURSO;
        this.jugadas = 0;
    }

    /**
     * Descubre una casilla y actualiza el estado de la partida: si era una
     * mina se pierde, y si ya no quedan casillas seguras por descubrir se gana.
     * Al terminar, el tablero se muestra completo.
     *
     * @param coordenada casilla a descubrir
     * @throws JuegoTerminadoException       si la partida ya había terminado
     * @throws CoordenadaInvalidaException   si la posición no existe en el tablero
     * @throws CasillaYaDescubiertaException si la casilla ya estaba descubierta
     * @throws CasillaMarcadaException       si la casilla tiene una marca
     */
    public void descubrir(Coordenada coordenada)
            throws JuegoTerminadoException, CoordenadaInvalidaException,
            CasillaYaDescubiertaException, CasillaMarcadaException {
        verificarEnCurso();
        boolean eraMina = tablero.descubrir(coordenada);
        jugadas++;
        if (eraMina) {
            terminar(EstadoJuego.PERDIDO);
        } else if (tablero.todasLasSegurasDescubiertas()) {
            terminar(EstadoJuego.GANADO);
        }
    }

    /**
     * Pone o quita la marca de una casilla que el jugador cree que tiene mina.
     *
     * @param coordenada casilla a marcar o desmarcar
     * @return {@code true} si la casilla quedó marcada
     * @throws JuegoTerminadoException       si la partida ya había terminado
     * @throws CoordenadaInvalidaException   si la posición no existe en el tablero
     * @throws CasillaYaDescubiertaException si la casilla ya estaba descubierta
     */
    public boolean marcar(Coordenada coordenada)
            throws JuegoTerminadoException, CoordenadaInvalidaException, CasillaYaDescubiertaException {
        verificarEnCurso();
        return tablero.marcar(coordenada);
    }

    private void verificarEnCurso() throws JuegoTerminadoException {
        if (estado != EstadoJuego.EN_CURSO) {
            throw new JuegoTerminadoException();
        }
    }

    private void terminar(EstadoJuego resultado) {
        estado = resultado;
        tablero.revelarTodo();
        if (resultado == EstadoJuego.GANADO) {
            jugador.registrarVictoria();
        } else {
            jugador.registrarDerrota();
        }
    }

    /**
     * Indica si la partida ya terminó (por victoria o por derrota).
     *
     * @return {@code true} si el estado no es {@link EstadoJuego#EN_CURSO}
     */
    public boolean isTerminado() {
        return estado != EstadoJuego.EN_CURSO;
    }

    /**
     * Devuelve las minas que faltan por marcar: total de minas menos casillas
     * marcadas. Puede ser negativo si el jugador marca de más.
     *
     * @return minas pendientes según las marcas del jugador
     */
    public int getMinasRestantes() {
        return tablero.getTotalMinas() - tablero.contarMarcadas();
    }

    /**
     * Devuelve el estado actual de la partida.
     *
     * @return estado de la partida
     */
    public EstadoJuego getEstado() {
        return estado;
    }

    /**
     * Devuelve el tablero para que la vista pueda dibujarlo.
     *
     * @return el tablero de la partida
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Devuelve al jugador de la partida.
     *
     * @return el jugador
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Devuelve cuántas casillas descubrió el jugador con jugadas válidas
     * (una jugada que abre varias casillas en cascada cuenta como una).
     *
     * @return cantidad de jugadas realizadas
     */
    public int getJugadas() {
        return jugadas;
    }
}
