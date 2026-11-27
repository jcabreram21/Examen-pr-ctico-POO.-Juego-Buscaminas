package poo.examenpractico.modelo;

import java.io.Serializable;

/**
 * Persona que juega. Guarda su nombre y cuántas partidas ha ganado y perdido.
 */
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String nombre;
    private int partidasGanadas;
    private int partidasPerdidas;

    /**
     * Crea un jugador.
     *
     * @param nombre nombre del jugador (no puede ser nulo ni estar vacío)
     * @throws IllegalArgumentException si el nombre es nulo o está en blanco
     */
    public Jugador(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    /**
     * Devuelve el nombre del jugador.
     *
     * @return nombre sin espacios sobrantes
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve las partidas ganadas.
     *
     * @return cantidad de victorias
     */
    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    /**
     * Devuelve las partidas perdidas.
     *
     * @return cantidad de derrotas
     */
    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    /** Suma una victoria. Solo la usa {@link Juego} al terminar una partida. */
    void registrarVictoria() {
        partidasGanadas++;
    }

    /** Suma una derrota. Solo la usa {@link Juego} al terminar una partida. */
    void registrarDerrota() {
        partidasPerdidas++;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return nombre + " (ganadas: " + partidasGanadas + ", perdidas: " + partidasPerdidas + ")";
    }
}
