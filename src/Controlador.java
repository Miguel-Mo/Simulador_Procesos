import java.util.ArrayList;
import java.util.Scanner;

public class Controlador {

    static ArrayList<Proceso> arrayprocesos = new ArrayList();


    /**
     * Primero haremos la clase creacionDeProcesos
     * Esta clase la dividiremos en dos: una parte donde introducimos los procesos
     * y la otra parte donde confirmamos esos procesos, por si nos hemos confundido.
     */
    public static void creacionDeProcesos() {
        introProcesos();
        confirmacionProcesos();
    }


    /**
     * En la clase introProcesos contaremos con la variables cantidadProcesos,
     * nombreProceso,ordenllegada,cantidadCiclos,terminado además de una variable
     * temporal que nos servirá para comprobar si lo que hemos introducido por consola
     * es un numero.
     *
     * Lo primero que hacemos es limpiar la lista de procesos, por si venimmos de algún
     * otro método, para poder introducir desde 0. Comprobamos si nos han introducido un numero
     * y despues hacemos un for para crear x cantidad de procesos que le hemos indicado, dentro de
     * ese for le asignamos la rafaga que queremos que haga y el orden de llegada, la añadimos a
     * la lista y luego cuando hemos terminado de hacer la lista, la recorremos y la imprimimos.
     */

    public static  void introProcesos(){

        int cantidadProcesos, nombreProceso,ordenllegada,cantidadCiclos,terminado;

        String variableTemporal;

        arrayprocesos.clear();
        terminado=0;
        Scanner sc=new Scanner(System.in);
        do{
            System.out.println("¿Cuántos procesos quieres crear?");
            variableTemporal = sc.nextLine();
        }
        while (!isNumeric(variableTemporal));

        cantidadProcesos=Integer.parseInt(variableTemporal);



        for(int i=1;cantidadProcesos+1>i;i++){

            nombreProceso=i;

            do{
                System.out.println("¿Cuándo quieres que empiece el proceso "+i +"?");
                variableTemporal = sc.nextLine();
            }
            while (!isNumeric(variableTemporal));

            ordenllegada=Integer.parseInt(variableTemporal);

            do{
                System.out.println("¿Cuántos ciclos quieres que tenga el proceso "+i+"?");
                variableTemporal = sc.nextLine();
            }
            while (!isNumeric(variableTemporal));

            cantidadCiclos=Integer.parseInt(variableTemporal);

            Proceso proceso=new Proceso(nombreProceso,ordenllegada,cantidadCiclos,terminado);

            arrayprocesos.add(proceso);

        }

        for (Proceso proceso:arrayprocesos) {
            proceso.toString();
            System.out.println(proceso.toString());
        }

    }


    /**
     * En el método confirmacionProcesos lo unico que hacemos es preguntar al usuario si esa es la lista que
     * queremos utilizar, si es la que queremos seguimos, si no volvemos a empezar desde el metodo anterior.
     */

    public static  void confirmacionProcesos(){

        int confirmacion;
        String variableTemporal;
        Scanner sc=new Scanner(System.in);


        do{
            System.out.println("¿Esta es la lista de procesos con la que quieres trabajar? Pulsa el numero"+"\n"+"1. SI"+"\n"+"2. NO,QUIERO HACER OTRA"+"\n"+"3. SALIR");
            variableTemporal = sc.nextLine();
        }
        while (!isNumeric(variableTemporal));

        confirmacion=Integer.parseInt(variableTemporal);

        if(confirmacion==1){
            System.out.println("Ok. ");
        }else if(confirmacion==2){
            System.out.println("empecemos de nuevo"+"\n");
            arrayprocesos.clear();
            creacionDeProcesos();
        }else if(confirmacion==3){
            System.out.println("Adios");
        }else{
            System.out.println("Utiliza uno de los numeros que hemos mencionado");
            confirmacionProcesos();
        }
    }


