
import java.awt.datatransfer.DataFlavor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundRobin {

    private int reloj = 1;
    private float contadorq = 1;
    private float quantum = 3;
    private boolean semaforo=true;
    private int contador=1;
    ArrayList<Proceso> arrayCola = new ArrayList<>();
    ArrayList<Proceso> arrayRobin= new ArrayList<>();
    ArrayList<Proceso> arrayProcesos;
    int cicloContador;
    Proceso procesoActual;

    public RoundRobin(float quantum, ArrayList<Proceso> arrayProceso) {
        this.quantum = quantum;
        this.arrayProcesos = arrayProceso;
    }


    /**
     * Este metodo es fantasia. En el Round Robin tenemos que encolar cada vez que o bien
     * rompe el quantum o se acaban las rafagas. Lo que hacemos es imprimir el proceso si se rompe,
     * entonces pasamos al metodo revisionCola y pasamos el quantum a uno.
     */

    public void calcular() {

        try {
            arrayRobin = cloneList(arrayProcesos);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        Collections.sort(arrayRobin);
        arrayCola.add(arrayRobin.get(0));



        while(semaforo){
            cicloContador=arrayCola.get(0).getCiclos();


            //revisamos la cola cuando rompe el quantum, ahi añadimos los que toque por su ciclo y quitamos el que estaba ejecutando
            //el quantum puede romper tanto porque llegue al maximo quantum o porque el proceso se haya acabado
            if(arrayCola.get(0).getCiclos()==0||contadorq==(quantum+1)){
                revisionCola();
                if(arrayCola.isEmpty()){
                    break;
                }
                contadorq=1;
            }

            //Variable para procesoActual
            procesoActual=arrayCola.get(0);

            //imprimimos el primero de la cola, le restamos ciclos y le sumamos quatum

            imprimir();

            //cerramos el semaforo y para casa
            if(!arrayCola.isEmpty()&&arrayCola.size()==0){
                semaforo=false;
            }
        }


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


    /**
     * El metodo revisionCola() lo vamos a dividir en dos, o bien revisamos la cola
     * porque al proceso se le han acabado las rafagas o bien porque el quantum se ha roto
     */

    //añadimos los que toque por su ciclo y quitamos el que estaba ejecutando
    public  void revisionCola(){

        //mandar al fondo si un proceso le quedan ciclos por hacer
        //añadimos y modificanos la cola
        if(arrayCola.get(0).getCiclos()==0){//si el objeto que estamos utilizando ha finalizado
            colaporvacio();
        }else{
            colaporquantum();
        }

    }


    /**
     * Si el proceso esta vacio usamos el metodo colaporvacio() en el lo primero que hacemos
     * es usar el metodo limpiarvacio() que es para quitar el que estabamos usando, dado que esta vacio, luego
     * seguimos encolando.
     */
    public void colaporvacio(){
        limpiarvacio();
        if(!arrayCola.isEmpty()){
            encolamos();
        }

    }


    /**
     * En el metodo colaporquantum() que lo usamos si el quantum se ha roto,
     * si es asi primero encolamos y luego pasamos el que estaba en primer lugar al ultimo lugar
     * por ultimo cogemos los ciclos del primero para resetear
     */



    public void colaporquantum(){
        //añadimos nuevos procesos desde el array robin que cumplan las condiciones de llegada y reloj
        encolamos();
        arrayCola.add(arrayCola.get(0));
        arrayCola.remove(0);
        cicloContador=arrayCola.get(0).getCiclos();
    }


    /**
     * En el metodo limpiarvacio() llo que hacemos es quitar el que esta vacio de rafagas
     * tanto del array clon como del array cola
     */
    public void limpiarvacio(){
        //lo quitamos del arrayRobin
        for(int i=0;i<arrayRobin.size();i++){
            if(arrayCola.get(0).getNombreProceso()==arrayRobin.get(i).getNombreProceso()){
                arrayRobin.remove(i);
            }
        }
        //lo quitamos de la cola
        arrayCola.remove(0);//lo quitamos
    }


    /**
     * En el metodo encolamos()  lo que hacemos es meter dentro del array clon los procesos que toquen
     * por orden de llegada y los quitamos del array clon.
     */

    public void encolamos(){
        //añadimos nuevos procesos desde el array robin que cumplan las condiciones de llegada y reloj
        for(int i=0;i<arrayRobin.size();i++){
            if(arrayRobin.get(i).getOrdenLlegada()<reloj&&procesoActual!=arrayRobin.get(i)){
                arrayCola.add(arrayRobin.get(i));
            }
        }


        //lo quitamos del arrayRobin
        for(int i=0;i<arrayRobin.size();i++){
            for(int j=0;j<arrayCola.size();j++){
                //aqui no esta quitando todos los que esten en la cola del array robin y eso es lo que quiero
                if(arrayCola.get(j).getNombreProceso()==arrayRobin.get(i).getNombreProceso()){
                    arrayRobin.remove(i);
                }
            }

        }


        cicloContador=arrayCola.get(0).getCiclos();

    }


    /**
     * En el metodo imprimir imprimimos el proceso que toca y si termina le añadimos
     * terminado aparte de setear el estaado terminado de la clase proceso con el metodo
     * terminar()
     */

    //imprimimos el primero de la cola, le restamos ciclos y le sumamos quatum
    public  void imprimir(){

        cicloContador--;
        if(arrayCola.get(0).getCiclos()>1){
            System.out.println(reloj+" - " +procesoActual.toString()+" Quedan: "+cicloContador);
            arrayCola.get(0).setCiclos(cicloContador);


        }else {
            terminar();//si le queda un ciclo hacemos el metodo terminar que asigna valor a terminado y lo imprime con una agregación
        }

        contadorq++;
        reloj++;

    }


    //asigna valor a terminado y lo imprime con una agregación
    public  void terminar(){
        System.out.println(reloj+" - " +procesoActual.toString()+" Quedan: "+cicloContador+" TERMINADO");

        //asignar terminado en array procesos revisando el array de procesos entero
        for(int i=0;i<arrayProcesos.size();i++){
            if(arrayCola.get(0).getNombreProceso()==arrayProcesos.get(i).getNombreProceso()){
                arrayProcesos.get(i).setTerminado(reloj);
            }

        }
        arrayCola.get(0).setCiclos(cicloContador);
    }

}
