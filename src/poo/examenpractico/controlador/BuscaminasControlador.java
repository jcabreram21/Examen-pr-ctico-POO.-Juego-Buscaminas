package poo.examenpractico.controlador;

import poo.examenpractico.vista.VistaConsola;
import poo.examenpractico.modelo.Juego;
import poo.examenpractico.modelo.Jugador;
import poo.examenpractico.modelo.Coordenada;
import poo.examenpractico.modelo.EstadoJuego;
import poo.examenpractico.modelo.Casilla;

import poo.examenpractico.excepciones.BuscaminasException;
import poo.examenpractico.excepciones.PersistenciaException;

public class BuscaminasControlador {

    private final VistaConsola vista;
    private final ServicioJuego servicio;


    public BuscaminasControlador(
            VistaConsola vista, ServicioJuego servicio) {

        this.vista = vista;
        this.servicio = servicio;
    }

    //Menú principal
    public void iniciar() {

        String opcion;

        do {

            vista.mostrarMenuPrincipal();

            opcion = vista.pedirEntrada("Seleccione una opción: ");

            try {

                switch (opcion) {

                    case "1":
                        nuevaPartida();
                        break;

                    case "2":
                        cargarPartida();
                        break;

                    case "0":
                        vista.mostrarMensaje("Gracias por jugar.");
                        break;

                    default:
                        vista.mostrarError("Opción inválida.");
                        break;
                }

            } catch (PersistenciaException e) {

                vista.mostrarError(e.getMessage());
            }

        } while (!opcion.equals("0"));
    }

    //Crear una nueva partida
    private void nuevaPartida() {

        String nombre = vista.pedirEntrada("Ingrese el nombre del jugador: ");

        if (nombre == null || nombre.trim().isEmpty()) {

            vista.mostrarError("El nombre del jugador no puede estar vacío.");

            return;
        }

        Jugador jugador = new Jugador(nombre);

        Juego juego = servicio.nuevaPartida(jugador);

        vista.mostrarMensaje("Nueva partida iniciada.");

        jugar(juego);
    }

    //Cargar una partida existente
    private void cargarPartida()
            throws PersistenciaException {

        Juego juego = servicio.cargarPartida();

        vista.mostrarMensaje("Partida cargada correctamente.");

        jugar(juego);
    }

    //Ciclo principal de la partida
    private void jugar(Juego juego) {

        mostrarTablero(juego);

        while (!juego.isTerminado()) {

            vista.mostrarMenuJuego();

            String opcion = vista.pedirEntrada("Seleccione una opción: ");

            try {

                switch (opcion) {

                    case "1":
                        descubrirCasilla(juego);
                        break;

                    case "2":
                        marcarCasilla(juego);
                        break;

                    case "3":
                        guardarPartida(juego);
                        break;

                    case "0":
                        vista.mostrarMensaje("Regresando al menú principal.");
                        return;

                    default:
                        vista.mostrarError("Opción inválida.");
                        break;
                }

            } catch (BuscaminasException e) {

                vista.mostrarError(e.getMessage());
            }

            mostrarTablero(juego);
        }

        mostrarResultado(juego);
    }

    //Descubrir una casilla
    private void descubrirCasilla(Juego juego)
            throws BuscaminasException {

        String texto = vista.pedirCoordenada();

        Coordenada coordenada =
                Coordenada.desdeTexto(texto);

        juego.descubrir(coordenada);

        char simbolo = juego.getTablero().getSimbolo(
                coordenada.getFila(),
                coordenada.getColumna());

        if (simbolo == Casilla.SIMBOLO_MINA) {

            vista.mostrarMinaEnCasilla(
                    coordenada.toString());

        } else if (simbolo == Casilla.SIMBOLO_VACIA) {

            vista.mostrarCasillaVacia(
                    coordenada.toString());

        } else {

            vista.mostrarMensaje("Casilla " + coordenada + " descubierta: " + simbolo);
        }
    }

    //Marcar o desmarcar una casilla
    private void marcarCasilla(Juego juego)
            throws BuscaminasException {

        String texto = vista.pedirCoordenada();

        Coordenada coordenada =
                Coordenada.desdeTexto(texto);

        boolean marcada = juego.marcar(coordenada);

        if (marcada) {

            vista.mostrarMensaje("Casilla " + coordenada + " marcada.");

        } else {

            vista.mostrarMensaje("Casilla " + coordenada + " desmarcada.");
        }
    }

    //Guardar partida
    private void guardarPartida(Juego juego)
            throws PersistenciaException {

        servicio.guardarPartida(juego);

        vista.mostrarMensaje("Partida guardada correctamente.");
    }

    //Mostrar información y tablero
    private void mostrarTablero(Juego juego) {

        vista.mostrarMensaje("\nJugador: "
                + juego.getJugador().getNombre());

        vista.mostrarMensaje("Minas pendientes de marcar: "
                + juego.getMinasRestantes());

        vista.mostrarMensaje("Jugadas realizadas: "
                + juego.getJugadas());

        vista.mostrarTablero(
                juego.getTablero().getSimbolos());
    }

    //Mostrar resultado final
    private void mostrarResultado(Juego juego) {

        EstadoJuego estado = juego.getEstado();

        if (estado == EstadoJuego.GANADO) {

            vista.mostrarResultadoPartida(true);

        } else if (estado == EstadoJuego.PERDIDO) {

            vista.mostrarResultadoPartida(false);
        }
    }
}