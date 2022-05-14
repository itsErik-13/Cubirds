/*
 * Esta clase representa la baraja de juego, necesitará implementar las siguientes funcionalidades
 *  - Un constructor con las cartas de la baraja
 *  - Barajar las cartas
 *  - Devolver el número de cartas de la baraja
 *  - Coger una carta de la baraja
 *  - Insertar una carta en la baraja
 */
package es.uvigo.esei.cubirds.core;

import java.util.Queue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Baraja {
    private Queue<Carta> baraja;
    public int cont=0;
    /**
     * Crea y mezcla la baraja.
     */
    public Baraja(){
        baraja = new ArrayDeque<>();
        for(int i = 0; i < Carta.numCartas.length ; i++){
            for (int j = 0; j < Carta.numCartas[i]; j++) {
                baraja.add(new Carta(Carta.especies[i],Carta.bandadas[i][0],Carta.bandadas[i][1]));
            }
        }
        barajar();
    }
    /**
     * Mezcla la baraja
     */
    public void barajar(){
        int numCartas = baraja.size();
        List<Carta> carta = new ArrayList<>();
        int pos;
        for (int i = 0; i < numCartas; i++) {
            carta.add(baraja.remove());
        }
        while(!carta.isEmpty()) {
            pos = (int) (Math.random() * numCartas);
            baraja.add(carta.remove(pos));
            numCartas--;
        }
    }
    
    /**
     * Devuelve la carta que está en la cima de la baraja
     * @return  
     */
    public Carta sacarCarta(){
        return baraja.remove();
    }

    /**
     * Introduce la carta pasada como parámetro en la baraja
     * @param carta  
     */
    public void insertarCarta(Carta carta){
        baraja.add(carta);
    }

    /**
     * 
     * @return Devuelve el numero de cartas que quedan en la baraja
     */
    public int size(){
        return baraja.size();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        while(!baraja.isEmpty()){
            sb.append(baraja.remove().toString()).append("\n");
        }
        return sb.toString();
    }
}
