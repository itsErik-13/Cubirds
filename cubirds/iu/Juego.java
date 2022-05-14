/*
 * Esta clase representa el funcionamiento general del juego
 */
package es.uvigo.esei.cubirds.iu;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
            List<Carta> toAdd = ponerCartasMesa(mesa, actual);
            // Si no recoge cartas se le permite tomar 2 de la baraja.
            if (toAdd.size() == 0) {
                reponerCartas(actual, baraja);
            } else {
                actual.meterCartasMano(toAdd);
            }

            // Se mira si el jugador puede bajar cartas a la zona de juego, de ser así se inserta 1 en la zona de juego y el resto en md.
            md.addDescarte(bajarCartas(actual));

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
            System.out.println(Jugador.ANSI_GREEN +"El ganador es: " + jugadores[turno].getNombre() + Jugador.ANSI_RESET);
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
            System.out.println("Se ha repuesto tu mano con 8 cartas");
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
                System.out.println("Has cogido :");
                for (Carta carta : aux) {
                    System.out.println(Jugador.ANSI_BLUE + carta.toStringEntero() + Jugador.ANSI_RESET);
                }
                System.out.println();
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
        int maxCartas = Integer.MIN_VALUE;
        for (int i = 0; i < j.length; i++) {
            if (maxCartas < j[i].numCartasZonaJuego()) {
                toRet.clear();
                maxCartas = j[i].numCartasZonaJuego();
                toRet.add(j[i]);
            } else if (maxCartas == j[i].numCartasZonaJuego()) {
                toRet.add(j[i]);
            }
        }
        System.out.println("No quedan suficientes cartas para continuar el juego\n");
        System.out.println(Jugador.ANSI_PURPLE + "Ganador/es: ");
        for (Jugador jugador : toRet) {
            System.out.println(Jugador.ANSI_PURPLE + "\t-" +jugador.getNombre());
        }
        System.out.println(Jugador.ANSI_RESET);
    }

    public static List<Carta> ponerCartasMesa(Mesa mesa, Jugador j){
        List<Carta> toRet = j.colocarMesa(leerEspecie(j.getCartasDistintasMano()), leerFila(), leerExtremo(), mesa);
        System.out.println("\nHas cogido:");
        for (Carta carta : toRet) {
            System.out.println(Jugador.ANSI_BLUE + carta.toStringEntero() + Jugador.ANSI_RESET);
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

    /**
     * 
     * @return Retorna la especie que se desea insertar en la mesa
     */
    private static Carta leerEspecie(List<Carta> lista) {
        int especie = 0;
        do {
            especie = ES.pideNumero("Introduce la especie (): " + "(1-" + lista.size() + ")");
        } while (especie < 1 || especie > lista.size());
        return lista.get(especie - 1);
    }

    /**
     * 
     * @return Retorna la fila en la que se quiere insertar la mesa
     */
    private static int leerFila() {
        int fila = 0;
        do {
            fila = ES.pideNumero("\nIntroduce una fila(1...4)");
        } while (fila < 1 || fila > 4);

        return --fila;
    }

    /**
     * 
     * @return Retorna el extremo a insertar la carta, true para derecha y false para izquierda
     */
    private static boolean leerExtremo() {
        int num = 0;
        do {
            num = ES.pideNumero("\nQuieres poner la/s carta/s por la izquierda(1) o por la derecha(2): ");
        } while ((num < 1 || num > 2));

        return num == 2;
    }

    /**
     * 
     * @return Devuelve cierto si el jugador quiere bajar cartas y falso en caso
     *         contrario
     */
    public static boolean quiereBajarCartas() {
        String s;
        do {
            s = ES.pideCadena("¿Quieres bajar cartas a la zona de juego?(S: si, N: no)");
            if (!s.equalsIgnoreCase("s") && !s.equalsIgnoreCase("n")) {
                System.out.println("Opción no válida.");
            }
        } while (!s.equalsIgnoreCase("s") && !s.equalsIgnoreCase("n"));

        return s.equalsIgnoreCase("s");
    }

    public static Stack<Carta> bajarCartas(Jugador j){
        Stack<Carta> toRet = new Stack<>();
        if (j.getMano().bandadaPequenha()) {
            int cont = 1;
            List<Carta> posibilidades = j.getMano().posibilidadesBandadas();
            System.out.println("Puede bajar las siguientes especies a la zona de juego: ");
            for (Carta carta : posibilidades) {
                System.out.println("   -" + cont + "." + carta);
            }
            if (quiereBajarCartas()) {
                Carta aBajar = leerEspecie(posibilidades);
                toRet = j.quitarCartasMano(aBajar);
                j.insertarCartasZonaJuego(toRet.pop());;
            }
        }
        return toRet;
    }
}
