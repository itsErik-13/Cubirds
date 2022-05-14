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
import java.util.Stack;
import java.util.LinkedList;

public class Mesa {

    private final int MAX_FILAS = 4;
    private final int NUM_CARTAS_FILA = 3;

    // DEBE DE SER MENOR QUE EL NUMERO DE CARTAS Y NUMERO DE ESPECIES(COMO ES OBVIO)
    private final int ESPECIES_DISTINTAS_INICIAL = 3;
    private final int ESPECIES_DISTINTAS_JUEGO = 2;
    private List<Carta>[] mesa;

    public Mesa() {
        this.mesa = new List[MAX_FILAS];
        for (int i = 0; i < mesa.length; i++) {
            mesa[i] = new LinkedList<>();
        }
    }

    /**
     * Coloca la mesa inicial cumpliendo las restricciones
     */
    public void colocarMesaInicial(Baraja baraja) {
        for (int i = 0; i < MAX_FILAS; i++) {
            while (mesa[i].size() < ESPECIES_DISTINTAS_INICIAL) {
                Carta cartaIntroducir = baraja.sacarCarta();
                if (mesa[i].contains(cartaIntroducir)) {
                    baraja.insertarCarta(cartaIntroducir);
                } else {
                    mesa[i].add(cartaIntroducir);
                }
            }
            while (mesa[i].size() < NUM_CARTAS_FILA) {
                mesa[i].add(baraja.sacarCarta());
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
    public List<Carta> insertar(Stack<Carta> carta, int fila, boolean extremo) {
        List<Carta> toRet = cartasRodeadas(carta.peek(), fila, extremo);
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

    public List<Carta> cartasRodeadas(Carta carta, int fila, boolean extremo) {
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
            for (int i = 0; i < baraja.size() && rellenar; i++) {
                if (contarEspeciesDistintasFila(mesa[fila]) >= ESPECIES_DISTINTAS_JUEGO) {
                    rellenar = false;
                } else {
                    Carta cartaIntroducir = baraja.sacarCarta();
                    if (mesa[fila].contains(cartaIntroducir)) {
                        baraja.insertarCarta(cartaIntroducir);
                    } else {
                        mesa[fila].add(cartaIntroducir);
                    }
                }
            }
            if (rellenar) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("\nMesa: \n");
        for (int i = 0; i < MAX_FILAS; i++) {
            for (Carta carta : mesa[i]) {
                sb.append(Jugador.ANSI_YELLOW);
                sb.append(carta);
                sb.append(' ');
            }
            sb.append("\n");
        }
        sb.append(Jugador.ANSI_RESET);
        return sb.toString();
    }

    private int contarEspeciesDistintasFila(List<Carta> fila) {
        List<Carta> temp = new LinkedList<>();
        for (Carta carta : fila) {
            if (!temp.contains(carta)) {
                temp.add(carta);
            }
        }
        return temp.size();
    }
}
