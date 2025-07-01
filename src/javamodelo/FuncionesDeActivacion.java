package javamodelo;

public class FuncionesDeActivacion {
    public static Double sigmoide(Double x) { // Ya no se usa sigmoid se usa porEj ReLu
        return 1 / (1 + Math.exp(-x));
    }
    public static Float sigmoide(Float x) { // Ya no se usa sigmoid se usa porEj ReLu
        return (float) (1 / (1 + Math.exp(-x)));
    }

    public static Double derivadaSigmoide(Double x) {
        return x * (1 - x); // Derivada de la función sigmoide
    }

    public static Float derivadaSigmoide(Float x) {
        return x * (1 - x); // Derivada de la función sigmoide
    }

    public static Double lineal(Double x) {
        return x; // Función lineal
    }
    public static Float lineal(Float x) {
        return x; // Función lineal
    }

    public static Double derivadaLineal(Double x) {
        return 1.0; // Derivada de la función lineal es 1
    }
    public static Float derivadaLineal(Float x) {
        return 1.0f; // Derivada de la función lineal es 1
    }

    public static Double tangenteHiperbolica(Double x) {
        return Math.tanh(x); // Función tangente hiperbólica
    }
    public static Float tangenteHiperbolica(Float x) {
        return (float) Math.tanh(x); // Función tangente hiperbólica
    }

    public static Double tangenteHiperbolicaDerivada(Double x) {
        return 1 - Math.pow(Math.tanh(x), 2); // Derivada de la función tangente hiperbólica
    }

    public static Float tangenteHiperbolicaDerivada(Float x) {
        return (float) (1 - Math.pow(Math.tanh(x), 2)); // Derivada de la función tangente hiperbólica
    }

    public static Double relu(Double x) {
        return Math.max(0, x); // Función ReLU (Rectified Linear Unit)
    }

    public static Float relu(Float x) {
        return Math.max(0, x); // Función ReLU (Rectified Linear Unit)
    }

    public static Double derivadaReLU(Double x) {
        return (double) (x > 0 ? 1 : 0); // Derivada de la función ReLU
    }

    public static Float derivadaReLU(Float x) {
        return (float) (x > 0 ? 1 : 0); // Derivada de la función ReLU
    }

    public static Double escalon(Double x) {
        return (x >= 0) ? 1.0 : 0.0; // Función escalón
    }

    public static Float escalon(Float x) {
        return (float) ((x >= 0) ? 1.0 : 0.0); // Función escalón
    }

    public static Double derivadaEscalon(Double x) {
        return 0.0; // La derivada de la función escalón no está definida en 0, pero se puede considerar 0 en otros puntos
    }

    public static Float derivadaEscalon(Float x) {
        return 0.0f; // La derivada de la función escalón no está definida en 0, pero se puede considerar 0 en otros puntos
    }

    public static Double softmax(Double x, Double[] inputs) {
        double sum = 0.0;
        for (Double input : inputs) {
            sum += Math.exp(input);
        }
        return Math.exp(x) / sum; // Función softmax
    }

    public static Float softmax(Float x, Float[] inputs) {
        double sum = 0.0;
        for (Float input : inputs) {
            sum += Math.exp(input);
        }
        return (float) (Math.exp(x) / sum); // Función softmax
    }

    public static Double derivadaSoftmax(Double x, Double[] inputs) {
        double sum = 0.0;
        for (Double input : inputs) {
            sum += Math.exp(input);
        }
        double softmaxValue = Math.exp(x) / sum;
        return softmaxValue * (1 - softmaxValue); // Derivada de la función softmax
    }

    public static Float derivadaSoftmax(Float x, Float[] inputs) {
        float sum = 0.0f;
        for (Float input : inputs) {
            sum += (float) Math.exp(input);
        }
        float softmaxValue = (float) (Math.exp(x) / sum);
        return (softmaxValue * (1 - softmaxValue)); // Derivada de la función softmax
    }

