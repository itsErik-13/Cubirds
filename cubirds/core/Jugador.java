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

import es.uvigo.esei.cubirds.iu.ES;

public class Jugador {

    //Colores: 
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
     * @param conjuntoCarta
     * @return Devuelve el numero de especies de un conjunto
     */
    public static List<Carta> getCartasDistintas(List<Stack<Carta>> conjuntoCarta) {
        List<Carta> toRet = new LinkedList<>();
        for (Stack<Carta> q : conjuntoCarta) {
                toRet.add(q.peek());
        }
        return toRet;
    }

    /**
     * 
     * @param carta
     * @param conjunto
     * @return Retorna cierto si la carta está en el conjunto y falso en caso contrario
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

    /**
     * 
     * @param nombre
     * @param b
     * Crea al jugador
     */
    public Jugador(String nombre, Baraja b) {
        this.nombre = nombre;
        this.mano = new Mano(b);
        this.zonaJuego = new ZonaJuego(b.sacarCarta());
    }
    
    /**
     * Introduce cartas en la mano
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
     * Devuelve el numero de cartas en la mano
     * @return
     */
    public int numCartasMano() {
        return mano.numeroCartas();
    }

    public int numCartasZonaJuego() {
        return zonaJuego.getNumCartas();
    }

    /**
     * Introduce cartas en la zona de juego (si el jugador quiere)
     * @return Devuelve la pila que se va a introducir en el montón de descartes
     */
    public Stack<Carta> bajarCartasZonaJuego(){
        Stack<Carta> toRet = new Stack<>();
        if(mano.bandadaPequenha()){
            int cont = 1;
            List<Carta> posibilidades = mano.posibilidadesBandadas();
            System.out.println("Puede bajar las siguientes especies a la zona de juego: ");
            for (Carta carta : posibilidades) {
                System.out.println("   -" + cont + "." + carta);
            }
            if(quiereBajarCartas()){
                Carta aBajar = leerEspecie(posibilidades);
                toRet = quitarCartasMano(aBajar);
                zonaJuego.insertarCarta(toRet.pop());
            }
        }
        return toRet;
    }

    /**
     * 
     * @return Devuelve cierto si el jugador quiere bajar cartas y falso en caso contrario
     */
    public boolean quiereBajarCartas(){
        String s;
        do {
            s = ES.pideCadena("¿Quieres bajar cartas a la zona de juego?(S: si, N: no)");
            if (!s.equalsIgnoreCase("s") && !s.equalsIgnoreCase("n")) {
                System.out.println("Opción no válida.");
            }
        } while (!s.equalsIgnoreCase("s") && !s.equalsIgnoreCase("n"));

        return s.equalsIgnoreCase("s");
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
    public List<Carta> colocarMesa(Baraja b, Mesa m) {
        Carta carta = leerEspecie(getCartasDistintas(mano.mano));
        int fila = leerFila();
        boolean extremo = leerExtremo();
        return m.insertar(quitarCartasMano(carta), fila, extremo);
    }

    /**
     * 
     * @return Retorna la fila en la que se quiere insertar la mesa
     */
    private int leerFila() {
        int fila = 0;
        do {
            fila = ES.pideNumero("\nIntroduce una fila(1...4)");
        } while (fila < 1 || fila > 4);

        return --fila;
    }

    /**
     * 
     * @param lista
     * @return Retorna la especie que se quiere insertar en la mesa
     */
    private Carta leerEspecie(List<Carta> lista) {
        int especie = 0;
        do {
            especie = ES.pideNumero("Introduce la especie (): " + "(1-" + lista.size() + ")");
        } while (especie < 1 || especie > lista.size());
        return lista.get(especie - 1);
    }

    /**
     * 
     * @return Retorna el extremo en el que se introducen las cartas
     */
    private boolean leerExtremo() {
        int num = 0;
        do {
            num = ES.pideNumero("\nQuieres poner la/s carta/s por la izquierda(0) o por la derecha(1): ");
        } while ((num < 0 || num > 1));

        return num == 1;
    }

    /**
     * 
     * @return Retorna el nombre del jugador
     */
    public String getNombre(){
        return nombre;
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
         * @param carta
         */
        public ZonaJuego(Carta carta) {
            zonaJuego = new LinkedList<>();
            insertarCarta(carta);
        }

        /**
         * Inserta la carta en la zona de juego
         * @param c
         */
        public void insertarCarta(Carta c) {
            if(Jugador.entaEnConjuntoPilas(c,zonaJuego)){
                for (Stack<Carta> s : zonaJuego) {
                    if (s.peek().equals(c)) {
                        s.add(c);
                    }
                }
            }
            else{
                Stack<Carta> s = new Stack<>();
                s.add(c);
                zonaJuego.add(s);
            }
        }

        /**
         * Devuelve el numero de cartas que hay en la zona de juego
         * @return
         */
        public int getNumCartas() {
            int cont = 0;
            for (Stack<Carta> s : zonaJuego) {
                cont += s.size();
            }
            return cont;
        }

        /**
         * Devuelve el numero de especies distintas en la zona de juego
         * @return
         */
        public int getNumEspeciesZonaJuego() {
            return zonaJuego.size();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(ANSI_RED);
            List<Carta> cartas = Jugador.getCartasDistintas(zonaJuego);
            for (Carta carta : cartas) {
                sb.append("   ·").append(carta).append(": ").append(Jugador.numCartasEspecie(carta, zonaJuego))
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

    private class Mano {
        private List<Stack<Carta>> mano;

        /**
         * Crea la mano introduciendo 8 cartas
         * @param b
         */
        public Mano(Baraja b) {
            mano = new LinkedList<>();
            cogerCartas(b);
        }
        /**
         * Coge 8 cartas de la baraja y las introduce en la mano
         * @param b
         * @return Retorna true si se pueden coger cartas y false en caso contrario
         */
        public boolean cogerCartas(Baraja b) {
            if (b.size() < 8) {
                return false;
            } else {
                for (int i = 0; i < 8; i++) {
                    Carta card = b.sacarCarta();
                    if (Jugador.entaEnConjuntoPilas(card,mano)) {
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
         * @param c
         */
        public void anhadirCartas(List<Carta> c) {
            while(!c.isEmpty()){
                Carta card=c.get(0);
                c.remove(card);
                if(Jugador.entaEnConjuntoPilas(card,mano)){
                    for (Stack<Carta> s : mano) {
                        if (s.peek().equals(card)) {
                            s.add(card);
                        }
                    }
                }
                else{
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
         * @return
         */
        public int numeroCartas() {
            int cont = 0;
            for (Stack<Carta> i : mano) {
                cont += i.size();
            }
            return cont;
        }

        /**
         * 
         * @return Retorna true si el jugador puede bajar cartas a la zona de juego y false en caso contrario
         */
        public boolean bandadaPequenha() {
            return posibilidadesBandadas().size() != 0;
        }

        /**
         * 
         * @return Devuelve la lista de cartas de las cuales el jugador puede hacer bandada
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

        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(ANSI_BLUE);
            int cont = 1;
            List<Carta> cartas = Jugador.getCartasDistintas(mano);
            for (Carta carta : cartas) {
                sb.append("   -").append(cont).append('.').append(carta.toStringEntero()).append(": ").append(Jugador.numCartasEspecie(carta, mano)).append("\n");
                cont++;
            }
            sb.append(ANSI_RESET);
            return sb.toString();
        }
    }
}
