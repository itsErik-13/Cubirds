/*
 * Clase de Entrada de datos
 */
package es.uvigo.esei.cubirds.iu;

import java.util.Scanner;

public class ES {

    public static Scanner leer = new Scanner(System.in);

    public static String pideCadena(String mensaje) {
        String toRet = "";
        do {
            System.out.println(mensaje);
            toRet = leer.nextLine();
        } while (toRet.isEmpty());
        return toRet;
    }

    public static int pideNumero(String mensaje) {
        
        boolean esValido = false;
        int toret = 0;
        Scanner teclado = new Scanner(System.in);

        do {
            System.out.print(mensaje);

            try {
                toret = Integer.parseInt(teclado.nextLine());
                esValido = true;
            } catch (NumberFormatException exc) {
                System.err.println("La cadena introducida no se puede "
                        + "convertir a número entero. Por favor, "
                        + "introdúcela de nuevo.");
            }
        } while (!esValido);
        return toret;
    }
}
