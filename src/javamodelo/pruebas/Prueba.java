package javamodelo.pruebas;

import javamodelo.funciones_de_activacion.FuncionDeActivacion;
import javamodelo.funciones_de_activacion.FuncionDeActivacionContainer;
import javamodelo.pruebas.iris.Prueba_IRIS;
import javamodelo.pruebas.xor.Prueba_XOR;
import javamodelo.utils.Helper;

import java.util.ArrayList;

public class Prueba {
    protected final int NUM_NEURONAS = 3;
    protected final int EPOCHS = 100;
    protected final int BATCH_SIZE = 50;
    protected ArrayList<RedNeuronal> redesNeuronales;
    protected int NUM_CAPAS_ENTRADAS;
    protected int NUM_CAPAS_OCULTAS;
    protected int NUM_CAPAS_SALIDAS;

    protected static ArrayList<Data> DATASET;
    protected ArrayList<Data> datos_entrenamiento;
    protected ArrayList<Data> datos_test;

    public Prueba(int num_capas_entradas, int num_capas_ocultas, int num_capas_salidas, int porcentajeEntrenamiento) {
        redesNeuronales = new ArrayList<>();
        NUM_CAPAS_ENTRADAS = num_capas_entradas;
        NUM_CAPAS_OCULTAS = num_capas_ocultas;
        NUM_CAPAS_SALIDAS = num_capas_salidas;

        setUpEstructuraRedNeuronalPrueba();
        setUpDatosPrueba(porcentajeEntrenamiento);
        setUpDatosRedNeuronalPrueba();
    }

    private void setUpDatosRedNeuronalPrueba() {
        for (RedNeuronal redNeuronal : redesNeuronales) {
            redNeuronal.setDatosEntrenamiento(datos_entrenamiento);
            redNeuronal.setDatosTest(datos_test);
        }
    }

    public void setUpEstructuraRedNeuronalPrueba() {
        FuncionDeActivacion<Float> funcionDeActivacionOculta;
        for (int i = 0; i < this.NUM_NEURONAS; i++) {
            switch (i) {
                case 0:
                    funcionDeActivacionOculta = FuncionDeActivacionContainer.LEAKY_RELU;
                    break;
                case 1:
                    funcionDeActivacionOculta = FuncionDeActivacionContainer.RELU;
                    break;
                case 2:
                    funcionDeActivacionOculta = FuncionDeActivacionContainer.TANH;
                    break;
                case 3:
                    funcionDeActivacionOculta = FuncionDeActivacionContainer.ESCALON;
                    break;
                case 4:
                    funcionDeActivacionOculta = FuncionDeActivacionContainer.SWISH;
                    break;
                case 5:
                    funcionDeActivacionOculta = FuncionDeActivacionContainer.SOFTPLUS;
                    break;
                case 6:
                    funcionDeActivacionOculta = FuncionDeActivacionContainer.MISH;
                    break;
                default:
                    funcionDeActivacionOculta = FuncionDeActivacionContainer.getInstance().getFuncionDeActivacion((int) (Math.random() * 8 + 1));
            }

            RedNeuronal redNeuronal;
            FuncionDeActivacion<Float> funcionDeActivacionSalida;
            if (this instanceof Prueba_IRIS) {
                funcionDeActivacionSalida = FuncionDeActivacionContainer.SOFTMAX;
            } else {
                funcionDeActivacionSalida = FuncionDeActivacionContainer.SIGMOIDE;
            }
            redNeuronal = new RedNeuronal(this, EPOCHS, BATCH_SIZE, NUM_CAPAS_ENTRADAS, NUM_CAPAS_OCULTAS, funcionDeActivacionOculta, NUM_CAPAS_SALIDAS, funcionDeActivacionSalida);
            redesNeuronales.add(redNeuronal);
        }
    }

    protected void setUpDatosPrueba(int porcentajeEntrenamiento) {
        if (this instanceof Prueba_XOR) {
            DATASET = Helper.getXORDataset();
        } else if (this instanceof Prueba_IRIS) {
            DATASET = Helper.getIrisDataset("rec/Iris_dataset.csv");
        }

        int porcentaje = DATASET.size() * porcentajeEntrenamiento / 100;
        datos_entrenamiento = new ArrayList<>(DATASET.subList(0, porcentaje));
        datos_test = new ArrayList<>(DATASET.subList(porcentaje, DATASET.size()));
    }

    // ----- GETTERS & SETTERS -----
    public int getNUM_NEURONAS() {
        return NUM_NEURONAS;
    }

    public int getEPOCHS() {
        return EPOCHS;
    }

    public int getNUM_CAPAS_ENTRADAS() {
        return NUM_CAPAS_ENTRADAS;
    }

    public int getNUM_CAPAS_OCULTAS() {
        return NUM_CAPAS_OCULTAS;
    }

    public int getNUM_CAPAS_SALIDAS() {
        return NUM_CAPAS_SALIDAS;
    }

    public int getBATCH_SIZE() {
        return BATCH_SIZE;
    }

    public ArrayList<RedNeuronal> getRedesNeuronales() {
        return redesNeuronales;
    }

    public void setRedesNeuronales(ArrayList<RedNeuronal> redesNeuronales) {
        this.redesNeuronales = redesNeuronales;
    }
}
