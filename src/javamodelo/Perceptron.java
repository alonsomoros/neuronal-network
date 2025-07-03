package javamodelo;

import java.util.Random;

// salida = ∑ wi xi + BIAS
public class Perceptron {
    Double[] pesos;

    Double bias;

    Double tasaAprendizaje;

    Random random;
    public Perceptron(int numeroEntradas, Double bias) {
        this.bias = bias;
        this.tasaAprendizaje = 0.2;
        this.random = new Random();
        this.pesos = new Double[numeroEntradas];

        // Inicializar pesos aleatorios
        inicializarPesos();
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

    public float predecirY(Float x) {
        Double w0 = pesos[0];
        Double w1 = pesos[1];

        // Ecuación de la recta: y = -(w0/w1) * x - (bias/w1)
        return (float) (-(w0 / w1) * x - (this.bias / w1)); // Ecuación de la recta
    }
}
