package javaModelo;

public class FuncionesDeActivacion {
    public static Double sigmoide(Double x) { // Ya no se usa sigmoid se usa porEj ReLu
        return 1 / (1 + Math.exp(-x));
    }

    public static Double derivadaSigmoide(Double x) {
        return x * (1 - x); // Derivada de la función sigmoide
    }

    public static Double lineal(Double x) {
        return x; // Función lineal
    }

    public static Double derivadaLineal(Double x) {
        return 1.0; // Derivada de la función lineal es 1
    }

    public static Double tangenteHiperbolica(Double x) {
        return Math.tanh(x); // Función tangente hiperbólica
    }

    public static Double tangenteHiperbolicaDerivada(Double x) {
        return 1 - Math.pow(Math.tanh(x), 2); // Derivada de la función tangente hiperbólica
    }

    public static Double relu(Double x) {
        return Math.max(0, x); // Función ReLU (Rectified Linear Unit)
    }

    public static Double derivadaReLU(Double x) {
        return (double) (x > 0 ? 1 : 0); // Derivada de la función ReLU
    }

    public static Double escalon(Double x) {
        return (x >= 0) ? 1.0 : 0.0; // Función escalón
    }

    public static Double derivadaEscalon(Double x) {
        return 0.0; // La derivada de la función escalón no está definida en 0, pero se puede considerar 0 en otros puntos
    }

    public static Double softmax(Double x, Double[] inputs) {
        double sum = 0.0;
        for (Double input : inputs) {
            sum += Math.exp(input);
        }
        return Math.exp(x) / sum; // Función softmax
    }

    public static Double derivadaSoftmax(Double x, Double[] inputs) {
        double sum = 0.0;
        for (Double input : inputs) {
            sum += Math.exp(input);
        }
        double softmaxValue = Math.exp(x) / sum;
        return softmaxValue * (1 - softmaxValue); // Derivada de la función softmax
    }

    public static Double leakyReLU(Double x) {
        return (x > 0) ? x : 0.01 * x; // Función Leaky ReLU
    }

    public static Double derivadaLeakyReLU(Double x) {
        return (x > 0) ? 1.0 : 0.01; // Derivada de la función Leaky ReLU
    }

    public static Double elu(Double x, Double alpha) {
        return (x >= 0) ? x : alpha * (Math.exp(x) - 1); // Función ELU (Exponential Linear Unit)
    }

    public static Double derivadaELU(Double x, Double alpha) {
        return (x >= 0) ? 1.0 : elu(x, alpha) + alpha; // Derivada de la función ELU
    }

    public static Double swish(Double x) {
        return x / (1 + Math.exp(-x)); // Función Swish
    }

    public static Double derivadaSwish(Double x) {
        double sigmoid = 1 / (1 + Math.exp(-x));
        return sigmoid + x * sigmoid * (1 - sigmoid); // Derivada de la función Swish
    }

    public static Double mish(Double x) {
        return x * Math.tanh(Math.log1p(Math.exp(x))); // Función Mish
    }

    public static Double derivadaMish(Double x) {
        double sigmoid = 1 / (1 + Math.exp(-x));
        return sigmoid + x * (1 - sigmoid * sigmoid); // Derivada de la función Mish
    }

    public static Double softplus(Double x) {
        return Math.log1p(Math.exp(x)); // Función Softplus
    }

    public static Double derivadaSoftplus(Double x) {
        return 1 / (1 + Math.exp(-x)); // Derivada de la función Softplus
    }

    public static Double tanh(Double x) {
        return Math.tanh(x); // Función tangente hiperbólica
    }

    public static Double derivadaTanh(Double x) {
        return 1 - Math.pow(Math.tanh(x), 2); // Derivada de la función tangente hiperbólica
    }

    public static float funcionLineal(float m, float n, float x) {
        return - (m * x) - (n);
    }
}
