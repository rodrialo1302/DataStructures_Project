package TADs;

/**
 *
 * @author Rodri
 */
public class ListaEnlazadaInt {

    private NodoListaInt primerNodo = null;
    private NodoListaInt lastNodo = null;
    private int size = 0;

    protected NodoListaInt getPrimerNodo() {
        return primerNodo;
    }

    protected NodoListaInt getLastNodo() {
        return lastNodo;
    }

    public int getSize() {
        return size;
    }

    public void addElemento(int valor) {
        NodoListaInt newNodo = new NodoListaInt(valor, null);
        if (lastNodo != null) {
            this.lastNodo.sig = newNodo;
        } else {
            this.primerNodo = newNodo;

        }
        this.lastNodo = newNodo;
        this.size++;
    }
    public void concat(ListaEnlazadaInt newLista){
        this.lastNodo.sig = newLista.getPrimerNodo();
        this.lastNodo = newLista.getLastNodo();
        this.size += newLista.getSize();
    
}
    
    
    public int[] asArray(){
        int[] array = new int[this.size];
        NodoListaInt nodoP = this.primerNodo;
        int i = 0;
        while (nodoP != null){
            array[i] = nodoP.valor;
            nodoP = nodoP.sig;
            i++;
        }
        return array;
    }
}

class NodoListaInt {

    public int valor;
    public NodoListaInt sig;

    public NodoListaInt(int valor, NodoListaInt sig) {
        this.valor = valor;
        this.sig = sig;
    }
}
