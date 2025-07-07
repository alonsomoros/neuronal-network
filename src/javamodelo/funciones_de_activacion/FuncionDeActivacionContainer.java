package javamodelo.funciones_de_activacion;

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
        funciones.put(9, SOFTMAX);
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

    public static final FuncionDeActivacion<Float> SOFTMAX = new FuncionDeActivacion<>(
            "SOFTMAX",
            FuncionDeActivacionContainer::softmax,
            null
    );

//    public static final FuncionDeActivacion<Float> ELU = new FuncionDeActivacion<>(
//            "ELU",
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
    public static Double[] sigmoide(Double[] salida){
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = 1 / (1 + Math.exp(-salida[i]));
        }
        return salidaNueva;
    }
    public static Float[] sigmoide(Float[] salida){
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (float) (1 / (1 + Math.exp(-salida[i])));
        }
        return salidaNueva;
    }
    public static Double[] derivadaSigmoide(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = salida[i] * (1 - salida[i]);
        }
        return salidaNueva;
    }
    public static Float[] derivadaSigmoide(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = salida[i] * (1 - salida[i]);
        }
        return salidaNueva;
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
    public static Double[] tangenteHiperbolicaDerivada(Double[] salida){
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (1 - Math.pow(Math.tanh(salida[i]), 2)); // Derivada de la función tangente hiperbólica
        }
        return salidaNueva;
    }
    public static Float[] tangenteHiperbolica(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (float) Math.tanh(salida[i]);
        }
        return salidaNueva;
    }
    public static Double[] tangenteHiperbolica(Double[] salida){
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = Math.tanh(salida[i]);
        }
        return salidaNueva;
    }
    public static Float[] tangenteHiperbolicaDerivada(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (float) (1 - Math.pow(Math.tanh(salida[i]), 2)); // Derivada de la función tangente hiperbólica
        }
        return salidaNueva;
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
    public static Double[] relu(Double[] salida){
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = Math.max(0, salida[i]);
        }
        return salidaNueva;
    }
    public static Float[] relu(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = Math.max(0, salida[i]);
        }
        return salidaNueva;
    }
    public static Double[] derivadaReLU(Double[] salida){
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (double) (salida[i] > 0 ? 1 : 0); // Derivada de la función tangente hiperbólica
        }
        return salidaNueva;
    }
    public static Float[] derivadaReLU(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (float) (salida[i] > 0 ? 1 : 0); // Derivada de la función tangente hiperbólica
        }
        return salidaNueva;
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
    public static Double[] escalon(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (salida[i] >= 0) ? 1.0 : 0.0;;
        }
        return salidaNueva;
    }
    public static Float[] escalon(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (float) ((salida[i] >= 0) ? 1.0 : 0.0);;
        }
        return salidaNueva;
    }
    public static Double[] derivadaEscalon(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = 0.0d;
        }
        return salidaNueva; // La derivada de la función escalón no está definida en 0, pero se puede considerar 0 en otros puntos
    }
    public static Float[] derivadaEscalon(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = 0.0f;
        }
        return salidaNueva; // La derivada de la función escalón no está definida en 0, pero se puede considerar 0 en otros puntos
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
    public static Double[] leakyReLU(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (salida[i] > 0) ? salida[i] : 0.01 * salida[i];
        }
        return salidaNueva;
    }
    public static Float[] leakyReLU(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (salida[i] > 0) ? salida[i] : (float) (0.01 * salida[i]);
        }
        return salidaNueva;
    }
    public static Double[] derivadaLeakyReLU(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (salida[i] > 0) ? 1.0 : 0.01;
        }
        return salidaNueva;
    }
    public static Float[] derivadaLeakyReLU(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (float) ((salida[i] > 0) ? 1.0 : 0.01);
        }
        return salidaNueva;
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
    public static Double[] swish(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = salida[i] / (1 + Math.exp(-salida[i]));
        }
        return salidaNueva;
    }
    public static Float[] swish(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (float) (salida[i] / (1 + Math.exp(-salida[i])));
        }
        return salidaNueva;
    }
    public static Double[] derivadaSwish(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            double sigmoid = 1 / (1 + Math.exp(-salida[i]));
            salidaNueva[i] = sigmoid + salida[i] * sigmoid * (1 - sigmoid);
        }
        return salidaNueva;
    }
    public static Float[] derivadaSwish(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            double sigmoid = 1 / (1 + Math.exp(-salida[i]));
            salidaNueva[i] = (float) (sigmoid + salida[i] * sigmoid * (1 - sigmoid));
        }
        return salidaNueva;
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
    public static Double[] mish(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = salida[i] * Math.tanh(Math.log1p(Math.exp(salida[i])));
        }
        return salidaNueva;
    }
    public static Float[] mish(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (float) (salida[i] * Math.tanh(Math.log1p(Math.exp(salida[i]))));
        }
        return salidaNueva;
    }
    public static Double[] derivadaMish(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            double sigmoid = 1 / (1 + Math.exp(-salida[i]));
            salidaNueva[i] = sigmoid + salida[i] * (1 - sigmoid * sigmoid);
        }
        return salidaNueva;
    }
    public static Float[] derivadaMish(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            float sigmoid = (float) (1 / (1 + Math.exp(-salida[i])));
            salidaNueva[i] = sigmoid + salida[i] * (1 - sigmoid * sigmoid);
        }
        return salidaNueva;
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
    public static Double[] softplus(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = Math.log1p(Math.exp(salida[i]));
        }
        return salidaNueva;
    }
    public static Float[] softplus(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (float) Math.log1p(Math.exp(salida[i]));
        }
        return salidaNueva;
    }
    public static Double[] derivadaSoftplus(Double[] salida) {
        Double[] salidaNueva = new Double[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = 1 / (1 + Math.exp(-salida[i]));
        }
        return salidaNueva;
    }
    public static Float[] derivadaSoftplus(Float[] salida) {
        Float[] salidaNueva = new Float[salida.length];
        for (int i = 0; i < salida.length; i++) {
            salidaNueva[i] = (float) (1 / (1 + Math.exp(-salida[i])));
        }
        return salidaNueva;
    }

    // SOFTMAX
    private static Double[] softmax(Double[] z) {
        // estabilidad numérica: restamos max
        Double max = Double.NEGATIVE_INFINITY;
        for (Double v : z) if (v > max) max = v;

        double sum = 0;
        Double[] exps = new Double[z.length];
        for (int i = 0; i < z.length; i++) {
            exps[i] = Math.exp(z[i] - max);
            sum += exps[i];
        }
        for (int i = 0; i < exps.length; i++) {
            exps[i] /= sum;
        }
        return exps;
    }
    private static Float[] softmax(Float[] z) {
        // estabilidad numérica: restamos max
        Float max = Float.NEGATIVE_INFINITY;
        for (Float v : z) if (v > max) max = v;

        float sum = 0;
        Float[] exps = new Float[z.length];
        for (int i = 0; i < z.length; i++) {
            exps[i] = (float)Math.exp(z[i] - max);
            sum += exps[i];
        }
        for (int i = 0; i < exps.length; i++) {
            exps[i] /= sum;
        }
        return exps;
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