    public static Double leakyReLU(Double x) {
        return (x > 0) ? x : 0.01 * x; // Función Leaky ReLU
    }

    public static Float leakyReLU(Float x) {
        return (x > 0) ? x : (float) (0.01 * x); // Función Leaky ReLU
    }

    public static Double derivadaLeakyReLU(Double x) {
        return (x > 0) ? 1.0 : 0.01; // Derivada de la función Leaky ReLU
    }

    public static Float derivadaLeakyReLU(Float x) {
        return (float) ((x > 0) ? 1.0 : 0.01); // Derivada de la función Leaky ReLU
    }

    public static Double elu(Double x, Double alpha) {
        return (x >= 0) ? x : alpha * (Math.exp(x) - 1); // Función ELU (Exponential Linear Unit)
    }

    public static Float elu(Float x, Float alpha) {
        return (x >= 0) ? x : (float) (alpha * (Math.exp(x) - 1)); // Función ELU (Exponential Linear Unit)
    }

    public static Double derivadaELU(Double x, Double alpha) {
        return (x >= 0) ? 1.0 : elu(x, alpha) + alpha; // Derivada de la función ELU
    }

    public static Float derivadaELU(Float x, Float alpha) {
        return (x >= 0) ? (float) 1.0 : elu(x, alpha) + alpha; // Derivada de la función ELU
    }

    public static Double swish(Double x) {
        return x / (1 + Math.exp(-x)); // Función Swish
    }

    public static Float swish(Float x) {
        return (float) (x / (1 + Math.exp(-x))); // Función Swish
    }

    public static Double derivadaSwish(Double x) {
        double sigmoid = 1 / (1 + Math.exp(-x));
        return sigmoid + x * sigmoid * (1 - sigmoid); // Derivada de la función Swish
    }
    public static Float derivadaSwish(Float x) {
        double sigmoid = 1 / (1 + Math.exp(-x));
        return (float) (sigmoid + x * sigmoid * (1 - sigmoid)); // Derivada de la función Swish
    }

    public static Double mish(Double x) {
        return x * Math.tanh(Math.log1p(Math.exp(x))); // Función Mish
    }

    public static Float mish(Float x) {
        return (float) (x * Math.tanh(Math.log1p(Math.exp(x)))); // Función Mish
    }

    public static Double derivadaMish(Double x) {
        double sigmoid = 1 / (1 + Math.exp(-x));
        return sigmoid + x * (1 - sigmoid * sigmoid); // Derivada de la función Mish
    }

    public static Float derivadaMish(Float x) {
        float sigmoid = (float) (1 / (1 + Math.exp(-x)));
        return sigmoid + x * (1 - sigmoid * sigmoid); // Derivada de la función Mish
    }

    public static Double softplus(Double x) {
        return Math.log1p(Math.exp(x)); // Función Softplus
    }


    public static Float softplus(Float x) {
        return (float) Math.log1p(Math.exp(x)); // Función Softplus
    }

    public static Double derivadaSoftplus(Double x) {
        return 1 / (1 + Math.exp(-x)); // Derivada de la función Softplus
    }

    public static Float derivadaSoftplus(Float x) {
        return (float) (1 / (1 + Math.exp(-x))); // Derivada de la función Softplus
    }

    public static Double tanh(Double x) {
        return Math.tanh(x); // Función tangente hiperbólica
    }

    public static Float tanh(Float x) {
        return (float) Math.tanh(x); // Función tangente hiperbólica
    }

    public static Double derivadaTanh(Double x) {
        return 1 - Math.pow(Math.tanh(x), 2); // Derivada de la función tangente hiperbólica
    }
    public static Float derivadaTanh(Float x) {
        return (float) (1 - Math.pow(Math.tanh(x), 2)); // Derivada de la función tangente hiperbólica
    }

    public static Double funcionLineal(Double m, Double n, Double x) {
        return - (m * x) - (n);
    }
    public static Float funcionLineal(float m, float n, float x) {
        return - (m * x) - (n);
    }
}
