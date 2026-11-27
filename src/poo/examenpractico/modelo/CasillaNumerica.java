package poo.examenpractico.modelo;

/**
 * Casilla segura que tiene entre 1 y 8 minas en sus casillas vecinas.
 * Al descubrirse muestra ese número.
 */
public class CasillaNumerica extends Casilla {

    private static final long serialVersionUID = 1L;

    private final int minasAdyacentes;

    /**
     * Crea una casilla numérica.
     *
     * @param minasAdyacentes cantidad de minas vecinas, entre 1 y 8
     * @throws IllegalArgumentException si el valor está fuera de ese rango
     */
    public CasillaNumerica(int minasAdyacentes) {
        if (minasAdyacentes < 1 || minasAdyacentes > 8) {
            throw new IllegalArgumentException(
                    "Una casilla numérica debe tener entre 1 y 8 minas vecinas, pero se recibió " + minasAdyacentes);
        }
        this.minasAdyacentes = minasAdyacentes;
    }

    /** {@inheritDoc} */
    @Override
    public boolean esMina() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public int getMinasAdyacentes() {
        return minasAdyacentes;
    }

    /** {@inheritDoc} Una casilla con número no abre a sus vecinas. */
    @Override
    public boolean seExpande() {
        return false;
    }

    /** {@inheritDoc} Devuelve el dígito con la cantidad de minas vecinas. */
    @Override
    protected char getSimboloDescubierta() {
        return (char) ('0' + minasAdyacentes);
    }
}
