package javamodelo;

import javamodelo.funciones_de_activacion.FuncionDeActivacion;
import javamodelo.funciones_de_activacion.FuncionDeActivacionContainer;
import processing.core.PApplet;
import processing.event.KeyEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends PApplet {

    static class PRUEBA_DATOS_PERCEPTRON {
        static final int NUM_PUNTOS = 100;
        static int EPOCHS = 1000;
        static final Punto[] PUNTOS = new Punto[PRUEBA_DATOS_PERCEPTRON.NUM_PUNTOS];
        static final float TASA_APRENDIZAJE = 0.1f;
    }
    Perceptron perceptron;

    ArrayList<RedNeuronal> redesNeuronales;

    static class PRUEBA_DATOS_XOR {
        static final int NUM_NEURONAS = 5;
        static final int EPOCHS = 100;
        static final int NUM_CAPAS_ENTRADAS = 2;
        static final int NUM_CAPAS_OCULTAS = 4;
        static final int NUM_CAPAS_SALIDAS = 1;
        static final int BATCH_SIZE = 50;
    }
    Float[][][] datos_entrenamiento_XOR = {
            {{0f, 0f}, {0f}},
            {{0f, 1f}, {1f}},
            {{1f, 0f}, {1f}},
            {{1f, 1f}, {0f}}
    };

    Float[][][] datos_test_XOR = {
            {{0f, 0f}, {0f}},
            {{0f, 1f}, {1f}},
            {{1f, 0f}, {1f}},
            {{1f, 1f}, {0f}}
    };

    public static void main(String[] args) {
        PApplet.main("javamodelo.Main");
    }

    public void settings() {
        size(900, 900);
    }

    public void setup() {
        inicializarCanvas();
        textSize(16);

        redesNeuronales = new ArrayList<>();

//        setUpRedNeuronalXOR_entradas();
        setUpRedNeuronalXOR();

//        setUpPerceptron();
    }

    private void setUpRedNeuronalXOR() {


        FuncionDeActivacion<Float> fa;
        for (int i = 0; i < PRUEBA_DATOS_XOR.NUM_NEURONAS; i++) {
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
                    fa = FuncionDeActivacionContainer.SIGMOIDE;
            }

            RedNeuronal redNeuronal = new RedNeuronal(PRUEBA_DATOS_XOR.EPOCHS, PRUEBA_DATOS_XOR.BATCH_SIZE, PRUEBA_DATOS_XOR.NUM_CAPAS_ENTRADAS, PRUEBA_DATOS_XOR.NUM_CAPAS_OCULTAS, fa, PRUEBA_DATOS_XOR.NUM_CAPAS_SALIDAS, FuncionDeActivacionContainer.SIGMOIDE);
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
        System.out.println("Cuantas redes neuronales desea crear? [1 - 10]");
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

    private void setUpPerceptron() {
        perceptron = new Perceptron(2, 0.1);
        for (int i = 0; i < PRUEBA_DATOS_PERCEPTRON.PUNTOS.length; i++) {
            PRUEBA_DATOS_PERCEPTRON.PUNTOS[i] = new Punto(this);
        }
    }

    public void draw() {
        inicializarCanvas();
        if (redesNeuronales.isEmpty()) return;


        // Cálculos para dimensiones de las gráficas
        int columnasDeGraficas = (int) Math.round(Math.sqrt(redesNeuronales.size() + 1));                      // Porciones de columnas
        int filasDeGraficas = (int) Math.ceil((double) (redesNeuronales.size() + 1) / columnasDeGraficas);        // Porciones de filas
        int anchoCuadrante = this.width / columnasDeGraficas;                                                  // Ancho de cada cuadrante
        int altoCuadrante = this.height / filasDeGraficas;                                                     // Alto de cada cuadrante

        // Dimensiones del área de la gráfica
        Rectangle dimensionCuadrante = new Rectangle(filasDeGraficas, columnasDeGraficas, anchoCuadrante, altoCuadrante); // x = filas , y = columnas

        // Area para cuadricula
        Rectangle coords_grafica_error_epoch = new Rectangle(0, 0, anchoCuadrante, altoCuadrante);

        dibujarEstructuraGrafica(coords_grafica_error_epoch);

        for (RedNeuronal redNeuronal : redesNeuronales) {
            actualizarYDibujarRed(redNeuronal, dimensionCuadrante, coords_grafica_error_epoch);
        }
    }

    private void actualizarYDibujarRed(RedNeuronal redNeuronal, Rectangle dimensionCuadrante, Rectangle coordenadasGrafica) {

        int x_init = (redNeuronal.ID % dimensionCuadrante.y) * dimensionCuadrante.width;
        int y_init = (int) ((Math.floor((double) redNeuronal.ID / dimensionCuadrante.y)) * dimensionCuadrante.height);
        Rectangle area_cuadrante = new Rectangle(x_init, y_init, dimensionCuadrante.width, dimensionCuadrante.height);

        redNeuronal.actualizar(this);

        redNeuronal.getDibujador().dibujarCuadricula(this, area_cuadrante);
        redNeuronal.getDibujador().dibujarValoresGrafica(this, coordenadasGrafica, redNeuronal.getEpochs(), redNeuronal.getErroresEpochs());
    }

    private void dibujarEstructuraGrafica(Rectangle areaGrafica) {
        final int MARGEN_GRAFICA = 50;
        RedNeuronal redNeuronal = redesNeuronales.get(0);
        redNeuronal.getDibujador().dibujarEstructuraGrafica(this, areaGrafica, MARGEN_GRAFICA, redNeuronal.getEpochs());
    }

    private void inicializarCanvas() {
        background(255);
        stroke(0);
        fill(255);
    }

    private void dibujarPruebaPerceptronSimple() {
        // Dibujar la función lineal
//        dibujarFuncionLineal();
        // Dibujar la línea de decisión del perceptrón
        dibujarLineaDecision();
        // Mostrar los puntos
        for (Punto punto : PRUEBA_DATOS_PERCEPTRON.PUNTOS) {
            punto.show(this);
        }
        // Mostrar las etiquetas de los puntos
        for (Punto punto : PRUEBA_DATOS_PERCEPTRON.PUNTOS) {
            punto.showEtiqueta(this, perceptron, punto);
        }
        // Aprovecha el bucle de dibujo para entrenar el perceptrón
        entrenamientoPerceptronSimple();
    }

    private void dibujarLineaDecision() {
        Punto p3 = new Punto(this, -1.0f, perceptron.predecirY(-1f));
        Punto p4 = new Punto(this, 1.0f, perceptron.predecirY(1f));
        line(p3.getXpixel(), p3.getYpixel(), p4.getXpixel(), p4.getYpixel());
    }

    private void dibujarFuncionLineal() {
        Punto p1 = new Punto(this, -1.0f, function(-1.0f));
        Punto p2 = new Punto(this, 1.0f, function(1.0f));
        line(p1.getXpixel(), p1.getYpixel(), p2.getXpixel(), p2.getYpixel());
    }

    private void entrenamientoPerceptronSimple() {
        Punto entrenamiento = PRUEBA_DATOS_PERCEPTRON.PUNTOS[PRUEBA_DATOS_PERCEPTRON.EPOCHS];
        Float[] entradas = {entrenamiento.x, entrenamiento.y};
        Float objetivo = entrenamiento.etiqueta;
        perceptron.entrenar(entradas, objetivo);
        PRUEBA_DATOS_PERCEPTRON.EPOCHS--;
        if (PRUEBA_DATOS_PERCEPTRON.EPOCHS <= 0) {
            PRUEBA_DATOS_PERCEPTRON.EPOCHS = PRUEBA_DATOS_PERCEPTRON.PUNTOS.length;
        }
    }

    // Y = MX + N
    public static float function(float x) {
        float valor_m = -0.3f; // Pendiente
        float valor_n = -0.2f; // Intersección con el eje Y
        return FuncionDeActivacionContainer.funcionLineal(valor_m, valor_n, x);
    }

//    public void mousePressed() {
//        for (Punto punto : puntos) {
//            float[] entradas = {punto.x, punto.y};
//            Double objetivo = punto.etiqueta;
//            perceptron.entrenamiento(entradas, objetivo);
//        }
//        System.out.println("------------------------------------------------------------");

//    }
    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ENTER) {
            for (RedNeuronal rn : redesNeuronales) {
                rn.reset();
            }
        }
        super.keyPressed(event);

    }
}
