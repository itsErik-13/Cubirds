/*
 * Esta clase representa cada carta del juego, con todos sus atributos, constructor y métodos observadores
 */

package es.uvigo.esei.cubirds.core;

public class Carta {
    public static final String[] especies = {"Curruca de caña","Flamenco","Petirrojo","Tucán","Pato","Urraca","Lechuza","Guacamayo"};

    
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
