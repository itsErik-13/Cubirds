/*
 * Esta clase representa a cada jugador. Tendrá las siguientes funcionalidades
 * - Un constructor para crear el jugador
 * - Añadir y eliminar cartas de la mano
 * - Colocar cartas en la mesa
 * - Colocar cartas en su zona de juego
 * - Número de cartas en la mano
 * - Número de cartas en la zona de juego
 * - Número de especies distintas en la zona de juego
 * - Mostrar mano, zona de juego del jugador
 */
package es.uvigo.esei.cubirds.core;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private ZonaJuego zonaJuego;
    private Mano mano;

    public Jugador(Baraja b){
        zonaJuego = new ZonaJuego();
        mano = new Mano(b);
    }
      
    /*
    * Esta clase representa la zona de juego de un jugador. Tendrá las siguientes funcionalidades
    * - Un constructor para crear la zona de juego
    * - añadir cartas
    * - Número de cartas
    * - Número de especies distintas
    * - mostrar zona de juego
    */
    private class ZonaJuego {

    } 
    
          
    /*
    * Esta clase representa la mano de un jugador. Tendrá las siguientes funcionalidades
    * - Un constructor para crear la mano
    * - añadir cartas
    * - eliminar cartas
    * - número de cartas
    * - comprobar si hay cartas suficientes para bandada pequeña
    * - mostrar mano
    */
    
    private class Mano {
        private List <Carta> mano;
        public final int BANDADA_PEQUENA_FLAMENCO =2;
        public final int BANDADA_PEQUENA_TUCAN =3;
        public final int BANDADA_PEQUENA_LECHUZA =3;
        public final int BANDADA_PEQUENA_PATO =4;
        public final int BANDADA_PEQUENA_GUACAMAYO =4;
        public final int BANDADA_PEQUENA_URRACA =5;
        public final int BANDADA_PEQUENA_CURRUCADECANA =6;
        public final int BANDADA_PEQUENA_PETIRROJO =6;
        
        public Mano (Baraja b){
            mano=new ArrayList(8);
            
            for(Carta i: mano){
                mano.add(mano.size(),b.sacarCarta());
            }
            
        }
    
        public void anhadirCartas(List <Carta> c){
             mano.addAll(c);
        }
        
        public List<Carta> eliminarCartas(Carta carta){
            int cont=0;
            List <Carta> toRet=new ArrayList<>();
            for (Carta i : mano) {
                
                if(i.sonIguales(carta)){
                    toRet.add(mano.get(cont));
                    mano.remove(i);
                }
                cont++;
            }
            
            return toRet;
        }
        
        public int numeroCartas(){
            return mano.size();
        }
        
        public int numeroCartasTipo(String s){
            int cont=0;
            for (Carta carta : mano) {
                if(carta.getEspecie()==s){
                    cont++;
                }
            }
            
            return cont;
        }
        
        public boolean bandadaPequenha(Carta c){
            boolean toRet=false;
            switch (c.getEspecie()){
                case ("Flamenco"):
                    if(numeroCartasTipo("Flamenco")==BANDADA_PEQUENA_FLAMENCO){
                        toRet=true;
                    }
                    break;
                case ("Tucán"):
                    if(numeroCartasTipo("Tucán")==BANDADA_PEQUENA_TUCAN){
                        toRet=true;
                    }
                    break;    
                case ("Lechuza"):
                    if(numeroCartasTipo("Lechuza")==BANDADA_PEQUENA_LECHUZA){
                        toRet=true;
                    }
                    break;
                case ("Pato"):
                    if(numeroCartasTipo("Pato")==BANDADA_PEQUENA_PATO){
                        toRet=true;
                    }
                    break;    
                case ("Guacamayo"):
                    if(numeroCartasTipo("Guacamayo")==BANDADA_PEQUENA_GUACAMAYO){
                        toRet=true;
                    }
                    break;    
                case ("Urraca"):
                    if(numeroCartasTipo("Urraca")==BANDADA_PEQUENA_URRACA){
                        toRet=true;
                    }
                    break;    
                case ("Curruca de caña"):
                    if(numeroCartasTipo("Curruca de caña")==BANDADA_PEQUENA_CURRUCADECANA){
                        toRet=true;
                    }
                    break;
                case ("Petirrojo"):
                    if(numeroCartasTipo("Petirrojo")==BANDADA_PEQUENA_PETIRROJO){
                        toRet=true;
                    }
                    break;    
                    
            }
               return toRet;
        }
        @Override
        public String toString() {
            StringBuilder sb= new StringBuilder();
            for (Carta carta : mano) {
                sb.append(carta);
            }
            
            return sb.toString();
        }
    }
}