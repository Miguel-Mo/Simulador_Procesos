
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SJF {
    private int reloj = 1;
    private boolean semaforo=true;
    ArrayList<Proceso> arrayCola = new ArrayList<>();
    ArrayList<Proceso> arraySRT = new ArrayList<>();
    ArrayList<Proceso> arrayProcesos;
    int cicloContador;
    Proceso procesoActual;

    public SJF(ArrayList<Proceso> arrayProceso) {
        this.arrayProcesos = arrayProceso;
    }


    /**
     * SJF es el algoritmo no apropiativo. En el metodo calcular lo primero que hacemos
     * es crear dos arrays, uno que es un clon del array original (que lo dejamos intacto,
     * por si despues queremos utilizar algo de el) y un arrray cola que nos servirá para
     * saber que procesos estan en cola. Iniciamos un bucle while con un semaforo que no
     * se nos pondrá en falso hasta que no haya una cola vacia. En este algoritmo cogemos el
     * primer proceso hasta que se le acaban las rafagas, una vez se le acaban entramos en un if en el
     * que eliminamos el que se ha acabado, ponemos en cola los que toquen y despues ordenamos la cola.
     * Despues seguimos imprimiendo hasta que se le acabe la rafaga y sucesivamente hasta que hayamos terminado.
     */

    public void calcular() {
        try {
            arraySRT = cloneList(arrayProcesos);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Collections.sort(arraySRT);
        arrayCola.add(arraySRT.get(0));


        while(semaforo){
            cicloContador=arrayCola.get(0).getCiclos();


            if(cicloContador<=0){
                eliminamosActual();
                encolamos();
                if(arrayCola.isEmpty()){
                    break;
                }
                cicloContador=arrayCola.get(0).getCiclos();
                ordenamosCola();
            }


            procesoActual=arrayCola.get(0);
            cicloContador=arrayCola.get(0).getCiclos();
            imprimir();


            //cerramos el semaforo y para casa
            if(!arrayCola.isEmpty()&&arrayCola.size()==0){
                semaforo=false;
            }
        }



    }


    /**
     * Metodo imprimir para imprimir el resultado, sumamos reloj y tenemos una condicion para
     * que cuando sea la ultima vez que lo vayamos a imprimir añada un terminado
     */

    private void imprimir() {

        cicloContador--;
        if(cicloContador>0){
            System.out.println(reloj+" - " +procesoActual.toString()+" Quedan: "+cicloContador);
        }else {
            System.out.println(reloj+" - " +procesoActual.toString()+" Quedan: "+cicloContador+"---TERMINADO---");

            for(int i=0;i<arrayProcesos.size();i++){
                if(arrayCola.get(0).getNombreProceso()==arrayProcesos.get(i).getNombreProceso()){
                    arrayProcesos.get(i).setTerminado(reloj);
                }
            }
        }

        arrayCola.get(0).setCiclos(cicloContador);
        reloj++;
    }


    /**
     * Metodo para eliminar el proceso actual tanto del array clon como de la cola
     */

    private void eliminamosActual() {

        //recorremos las dos listas comparamos y quitamos
        for(int i=0;i<arraySRT.size();i++){
            for(int j=0;j<arrayCola.size();j++){
                if(arrayCola.get(j).getNombreProceso()==arraySRT.get(i).getNombreProceso()){
                    arraySRT.remove(i);
                }
            }

        }
        arrayCola.remove(arrayCola.get(0));
    }


    /**
     * Metodo encolamos para poner en cola los procesos que sean necesarios,
     * cuando los pponemos en cola los quitamos del array clon
     */
    private void encolamos() {

        //añadimos nuevos procesos desde el array SJF que cumplan las condiciones de llegada y reloj
        for(int i=0;i<arraySRT.size();i++){
            if(arraySRT.get(i).getOrdenLlegada()<reloj&&procesoActual!=arraySRT.get(i)){
                arrayCola.add(arraySRT.get(i));
            }
        }

        //lo quitamos del arraySRT
        for(int i=0;i<arraySRT.size();i++){
            for(int j=0;j<arrayCola.size();j++){
                if(arrayCola.get(j).getNombreProceso()==arraySRT.get(i).getNombreProceso()){
                    arraySRT.remove(i);
                }
            }
        }
    }


    /**
     * Metodo auxiliar para ordenar cola.
     */

    private void ordenamosCola() {
        arrayCola.sort(Comparator.comparing(Proceso::getCiclos));
    }


    /**
     * Metodo auxiliar para clonar lista
     * @param list
     * @return
     * @throws CloneNotSupportedException
     */
    public static ArrayList<Proceso> cloneList(List<Proceso> list) throws CloneNotSupportedException {
        ArrayList<Proceso> clone = new ArrayList<Proceso>(list.size());
        for (Proceso item : list) clone.add(item.clone());
        return clone;
    }


}