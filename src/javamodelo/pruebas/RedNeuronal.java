package javamodelo.pruebas;

import javamodelo.funciones_de_activacion.FuncionDeActivacion;
import javamodelo.funciones_de_activacion.FuncionDeActivacionContainer;
import javamodelo.pruebas.Data;
import javamodelo.pruebas.iris.IrisData;
import javamodelo.pruebas.iris.Prueba_IRIS;
import javamodelo.pruebas.xor.Prueba_XOR;
import javamodelo.utils.Dibujador;
import javamodelo.utils.Matrix;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RedNeuronal {

    private Prueba pruebaAsignada;
    private static int contadorID = 0;
    public int ID = ++contadorID;
    private Dibujador dibujador;
    private final int epochs;
    private final int filtro_epochs; // Filtro para mostrar el error cada 10 epochs
    private int currentEpoch = 0;
    private boolean isTraining = false;
    private final int batchSize;
    private ArrayList<Data> datos_entrenamiento;
    private ArrayList<Data> datos_test;
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
    public boolean usaSoftmax;

    public RedNeuronal(Prueba pruebaAsignada, int epochs, int batchSize, int numEntradas, int numOcultos, FuncionDeActivacion<Float> funcionDeActivacionOcultas, int numSalidas, FuncionDeActivacion<Float> funcionDeActivacionSalidas) {
        this.pruebaAsignada = pruebaAsignada;
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

        String funcion = funcionDeActivacionSalidas.getNombre();
        usaSoftmax = funcion.equals("SOFTMAX");
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

        String funcion = funcionDeActivacionSalidas.getFunction().toString();
        usaSoftmax = funcion.equals("SOFTMAX");
    }

    public void draw() {

    }

    public Float[] predict(Float[] entrada_array) {
        // Convertir el array de entrada a una matriz
        Matrix entradas = Matrix.fromFloatArray(entrada_array);

        // Calcular la capa oculta
        Matrix salidaOculto = Matrix.multiplicarMatrices(this.pesos_entradas_a_ocultos, entradas); // Se pone al revés para que porEj 2x1 (inputs) * 4x2 (pesos) sea 4x2·2x1 -> dando una 4x1
        salidaOculto.sumar(this.bias_ocultos);
        salidaOculto.mapArrayFunction(this.funcionDeActivacionOcultas.getFunction()); // Aplica la función de activación a la matriz

        // Calcular la salida
        Matrix salidaFinal = Matrix.multiplicarMatrices(this.pesos_ocultos_a_salidas, salidaOculto);
        salidaFinal.sumar(this.bias_salidas);

        salidaFinal.mapArrayFunction(this.funcionDeActivacionSalidas.getFunction()); // Aplica la función de activación a la matriz

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
        salidasOculto.mapArrayFunction(this.funcionDeActivacionOcultas.getFunction());

        // Calcular la salida
        Matrix salidas = Matrix.multiplicarMatrices(this.pesos_ocultos_a_salidas, salidasOculto); // [2x1]
        salidas.sumar(this.bias_salidas);
        salidas.mapArrayFunction(this.funcionDeActivacionSalidas.getFunction()); // Aplica la función de activación

        // ------ FIN DE FEED FORWARD ------

        // ------ INICIO DE BACKPROPAGATION ------

        //  <[ Hidden <- Output ]> = ΔW,ho = η (T - O) σ′(zO) Hᵀ

        // 1º Calcular el error de las salidas
        // ERROR = OBJETIVO - SALIDA
        Matrix error_salidas = Matrix.restarMatrices(objetivos, salidas); // [(T - O)] - [2x1]

        // 2º Gradiente
        Matrix gradiente_salidas; // δO
        if (usaSoftmax) {
            // Softmax + Cross‑Entropy: δO = output - target
            gradiente_salidas = Matrix.restarMatrices(salidas, objetivos);
        } else {
            gradiente_salidas = Matrix.mapArrayFunction(salidas, this.funcionDeActivacionSalidas.getDerivative()); // δO = [σ′(zOutput)] - [2x1]

            gradiente_salidas.multiplicarMatrizElementwise(error_salidas); // δO = [(T - O)] σ′(zOutput) - [2x1] (Element-wise)
            // 3º Multiplicar por la tasa de aprendizaje
            gradiente_salidas.multiplicar(this.tasa_aprendizaje); // δO = [η] (T - O) σ′(zO)
        }
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
        Matrix error_ocultos = Matrix.multiplicarMatrices(pesos_ocultos_a_salidas_transpuestos, gradiente_salidas); // [(W, ho)ᵀ δO] - [2x1]

        // 3º Gradiente de la capa oculta
        Matrix gradiente_ocultos = Matrix.mapArrayFunction(salidasOculto, this.funcionDeActivacionOcultas.getDerivative()); // [σ′(zH)] - [2x1]
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

        Matrix mseMatriz = Matrix.multiplicarElementwise(error_salidas, error_salidas); // MSE = (T - O)² - [2x1]
        float mse = Matrix.sumaElementosMatriz(mseMatriz); // MSE = Σ(T - O)²
        mse /= objetivos.toArray().length; // MSE = Σ(T - O)² / N

        float correcto = calcularCorrectos(salidas, objetivos);

        return new Float[]{mse, correcto};
    }

    private Float calcularCorrectos(Matrix salidas, Matrix objetivos) {
        Float correcto = 0f;

        if (this.pruebaAsignada instanceof Prueba_XOR) {
            correcto = calcularCorrectos_XOR(salidas, objetivos);
        } else if (this.pruebaAsignada instanceof Prueba_IRIS) {
            correcto = calcularCorrectos_IRIS(salidas, objetivos);
        }

        return correcto;
    }

    private float calcularCorrectos_IRIS(Matrix salidas, Matrix objetivos) {
        double[][] salidasArray = salidas.toArray();      // tamaño [3][1]
        double[][] objetivosArray = objetivos.toArray();    // tamaño [3][1]

        // 1) Encontrar índice del máximo en salida
        int idxMaxSalidas = 0;
        double maxVal = salidasArray[0][0];
        for (int i = 1; i < salidasArray.length; i++) {
            if (salidasArray[i][0] > maxVal) {
                maxVal = salidasArray[i][0];
                idxMaxSalidas = i;
            }
        }

        // 2) Encontrar índice del 1 en el target one-hot
        int idxMaxObjetivos = 0;
        for (int i = 0; i < objetivosArray.length; i++) {
            if (objetivosArray[i][0] == 1.0) {
                idxMaxObjetivos = i;
                break;
            }
        }

        // 3) Comparar índices
        return (idxMaxSalidas == idxMaxObjetivos) ? 1f : 0f;
    }

    private Float calcularCorrectos_XOR(Matrix salidas, Matrix objetivos) {
        Float correcto = 0f;

        for (int i = 0; i < salidas.toArray().length; i++) {
            float clasificacionBinaria = 0;

            // salidas = Matrix[2x1]
            if (salidas.toArray()[i][0] >= 0.5) {
                clasificacionBinaria = 1; // Clase positiva
            } else {
                clasificacionBinaria = 0; // Clase negativa
            }
            if (clasificacionBinaria == objetivos.toArray()[i][0]) correcto++;
        }

        return correcto;
    }

    private void entrenarEpoch(ArrayList<Data> datos_entrenamiento) {
        ArrayList<Data> batch = new ArrayList<>();
        for (int j = 0; j < this.getBatchSize(); j++) { // Tamaño del batch
            batch.add(datos_entrenamiento.get(new Random().nextInt(datos_entrenamiento.size())));
        }

        float coste = 0f;
        float sumCorrectos = 0f;
        for (Data data : batch) {
            Float[] entradas = data.getEntradas(); // [e1, e2]
            Float[] objetivos = data.getObjetivos(); // [obj1]
            Float[] resultadoEntrenamiento = this.entrenar(entradas, objetivos); // MSE y Correctos

            sumCorrectos += resultadoEntrenamiento[1];
            coste += resultadoEntrenamiento[0];
        }
        coste = (coste / this.getBatchSize());
        errores_epochs.add(coste);

        if (this.getCurrentEpoch() % (filtro_epochs / 10) == 0) {
            if (this.getCurrentEpoch() % filtro_epochs == 0) {
                System.out.printf("Red: %d - Epoch: %d, Coste: %.8f\nCorrectos: %.0f de %d (%.2f%%)\n", ID, this.getCurrentEpoch(), coste, sumCorrectos, this.getBatchSize(), (sumCorrectos / this.getBatchSize()));
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

    public void dibujarCuadricula(PApplet pApplet, Rectangle area_cuadrante) {
        dibujador.dibujarCuadricula(pApplet, area_cuadrante);
    }

    public void dibujarValoresGrafica(PApplet pApplet, Rectangle coordenadasGrafica, int epochs, ArrayList<Float> errores) {
        dibujador.dibujarValoresGrafica(pApplet, coordenadasGrafica, epochs, errores);
    }

    public void dibujarEstructuraGrafica(PApplet pApplet, Rectangle areaGrafica, int margen, int epochs) {
        dibujador.dibujarEstructuraGrafica(pApplet, areaGrafica, margen, epochs);
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

    public void setDatosEntrenamiento(ArrayList<Data> datos_entrenamiento) {
        this.datos_entrenamiento = datos_entrenamiento;
    }

    public ArrayList<Data> getDatosEntrenamiento() {
        return datos_entrenamiento;
    }

    public void setDatosTest(ArrayList<Data> datos_test) {
        this.datos_test = datos_test;
    }

    public ArrayList<Data> getDatosTest() {
        return datos_test;
    }

    public ArrayList<Float> getErroresEpochs() {
        return this.errores_epochs;
    }

    public Dibujador getDibujador() {
        return dibujador;
    }

    public void reset() {
        this.pesos_entradas_a_ocultos.randomizar();
        this.pesos_ocultos_a_salidas.randomizar();
        this.bias_ocultos.randomizar();
        this.bias_salidas.randomizar();
        this.tasa_aprendizaje = 0.1; // Resetear tasa de aprendizaje
        this.currentEpoch = 0;
        this.errores_epochs.clear();
        this.isTraining = false;
    }
}
