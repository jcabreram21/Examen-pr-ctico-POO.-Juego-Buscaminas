package poo.examenpractico.modelo;

/**
 * Casilla que contiene una mina. Descubrirla hace perder la partida.
 */
public class CasillaMina extends Casilla {

    private static final long serialVersionUID = 1L;

    /** {@inheritDoc} */
    @Override
    public boolean esMina() {
        return true;
    }

    /** {@inheritDoc} Una mina no cuenta minas vecinas, por eso devuelve 0. */
    @Override
    public int getMinasAdyacentes() {
        return 0;
    }

    /** {@inheritDoc} Una mina nunca abre a sus vecinas. */
    @Override
    public boolean seExpande() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    protected char getSimboloDescubierta() {
        return SIMBOLO_MINA;
    }
}
