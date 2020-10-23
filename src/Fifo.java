import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Fifo {


    /**
     * En el metodo calcular del fifo  lo primero que hacemos es hacerle un sort
     * al array que nos han pasado. Previamente hemos hecho un Overwrite del compareTo
     * en la clase Proceso para que lo ordene por orden de llegada. Loo primero que hacemos
     * es un for para recorrer la lista ya ordenada, luego imprimimos cada proceso hasta
     * que se termine su numero de ciclos, cuando es la ultima impresion señalamos que
     * esta terminado y le asignamos el parametro terminado para poder hacer los calculos
     * de eficiencia más tarde.
     * @param arrayProcesos
     */
    public static void calcular(ArrayList<Proceso> arrayProcesos){
        ArrayList arrayfifo =arrayProcesos;
        Collections.sort(arrayfifo);

        int contador=1;

        for (Proceso proceso: Controlador.arrayprocesos) {
            int cicloContador=proceso.getCiclos();

            while(cicloContador>=1){
                proceso.toString();
                cicloContador=cicloContador-1;
                if(cicloContador==0){
                    System.out.println(contador+" - " +proceso.toString()+" Quedan: "+cicloContador+" TERMINADO");
                    proceso.setTerminado(contador);
                }else{
                    System.out.println(contador+" - "+proceso.toString()+" Quedan: "+cicloContador+"; ");
                }

                contador++;
            }

        }
    }

}
