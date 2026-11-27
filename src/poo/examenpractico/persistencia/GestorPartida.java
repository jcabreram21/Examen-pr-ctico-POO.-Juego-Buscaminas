package poo.examenpractico.persistencia;

import poo.examenpractico.excepciones.PersistenciaException;
import poo.examenpractico.modelo.Juego;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Guarda y carga partidas en archivos binarios usando serialización de objetos.
 * <p>
 * Al serializar un {@link Juego} se guarda también su jugador, su tablero y
 * todas las casillas, así que al cargarlo el jugador continúa exactamente
 * donde se quedó.
 * </p>
 */
public class GestorPartida {

    /**
     * Guarda la partida en un archivo. Si el archivo ya existe, se reemplaza.
     *
     * @param juego   partida a guardar
     * @param archivo ruta del archivo de destino
     * @throws PersistenciaException si no se puede escribir el archivo
     */
    public void guardar(Juego juego, Path archivo) throws PersistenciaException {
        try (ObjectOutputStream salida = new ObjectOutputStream(Files.newOutputStream(archivo))) {
            salida.writeObject(juego);
        } catch (IOException e) {
            throw new PersistenciaException("No se pudo guardar la partida en " + archivo, e);
        }
    }

    /**
     * Carga una partida guardada anteriormente.
     *
     * @param archivo ruta del archivo a leer
     * @return la partida recuperada
     * @throws PersistenciaException si el archivo no existe, está dañado o no contiene una partida
     */
    public Juego cargar(Path archivo) throws PersistenciaException {
        try (ObjectInputStream entrada = new ObjectInputStream(Files.newInputStream(archivo))) {
            Object leido = entrada.readObject();
            if (!(leido instanceof Juego)) {
                throw new PersistenciaException("El archivo no contiene una partida de Buscaminas.");
            }
            return (Juego) leido;
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("No se pudo cargar la partida desde " + archivo, e);
        }
    }
}
