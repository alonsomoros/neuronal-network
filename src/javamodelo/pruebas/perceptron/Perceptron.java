package javamodelo.pruebas.perceptron;

import javamodelo.funciones_de_activacion.FuncionDeActivacionContainer;
import processing.core.PApplet;

import java.util.Random;

// salida = ∑ wi xi + BIAS
public class Perceptron {
    private Double[] pesos;

    private Double bias;

    private Double tasaAprendizaje;

    private Random random;

    public final int NUM_PUNTOS = 100;
    public final Punto[] PUNTOS = new Punto[NUM_PUNTOS];

    public static int epochs = 100;
    public Perceptron(int numeroEntradas, Double bias) {
        this.bias = bias;
        this.tasaAprendizaje = 0.2;
        this.random = new Random();
        this.pesos = new Double[numeroEntradas];

        // Inicializar puntos
        inicializarPuntos();

        // Inicializar pesos aleatorios
        inicializarPesos();
    }

    private void inicializarPuntos() {
        for (int i = 0; i < PUNTOS.length; i++) {
            PUNTOS[i] = new Punto();
        }
    }

    private void inicializarPesos() {
        // Inicializar los pesos indicados por números aleatorios entre -1 y 1
        for (int i = 0; i < this.pesos.length; i++) {
            this.pesos[i] = (double) (this.random.nextFloat() * 2 - 1); // -1 , 1
        }
    }

    public Float predict(Float[] entradas) {
        float sum = 0;
        for (int i = 0; i < entradas.length; i++) {
            sum += (float) (entradas[i] * pesos[i]);
        }
        sum += bias; // Añadir el bias a la suma

        return FuncionDeActivacionContainer.escalon(sum); // Función de Activación
    }

    public void entrenar(Float[] entradas, Float objetivo) {
        Float salida = predict(entradas);

        // Calcular el error
        Float error = objetivo - salida;

        if (error == 0) {
            System.out.println("---");
            return; // No hay error, no se necesita actualizar los pesos
        } else {
            // Imprimir las entradas antes de modificación
            imprimirEntradasPre(entradas, objetivo);
            // Imprimir el error
            imprimirError(error, salida);

            // Modificar todos los pesos
            for (int i = 0; i < pesos.length; i++) {
                // Actualizar cada peso según el error y la tasa de aprendizaje
                pesos[i] += error * entradas[i] * tasaAprendizaje; // Actualizar pesos
            }
            // Actualizar el bias
            bias += error * tasaAprendizaje;
            System.out.println("Pesos actualizados: " + java.util.Arrays.toString(pesos) + " | Bias actualizado: " + bias);
        }
    }

    private void entrenamientoPerceptronSimple() {
        Punto entrenamiento = PUNTOS[epochs - 1];
        Float[] entradas = {entrenamiento.x, entrenamiento.y};
        Float objetivo = entrenamiento.etiqueta;
        entrenar(entradas, objetivo);
        epochs--;
        if (epochs <= 0) {
            epochs = PUNTOS.length;
        }
    }

    public void dibujarPruebaPerceptronSimple(PApplet pApplet) {

        // Dibujar la función lineal
        dibujarFuncionLineal(pApplet);

        // Dibujar la línea de decisión del perceptrón
        dibujarLineaDecision(pApplet);

        // Mostrar los puntos
        for (Punto punto : PUNTOS) {
            punto.draw(pApplet);
        }

        // Mostrar las etiquetas de los puntos
        for (Punto punto : PUNTOS) {
            punto.showEtiqueta(pApplet, this, punto);
        }

        // Aprovecha el bucle de dibujo para entrenar el perceptrón
        entrenamientoPerceptronSimple();
    }

    private void dibujarFuncionLineal(PApplet pApplet) {
        Punto p1 = new Punto(-1.0f, function(-1.0f));
        Punto p2 = new Punto(1.0f, function(1.0f));
        pApplet.line(p1.getXpixel(pApplet), p1.getYpixel(pApplet), p2.getXpixel(pApplet), p2.getYpixel(pApplet));
    }

    private void dibujarLineaDecision(PApplet pApplet) {
        Punto p3 = new Punto(-1.0f, predecirY(-1f));
        Punto p4 = new Punto(1.0f, predecirY(1f));
        pApplet.line(p3.getXpixel(pApplet), p3.getYpixel(pApplet), p4.getXpixel(pApplet), p4.getYpixel(pApplet));
    }

    public float predecirY(Float x) {
        Double w0 = pesos[0];
        Double w1 = pesos[1];

        // Ecuación de la recta: y = -(w0/w1) * x - (bias/w1)
        return (float) (-(w0 / w1) * x - (this.bias / w1)); // Ecuación de la recta
    }

    // Y = MX + N

    public static float function(float x) {
        float valor_m = -0.3f; // Pendiente
        float valor_n = -0.2f; // Intersección con el eje Y
        return FuncionDeActivacionContainer.funcionLineal(valor_m, valor_n, x);
    }

    private void imprimirEntradasPre(Float[] entradas, Float objetivo) {
        System.out.println("Entradas: " + java.util.Arrays.toString(entradas));
        System.out.println("Pesos: " + java.util.Arrays.toString(pesos));
        System.out.println("Objetivo: " + objetivo + " (X > Y = 1)");
    }
    public void imprimirError(Float error, Float objetivo) {
        System.out.println("Salida: " + objetivo + " -> Error: " + error);
        System.out.println("---");
    }

    public void resetPesosRandom() {
        // Reiniciar los pesos a valores aleatorios
        for (int i = 0; i < this.pesos.length; i++) {
            this.pesos[i] = this.random.nextDouble() * 2 - 1; // -1 , 1
        }
        // Reiniciar el bias a un valor aleatorio
        this.bias = this.random.nextDouble() * 2 - 1; // -1 , 1
    }
}
