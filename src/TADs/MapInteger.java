package TADs;

public class MapInteger{
    
    public Nodo [] tabla;
    int tam;
    
    public MapInteger(int tam){
     //factor de carga maximo = 0.65
     this.tam =(int)(tam/0.65);
     tabla = new Nodo [this.tam];      
}
    public void put(int clave,int valor){
    int indice = clave % tam;
    if(tabla[indice] == null){
        tabla[indice] = new Nodo(valor,clave,null);
    }else{
        Nodo k = tabla[indice];
        while(k.sig != null) k = k.sig;
        
            k.sig = new Nodo (valor,clave,null);
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
                    return;
                }
                kAnt = k;
                k = k.sig;
            }
        }

    }
    
    
    
    public int getValue(int clave){
    int indice = clave % tam;      
    if(tabla[indice] == null){
        return -1;
    }else{
        Nodo k = tabla[indice];
        while(k != null){
            if(clave == k.clave)
                return k.valor;
     
            k = k.sig;
        }
         return -1;  
        }
        
    }

    private class Nodo {

        public int valor;
        public int clave;
        public Nodo sig;

        public Nodo(int valor, int clave, Nodo sig) {
            this.valor = valor;
            this.clave = clave;
            this.sig = sig;
        }

    }

    
    
    
}

