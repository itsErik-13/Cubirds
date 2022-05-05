/*
 * Esta clase representa el funcionamiento general del juego
 */
package es.uvigo.esei.cubirds.iu;

import java.util.LinkedList;
import java.util.List;


import es.uvigo.esei.cubirds.core.*;

public class Juego {

    public static void inicio(){

        /// Leer jugadores:
        int numJugadores = leeNumJugadores();

        Jugador[] jugadores = new Jugador[numJugadores];
        // Se crea la baraja
        Baraja baraja = new Baraja();

        // Se crea el montón de descartes
        MontonDescartes md = new MontonDescartes();

        // Se crean los jugadores y reparten las cartas
        for (int i = 0; i < numJugadores; i++) {
            jugadores[i] = new Jugador(ES.pideCadena("\nIntroduce el nombre del jugador " + (i + 1) + ":"), baraja);
        }

        // Se crea la mesa inicial
        Mesa mesa = new Mesa();
        mesa.colocarMesaInicial(baraja);

        // Se elige el jugador que empezará el juego
        int turno = (int) (Math.random() * numJugadores);

        boolean ganador = false;

        while (!ganador) {
            turno = ++turno % numJugadores;
            Jugador actual = jugadores[turno];

            // Se visualiza la mesa y los datos del jugador que va a jugar
            System.out.println(mesa);
            System.out.println(actual);
            
            // El jugador coloca las cartas en la mesa y recibe las que están rodeadas
            List<Carta> toAdd = actual.colocarMesa(baraja, mesa);

            // Si no recoge cartas se le permite tomar 2 de la baraja.
            if (toAdd.size() == 0) {
                reponerCartas(actual, baraja);
            } else {
                actual.meterCartasMano(toAdd);
            }

            // Se mira si el jugador puede bajar cartas a la zona de juego, de ser así se inserta 1 en la zona de juego y el resto en md.
            md.addDescarte(actual.bajarCartasZonaJuego());

            // Se comprueba si el jugador ya tiene las 7 especies distintas necesarias para ganar el juego
            if(actual.especiesDistintasZonaJuego() >=7){
                ganador = true;
            }

            // Si tiene 0 cartas se intenta reponer con 8, de no ser posible se termina la partida
            if (actual.numCartasMano() == 0 && !reponerMano(actual, baraja)) {
                ganador = true;
            }

            // Se muestran los datos actualizados del jugador
            System.out.println(actual);

            // Se rellena la mesa de ser necesario, si no es posible se termina el juego
            if (!mesa.rellenar(baraja)) {
                ganador = true;
            }

        }

        // Se comprueba quién ha ganado la partida
        if (jugadores[turno].especiesDistintasZonaJuego() >= 7) {
            System.out.println(Jugador.ANSI_GREEN +"El ganador es: " + jugadores[turno] + Jugador.ANSI_RESET);
        } else {
            ganadoresNoCartas(jugadores);
        }

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

    public static void ganadoresNoCartas(Jugador[] j) {
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
        System.out.println(Jugador.ANSI_PURPLE + "Ganador/es: ");
        for (Jugador jugador : toRet) {
            System.out.println(Jugador.ANSI_PURPLE + "\t-" +jugador.getNombre());
        }
        System.out.println(Jugador.ANSI_RESET);
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
