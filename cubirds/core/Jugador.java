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

import java.util.List;

import es.uvigo.esei.cubirds.iu.ES;

import java.util.LinkedList;

public class Jugador {

    private String nombre;
    private Mano mano;
    private ZonaJuego zonaJuego;

    public static int numCartasEspecie(Carta c, List<Carta> conjunto) {
        int cont = 0;
        for (Carta carta : conjunto) {
            if (carta.equals(c))
                cont++;
        }
        return cont;
    }

    public static List<Carta> getCartasDistintas(List<Carta> conjuntoCarta) {
        List<Carta> toRet = new LinkedList<>();
        for (Carta carta : conjuntoCarta) {
            if (!toRet.contains(carta)) {
                toRet.add(carta);
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
    public List<Carta> quitarCartasMano(Carta c) {
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

    public int especiesDistintasZonaJuego() {
        return zonaJuego.getNumCartasDistintas();
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
        Carta carta = leerEspecie();
        int fila = leerFila();
        boolean extremo = leerExtremo();
        return m.insertar(mano.eliminarCartas(carta), fila, extremo);
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

    private Carta leerEspecie() {
        List<Carta> cartasDistintas = Jugador.getCartasDistintas(mano.mano);
        int especie = 0;
        do {
            especie = ES.pideNumero("Introduce la especie: ");
        } while (especie < 1 || especie > cartasDistintas.size());
        return cartasDistintas.get(especie - 1);
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
        private List<Carta> conjuntoCarta;

        public ZonaJuego(Carta carta) {
            conjuntoCarta = new LinkedList<>();
            insertarCarta(carta);
        }

        public void insertarCarta(Carta c) {
            conjuntoCarta.add(c);
        }

        public int getNumCartas() {
            return conjuntoCarta.size();
        }

        public int getNumCartasDistintas() {
            return Jugador.getCartasDistintas(conjuntoCarta).size();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            List<Carta> cartas = Jugador.getCartasDistintas(conjuntoCarta);
            for (Carta carta : cartas) {
                sb.append("   ·").append(carta).append(": ").append(Jugador.numCartasEspecie(carta, conjuntoCarta))
                        .append("\n");
            }
            return sb.toString();
        }

        public String toStringRepetidos() {
            StringBuilder sb = new StringBuilder();
            for (Carta carta : conjuntoCarta) {
                sb.append("   ·").append(carta).append(" ");
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
        private List<Carta> mano;

        public Mano(Baraja b) {
            mano = new LinkedList<>();

            cogerCartas(b);

        }

        public boolean cogerCartas(Baraja b) {
            if (b.size() < 8) {
                return false;
            } else {
                for (int i = 0; i < 8; i++) {
                    mano.add(b.sacarCarta());
                }
                return true;
            }
        }

        public void anhadirCartas(List<Carta> c) {
            mano.addAll(c);
        }

        public List<Carta> eliminarCartas(Carta carta) {
            List<Carta> toRet = new LinkedList<>();
            for (Carta i : mano) {
                if (i.equals(carta)) {
                    toRet.add(i);
                }
            }
            mano.removeAll(toRet);
            return toRet;
        }

        public int numeroCartas() {
            return mano.size();
        }

        public boolean bandadaPequenha() {
            return posibilidadesBandadas().size() != 0;
        }

        public List<Carta> posibilidadesBandadas() {
            List<Carta> a = new LinkedList<>();
            for (Carta carta : mano) {
                if (!a.contains(carta) && Jugador.numCartasEspecie(carta, mano) >= carta.getBandadaP())
                    a.add(carta);
            }

            return a;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            List<Carta> cartas = Jugador.getCartasDistintas(mano);
            for (Carta carta : cartas) {
                sb.append("   ·").append(carta).append(": ").append(Jugador.numCartasEspecie(carta, mano)).append("\n");
            }
            return sb.toString();
        }

        public String toStringDistintas() {
            StringBuilder sb = new StringBuilder();
            for (Carta carta : mano) {
                sb.append("   ·").append(carta);
            }
            return sb.toString();
        }

    }
}
