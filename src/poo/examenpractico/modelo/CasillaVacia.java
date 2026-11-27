package poo.examenpractico.modelo;

/**
 * Casilla segura que no tiene ninguna mina en sus casillas vecinas.
 * Al descubrirla, el tablero abre automáticamente a sus vecinas.
 */
public class CasillaVacia extends Casilla {

    private static final long serialVersionUID = 1L;

    /** {@inheritDoc} */
    @Override
    public boolean esMina() {
        return false;
    }

    /** {@inheritDoc} Siempre es 0 en este tipo de casilla. */
    @Override
    public int getMinasAdyacentes() {
        return 0;
    }

    /** {@inheritDoc} Es la única casilla segura que abre a sus vecinas. */
    @Override
    public boolean seExpande() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    protected char getSimboloDescubierta() {
        return SIMBOLO_VACIA;
    }
}
