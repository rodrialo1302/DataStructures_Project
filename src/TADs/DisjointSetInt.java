package TADs;

/**
 *
 * @author Rodri
 */
public class DisjointSetInt {

    private MapInteger mapUsers2Grus;
    private MapLista mapGrus;

    public DisjointSetInt(int nUsers, int nConex) {
        mapUsers2Grus = new MapInteger(nUsers);
        mapGrus = new MapLista(nConex);

    }

    public int buscar(int clave) {
        int usrTmp = mapUsers2Grus.getValue(clave);
        if (usrTmp != -1) {
            return usrTmp;
        } else {
            return -1;
        }
    }

    public void add(int categoria, int elemento) {
        mapUsers2Grus.put(elemento, categoria);
        if (mapGrus.getValue(categoria) != null) {
            ListaEnlazadaInt tmpList = mapGrus.getValue(categoria);
            tmpList.addElemento(elemento);
        }else{
            ListaEnlazadaInt tmpList = new ListaEnlazadaInt();
            tmpList.addElemento(elemento);
            mapGrus.put(categoria, tmpList);
        }

    }

    public void union(int clave1, int clave2) {
        ListaEnlazadaInt list1 = mapGrus.getValue(clave1);
        ListaEnlazadaInt list2 = mapGrus.getValue(clave2);
        if (list1.getSize() >= list2.getSize()) {
            var tmpArray = list2.asArray();
            for (int i = 0; i < tmpArray.length; i++) {
                mapUsers2Grus.delete(tmpArray[i]);
                mapUsers2Grus.put(tmpArray[i], clave1);

            }
            list1.concat(list2);
            mapGrus.delete(clave2);
        } else {
            var tmpArray = list1.asArray();
            for (int i = 0; i < tmpArray.length; i++) {
                mapUsers2Grus.delete(tmpArray[i]);
                mapUsers2Grus.put(tmpArray[i], clave2);
            }
            list2.concat(list1);
            mapGrus.delete(clave1);
        }
    }

    public int[][] getCatSize() {
        var objArray = mapGrus.asArray();
        int[][] array = new int[objArray.length][2];
        for (int i = 0; i < objArray.length; i++) {
            array[i][0] = (int) objArray[i][0];
            ListaEnlazadaInt tmp = (ListaEnlazadaInt) objArray[i][1];
            array[i][1] = tmp.getSize();
        }

        return array;

    }
}
