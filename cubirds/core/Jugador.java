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

    private String nombre;
    private Mano mano;
    private ZonaJuego zonaJuego;

    public static int numCartasEspecie(Carta c, List<Stack<Carta>> conjunto) {
        int cont = 0;
        for (Stack<Carta> q : conjunto) {
            if (q.peek().equals(c))
                cont = q.size();
        }
        return cont;
    }

    public static List<Carta> getCartasDistintas(List<Stack<Carta>> conjuntoCarta) {
        List<Carta> toRet = new LinkedList<>();
        for (Stack<Carta> q : conjuntoCarta) {
                toRet.add(q.peek());
        }
        return toRet;
    }

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
     * @author ivanr
     *
     *         Crea un jugador con su mano y su zona de juego
     *
     * @param b
     */
    public Jugador(String nombre, Baraja b) {
        this.nombre = nombre;
        this.mano = new Mano(b);
        this.zonaJuego = new ZonaJuego(b.sacarCarta());
    }

    /**
     * @author ivanr
     *
     *         Mete una lista de cartas a la mano
     *
     * @param c
     */
    public void meterCartasMano(List<Carta> c) {
        mano.anhadirCartas(c);
    }

    /**
     * @author ivanr
     *
     *         Saca una lista de cartas de la mano
     *
     * @param c
     * @return
     */
    public Stack<Carta> quitarCartasMano(Carta c) {
        return mano.eliminarCartas(c);
    }

    /**
     * @author ivanr
     *
     *         Devuelve el numero de cartas en la mano
     *
     * @return
     */
    public int numCartasMano() {
        return mano.numeroCartas();
    }

    public int numCartasZonaJuego() {
        return zonaJuego.getNumCartas();
    }

    /**
     * 
     * @return Se devuelven las cartas que pasaran al monton de descartes
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

    public int especiesDistintasZonaJuego() {
        return zonaJuego.getNumEspeciesZonaJuego();
    }

    /**
     * @author ivanr
     *
     *         Coloca una o varias cartas en la mesa
     *
     * @param b
     * @param m
     */
    public List<Carta> colocarMesa(Baraja b, Mesa m) {
        Carta carta = leerEspecie(getCartasDistintas(mano.mano));
        int fila = leerFila();
        boolean extremo = leerExtremo();
        return m.insertar(quitarCartasMano(carta), fila, extremo);
    }

    /**
     * @author ivanr
     *
     *         Aqui leo la fila de la mesa en la que voy a insertar la/s carta/s
     * @return
     */
    private int leerFila() {
        int fila = 0;
        do {
            fila = ES.pideNumero("\nIntroduce una fila(1...4)");
        } while (fila < 1 || fila > 4);

        return --fila;
    }

    private Carta leerEspecie(List<Carta> lista) {
        int especie = 0;
        do {
            especie = ES.pideNumero("Introduce la especie (): " + "(1-" + lista.size() + ")");
        } while (especie < 1 || especie > lista.size());
        return lista.get(especie - 1);
    }

    /**
     * @author ivanr
     *
     *         Aqui leo el extremo (que esta en la fila escogida de la mesa) en el
     *         que
     *         insertar mis cartas
     *
     * @param repite
     * @param num
     * @param extremo
     * @return
     */
    private boolean leerExtremo() {
        int num = 0;
        do {
            num = ES.pideNumero("\nQuieres poner la/s carta/s por la izquierda(0) o por la derecha(1): ");
        } while ((num < 0 || num > 1));

        return num == 1;
    }

    /**
     * @author ivanr
     *
     *         Devuelve un String con las cartas que tiene un jugador en la mano y
     *         en la
     *         mesa
     *
     * @return
     */
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

        public ZonaJuego(Carta carta) {
            zonaJuego = new LinkedList<>();
            insertarCarta(carta);
        }

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

        public int getNumCartas() {
            int cont = 0;
            for (Stack<Carta> s : zonaJuego) {
                cont += s.size();
            }
            return cont;
        }

        public int getNumEspeciesZonaJuego() {
            return zonaJuego.size();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            List<Carta> cartas = Jugador.getCartasDistintas(zonaJuego);
            for (Carta carta : cartas) {
                sb.append("   ·").append(carta).append(": ").append(Jugador.numCartasEspecie(carta, zonaJuego))
                        .append("\n");
            }
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

        public Mano(Baraja b) {
            mano = new LinkedList<>();
            cogerCartas(b);
        }

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

        public int numeroCartas() {
            int cont = 0;
            for (Stack<Carta> i : mano) {
                cont += i.size();
            }
            return cont;
        }

        public boolean bandadaPequenha() {
            return posibilidadesBandadas().size() != 0;
        }

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
            StringBuilder sb = new StringBuilder();
            int cont = 1;
            List<Carta> cartas = Jugador.getCartasDistintas(mano);
            for (Carta carta : cartas) {
                sb.append("   -").append(cont).append('.').append(carta).append(": ").append(Jugador.numCartasEspecie(carta, mano)).append("\n");
                cont++;
            }
            return sb.toString();
        }
    }
}
