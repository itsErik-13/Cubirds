/*
 * Esta clase representa cada carta del juego, con todos sus atributos, constructor y métodos observadores
 */

package es.uvigo.esei.cubirds.core;

import javax.swing.text.html.FormView;

public class Carta {
    public static final int[] numCartas =new int[]{20,7,20,10,13,17,10,13};
    public static final String[] especies = {"Curruca de caña","Flamenco","Petirrojo","Tucán","Pato","Urraca","Lechuza","Guacamayo"};
    public static final int[] bandadasP = {6,2,6,3,4,5,3,4};
    public static final int[] bandadasG = {9,3,9,4,6,7,4,6};

    
    
    private String especie;
    private int bandadaP;
    private int bandadaG;

    public Carta(String especie, int bandadaP, int bandadaG) {
        this.especie = especie;
        this.bandadaP = bandadaP;
        this.bandadaG = bandadaG;
    } 

    public String getEspecie() {
        return especie;
    }

    public int getBandadaP() {
        return bandadaP;
    }

    public int getBandadaG() {
        return bandadaG;
    }
    
    @Override
    public boolean equals(Object carta){
        if(carta instanceof Carta){
          Carta toCompare = (Carta) carta;
          return this.getEspecie().equals(toCompare.getEspecie());
        }
        return false;
      }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.especie).append(" ").append(this.bandadaP).append("/").append(bandadaG);
        return sb.toString();
    } 
}
