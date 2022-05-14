/*
 * Esta clase representa el montón de descartes. Funcionalidades a implementar:
 * - Constructor para crear un montón
 * - añadir carta
 */
package es.uvigo.esei.cubirds.core;

import java.util.Stack;

public class MontonDescartes {
    private Stack<Carta> descartes;

    public MontonDescartes(){
        descartes = new Stack<>();
    }
    
    /**
     * Añade numCartas unidades de la carta pasada como parámetro al montón de descartes
     * @param carta
     * @param numCartas
     */
    public void addDescarte(Stack<Carta> cartas){
        descartes.addAll(cartas);
    }

    public Carta sacarCarta(){
        return descartes.pop();
    }
}
