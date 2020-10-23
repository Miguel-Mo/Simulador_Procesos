
public class Proceso implements Comparable<Proceso>{
    private int nombreProceso;
    private int ordenLlegada;
    private int ciclos;
    private int terminado;

    public Proceso(int nombreProceso,int ordenLlegada,int ciclos,int terminado){
        this.nombreProceso=nombreProceso;
        this.ordenLlegada=ordenLlegada;
        this.ciclos=ciclos;
        this.terminado=terminado;

    }

    public int getNombreProceso() {
        return nombreProceso;
    }

    public int getOrdenLlegada() {
        return ordenLlegada;
    }

    public int getCiclos() {
        return ciclos;
    }

    public int getTerminado() {return terminado;}

    public void setNombreProceso(int nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public void setOrdenLlegada(int ordenLlegada) {
        this.ordenLlegada = ordenLlegada;
    }

    public void setCiclos(int ciclos) {
        this.ciclos = ciclos;
    }

    public void setTerminado(int terminado) {
        this.terminado = terminado;
    }

    @Override
    public String toString() {
        return  "Proceso " + nombreProceso +"; "+
                "Orden de Llegada "+ordenLlegada+"; "
                +"ciclos a imprimir=" + ciclos +"; ";
    }




    @Override
    public int compareTo(Proceso o) {
        if (ordenLlegada < o.getOrdenLlegada()) {
            return -1;
        }
        if (ordenLlegada> o.getOrdenLlegada()) {
            return 1;
        }
        return 0;
    }


    @Override
    public Proceso clone() throws CloneNotSupportedException {
        return new Proceso(this.nombreProceso,this.ordenLlegada,this.ciclos,this.terminado);
    }
}
