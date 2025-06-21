import processing.core.PApplet;

public class Perceptron extends PApplet {
    float[] entradas;
    float[] pesos = new float[2];

    float tasaAprendizaje;

    public Perceptron() {
        tasaAprendizaje = 0.1f;
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = (random(-1,1)); // -1 , 1
        }
    }

    public int guess(float[] entradas) {
        float sum = 0;
        for (int i = 0; i < entradas.length; i++) {
            sum += entradas[i] * pesos[i];
        }
        return signo(sum); // Función de Activación
    }

    private int signo(float n) {
        if (n >= 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public void entrenamiento(float[] entradas, int objetivo) {
        int intento = guess(entradas);
        float error = objetivo - intento;

        // Modificar todos los pesos
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] += error * entradas[i] * tasaAprendizaje; // Update weights
        }
    }
}
