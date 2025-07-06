package javamodelo.pruebas.xor;

import javamodelo.funciones_de_activacion.FuncionDeActivacion;
import javamodelo.funciones_de_activacion.FuncionDeActivacionContainer;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Prueba_XOR {
    public final int NUM_NEURONAS = 8;
    public final int EPOCHS = 100;
    public final int NUM_CAPAS_ENTRADAS = 2;
    public final int NUM_CAPAS_OCULTAS = 4;
    public final int NUM_CAPAS_SALIDAS = 1;
    public final int BATCH_SIZE = 50;

    public Float[][][] datos_entrenamiento_XOR = {
            {{0f, 0f}, {0f}},
            {{0f, 1f}, {1f}},
            {{1f, 0f}, {1f}},
            {{1f, 1f}, {0f}}
    };

    public Float[][][] datos_test_XOR = {
            {{0f, 0f}, {0f}},
            {{0f, 1f}, {1f}},
            {{1f, 0f}, {1f}},
            {{1f, 1f}, {0f}}
    };

    public ArrayList<RedNeuronal> redesNeuronales;

    public Prueba_XOR() {
        redesNeuronales = new ArrayList<>();

        setUpRedNeuronalXOR_predefinida();
//        setUpRedNeuronalXOR_entradas();
    }

    public void draw(PApplet applet) {
        if (getRedesNeuronales().isEmpty()) return;

        // Cálculos para dimensiones de las gráficas
        int columnasDeGraficas = (int) Math.round(Math.sqrt(this.redesNeuronales.size() + 1));                      // Porciones de columnas
        int filasDeGraficas = (int) Math.ceil((double) (this.redesNeuronales.size() + 1) / columnasDeGraficas);     // Porciones de filas
        int anchoCuadrante = applet.width / columnasDeGraficas;                                                     // Ancho de cada cuadrante
        int altoCuadrante = applet.height / filasDeGraficas;                                                        // Alto de cada cuadrante

        // Dimensiones del área de la gráfica
        Rectangle dimensionCuadrante = new Rectangle(filasDeGraficas, columnasDeGraficas, anchoCuadrante, altoCuadrante); // x = filas , y = columnas
        // Area para cuadricula
        Rectangle coords_grafica_error_epoch = new Rectangle(0, 0, anchoCuadrante, altoCuadrante);

        dibujarEstructuraGrafica(applet, coords_grafica_error_epoch);

        for (RedNeuronal redNeuronal : redesNeuronales) {
            actualizarYDibujarRed(applet, redNeuronal, dimensionCuadrante, coords_grafica_error_epoch);
        }
    }

    private void actualizarYDibujarRed(PApplet pApplet, RedNeuronal redNeuronal, Rectangle dimensionCuadrante, Rectangle coordenadasGrafica) {

        int x_init = (redNeuronal.ID % dimensionCuadrante.y) * dimensionCuadrante.width;
        int y_init = (int) ((Math.floor((double) redNeuronal.ID / dimensionCuadrante.y)) * dimensionCuadrante.height);
        Rectangle area_cuadrante = new Rectangle(x_init, y_init, dimensionCuadrante.width, dimensionCuadrante.height);

        redNeuronal.actualizar(pApplet);

        redNeuronal.dibujarCuadricula(pApplet, area_cuadrante);
        redNeuronal.dibujarValoresGrafica(pApplet, coordenadasGrafica, redNeuronal.getEpochs(), redNeuronal.getErroresEpochs());
    }

    private void dibujarEstructuraGrafica(PApplet pApplet, Rectangle areaGrafica) {
        final int MARGEN_GRAFICA = 50;
        RedNeuronal redNeuronal = redesNeuronales.get(0);
        redNeuronal.dibujarEstructuraGrafica(pApplet, areaGrafica, MARGEN_GRAFICA, redNeuronal.getEpochs());
    }

    private void setUpRedNeuronalXOR_predefinida() {
        FuncionDeActivacion<Float> fa;
        for (int i = 0; i < this.NUM_NEURONAS; i++) {
            switch (i) {
                case 0:
                    fa = FuncionDeActivacionContainer.LEAKY_RELU;
                    break;
                case 1:
                    fa = FuncionDeActivacionContainer.RELU;
                    break;
                case 2:
                    fa = FuncionDeActivacionContainer.TANH;
                    break;
                case 3:
                    fa = FuncionDeActivacionContainer.ESCALON;
                    break;
                case 4:
                    fa = FuncionDeActivacionContainer.SWISH;
                    break;
                case 5:
                    fa = FuncionDeActivacionContainer.SOFTPLUS;
                    break;
                case 6:
                    fa = FuncionDeActivacionContainer.MISH;
                    break;
                default:
                    fa = FuncionDeActivacionContainer.getInstance().getFuncionDeActivacion((int) (Math.random() * 8 + 1));
            }

            RedNeuronal redNeuronal = new RedNeuronal(EPOCHS, BATCH_SIZE, NUM_CAPAS_ENTRADAS, NUM_CAPAS_OCULTAS, fa, NUM_CAPAS_SALIDAS, FuncionDeActivacionContainer.SIGMOIDE);
            redNeuronal.setDatosTest(datos_test_XOR);
            redNeuronal.setDatosEntrenamiento(datos_entrenamiento_XOR);
            redesNeuronales.add(redNeuronal);
        }
    }

    private void setUpRedNeuronalXOR_entradas() {
        int numEntradas = 2;
        int numSalidas = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el número de epochs: [1, 10, 100, 1000, etc.)");
        int epochs = scanner.nextInt();
        System.out.println("Ingrese el número de neuronas ocultas: [2, 4, 6, etc.)");
        int numOcultas = scanner.nextInt();
        System.out.println("Ingrese el tamaño del batch: [1, 10, 50, 100, etc.)");
        int batchSize = scanner.nextInt();
        System.out.println("Ingrese la tasa de aprendizaje: [0 - 1]. Ej.:  0,1");
        float tasaAprendizaje = scanner.nextFloat();
        System.out.println("Cuantas redes neuronales desea crear? [1 - 8]");
        int numRedes = scanner.nextInt();

        for (int i = 0; i < numRedes; i++) {
            System.out.println("¿Que tipo de función quiere usar como capa oculta?:\n1. ReLU\n2. Sigmoide\n3. Tanh (Tangente Hiperbólica)\n4. Escalón\n5. LeakyReLU\n6. Swish\n7. Softplus\n8. Mish\n(Conteste con el Número)");
            int seleccion = scanner.nextInt();
            FuncionDeActivacion<Float> funcionOculta = FuncionDeActivacionContainer.getInstance().getFuncionDeActivacion(seleccion);
            RedNeuronal redNeuronal = new RedNeuronal(epochs, batchSize, numEntradas, numOcultas, funcionOculta, numSalidas, FuncionDeActivacionContainer.SIGMOIDE);
            redNeuronal.setTasa_aprendizaje(tasaAprendizaje);
            redNeuronal.setDatosTest(datos_test_XOR);
            redNeuronal.setDatosEntrenamiento(datos_entrenamiento_XOR);
            redesNeuronales.add(redNeuronal);
        }

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

    public Float[][][] getDatos_entrenamiento_XOR() {
        return datos_entrenamiento_XOR;
    }

    public void setDatos_entrenamiento_XOR(Float[][][] datos_entrenamiento_XOR) {
        this.datos_entrenamiento_XOR = datos_entrenamiento_XOR;
    }

    public Float[][][] getDatos_test_XOR() {
        return datos_test_XOR;
    }

    public void setDatos_test_XOR(Float[][][] datos_test_XOR) {
        this.datos_test_XOR = datos_test_XOR;
    }

    public ArrayList<RedNeuronal> getRedesNeuronales() {
        return redesNeuronales;
    }

    public void setRedesNeuronales(ArrayList<RedNeuronal> redesNeuronales) {
        this.redesNeuronales = redesNeuronales;
    }
}
