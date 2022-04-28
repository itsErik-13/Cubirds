/*
 * Esta clase representa cada carta del juego, con todos sus atributos, constructor y métodos observadores
 */

package es.uvigo.esei.cubirds.core;

public class Carta {
    // public static final String C1_ESPECIE = "Curruca de caña";
    // public static final String C2_ESPECIE = "Flamenco";
    // public static final String C3_ESPECIE = "Petirrojo";
    // public static final String C4_ESPECIE = "Tucán";
    // public static final String C5_ESPECIE = "Pato";
    // public static final String C6_ESPECIE = "Urraca";
    // public static final String C7_ESPECIE = "Lechuza";
    // public static final String C8_ESPECIE = "Guacamayo";

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
