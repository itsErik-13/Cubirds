/*
 * Esta clase representa el funcionamiento general del juego
 */
package es.uvigo.esei.cubirds.iu;

import java.util.LinkedList;
import java.util.List;

import es.uvigo.esei.cubirds.core.*;

public class Juego {

    public static void inicio() {

        // //Prueba de creación de baraja
        // Baraja baraja = new Baraja();
        // System.out.println(baraja);

        //Prueba de creacion de mesa inicial + modificaciones en la mesa
        Baraja baraja = new Baraja();
        Mesa mesa = new Mesa();
        mesa.colocarMesaInicial(baraja);
        System.out.println(mesa);
        List<Carta> cartas = new LinkedList<>();
        cartas.add(new Carta("Curruca de caña",6, 9));
        cartas.add(new Carta("Curruca de caña",6, 9));
        cartas.add(new Carta("Curruca de caña",6, 9));
        mesa.insertar(cartas, 3, false,baraja);
        System.out.println(mesa);

        // Se crea la baraja

        // Se crea el montón de descartes

        // Se crea la mesa con las cartas iniciales

        // Se crean los jugadores

        // Se reparten las cartas

        // Empieza el juego

        // Jugador coloca en la mesa
        // Se comprueba si el jugador se ha quedado sin cartas
        // Se pregunta si quiere colocar cartas en la zona de juego
        // Si el jugador no es ganador, se comprueba si se ha quedado sin cartas
        // Se rellena la mesa
    }

}
