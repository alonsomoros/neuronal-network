package javamodelo.utils;

import javamodelo.pruebas.iris.IrisData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Helper {
    public static ArrayList<IrisData> getIrisDataset(String ruta) {
        ArrayList<IrisData> dataset = new ArrayList<>();
        File file = new File(ruta);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(";");
                String nombre = partes[4];
                float[] caracteristicas = new float[4];
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
}
