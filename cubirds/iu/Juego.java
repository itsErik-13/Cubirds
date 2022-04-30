/*
 * Esta clase representa el funcionamiento general del juego
 */
package es.uvigo.esei.cubirds.iu;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.sql.rowset.serial.SerialException;

import es.uvigo.esei.cubirds.core.*;

public class Juego {

    public static void inicio() throws InterruptedException {

        /// Leer jugadores:
        int numJugadores = leeNumJugadores();

        Jugador[] jugadores = new Jugador[numJugadores];
        // Se crea la baraja
        Baraja baraja = new Baraja();
        // Se crea el montón de descartes
        MontonDescartes md = new MontonDescartes();
        // Se crean los jugadores
        for (int i = 0; i < numJugadores; i++) {
            jugadores[i] = new Jugador(ES.pideCadena("Introduce el nombre del jugador " + (i + 1) + ":"), baraja);
        }

        Mesa mesa = new Mesa();
        mesa.colocarMesaInicial(baraja);

        int turno = (int) (Math.random() * numJugadores);

        boolean ganador = false;

        while (!ganador) {
            turno = ++turno % numJugadores;
            Jugador actual = jugadores[turno];
            System.out.println(baraja.size());
            System.out.println(mesa);
            System.out.println(actual);
            List<Carta> toAdd = actual.colocarMesa(baraja, mesa);
            if (toAdd.size() == 0) {
                reponerCartas(actual, baraja);
            } else {
                actual.meterCartasMano(toAdd);
            }
            md.addDescarte(actual.bajarCartasZonaJuego());
            if(actual.especiesDistintasZonaJuego() >=7){
                ganador = true;
            }
            if (actual.numCartasMano() == 0 && !reponerMano(actual, baraja)) {
                ganador = true;
            }
            System.out.println(actual);
            if (!mesa.rellenar(baraja)) {
                ganador = true;
            }

        }
        if (jugadores[turno].especiesDistintasZonaJuego() >= 7) {
            System.out.println("El ganador es: " + jugadores[turno]);
        } else {
            System.out.println(ganadoresNoCartas(jugadores));
        }

        // Se reparten las cartas

        // Empieza el juego

        // Jugador coloca en la mesa
        // Se comprueba si el jugador se ha quedado sin cartas
        // Se pregunta si quiere colocar cartas en la zona de juego
        // Si el jugador no es ganador, se comprueba si se ha quedado sin cartas
        // Se rellena la mesa

    }

    public static boolean reponerMano(Jugador j, Baraja b) {
        boolean toRet = true;
        if (b.size() < 8) {
            toRet = false;
        } else {
            List<Carta> aux = new LinkedList<>();
            for (int index = 0; index < 8; index++) {
                aux.add(b.sacarCarta());
            }
            j.meterCartasMano(aux);
        }
        return toRet;
    }

    public static boolean reponerCartas(Jugador j, Baraja b) {
        boolean toRet = true;
        if (quiereReponer()) {
            if (b.size() < 2) {
                toRet = false;
            } else {
                List<Carta> aux = new LinkedList<>();
                for (int index = 0; index < 2; index++) {
                    aux.add(b.sacarCarta());
                }
                j.meterCartasMano(aux);
            }
        }
        return toRet;

    }

    public static boolean quiereReponer() {
        String s;
        do {
            s = ES.pideCadena("¿Quieres coger dos cartas?(S: si, N: no)");
            if (!s.equalsIgnoreCase("s") && !s.equalsIgnoreCase("n")) {
                System.out.println("Opción no válida.");
            }
        } while (!s.equalsIgnoreCase("s") && !s.equalsIgnoreCase("n"));

        return s.equalsIgnoreCase("s");
    }

    public static List<Jugador> ganadoresNoCartas(Jugador[] j) {
        List<Jugador> toRet = new LinkedList<>();
        int maxBandadas = Integer.MIN_VALUE;
        for (int i = 0; i < j.length; i++) {
            if (maxBandadas < j[i].especiesDistintasZonaJuego()) {
                toRet.clear();
                maxBandadas = j[i].especiesDistintasZonaJuego();
                toRet.add(j[i]);
            } else if (maxBandadas == j[i].especiesDistintasZonaJuego()) {
                toRet.add(j[i]);
            }
        }
        return toRet;
    }

    public static int leeNumJugadores() {
        int toRet;
        do {
            toRet = ES.pideNumero("Introduce el numero de jugadores (debe estar entre 2 y 5):");
            if (toRet < 2 || toRet > 5) {
                System.out.println("El número de jugadores debe estar entre 2 y 5");
            }
        } while (toRet < 2 || toRet > 5);

        return toRet;

    }
}
