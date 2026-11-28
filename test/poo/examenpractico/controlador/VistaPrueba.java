package poo.examenpractico.controlador;

import java.util.ArrayList;
import java.util.List;

import poo.examenpractico.vista.VistaConsola;

public class VistaPrueba extends VistaConsola {

    //Contadores
    private int vecesMostrado;
    private int vecesMenuJuego;
    private int vecesTablero;
    private int vecesSolicitadaEntrada;
    private int vecesError;

    private Boolean resultado;

    private final List<String> mensajes = new ArrayList<>();

    //Entradas simuladas
    private final String[] entradas;
    private int indice;


    public VistaPrueba(String... entradas) {

        this.entradas = entradas.length == 0
                ? new String[]{"0"} : entradas;
    }

    @Override
    public void mostrarMenuPrincipal() {
        vecesMostrado++;
    }

    @Override
    public void mostrarMenuJuego() {
        vecesMenuJuego++;
    }

    @Override
    public void mostrarTablero(char[][] celdas) {
        vecesTablero++;
    }

    @Override
    public String pedirEntrada(String mensaje) {

        vecesSolicitadaEntrada++;

        if (indice >= entradas.length) {

            throw new AssertionError("No hay más entradas de prueba.");
        }

        return entradas[indice++];
    }

    @Override
    public String pedirCoordenada() {
        return pedirEntrada("Coordenada: ");
    }

    @Override
    public void mostrarError(String mensaje) {
        vecesError++;
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        mensajes.add(mensaje);
    }

    @Override
    public void mostrarResultadoPartida(boolean gano) {
        resultado = gano;
    }

    // Getters
    public int getVecesMostrado() {
        return vecesMostrado;
    }

    public int getVecesMenuJuego() {
        return vecesMenuJuego;
    }

    public int getVecesTablero() {
        return vecesTablero;
    }

    public int getVecesSolicitadaEntrada() {
        return vecesSolicitadaEntrada;
    }

    public int getVecesError() {
        return vecesError;
    }

    public Boolean getResultado() {
        return resultado;
    }

    public boolean contieneMensaje(String mensaje) {
        return mensajes.contains(mensaje);
    }
}