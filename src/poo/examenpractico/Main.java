package poo.examenpractico;

import java.nio.file.Paths;

import poo.examenpractico.vista.VistaConsola;
import poo.examenpractico.controlador.BuscaminasControlador;
import poo.examenpractico.controlador.ServicioJuego;
import poo.examenpractico.controlador.ServicioJuegoReal;
import poo.examenpractico.persistencia.GestorPartida;

public class Main {

    public static void main(String[] args) {

        VistaConsola vista = new VistaConsola();

        GestorPartida gestor = new GestorPartida();

        ServicioJuego servicio = new ServicioJuegoReal(
                gestor, Paths.get("partida.dat"));

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();
    }
}