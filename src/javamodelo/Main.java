package javamodelo;

import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;


public class Main extends PApplet {
    Perceptron perceptron;
    Punto[] puntos = new Punto[100];
    int entrenamientoIndex = 0;
    ArrayList<RedNeuronal> redesNeuronales;
    RedNeuronal redNeuronal_XOR;
    RedNeuronal redNeuronal_XOR2;
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
        size(800, 400);
    }

    public void setup() {
        background(255);
        stroke(0);
        fill(255);
        textSize(16);

        redesNeuronales = new ArrayList<>();

//        setUpPerceptron();
        setUpRedNeuronalXOR();
    }

    private void setUpRedNeuronalXOR() {
        int epochs = 100;
        int numEntradas = 2;
        int numOcultas = 4;
        int numSalidas = 1;
        int batchSize = 50;

        redNeuronal_XOR = new RedNeuronal(epochs, batchSize, numEntradas, numOcultas, FuncionDeActivacionContainer.RELU, numSalidas, FuncionDeActivacionContainer.SIGMOIDE);
        redNeuronal_XOR.setDatosTest(datos_test_XOR);
        redNeuronal_XOR.setDatosEntrenamiento(datos_entrenamiento_XOR);
        redesNeuronales.add(redNeuronal_XOR);

        redNeuronal_XOR2 = new RedNeuronal(epochs, batchSize, numEntradas, numOcultas, FuncionDeActivacionContainer.SIGMOIDE, numSalidas, FuncionDeActivacionContainer.SIGMOIDE);
        redNeuronal_XOR2.setDatosTest(datos_test_XOR);
        redNeuronal_XOR2.setDatosEntrenamiento(datos_entrenamiento_XOR);
        redesNeuronales.add(redNeuronal_XOR2);
    }

    private void setUpPerceptron() {
        perceptron = new Perceptron(2, 0.1);
        for (int i = 0; i < puntos.length; i++) {
            puntos[i] = new Punto(this);
        }
    }

    public void draw() {
        background(255);
        stroke(0);
        fill(255);

        redNeuronal_XOR.actualizar(this);
        redNeuronal_XOR2.actualizar(this);

        // Area para cuadricula
        Rectangle area_cuadricula = new Rectangle(0, 0, this.width / 2, this.height);
        // Area para gráfica
        Rectangle area_grafica = new Rectangle(this.width / 2, 0, this.width / 2, this.height);
        int margin = 50;

        redNeuronal_XOR.dibujarGraficaEstructuraErrorEpoch(this, area_grafica, margin);
        redNeuronal_XOR.dibujarCuadriculaXOR(this, area_cuadricula);

        if (redNeuronal_XOR.getCurrentEpoch() > 1) {
            redNeuronal_XOR.dibujarGraficaProgresoErrorEpoch(this, area_grafica, margin, redNeuronal_XOR.getErroresEpochs());
        }

        if (redNeuronal_XOR2.getCurrentEpoch() > 1) {
            redNeuronal_XOR2.dibujarGraficaProgresoErrorEpoch(this, area_grafica, margin, redNeuronal_XOR2.getErroresEpochs());
        }

    }

    private void dibujarPruebaPerceptronSimple() {
        // Dibujar la función lineal
//        dibujarFuncionLineal();
        // Dibujar la línea de decisión del perceptrón
        dibujarLineaDecision();
        // Mostrar los puntos
        for (Punto punto : puntos) {
            punto.show(this);
        }
        // Mostrar las etiquetas de los puntos
        for (Punto punto : puntos) {
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
        Punto entrenamiento = puntos[entrenamientoIndex];
        Float[] entradas = {entrenamiento.x, entrenamiento.y};
        Float objetivo = entrenamiento.etiqueta;
        perceptron.entrenar(entradas, objetivo);
        entrenamientoIndex++;
        if (entrenamientoIndex >= puntos.length) {
            entrenamientoIndex = 0;
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
//    @Override
//    public void keyPressed(KeyEvent event) {
//        if (event.getKey() == ENTER) {
//            for (int i = 0; i < puntos.length; i++) {
//                puntos[i] = new Punto(this);
//                perceptron.resetPesosRandom();
//            }
//        }
//        super.keyPressed(event);

//    }
}