    /**
     * En el metodo seleccionalgoritmo seleccionaremos que algoritmo queremos utilizar
     *en función del que elijamos llamamos a uno u a otro. Cuando terminamos llamamos al metodo
     * calcularEficiencia.
     */


    public static void seleccionalgoritmo() {

        int algoritmo;
        int quantum;
        String variableTemporal;
        Scanner sc=new Scanner(System.in);

        do{
            System.out.println("¿Qué algoritmo quieres utilizar?"+"\n" +"1. FIFO"+"\n"+"2. Round Robin"+"\n"+"3. SJB"+"\n"+"4.SRT"+"\n"+"5. SALIR"+"\n");
            variableTemporal = sc.nextLine();
        }
        while (!isNumeric(variableTemporal));

        algoritmo = Integer.parseInt(variableTemporal);


        switch(algoritmo){
            case 1:
                Fifo.calcular(arrayprocesos);
                break;
            case 2:
                System.out.println("¿Qué quantum quieres?");
                quantum=sc.nextInt();

                new RoundRobin(quantum,arrayprocesos).calcular();
                break;
            case 3:
                new SJF(arrayprocesos).calcular();
                break;

            case 4:
                new SRT(arrayprocesos).calcular();
                break;
            case 5:
                System.out.println("Adios");
                break;
        }
        calcularEficiencia();
    }


    /**
     * En el metodo calcularEficiencia preguntamos primero si la quiere calcular,
     * si es afirmativo la calculamos con la formula correspondiente,
     * si quiere crear otra tabla de procesos empezamos de nuevo el ciclo de metodos y si no salimos.
     */
    public static void calcularEficiencia(){

        String variableTemporal;
        int opcion;
        Scanner sc=new Scanner(System.in);

        do{
            System.out.println("¿Desea calcular la eficiencia?"+"\n" +"1. Si"+"\n"+"2.No,quiero hacer otra simulacion"+"\n"+"3. Salir");
            variableTemporal = sc.nextLine();
        }
        while (!isNumeric(variableTemporal));

        opcion = Integer.parseInt(variableTemporal);

        float sumaEficiencias=0;
        switch(opcion){
            case 1:

                for (int i=0;i<arrayprocesos.size();i++){
                    Proceso proceso= arrayprocesos.get(i);
                    float eficienciaProceso=(float)(proceso.getTerminado()-proceso.getOrdenLlegada())/proceso.getCiclos();
                    System.out.println("La eficiencia del proceso "+proceso.getNombreProceso()+ " es de "+eficienciaProceso);
                    sumaEficiencias=sumaEficiencias+eficienciaProceso;
                }


                float eficienciaGlobal=sumaEficiencias/(float)arrayprocesos.size();

                System.out.println("La eficiencia global es de "+eficienciaGlobal);

                menuRegresivo();
                break;
            case 2:
                creacionDeProcesos();
                seleccionalgoritmo();
                calcularEficiencia();
                menuRegresivo();
                break;
            case 3:
                System.out.println("Adios");
                break;

        }


    }


    /**
     * El método menuRegresivo y isNumeric son dos metodos auxiliares que nos sirven para
     * redireccionar al usuario en el caso de que lo necesite (menuRegresivo) y para comprobar
     * que lo que nos han escrito por pantalla es un numero(isNumeric)
     */

    public static void menuRegresivo(){
        String variableTemporal;
        int opcion;
        Scanner sc=new Scanner(System.in);


        do{
            System.out.println("¿Qué desea hacer?"+"\n" +"1. Calcular la misma tabla con otro algoritmo"+"\n"+
                    "2.Crear otra tabla de procesos"+"\n"+"3. Salir");
            variableTemporal = sc.nextLine();
        }
        while (!isNumeric(variableTemporal));

        opcion = Integer.parseInt(variableTemporal);

        switch(opcion){
            case 1:
                seleccionalgoritmo();
                break;
            case 2:
                creacionDeProcesos();
                break;
            case 3:
                System.out.println("Adios");
                break;

        }
    }


    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
}
