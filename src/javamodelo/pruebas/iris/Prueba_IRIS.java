package javamodelo.pruebas.iris;

import javamodelo.utils.Helper;

import java.util.ArrayList;

public class Prueba_IRIS {
    public final ArrayList<IrisData> IRIS_DATASET = Helper.getIrisDataset("rec/Iris_dataset.csv");
    public final ArrayList<IrisData> DATOS_ENTRENAMIENTO;
    public final ArrayList<IrisData> DATOS_TEST;

    public Prueba_IRIS(int porcentajeEntrenamiento) {
        int porcentaje = IRIS_DATASET.size() * porcentajeEntrenamiento / 100;
        DATOS_ENTRENAMIENTO = new ArrayList<>(IRIS_DATASET.subList(0, porcentaje));
        DATOS_TEST = new ArrayList<>(IRIS_DATASET.subList(porcentaje, IRIS_DATASET.size()));
    }

}
