package javamodelo.utils;

import javamodelo.pruebas.Data;
import javamodelo.pruebas.iris.IrisData;
import javamodelo.pruebas.xor.XORData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Helper {
    public static ArrayList<Data> getIrisDataset(String ruta) {
        ArrayList<Data> dataset = new ArrayList<>();
        File file = new File(ruta);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(";");
                String nombre = partes[4];
                Float[] caracteristicas = new Float[4];
                for (int i = 0; i < partes.length - 1; i++) {
                    caracteristicas[i] = Float.parseFloat((partes[i]));
                }
                dataset.add(new IrisData(caracteristicas, nombre));
            }
        } catch (FileNotFoundException exception) {
            exception.getMessage();
        }
        return dataset;
    }

    public static ArrayList<Data> getXORDataset() {
        ArrayList<Data> datos_XOR = new ArrayList<>();
        datos_XOR.add(new XORData(new Float[]{0f, 0f}, new Float[]{0f}));
        datos_XOR.add(new XORData(new Float[]{0f, 1f}, new Float[]{1f}));
        datos_XOR.add(new XORData(new Float[]{1f, 0f}, new Float[]{1f}));
        datos_XOR.add(new XORData(new Float[]{1f, 1f}, new Float[]{0f}));
        return datos_XOR;
    }
}
