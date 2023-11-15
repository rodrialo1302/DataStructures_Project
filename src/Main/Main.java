package Main;

/**
 * @author Rodrigo Alonso Pastor 
 * Java 15
 */
import TADs.DisjointSetInt;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static int openFile(Scanner archivo, List<Integer[]> red) {
        int users = Integer.parseInt(archivo.nextLine());
        Integer.parseInt(archivo.nextLine()); // linea de conexiones

        while (archivo.hasNextLine()) {
            var connRaw = archivo.nextLine().split(" ");
            Integer[] connInt = {Integer.parseInt(connRaw[0]),
                Integer.parseInt(connRaw[1])};
            red.add(connInt);
        }

        return users;
    }

    public static void openExtra(List<Integer[]> red, Scanner extra) {
        while (extra.hasNextLine()) {
            var connRaw = extra.nextLine().split(" ");
            Integer[] connInt = {Integer.parseInt(connRaw[0]),
                Integer.parseInt(connRaw[1])};
            red.add(connInt);
        }
    }

    public static void saveExtra(String path, List<List<Integer>> extra) throws IOException {
        FileWriter file = new FileWriter(path);
        for (int i = 0; i < extra.size(); i++) {
            file.write(extra.get(i).get(0) + " " + extra.get(i).get(1) + "\n");
        }
        file.close();
    }

    public static int sortGrus(int[][] grus, int percent, int users, List<List<Integer>> extra) {
        double sumUsuarios = 0;
        double cuentas;
        int relaciones = 0; // relaciones = grumos recorridos
        do {
            sumUsuarios = grus[0][1] + sumUsuarios;
            relaciones++;
            cuentas = ((sumUsuarios / users) * 100);
        } while (cuentas < percent);

        if (relaciones > 1) {

            for (int i = 0; i < relaciones - 1; i++) {
                List<Integer> rel = new ArrayList<>();
                rel.add(grus[i][0]);
                rel.add(grus[i+1][0]);
                extra.add(rel);

            }

        }
        return relaciones;
    }

 

    public static void main(String[] args) {
        List<Integer[]> red = new ArrayList<>();
        int percent;
        int users = 0;
        List<List<Integer>> extra = new ArrayList<>();
        long startTime = 0, endTime = 0;
        double duration;

        try {
            System.out.print("Fichero principal : ");
            Scanner scan = new Scanner(System.in);
            File file = new File(scan.nextLine());
            //File file = new File("test2000000.txt");
            Scanner fich = new Scanner(file);
            // Inicio medición tiempo
            startTime = System.nanoTime();
            users = openFile(fich, red);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Fichero not found");
            System.exit(-1);
        } catch (NumberFormatException e) {
            System.err.println("Error: formato no numérico");
            System.exit(-1);
        }
        // Fin medición tiempo
        endTime = System.nanoTime();
        duration = (endTime - startTime) * Math.pow(10, -9);
        System.out.println(String.format("Lectura fichero: %.5f sec", duration));

        try {
            System.out.print("Fichero de nuevas conexiones (pulse enter si no existe): ");
            Scanner scan = new Scanner(System.in);
            String extraName = scan.nextLine();
            if (!"".equals(extraName)) {
                File file = new File(extraName);
                Scanner fich = new Scanner(file);
                openExtra(red, fich);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Fichero not found");
            System.exit(-1);
        }
        System.out.println(users + " usuarios, " + red.size() + " conexiones.");

        System.out.print("Porcentaje tamaño mayor grumo: ");
        Scanner perc = new Scanner(System.in);
        percent = perc.nextInt();
        
        startTime = System.nanoTime();
         /**
         * ********************************************************************
         ***************************ETAPAS 2 Y 3*******************************
        **********************************************************************
         */       
        
        
        
        DisjointSetInt grumos = new DisjointSetInt(users, red.size());

        for (int i = 0; i < red.size(); i++) {
            int u1 = red.get(i)[0];
            int u2 = red.get(i)[1];
            int catU1 = grumos.buscar(u1);
            int catU2 = grumos.buscar(u2);

            if (catU1 == -1 && catU2 != -1) {
                grumos.add(catU2, u1);
            }
            if (catU1 != -1 && catU2 == -1) {
                grumos.add(catU1, u2);
            }
            if (catU1 == -1 && catU2 == -1) {
                grumos.add(u1, u1);
                grumos.add(u1, u2);
            }

            if (catU1 != -1 && catU2 != -1) {
                if (catU1 != catU2) {
                    grumos.union(catU1, catU2);
                }
            }

        }

        int[][] gruList = grumos.getCatSize();
        
        
        endTime = System.nanoTime();
        // Fin medición tiempo
        duration = (endTime - startTime) * Math.pow(10, -9);
        System.out.println(String.format("Creación lista grumos: %.5f sec", duration));

        /**
         * *********************************************************************
         ********************************ETAPA 4********************************
        **********************************************************************
         */
        // Inicio medición tiempo
        startTime = System.nanoTime();
        Arrays.sort(gruList, Comparator.comparingInt(o -> -o[1]));

        int relaciones = sortGrus(gruList, percent, users, extra);
        endTime = System.nanoTime();
        // Fin medición tiempo
        duration = (endTime - startTime) * Math.pow(10, -9);
        System.out.println(String.format("Ordenación y selección lista grumos: %.5f sec", duration));

        /***********************************************************************
        *****************************FIN ETAPAS*********************************
        ***********************************************************************/
        
             
        System.out.println("Existen " + gruList.length + " grumos.");

        if (relaciones == 1) {
            double dUsers = users;
            double grumoPorcentaje = (gruList[0][1] / dUsers);
            System.out.println(String.format("El mayor grumo contiene %d usuarios (%.2f%%)", gruList[0][1], grumoPorcentaje*100));
            System.out.println("No son necesarias nuevas relaciones de amistad");
        } else {
            System.out.println("Se deben unir los " + relaciones + " mayores");
            for (int i = 0; i < relaciones; i++) {
                double dUsers = users;
                double grumoPorcentaje = (gruList[i][1] / dUsers);
                System.out.println(String.format("#%d: %d usuarios (%.2f%%) "
                        , i + 1,gruList[i][1],grumoPorcentaje*100));
            }
            System.out.println("Nuevas relaciones de amistad (salvadas en extra.txt)");
            for (int i = 0; i < extra.size(); i++) {
                System.out.println(extra.get(i).get(0) + " <-> " + extra.get(i).get(1));
            }

            try {
                saveExtra("extra.txt", extra);
            } catch (IOException e) {
                System.err.println("Error escritura fichero ");
                System.exit(-1);
            }

        }

    }
}