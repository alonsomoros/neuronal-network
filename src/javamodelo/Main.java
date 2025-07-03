package javamodelo;

import javamodelo.utils.Dibujador;
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
    int epochs = 10000;
    int filtro_epochs = epochs / 10; // Cada cuántas epochs se imprime el resultado
    Float[] errores_epochs = new Float[epochs];
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
        size(800, 400);
    }

    public void setup() {
        background(255);
        stroke(0);
        fill(255);
        textSize(16);

//        setUpPerceptron();
        setUpRedNeuronalXOR();
    }

    private void setUpRedNeuronalXOR() {
        redNeuronal = new RedNeuronal(2, 4, 1, FuncionesDeActivacion::relu, FuncionesDeActivacion::derivadaReLU,FuncionesDeActivacion::sigmoide, FuncionesDeActivacion::derivadaSigmoide);

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

    public void draw() {
        background(255);
        stroke(0);
        fill(255);

        // Dibujar la función lineal
//        dibujarPruebaPerceptronSimple();
        dibujarPruebaRedNeuronalXOR();
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

    private void dibujarPruebaRedNeuronalXOR() {
        int resolution = 10;
        int cols = (width / 2) / resolution; // Para tener lugar a la otra parte
        int rows = height / resolution;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                float x = (float) i / cols;
                float y = (float) j / rows;
                Float[] inputs = {x, y};
                Float[] prediction = redNeuronal.predict(inputs);
                stroke(0);
                fill((prediction[0] * 255));
                rect(i * resolution, j * resolution, resolution, resolution);
            }
        }
        dibujarGraficaProgresoErrorEpoch(errores_epochs);
    }

    private void dibujarGraficaProgresoErrorEpoch(Float[] errores_epoch) {
        fill(255);

        float x_init = (float) width / 2;
        float x_end = width;
        float y_init = 0;
        float y_end = height;
        float margin = 50;

        Dibujador.dibujarGrafica(this, x_init, y_init, x_end, y_end, margin, errores_epoch, errores_epoch.length);
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

    private void entrenamientoRedNeuronalXOR() {
        for (int i = 1; i <= epochs; i++) {
            ArrayList<Float[][]> batch = new ArrayList<>();
            for (int j = 0; j < batchSize; j++) { // Tamaño del batch
                batch.add(datos_entrenamiento[new Random().nextInt(datos_entrenamiento.length)]);
            }

            float coste = 0f;
            float sumCorrectos = 0f;
            for (Float[][] data : batch) {
                Float[] entradas = data[0]; // [e1, e2]
                Float[] objetivos = data[1]; // [obj1]
                Float[] resultadoEntrenamiento = redNeuronal.entrenar(entradas, objetivos); // MSE y Correctos


                sumCorrectos += resultadoEntrenamiento[1];

                coste += resultadoEntrenamiento[0];
            }
            coste = (coste / batchSize);

            errores_epochs[i - 1] = coste;

            if (i % (filtro_epochs / 10) == 0) {
                System.out.println("<< Error en el epoch " + i + " es: " + errores_epochs[i - 1] + " >>"); // Cuidado porque si no es la anterior sale null, ya que todavía no ha hecho la siguiente

                if (i % filtro_epochs == 0) {
                    System.out.printf("Epoch: %d, Coste: %.8f\nAccuracy: %.2f\nCorrectos: %.0f de %d\n", i, coste, (sumCorrectos / batchSize), sumCorrectos, batchSize);
                }
            }
        }
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
}
