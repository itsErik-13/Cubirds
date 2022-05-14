/*
 * Esta clase representa a cada jugador. Tendrá las siguientes funcionalidades
 * - Un constructor para crear el jugador
 * - Añadir y eliminar cartas de la mano
 * - Colocar cartas en la mesa
 * - Colocar cartas en su zona de juego
 * - Número decartas en la mano
 * - Número de cartas en la zona de juego
 * - Número de especies distintas en la zona de juego
 * - Mostrar mano, zona de juego del jugador
 * -To do: cambiar zona juego y mano.
 */
package es.uvigo.esei.cubirds.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Jugador {

    // Colores:
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    private String nombre;
    private Mano mano;
    private ZonaJuego zonaJuego;

    /**
     * 
     * @param c
     * @param conjunto
     * @return Devuelve el numero de cartas que hay de la especie c en el conjunto.
     */
    public static int numCartasEspecie(Carta c, List<Stack<Carta>> conjunto) {
        int cont = 0;
        for (Stack<Carta> q : conjunto) {
            if (q.peek().equals(c))
                cont = q.size();
        }
        return cont;
    }

    /**
     * 
     * @param carta
     * @param conjunto
     * @return Retorna cierto si la carta está en el conjunto y falso en caso
     *         contrario
     */
    public static boolean entaEnConjuntoPilas(Carta carta, List<Stack<Carta>> conjunto) {
        boolean toRet = false;
        for (Stack<Carta> i : conjunto) {
            if (!i.isEmpty()) {
                if (i.peek().equals(carta)) {
                    toRet = true;
                }
            }
        }
        return toRet;
    }

    public List<Carta> getCartasDistintasMano() {
        return mano.getCartasDistintas();
    }

    public List<Carta> getCartasDistintasZonaJuego() {
        return zonaJuego.getCartasDistintas();
    }

    /**
     * 
     * @param nombre
     * @param b
     *               Crea al jugador
     */
    public Jugador(String nombre, Baraja b) {
        this.nombre = nombre;
        this.mano = new Mano(b);
        this.zonaJuego = new ZonaJuego(b.sacarCarta());
    }

    /**
     * Introduce cartas en la mano
     * 
     * @param c
     */
    public void meterCartasMano(List<Carta> c) {
        mano.anhadirCartas(c);
    }

    /**
     * 
     * @param c
     * @return Devuelve la pila de cartas que se quita de la mano
     */
    public Stack<Carta> quitarCartasMano(Carta c) {
        return mano.eliminarCartas(c);
    }

    /**
     * @author ivanr
     *         Devuelve el numero de cartas en la mano
     * @return
     */
    public int numCartasMano() {
        return mano.numeroCartas();
    }

    public int numCartasZonaJuego() {
        return zonaJuego.getNumCartas();
    }

    /**
     * Inserta una carta en la zona de juego
     * 
     */
    public void insertarCartasZonaJuego(Carta c) {
        zonaJuego.insertarCarta(c);
    }

    /**
     * 
     * @return Devuelve el numero de especies distintas de la zona juego
     */
    public int especiesDistintasZonaJuego() {
        return zonaJuego.getNumEspeciesZonaJuego();
    }

    /**
     * 
     * @param b
     * @param m
     * @return Introduce cartas en la mesa y retorna las cartas rodeadas
     */
    public List<Carta> colocarMesa(Carta carta, int fila, boolean extremo, Mesa m) {
        return m.insertar(quitarCartasMano(carta), fila, extremo);
    }

    /**
     * 
     * @return Retorna el nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    public Mano getMano() {
        return mano;
    }

    public ZonaJuego getZonaJuego() {
        return zonaJuego;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.nombre);

        if (numCartasMano() == 1) {
            sb.append("\nTienes ").append(numCartasMano()).append(" carta en la mano: ").append("\n\n");
            sb.append(mano.toString()).append("\n");
        } else {
            sb.append("\nTienes ").append(numCartasMano()).append(" cartas en la mano: ").append("\n\n");
            sb.append(mano.toString()).append("\n");
        }

        if (numCartasZonaJuego() == 1) {
            sb.append("\nTienes ").append(numCartasZonaJuego()).append(" carta en la zona de juego: ").append("\n\n");
            sb.append(zonaJuego.toString()).append("\n");
        } else {
            sb.append("\nTienes ").append(numCartasZonaJuego()).append(" cartas en la zona de juego: ").append("\n\n");
            sb.append(zonaJuego.toString()).append("\n");
        }

        return sb.toString();
    }

    /*
     * Esta clase representa la zona de juego de un jugador. Tendrá las siguientes
     * funcionalidades
     * - Un constructor para crear la zona de juego
     * - añadir cartas
     * - Número de cartas
     * - Número de especies distintas
     * - mostrar zona de juego
     */
    private static class ZonaJuego {
        private List<Stack<Carta>> zonaJuego;

        /**
         * Crea la zona de juego insertando una carta
         * 
         * @param carta
         */
        public ZonaJuego(Carta carta) {
            zonaJuego = new LinkedList<>();
            insertarCarta(carta);
        }

        /**
         * Inserta la carta en la zona de juego
         * 
         * @param c
         */
        public void insertarCarta(Carta c) {
            if (Jugador.entaEnConjuntoPilas(c, zonaJuego)) {
                for (Stack<Carta> s : zonaJuego) {
                    if (s.peek().equals(c)) {
                        s.add(c);
                    }
                }
            } else {
                Stack<Carta> s = new Stack<>();
                s.add(c);
                zonaJuego.add(s);
            }
        }

        /**
         * Devuelve el numero de cartas que hay en la zona de juego
         * 
         * @return
         */
        private int getNumCartas() {
            int cont = 0;
            for (Stack<Carta> s : zonaJuego) {
                cont += s.size();
            }
            return cont;
        }

        /**
         * Devuelve el numero de especies distintas en la zona de juego
         * 
         * @return
         */
        private int getNumEspeciesZonaJuego() {
            return zonaJuego.size();
        }

        private List<Carta> getCartasDistintas() {
            List<Carta> toRet = new LinkedList<>();
            for (Stack<Carta> q : zonaJuego) {
                toRet.add(q.peek());
            }
            return toRet;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            List<Carta> cartas = getCartasDistintas();
            for (Carta carta : cartas) {
                sb.append(ANSI_RED).append("   ·").append(carta).append(": ").append(Jugador.numCartasEspecie(carta, zonaJuego))
                        .append("\n");
            }
            sb.append(ANSI_RESET);
            return sb.toString();
        }
    }

    /*
     * Esta clase representa la mano de un jugador. Tendrá las siguientes
     * funcionalidades
     * - Un constructor para crear la mano
     * - añadir cartas
     * - eliminar cartas
     * - número de cartas
     * - comprobar si hay cartas suficientes para bandada pequeña
     * - mostrar mano
     */

    public class Mano {
        private List<Stack<Carta>> mano;

        /**
         * Crea la mano introduciendo 8 cartas
         * 
         * @param b
         */
        public Mano(Baraja b) {
            mano = new LinkedList<>();
            cogerCartas(b);
        }

        /**
         * Coge 8 cartas de la baraja y las introduce en la mano
         * 
         * @param b
         * @return Retorna true si se pueden coger cartas y false en caso contrario
         */
        public boolean cogerCartas(Baraja b) {
            if (b.size() < 8) {
                return false;
            } else {
                for (int i = 0; i < 8; i++) {
                    Carta card = b.sacarCarta();
                    if (Jugador.entaEnConjuntoPilas(card, mano)) {
                        for (Stack<Carta> s : mano) {
                            if (s.peek().equals(card)) {
                                s.add(card);
                            }
                        }
                    } else {
                        Stack<Carta> c = new Stack<>();
                        c.add(card);
                        mano.add(c);
                    }
                }
                return true;
            }
        }

        /**
         * Añade una lista de cartas a a mano
         * 
         * @param c
         */
        public void anhadirCartas(List<Carta> c) {
            while (!c.isEmpty()) {
                Carta card = c.get(0);
                c.remove(card);
                if (Jugador.entaEnConjuntoPilas(card, mano)) {
                    for (Stack<Carta> s : mano) {
                        if (s.peek().equals(card)) {
                            s.add(card);
                        }
                    }
                } else {
                    Stack<Carta> s = new Stack<>();
                    s.add(card);
                    mano.add(s);
                }
            }
        }

        /**
         * 
         * @param carta
         * @return Devuelve la pila de cartas de la especie que se pasa
         */
        public Stack<Carta> eliminarCartas(Carta carta) {
            Stack<Carta> toRet = new Stack<>();
            for (Stack<Carta> c : mano) {
                if (c.contains(carta)) {
                    toRet = c;
                }
            }
            mano.remove(toRet);
            return toRet;
        }

        /**
         * Devuelve el numero de cartas que hay en la mano
         * 
         * @return
         */
        private int numeroCartas() {
            int cont = 0;
            for (Stack<Carta> i : mano) {
                cont += i.size();
            }
            return cont;
        }

        /**
         * 
         * @return Retorna true si el jugador puede bajar cartas a la zona de juego y
         *         false en caso contrario
         */
        public boolean bandadaPequenha() {
            return posibilidadesBandadas().size() != 0;
        }

        /**
         * 
         * @return Devuelve la lista de cartas de las cuales el jugador puede hacer
         *         bandada
         */
        public List<Carta> posibilidadesBandadas() {
            List<Carta> a = new LinkedList<>();
            for (Stack<Carta> c : mano) {
                if (!a.contains(c.peek()) && c.size() >= c.peek().getBandadaP()) {
                    a.add(c.peek());
                }
            }
            return a;
        }

        private List<Carta> getCartasDistintas() {
            List<Carta> toRet = new LinkedList<>();
            for (Stack<Carta> q : mano) {
                toRet.add(q.peek());
            }
            return toRet;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            int cont = 1;
            List<Carta> cartas = getCartasDistintas();
            for (Carta carta : cartas) {
                sb.append(ANSI_BLUE).append("   -").append(cont).append('.').append(carta.toStringEntero()).append(": ")
                        .append(Jugador.numCartasEspecie(carta, mano)).append("\n");
                cont++;
            }
            sb.append(ANSI_RESET);
            return sb.toString();
        }

    }

}
