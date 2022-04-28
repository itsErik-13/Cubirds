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
 */
package es.uvigo.esei.cubirds.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;

public class Jugador {

    private Mano mano;
    private ZonaJuego zonaJuego;

    /**
     * @author ivanr
     *
     *         Crea un jugador con su mano y su zona de juego
     *
     * @param b
     */
    public Jugador(Baraja b) {
        this.mano = new Mano(b);
        this.zonaJuego = new ZonaJuego();
    }

    public static int numCartasEspecie(Carta c, List<Carta> conjunto) {
        int cont = 0;
        for (Carta carta : conjunto) {
            if (carta.equals(c))
                cont++;
        }
        return cont;
    }
    

    /**
     * @author ivanr
     *
     *         Mete una lista de cartas a la mano
     *
     * @param c
     */
    public void meterCartas(List<Carta> c) {
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
    public List<Carta> quitarCartas(Carta c) {
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
        return zonaJuego.numeroCartas();
    }

    public int especiesDistintasZonaJuego() {
        return zonaJuego.especiesDistintas();
    }

    /**
     * @author ivanr
     *
     *         Coloca una o varias cartas en la mesa
     *
     * @param b
     * @param m
     * @param j
     */
    public void colocarMesa(Baraja b, Mesa m, Jugador j) {
        Carta carta = new Carta("", 0, 0);
        String cad = "";
        int fila = 1, num = 0;
        boolean extremo = false, repite = true;
        List<Carta> l = new LinkedList();
        List<Carta> s = new LinkedList();
        // List<Carta> r = new LinkedList();

        System.out.println("\t               ...Mostrando mesa...\n" + m);
        System.out.println("\t    ...Mostrando mano...\n" + toString());

        fila = leerFila(fila, repite);

        carta = leerEspecie(l, s, repite, carta, cad);

        extremo = leerExtremo(repite, num, extremo);

        m.insertar(mano.eliminarCartas(carta), fila, extremo, b, j);

        System.out.println("\t           ...Mostrando mesa cambiada...\n" + m);

        System.out.println("\t    ...Mostrando mano cambiada...\n" + toString());
    }

    /**
     * @author ivanr
     *
     *         Mete una carta en la zona de juego
     *
     * @param a
     */
    /**
     * @author ivanr
     *
     *         Aqui leo la fila de la mesa en la que voy a insertar la/s carta/s
     *
     * @param fila
     * @param repite
     * @return
     */
    private int leerFila(int fila, boolean repite) {
        do {
            try {
                repite = true;
                fila = pideNumero("\nIntroduce una fila(1...4)");
            } catch (NumberFormatException e) {
                System.err.println("Introduce un entero, cabezon");
                repite = false;
            }
            if (fila < 1 || fila > 4) {
                System.err.println("Fila incorrecta");
                repite = false;
            }
        } while ((fila < 1 || fila > 4) || !repite);

        fila--;
        return fila;
    }

    /**
     * @author ivanr
     *
     *         Aqui leo el tipo de pajaro a insertar de mi mano a la mesa
     *
     * @param l
     * @param s
     * @param repite
     * @param carta2
     */
    private Carta leerEspecie(List<Carta> l, List<Carta> s, boolean repite, Carta carta1, String cad) {
        Carta carta2 = null;

        anhadirTiposPajaros(l);
        System.out.println("\t...Mostrando tipos de pajaros que tienes...\n");

        for (int i = 0; i < mano.tiposParaJugador(l, s).size(); i++) {
            System.out.println(mano.tiposParaJugador(l, s).get(i).toString());
        }

        do {
            repite = false;
            cad = pideCadena("\nIntroduce el tipo de pajaro que quieres meter en la fila");
            for (int i = 0; i < mano.tiposParaJugador(l, s).size(); i++) {
                carta1.setEspecie(cad);
                if (carta1.getEspecie()
                        .equals(mano.tiposParaJugador(l, s).get(i).getEspecie().replace("[", "").replace("]", ""))
                        || carta1.getEspecie().equals(mano.tiposParaJugador(l, s).get(i).getEspecie().replace("[", "")
                                .replace("]", "").toLowerCase())) {
                    repite = true;
                }
            }
            if (!repite) {
                System.err.println("Tipo de pajaro incorrecto");
            }
        } while (!repite);
        switch (cad) {
            case "flamenco":
                carta2 = new Carta("[Flamenco]", 2, 3);
                break;
            case "lechuza":
                carta2 = new Carta("[Lechuza]", 3, 4);
                break;
            case "tucan":
                carta2 = new Carta("[Tucan]", 3, 4);
                break;
            case "guacamayo":
                carta2 = new Carta("[Guacamayo]", 4, 6);
                break;
            case "pato":
                carta2 = new Carta("[Pato]", 4, 6);
                break;
            case "urraca":
                carta2 = new Carta("[Urraca]", 5, 7);
                break;
            case "curruca de caña":
                carta2 = new Carta("[Curruca de caña]", 6, 9);
                break;
            case "petirrojo":
                carta2 = new Carta("[Petirrojo]", 6, 9);
                break;
            default:
                System.out.println("No se encuentra pajaro");
                break;
        }

        return carta2;
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
    private boolean leerExtremo(boolean repite, int num, boolean extremo) {

        do {
            try {
                repite = true;
                num = pideNumero("\n¿Quieres poner la/s carta/s por la derecha(1) o por la izquierda(0)?");
                if (num == 1) {
                    extremo = true;
                }
            } catch (NumberFormatException e) {
                System.err.println("Introduce un entero, cabezon");
                repite = false;
            }
            if ((num < 0 || num > 1)) {
                System.err.println("O 0 o 1 marica");
                repite = false;
            }
        } while ((num < 0 || num > 1) || !repite);

        return extremo;
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
        StringBuilder sb = new StringBuilder();

        if (numCartasMano() == 1) {
            sb.append("\nTienes ").append(numCartasMano()).append(" carta en la mano: ").append("\n");
            sb.append(mano.toString()).append("\n");
        } else {
            sb.append("\nTienes ").append(numCartasMano()).append(" cartas en la mano: ").append("\n");
            sb.append(mano.toString()).append("\n");
        }

        /*
         * if (numCartasZonaJuego() == 1) {
         * sb.append("\nTienes ").append(numCartasZonaJuego()).
         * append(" carta en la zona de juego: ").append("\n");
         * sb.append(zonaJuego.toString()).append("\n");
         * }
         * else {
         * sb.append("\nTienes ").append(numCartasZonaJuego()).
         * append(" cartas en la zona de juego: ").append("\n");
         * sb.append(zonaJuego.toString()).append("\n");
         * }
         */
        return sb.toString();
    }

    private void anhadirTiposPajaros(List<Carta> c) {
        c.add(new Carta("[Flamenco]", 2, 3));
        c.add(new Carta("[Lechuza]", 3, 4));
        c.add(new Carta("[Tucan]", 3, 4));
        c.add(new Carta("[Guacamayo]", 4, 6));
        c.add(new Carta("[Pato]", 4, 6));
        c.add(new Carta("[Urraca]", 5, 7));
        c.add(new Carta("[Curruca de caña]", 6, 9));
        c.add(new Carta("[Petirrojo]", 6, 9));
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
        private List<Carta> conjuntoCarta;

        public ZonaJuego() {
            conjuntoCarta = new LinkedList<>();
        }

        public void insertarCarta(Carta c) {
            conjuntoCarta.add(c);
        }

        public int getNumCartas() {
            return conjuntoCarta.size();
        }
<<<<<<< HEAD
        
        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            for(Carta i: conjuntoCarta){
                sb.append(i);
=======

        public int getNumCartasDistintas() {
            return getCartasDistintas().size();
        }

        public List<Carta> getCartasDistintas() {
            List<Carta> toRet = new LinkedList<>();
            for (Carta carta : conjuntoCarta) {
                if (!toRet.contains(carta)) {
                    toRet.add(carta);
                }
>>>>>>> 41f19fbbc27200f504441c0312384014f932faac
            }
            return toRet;
        }

        @Override
        public String toString() {
            return conjuntoCarta.toString();
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
        private List<Carta> mano;

        public Mano(Baraja b) {
            mano = new ArrayList<>();

        }

        public void anhadirCartas(List<Carta> c) {
            mano.addAll(c);
        }

        public List<Carta> eliminarCartas(Carta carta) {
            List<Carta> toRet = new ArrayList<>();
            for (Carta i : mano) {
                if (i.equals(carta)) {
                    toRet.add(i);
                    mano.remove(i);
                }
            }

            return toRet;
        }

        public int numeroCartas() {
            return mano.size();
        }

        public boolean bandadaPequenha(Carta c) {
            boolean toRet = false;
            for (int i = 0; i < Carta.especies.length; i++) {
                if (c.getEspecie().equals(Carta.especies[i]) && Jugador.numCartasEspecie(c, mano) >= Carta.bandadasP[i]) 
                    toRet = true;
            }
            return toRet;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Carta carta : mano) {
                sb.append(carta);
            }

            return sb.toString();
        }
    }
}
