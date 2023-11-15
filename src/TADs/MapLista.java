/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TADs;

/**
 *
 * @author Rodri
 */
public class MapLista {

    private Nodo[] tabla;
    private int tam;
    private int size = 0;

    public MapLista(int tam) {
        //factor de carga maximo = 0.65
        this.tam = (int) (tam / 0.65);
        tabla = new Nodo[this.tam];
    }

    public void put(int clave, ListaEnlazadaInt valor) {
        int indice = clave % tam;
        if (tabla[indice] == null) {
            tabla[indice] = new Nodo(valor, clave, null);
        } else {
            Nodo k = tabla[indice];
            while (k.sig != null) {
                k = k.sig;
            }

            k.sig = new Nodo(valor, clave, null);
        }
        size++;
    }

    public ListaEnlazadaInt getValue(int clave) {
        int indice = clave % tam;
        if (tabla[indice] == null) {
            return null;
        } else {
            Nodo k = tabla[indice];
            while (k != null) {
                if (clave == k.clave) {
                    return k.valor;
                }

                k = k.sig;
            }
            return null;
        }

    }

    public void delete(int clave) {
        int indice = clave % tam;
        if (tabla[indice] != null) {
            Nodo k = tabla[indice];
            Nodo kAnt = null;
            while (k != null) {
                if (clave == k.clave) {
                    if (kAnt == null) {
                        tabla[indice] = k.sig;
                    } else {
                        kAnt.sig = k.sig;
                    }
                    size--;
                    return;
                }
                kAnt = k;
                k = k.sig;
            }
        }

    }

    public Object[][] asArray() {
        Object[][] array = new Object[this.size][2];
        int j = 0;
        for (int i = 0; i < this.tam; i++){
            Nodo nodoP = tabla[i];
            while(nodoP != null){
                array[j][0] = nodoP.clave;
                array[j][1] = nodoP.valor;
                nodoP = nodoP.sig;
                j++;
                
            }
        }
        return array;
    }

    private class Nodo {

        public ListaEnlazadaInt valor;
        public int clave;
        public Nodo sig;

        public Nodo(ListaEnlazadaInt valor, int clave, Nodo sig) {
            this.valor = valor;
            this.clave = clave;
            this.sig = sig;
        }

    }

}
