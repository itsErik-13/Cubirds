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
        for (int i = 0; i < 20; i++) {
            baraja.add(new Carta("Curruca de caña", 6, 9));
            cont++;
        }
        for (int i = 0; i < 7; i++) {
            baraja.add(new Carta("Flamenco", 2, 3));
        }
        for (int i = 0; i < 20; i++) {
            baraja.add(new Carta("Petirrojo", 6, 9));
        }
        for (int i = 0; i < 10; i++) {
            baraja.add(new Carta("Tucán", 3, 4));
        }
        for (int i = 0; i < 13; i++) {
            baraja.add(new Carta("Pato", 4, 6));
        }
        for (int i = 0; i < 17; i++) {
            baraja.add(new Carta("Urraca", 5, 7));
        }
        for (int i = 0; i < 10; i++) {
            baraja.add(new Carta("Lechuza", 3, 4));
        }
        for (int i = 0; i < 13; i++) {
            baraja.add(new Carta("Guacamayo", 4, 6));
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
