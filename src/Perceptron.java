import java.util.Random;

// salida = ∑ wi xi + BIAS
public class Perceptron {
    float[] pesos;

    float bias;

    float tasaAprendizaje;

    Random random;

    public Perceptron(int numeroEntradas, float bias) {
        this.bias = bias;
        this.tasaAprendizaje = 0.1f;
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
            return; // No hay error, no se necesita actualizar los pesos
        } else {
            // Imprimir el error
            imprimirError(error, entradas, objetivo);
        }

        // Modificar todos los pesos
        for (int i = 0; i < pesos.length; i++) {
            if (error == 0) {
                return; // No hay error, no se necesita actualizar los pesos
            } else {
                // Imprimir el error
                imprimirError(error, entradas, objetivo);
                // Actualizar cada peso según el error y la tasa de aprendizaje
                pesos[i] += error * entradas[i] * tasaAprendizaje; // Actualizar pesos
            }
        }
        // Actualizar el bias
        bias += error * tasaAprendizaje;

    }

    public void imprimirError(float error, float[] entradas, int salida) {
        System.out.println("Error: " + error + " para la entrada: " + java.util.Arrays.toString(entradas) + " con salida: " + salida);
    }
}
