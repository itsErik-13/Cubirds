/*
 * Esta clase representa la mesa común a todos los jugadores. Funcionalidades a implementar:
 * - Constructor que cree la mesa
 * - Colocar la mesa inicial cumpliendo las restricciones
 * - Rellenar fila de la mesa cumpliendo las restricciones
 * - Insertar cartas
 * - Eliminar cartas rodeadas
 * - Mostrar mesa
 */
package es.uvigo.esei.cubirds.core;

import java.util.List;
import java.util.LinkedList;

public class Mesa {

    private final int MAX_FILAS = 4;
    private List<Carta>[] mesa;

    public Mesa() {
        this.mesa = new LinkedList[MAX_FILAS];
        for (int i = 0; i < mesa.length; i++) {
            mesa[i] = new LinkedList<>();
        }
    }

    /**
     * Coloca la mesa inicial cumpliendo las restricciones
     */
    public void colocarMesaInicial(Baraja baraja) {
        for (int i = 0; i < MAX_FILAS; i++) {
            while (mesa[i].size() < 3) {
                Carta cartaIntroducir = baraja.sacarCarta();

                if (mesa[i].contains(cartaIntroducir)) {
                    baraja.insertarCarta(cartaIntroducir);
                } else {
                    mesa[i].add(cartaIntroducir);
                }

            }
        }
    }

    /**
     * 
     * @param carta   Lista de cartas a insertar
     * @param fila    Numero de la fila en la que se va a insertar, de 0 a 3
     * @param extremo Extremo por el que se va a insertar, true para derecha y false
     *                para izquierda
     * @param baraja
     * @return Devuelve las cartas rodeadas al introducir
     */
    public Stack<Carta> insertar(Stack<Carta> carta, int fila, boolean extremo) {
        Stack<Carta> toRet = cartasRodeadas(carta.peek(), fila, extremo);
        if (extremo) {
            mesa[fila].addAll(carta);
        } else {
            mesa[fila].addAll(0, carta);
        }
        return toRet;
    }

    /**
     * 
     * @param carta
     * @param fila
     * @param extremo
     * @return Devuelve las cartas que se encuentran entre una carta y la más
     *         proxima de esa especie por el correspondiente extremo
     */

     //Cambiar para que devuelva un stack
    public Stack<Carta> cartasRodeadas(Carta carta, int fila, boolean extremo) {
        List<Carta> toRet = new LinkedList<>();
        if (mesa[fila].contains(carta)) {
            if (extremo) {
                for (int i = mesa[fila].size() - 1; i >= 0 && !mesa[fila].get(i).equals(carta); i--) {
                    toRet.add(mesa[fila].get(i));
                }
            } else {
                for (int i = 0; i < mesa[fila].size() && !mesa[fila].get(i).equals(carta); i++) {
                    toRet.add(mesa[fila].get(i));
                }
            }
            for (Carta cartasRodeadas : toRet) {
                mesa[fila].remove(cartasRodeadas);
            }
        }
        return toRet;
    }

    /**
     * 
     * @param baraja
     * @param fila   La fila que se va a rellenar, si es necesario
     * @return Devuelve true si se ha rellenado la fila, false en caso contrario, de
     *         modo que se puede usar para
     *         acabar la partida si no puede rellenarse la fila
     */
    public boolean rellenar(Baraja baraja) {
        for (int fila = 0; fila < MAX_FILAS; fila++) {
            boolean rellenar = true;
            for (int i = 0; i < mesa[fila].size() - 1; i++) {
                if (!mesa[fila].get(i).equals(mesa[fila].get(i + 1)))
                    rellenar = false;
            }
            for (int i = 0; i < baraja.size() && rellenar; i++) {
                Carta cartaIntroducir = baraja.sacarCarta();
                if (cartaIntroducir.equals(mesa[fila].get(0))) {
                    baraja.insertarCarta(cartaIntroducir);
                } else {
                    mesa[fila].add(cartaIntroducir);
                    rellenar = false;
                }
            }
            if (rellenar) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Mesa: \n");
        for (int i = 0; i < MAX_FILAS; i++) {
            for (Carta carta : mesa[i]) {
                sb.append(carta);
                sb.append(carta.getEspecie().equals("Curruca de caña") ? "\t" : "\t\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
