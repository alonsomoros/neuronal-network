package java;

import java.util.Random;

// salida = ∑ wi xi + BIAS
public class Perceptron {
    float[] pesos;

    float bias;

    float tasaAprendizaje;

    Random random;
    public Perceptron(int numeroEntradas, float bias) {
        this.bias = bias;
        this.tasaAprendizaje = 0.2f;
        this.random = new Random();
        this.pesos = new float[numeroEntradas];

        // Inicializar pesos aleatorios
        inicializarPesos();
    }

    private void inicializarPesos() {
        // Inicializar los pesos indicados por números aleatorios entre -1 y 1
        for (int i = 0; i < this.pesos.length; i++) {
            this.pesos[i] = this.random.nextFloat() * 2 - 1; // -1 , 1
        }
    }

    public int predecirSalida(float[] entradas) {
        float sum = 0;
        for (int i = 0; i < entradas.length; i++) {
            sum += entradas[i] * pesos[i];
        }
        sum += bias; // Añadir el bias a la suma

        return funcionActivacion_Signo(sum); // Función de Activación
    }

    private int funcionActivacion_Signo(float n) {
        if (n >= 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public void entrenamiento(float[] entradas, int objetivo) {
        int salida = predecirSalida(entradas);

        // Calcular el error
        float error = objetivo - salida;

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

    private void imprimirEntradasPre(float[] entradas, int objetivo) {
        System.out.println("Entradas: " + java.util.Arrays.toString(entradas));
        System.out.println("Pesos: " + java.util.Arrays.toString(pesos));
        System.out.println("Objetivo: " + objetivo + " (X > Y = 1)");
    }

    public void imprimirError(float error, int objetivo) {
        System.out.println("Salida: " + objetivo + " -> Error: " + error);
        System.out.println("---");
    }

    public void resetPesosRandom() {
        // Reiniciar los pesos a valores aleatorios
        for (int i = 0; i < this.pesos.length; i++) {
            this.pesos[i] = this.random.nextFloat() * 2 - 1; // -1 , 1
        }
        // Reiniciar el bias a un valor aleatorio
        this.bias = this.random.nextFloat() * 2 - 1; // -1 , 1
    }

    public float predecirY(float x) {
        float w0 = pesos[0];
        float w1 = pesos[1];

        // Ecuación de la recta: y = -(w0/w1) * x - (bias/w1)
        return -(w0 / w1) * x - (this.bias / w1); // Ecuación de la recta
    }
}
