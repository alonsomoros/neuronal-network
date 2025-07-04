package javamodelo;

import javamodelo.utils.Dibujador;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class RedNeuronal {

    private static int contadorID = 0;
    public int ID = ++contadorID;
    private Dibujador dibujador;
    private final int epochs;
    private final int filtro_epochs; // Filtro para mostrar el error cada 10 epochs
    private int currentEpoch = 0;
    private boolean isTraining = false;
    private final int batchSize;
    private Float[][][] datos_entrenamiento;
    private Float[][][] datos_test;
    private final int nodos_entradas; // Número de nodos en la capa de entrada
    private final int nodos_ocultos; // Número de nodos en la capa oculta
    private final int nodos_salidas; // Número de nodos en la capa de salida
    private Matrix pesos_entradas_a_ocultos; // Matriz de pesos entre la capa de entrada y la capa oculta
    private Matrix pesos_ocultos_a_salidas; // Matriz de pesos entre la capa oculta y la capa de salida
    private Matrix bias_ocultos; // Bias para la capa oculta
    private Matrix bias_salidas; // Bias para la capa de salida
    private double tasa_aprendizaje; // Tasa de aprendizaje para el entrenamiento
    private final FuncionDeActivacion<Float> funcionDeActivacionOcultas;
    private final FuncionDeActivacion<Float> funcionDeActivacionSalidas;
    private ArrayList<Float> errores_epochs = new ArrayList<>();

    public RedNeuronal(int epochs, int batchSize, int numEntradas, int numOcultos, FuncionDeActivacion<Float> funcionDeActivacionOcultas, int numSalidas, FuncionDeActivacion<Float> funcionDeActivacionSalidas) {
        this.dibujador = new Dibujador(this);

        this.epochs = epochs;
        this.filtro_epochs = epochs / 10;
        this.batchSize = batchSize;

        this.nodos_entradas = numEntradas;
        this.nodos_ocultos = numOcultos;
        this.nodos_salidas = numSalidas;

        this.pesos_entradas_a_ocultos = new Matrix(this.nodos_ocultos, this.nodos_entradas); // Filas: Capa Oculta - Columnas: Capa Entrada
        this.pesos_entradas_a_ocultos.randomizar();
        this.pesos_ocultos_a_salidas = new Matrix(this.nodos_salidas, this.nodos_ocultos); // Filas: Capa Salida - Columnas: Capa Oculta
        this.pesos_ocultos_a_salidas.randomizar();

        this.bias_ocultos = new Matrix(numOcultos, 1); // Bias para la capa oculta
        this.bias_ocultos.randomizar();
        this.bias_salidas = new Matrix(numSalidas, 1); // Bias para la capa de salida
        this.bias_salidas.randomizar();
        this.tasa_aprendizaje = 0.1; // Tasa de aprendizaje por defecto

        this.funcionDeActivacionOcultas = funcionDeActivacionOcultas; // Función de activación de ocultas
        this.funcionDeActivacionSalidas = funcionDeActivacionSalidas; // Función de activación de salidas
    }

    public RedNeuronal(int epochs, int batchSize, int numEntradas, int numOcultos, int numSalidas) {
        this.dibujador = new Dibujador(this);

        this.epochs = epochs;
        this.batchSize = batchSize;
        this.filtro_epochs = epochs / 10;

        this.nodos_entradas = numEntradas;
        this.nodos_ocultos = numOcultos;
        this.nodos_salidas = numSalidas;

        this.pesos_entradas_a_ocultos = new Matrix(this.nodos_ocultos, this.nodos_entradas); // Filas: Capa Oculta - Columnas: Capa Entrada
        this.pesos_entradas_a_ocultos.randomizar();
        this.pesos_ocultos_a_salidas = new Matrix(this.nodos_salidas, this.nodos_ocultos); // Filas: Capa Salida - Columnas: Capa Oculta
        this.pesos_ocultos_a_salidas.randomizar();

        this.bias_ocultos = new Matrix(numOcultos, 1); // Bias para la capa oculta
        this.bias_ocultos.randomizar();
        this.bias_salidas = new Matrix(numSalidas, 1); // Bias para la capa de salida
        this.bias_salidas.randomizar();
        this.tasa_aprendizaje = 0.1; // Tasa de aprendizaje por defecto

        this.funcionDeActivacionOcultas = FuncionDeActivacionContainer.SIGMOIDE; // Función de activación de ocultas
        this.funcionDeActivacionSalidas = FuncionDeActivacionContainer.SIGMOIDE; // Función de activación de salidas
    }

    public Float[] predict(Float[] entrada_array) {
        // Convertir el array de entrada a una matriz
        Matrix entradas = Matrix.fromFloatArray(entrada_array);

        // Calcular la capa oculta
        Matrix salidaOculto = Matrix.multiplicarMatrices(this.pesos_entradas_a_ocultos, entradas); // Se pone al revés para que porEj 2x1 (inputs) * 4x2 (pesos) sea 4x2·2x1 -> dando una 4x1
        salidaOculto.sumar(this.bias_ocultos);
        salidaOculto.mapFloat(this.funcionDeActivacionOcultas.getFunction()); // Aplica la función de activación a la matriz

        // Calcular la salida
        Matrix salidaFinal = Matrix.multiplicarMatrices(this.pesos_ocultos_a_salidas, salidaOculto);
        salidaFinal.sumar(this.bias_salidas);

        salidaFinal.mapFloat(this.funcionDeActivacionSalidas.getFunction()); // Aplica la función de activación a la matriz

        // Convertir la salida a un array
        return salidaFinal.toArrayUnidimensional();
    }

    public Float[] entrenar(Float[] entradasArray, Float[] objetivosArray) {
        /* Para la prueba:
        let redNeuronal = new RedNeuronal(2, 2, 2);
        let entradas = [1, 0];
        let objetivos = [1, 0];

        Entradas: [2x1]
        Objetivos: [2x1]
        Salidas: [2x1]

        Pesos Entradas a Ocultos: [2x2]
        Pesos Ocultos a Salidas: [2x2]
        Bias Ocultos: [2x1]
        Bias Salidas: [2x1]

        Por lo tanto:
        Salidas Ocultos: [2x1]
        Salidas: [2x1]
        Error Salidas: [2x1]
        Error Ocultos: [2x1]

        Gradiente Salidas: [2x1]
        Gradiente Ocultos: [2x1]

        Pesos Ocultos a Salidas Delta: [2x2]
        Pesos Entradas a Ocultos Delta: [2x2]
        Bias Ocultos Delta: [2x1]
        Bias Salidas Delta: [2x1]
        */

        // ------ INICIO DE FEED FORWARD ------

        // Convertir el array de entradas y objetivos a una matriz
        Matrix entradas = Matrix.fromFloatArray(entradasArray);
        Matrix objetivos = Matrix.fromFloatArray(objetivosArray);

        // Calcular la capa oculta
        Matrix salidasOculto = Matrix.multiplicarMatrices(this.pesos_entradas_a_ocultos, entradas); // Se pone al revés para que porEj 2x1 (inputs) * 4x2 (pesos) sea 4x2·2x1 -> dando una 4x1
        salidasOculto.sumar(this.bias_ocultos);
        salidasOculto.mapFloat(this.funcionDeActivacionOcultas.getFunction()); // Aplica la función de activación

        // Calcular la salida
        Matrix salidas = Matrix.multiplicarMatrices(this.pesos_ocultos_a_salidas, salidasOculto); // [2x1]
        salidas.sumar(this.bias_salidas);
        salidas.mapFloat(this.funcionDeActivacionSalidas.getFunction()); // Aplica la función de activación

        // ------ FIN DE FEED FORWARD ------

        // ------ INICIO DE BACKPROPAGATION ------

        //  <[ Hidden <- Output ]> = ΔW,ho = η (T - O) σ′(zO) Hᵀ

        // 1º Calcular el error de las salidas
        // ERROR = OBJETIVO - SALIDA
        Matrix error_salidas = Matrix.restarMatrices(objetivos, salidas); // [(T - O)] - [2x1]

        // 2º Gradiente
        Matrix gradiente_salidas = Matrix.mapFloat(salidas, this.funcionDeActivacionSalidas.getDerivative()); // [σ′(zOutput)] - [2x1]
        gradiente_salidas.multiplicarMatrizElementwise(error_salidas); // [(T - O)] σ′(zOutput) - [2x1] (Element-wise)
        // 3º Multiplicar por la tasa de aprendizaje
        gradiente_salidas.multiplicar(this.tasa_aprendizaje); // [η] (T - O) σ′(zO)

        // 4º Calcular la traspuesta de las salidas ocultas
        Matrix salidasOculto_Traspuesta = Matrix.transponerMatriz(salidasOculto); // [Hᵀ] - [1x2]

        // 5º Calcular el delta de los pesos de la capa oculta a la capa de salida
        // [ΔW, ho] = η (T - O) σ′(zO) Hᵀ
        Matrix pesos_ocultos_a_salidas_delta = Matrix.multiplicarMatrices(gradiente_salidas, salidasOculto_Traspuesta); // Debería ser: δ outputs(#Salidas x 1) x Hᵀ(1 x #Ocultos) = (#Salidas x #Ocultos)

        // 6º Actualizar los pesos de la capa oculta a la capa de salida
        this.pesos_ocultos_a_salidas.sumar(pesos_ocultos_a_salidas_delta);
        this.bias_salidas.sumar(gradiente_salidas);


        //  <[ Input <- Hidden ]> = ΔW,ih = η (W,ho)ᵀ (T - O) σ′(zO) * σ′(zH) Iᵀ

        // 1º Calcular la traspuesta de los pesos de la capa oculta → salidas
        Matrix pesos_ocultos_a_salidas_transpuestos = Matrix.transponerMatriz(this.pesos_ocultos_a_salidas); // [W,ho]ᵀ - [2x2]

        // 2º Calcular el error de las salidas de la capa oculta
        // ERROR = (W, ho)ᵀ (T - O) σ′(zO)
        Matrix error_ocultos = Matrix.multiplicarMatrices(pesos_ocultos_a_salidas_transpuestos, error_salidas); // [(W, ho)ᵀ (T - O) σ′(zO)] - [2x1]

        // 3º Gradiente de la capa oculta
        Matrix gradiente_ocultos = Matrix.mapFloat(salidasOculto, this.funcionDeActivacionOcultas.getDerivative()); // [σ′(zH)] - [2x1]
        gradiente_ocultos.multiplicarMatrizElementwise(error_ocultos); // [(W, ho)ᵀ (T - O) σ′(zO)] σ′(zH) // - [2x1] (Element-wise)
        // 4º Multiplicar por la tasa de aprendizaje
        gradiente_ocultos.multiplicar(this.tasa_aprendizaje); // [η] (W, ho)ᵀ (T - O) σ′(zO) σ′(zH)

        // 5º Calcular la traspuesta de las entradas
        Matrix entradas_Traspuesta = Matrix.transponerMatriz(entradas); // [Iᵀ] - [1x2]

        // 6º Calcular el delta de los pesos de la capa de entradas a la capa oculta
        // [ΔW, ih] = η (W, ho)ᵀ (T - O) σ′(zO) * σ′(zH) Iᵀ
        Matrix pesos_entradas_a_ocultos_delta = Matrix.multiplicarMatrices(gradiente_ocultos, entradas_Traspuesta); // Debería ser: δ ocultos(#Ocultos x 1) x Iᵀ(1 x #Entradas) = (#Ocultos x #Entradas)

        // 7º Actualizar los pesos de la capa de entrada a la capa oculta
        this.pesos_entradas_a_ocultos.sumar(pesos_entradas_a_ocultos_delta);
        this.bias_ocultos.sumar(gradiente_ocultos);

        // ------ FIN DE BACKPROPAGATION ------

        // ---------- CALCULO DEL MSE ----------

        Matrix mseMatriz = Matrix.multiplicarMatrices(error_salidas, error_salidas); // MSE = (T - O)² - [2x1]
        float mse = Matrix.sumaElementosMatriz(mseMatriz); // MSE = Σ(T - O)²
        mse /= objetivos.toArray().length; // MSE = Σ(T - O)² / N

        float correctos = 0f;
        for (int i = 0; i < salidas.toArray().length; i++) {
            float clasificacionBinaria = 0;

            // salidas = Matrix[2x1]
            if (salidas.toArray()[i][0] >= 0.5) {
                clasificacionBinaria = 1; // Clase positiva
            } else {
                clasificacionBinaria = 0; // Clase negativa
            }
            if (clasificacionBinaria == objetivos.toArray()[i][0]) correctos++;
        }

        return new Float[]{mse, correctos};
    }

    private void entrenarEpoch(Float[][][] datos_entrenamiento) {
        ArrayList<Float[][]> batch = new ArrayList<>();
        for (int j = 0; j < this.getBatchSize(); j++) { // Tamaño del batch
            batch.add(datos_entrenamiento[new Random().nextInt(datos_entrenamiento.length)]);
        }

        float coste = 0f;
        float sumCorrectos = 0f;
        for (Float[][] data : batch) {
            Float[] entradas = data[0]; // [e1, e2]
            Float[] objetivos = data[1]; // [obj1]
            Float[] resultadoEntrenamiento = this.entrenar(entradas, objetivos); // MSE y Correctos

            sumCorrectos += resultadoEntrenamiento[1];
            coste += resultadoEntrenamiento[0];
        }
        coste = (coste / this.getBatchSize());
        errores_epochs.add(coste);

        if (this.getCurrentEpoch() % (filtro_epochs / 10) == 0) {
//            System.out.println("<< Error en el epoch " + this.getCurrentEpoch() + " de " + ID + " que es: " + errores_epochs.get(this.getCurrentEpoch()) + " >>"); // Cuidado porque si no es la anterior sale null, ya que todavía no ha hecho la siguiente

            if (this.getCurrentEpoch() % filtro_epochs == 0) {
                System.out.printf("Epoch: %d, Coste: %.8f\nAccuracy: %.2f\nCorrectos: %.0f de %d\n", this.getCurrentEpoch(), coste, (sumCorrectos / this.getBatchSize()), sumCorrectos, this.getBatchSize());
            }
        }
    }

    public void actualizar(PApplet pApplet) {
        pApplet.fill(255);

        isTraining = true;
        if (isTraining && currentEpoch < this.getEpochs()) {
            entrenarEpoch(getDatosEntrenamiento());
            currentEpoch++;
        }
        if (currentEpoch >= this.getEpochs()) {
            isTraining = false;
        }
    }

    public void dibujarCuadriculaXOR(PApplet pApplet, Rectangle area_cuadricula) {
        float margin = 0;
        dibujador.dibujarCuadricula(pApplet, area_cuadricula, margin);
    }

    public void dibujarGraficaProgresoErrorEpoch(PApplet pApplet, Rectangle areaGrafica, int margin, ArrayList<Float> errores_epoch) {
        dibujador.dibujarValoresGrafica(pApplet, areaGrafica, margin, this.getEpochs(), errores_epoch);
    }

    public void dibujarGraficaEstructuraErrorEpoch(PApplet pApplet, Rectangle areaGrafica, int margin) {
        dibujador.dibujarEstructuraGrafica(pApplet, areaGrafica, margin, this.getEpochs());
    }

    public void setTasa_aprendizaje(double tasa_aprendizaje) {
        this.tasa_aprendizaje = tasa_aprendizaje;
    }

    public double getTasa_aprendizaje() {
        return tasa_aprendizaje;
    }

    public FuncionDeActivacion<Float> getFuncionDeActivacionOcultas() {
        return funcionDeActivacionOcultas;
    }

    public FuncionDeActivacion<Float> getFuncionDeActivacionSalidas() {
        return funcionDeActivacionSalidas;
    }

    public int getEpochs() {
        return epochs;
    }

    public int getCurrentEpoch() {
        return currentEpoch;
    }

    public boolean isTraining() {
        return isTraining;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setDatosEntrenamiento(Float[][][] datos_entrenamiento) {
        this.datos_entrenamiento = datos_entrenamiento;
    }

    public Float[][][] getDatosEntrenamiento() {
        return datos_entrenamiento;
    }

    public void setDatosTest(Float[][][] datos_test) {
        this.datos_test = datos_test;
    }

    public Float[][][] getDatosTest() {
        return datos_test;
    }

    public ArrayList<Float> getErroresEpochs() {
        return this.errores_epochs;
    }

    public Dibujador getDibujador() {
        return dibujador;
    }
}
