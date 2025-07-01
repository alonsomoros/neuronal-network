package javamodelo;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Main extends PApplet {
    Perceptron perceptron;
    Punto[] puntos = new Punto[100];
    int entrenamientoIndex = 0;

    RedNeuronal redNeuronal;
    int batchSize = 1000; // Tamaño del batch para el entrenamiento
    int epochs = 100;
    int filtro_epochs = epochs / 10; // Cada cuántas epochs se imprime el resultado
    Float[][][] datos_entrenamiento = {
            {{0f, 0f}, {0f}},
            {{0f, 1f}, {1f}},
            {{1f, 0f}, {1f}},
            {{1f, 1f}, {0f}}
    };

    Float[][][] datos_test = {
            {{0f, 0f}, {0f}},
            {{0f, 1f}, {1f}},
            {{1f, 0f}, {1f}},
            {{1f, 1f}, {0f}}
    };

    public static void main(String[] args) {
        PApplet.main("javamodelo.Main");
    }

    public void settings() {
        size(400, 400);
    }

    public void setup() {
        background(255);
        stroke(0);
        fill(255);
        textSize(16);

//        setUpPerceptron();
        setUpRedNeuronalXOR();
    }

    public void draw() {
        background(255);
        stroke(0);
        fill(255);

        // Dibujar la función lineal
//        dibujarPruebaPerceptronSimple();
        dibujarPruebaRedNeuronalXOR();
    }

    private void entrenamientoRedNeuronalXOR() {
        for (int i = 0; i < epochs; i++) {
            ArrayList<Float[][]> batch = new ArrayList<>();
            for (int j = 0; j < batchSize; j++) { // Tamaño del batch
                batch.add(datos_entrenamiento[new Random().nextInt(datos_entrenamiento.length)]);
            }
            float sumMSE = 0f;
            float sumCorrectos = 0f;
            for (Float[][] data : batch) {
                Float[] entradas = data[0]; // [e1, e2]
                Float[] objetivos = data[1]; // [obj1]
                Float[] resultadoEntrenamiento = redNeuronal.entrenar(entradas, objetivos); // MSE y Correctos
                sumCorrectos += resultadoEntrenamiento[1];
                sumMSE += resultadoEntrenamiento[0];
            }
            if (i % filtro_epochs == 0) {
                System.out.println("Epoch: " + i + ", MSE: " + (sumMSE / batchSize) + "\nAccuracy: " + (sumCorrectos / batchSize) + "\nCorrectos: " + sumCorrectos + " de " + batchSize + "\n");
            }
        }

    }

    private void dibujarPruebaRedNeuronalXOR() {
        int resolution = 10;
        int cols = width / resolution;
        int rows = height / resolution;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                float x = (float) i / cols;
                float y = (float) j / rows;
//                System.out.println("x: " + x + ", y: " + y);
                Float[] inputs = {x, y};
                Float[] prediction = redNeuronal.predict(inputs);
//                System.out.println("Prediction: " + prediction[0] + " que es de color " + Color.getColor("Color", (int) (prediction[0] * 255)));
                stroke(0);
                fill((prediction[0] * 255));
                rect(i * resolution, j * resolution, resolution, resolution);
            }
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

    // Y = MX + N
    public static float function(float x) {
        float valor_m = -0.3f; // Pendiente
        float valor_n = -0.2f; // Intersección con el eje Y
        return FuncionesDeActivacion.funcionLineal(valor_m, valor_n, x);
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

    private void setUpRedNeuronalXOR() {
        redNeuronal = new RedNeuronal(2, 4, 1);

        System.out.println("Antes del entrenamiento: ");
        for (Float[][] floats : datos_test) {
            Float[] resul = redNeuronal.predict(floats[0]);
            System.out.println(Arrays.toString(resul));
        }

        entrenamientoRedNeuronalXOR();

        System.out.println("Después del entrenamiento: ");
        for (Float[][] floats : datos_test) {
            Float[] resul = redNeuronal.predict(floats[0]);
            System.out.println(Arrays.toString(resul));
        }
    }

    private void setUpPerceptron() {
        perceptron = new Perceptron(2, 0.1);
        for (int i = 0; i < puntos.length; i++) {
            puntos[i] = new Punto(this);
        }
    }
}
