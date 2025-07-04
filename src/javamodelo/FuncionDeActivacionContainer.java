package javamodelo;

import java.util.HashMap;
import java.util.Map;

public class FuncionDeActivacionContainer {

    private static FuncionDeActivacionContainer instance;
    private final Map<Integer, FuncionDeActivacion<Float>> funciones;


    public static FuncionDeActivacionContainer getInstance() {
        if (instance == null) {
            instance = new FuncionDeActivacionContainer();
        }
        return instance;
    }

    public FuncionDeActivacionContainer() {
        funciones = new HashMap<>();
        funciones.put(1, RELU);
        funciones.put(2, SIGMOIDE);
        funciones.put(3, TANH);
        funciones.put(4, ESCALON);
        funciones.put(5, LEAKY_RELU);
        funciones.put(6, SWISH);
        funciones.put(7, SOFTPLUS);
        funciones.put(8, MISH);
    }

    // -- INICIALIZADORES --
    public static final FuncionDeActivacion<Float> SIGMOIDE = new FuncionDeActivacion<>(
            "SIGMOIDE",
            FuncionDeActivacionContainer::sigmoide,
            FuncionDeActivacionContainer::derivadaSigmoide
    );

    public static final FuncionDeActivacion<Float> TANH = new FuncionDeActivacion<>(
            "TANH",
            FuncionDeActivacionContainer::tangenteHiperbolica,
            FuncionDeActivacionContainer::tangenteHiperbolicaDerivada
    );

    public static final FuncionDeActivacion<Float> RELU = new FuncionDeActivacion<>(
            "RELU",
            FuncionDeActivacionContainer::relu,
            FuncionDeActivacionContainer::derivadaReLU
    );

    public static final FuncionDeActivacion<Float> ESCALON = new FuncionDeActivacion<>(
            "ESCALON",
            FuncionDeActivacionContainer::escalon,
            FuncionDeActivacionContainer::derivadaEscalon
    );

    public static final FuncionDeActivacion<Float> LEAKY_RELU = new FuncionDeActivacion<>(
            "LEAKY_RELU",
            FuncionDeActivacionContainer::leakyReLU,
            FuncionDeActivacionContainer::derivadaLeakyReLU
    );

    public static final FuncionDeActivacion<Float> SWISH = new FuncionDeActivacion<>(
            "SWISH",
            FuncionDeActivacionContainer::swish,
            FuncionDeActivacionContainer::derivadaSwish
    );

    public static final FuncionDeActivacion<Float> MISH = new FuncionDeActivacion<>(
            "MISH",
            FuncionDeActivacionContainer::mish,
            FuncionDeActivacionContainer::derivadaMish
    );

    public static final FuncionDeActivacion<Float> SOFTPLUS = new FuncionDeActivacion<>(
            "SOFTPLUS",
            FuncionDeActivacionContainer::softplus,
            FuncionDeActivacionContainer::derivadaSoftplus
    );

//    public static final FuncionDeActivacion<Float> SOFTMAX = new FuncionDeActivacion<>(
//            FuncionDeActivacionContainer::softmax,
//            FuncionDeActivacionContainer::derivadaSoftmax
//    );
//
//    public static final FuncionDeActivacion<Float> ELU = new FuncionDeActivacion<>(
//            FuncionDeActivacionContainer::elu,
//            FuncionDeActivacionContainer::derivadaELU
//    );

    // -- FUNCIONES--

    // SIGMOIDE
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

    // TANGENTE HIPERBÓLICA
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

    // RELU
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

    // ESCALON
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

    // LEAKY RELU
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

    // SWISH
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

    // MISH
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

    // SOFTPLUS
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

    // SOFTMAX
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

    // ELU
    public static Float elu(Float x, Float alpha) {
        return (x >= 0) ? x : (float) (alpha * (Math.exp(x) - 1)); // Función ELU (Exponential Linear Unit)
    }

    public static Double elu(Double x, Double alpha) {
        return (x >= 0) ? x : (alpha * (Math.exp(x) - 1)); // Función ELU (Exponential Linear Unit)
    }

    public static Float derivadaELU(Float x, Float alpha) {
        return (x >= 0) ? (float) 1.0 : elu(x, alpha) + alpha; // Derivada de la función ELU
    }

    public static Double derivadaELU(Double x, Double alpha) {
        return (x >= 0) ? 1.0 : elu(x, alpha) + alpha; // Derivada de la función ELU
    }

    // Función lineal
    public static Double funcionLineal(Double m, Double n, Double x) {
        return -(m * x) - (n);
    }

    public static Float funcionLineal(float m, float n, float x) {
        return -(m * x) - (n);
    }

    public FuncionDeActivacion<Float> getFuncionDeActivacion(int seleccion) {
        return funciones.get(seleccion);
    }
}
