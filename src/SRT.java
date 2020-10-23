import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SRT {
    private int reloj = 1;
    private boolean semaforo=true;
    ArrayList<Proceso> arrayCola = new ArrayList<>();
    ArrayList<Proceso> arraySRT = new ArrayList<>();
    ArrayList<Proceso> arrayProcesos;
    int cicloContador;
    Proceso procesoActual;

    public SRT(ArrayList<Proceso> arrayProceso) {
        this.arrayProcesos = arrayProceso;
    }


    /**
     * SRT es el algoritmo apropiativo,cada vez que llegue algun proceso
     * que tenga una rafaga menor lo ponemos primero en cola. El orden de los metodos es,
     *  imprimir, encolar, ordenamos la cola, y cogemos el primero.Despues volvemos a repetir, es ligeramente
     *  diferente el orden del SJF. Antes imprimiamos siempre y encolabamos solo cuando se acababan las rafagas
     *  ahora imprimrimos y revisamos la cola siempre.
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
            procesoActual=arrayCola.get(0);
            cicloContador=arrayCola.get(0).getCiclos();
            imprimir();
            encolamos();
            if(arrayCola.isEmpty()){
                break;
            }
            cicloContador=arrayCola.get(0).getCiclos();
            ordenamosCola();
            cogemosPrimero();

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
     * Metodo encolamos para poner en cola los procesos que sean necesarios,
     * cuando los ponemos en cola los quitamos del array clon
     */

    private void encolamos() {
        //añadimos nuevos procesos desde el array robin que cumplan las condiciones de llegada y reloj
        for(int i=0;i<arraySRT.size();i++){
            if(arraySRT.get(i).getOrdenLlegada()<reloj&&procesoActual!=arraySRT.get(i)){
                arrayCola.add(arraySRT.get(i));
            }
        }

        //lo quitamos del arraySRT
        for(int i=0;i<arraySRT.size();i++){
            for(int j=0;j<arrayCola.size();j++){
                //aqui no esta quitando todos los que esten en la cola del array robin y eso es lo que quiero
                if(arrayCola.get(j).getNombreProceso()==arraySRT.get(i).getNombreProceso()){
                    arraySRT.remove(i);
                }
            }

        }

        if(procesoActual.getCiclos()==0){
            arrayCola.remove(arrayCola.get(0));
        }

    }

    /**
     * Metodo auxiliar para odenar cola
     */

    private void ordenamosCola() {
        arrayCola.sort(Comparator.comparing(Proceso::getCiclos));
    }

    /**
     * Metodo auxiliar para coger el primero
     */
    private void cogemosPrimero(){
        procesoActual=arrayCola.get(0);
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
