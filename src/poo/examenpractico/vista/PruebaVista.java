package poo.examenpractico.vista;


public class PruebaVista {


	    public static void main(String[] args) {
	        VistaConsola vista = new VistaConsola();
	        
	        vista.mostrarMenuPrincipal();
	        vista.mostrarMenuJuego();

	        char[][] celdas = new char[10][10];
	        for (int fila = 0; fila < celdas.length; fila++) {
	            for (int col = 0; col < celdas[fila].length; col++) {
	                celdas[fila][col] = '-';
	            }
	        }

	        vista.mostrarTablero(celdas);
	    }
	}