package javamodelo.pruebas.xor;

import javamodelo.funciones_de_activacion.FuncionDeActivacion;
import javamodelo.funciones_de_activacion.FuncionDeActivacionContainer;
import javamodelo.pruebas.Prueba;
import processing.core.PApplet;

import java.awt.*;
import java.util.Scanner;

public class Prueba_XOR extends Prueba {
    public Prueba_XOR(int porcentajeEntrenamiento) {
        super(porcentajeEntrenamiento);
//        setUpRedNeuronalXOR_entradas();
    }

    public void draw(PApplet pApplet) {
        if (getRedesNeuronales().isEmpty()) return;

        // Cálculos para dimensiones de las gráficas
        int columnasDeGraficas = (int) Math.round(Math.sqrt(this.redesNeuronales.size() + 1));                      // Porciones de columnas
        int filasDeGraficas = (int) Math.ceil((double) (this.redesNeuronales.size() + 1) / columnasDeGraficas);     // Porciones de filas
        int anchoCuadrante = pApplet.width / columnasDeGraficas;                                                     // Ancho de cada cuadrante
        int altoCuadrante = pApplet.height / filasDeGraficas;                                                        // Alto de cada cuadrante

        // Dimensiones del área de la gráfica
        Rectangle dimensionCuadrante = new Rectangle(filasDeGraficas, columnasDeGraficas, anchoCuadrante, altoCuadrante); // x = filas , y = columnas
        // Area para cuadricula
        Rectangle coords_grafica_error_epoch = new Rectangle(0, 0, anchoCuadrante, altoCuadrante);

        dibujarEstructuraGrafica(pApplet, coords_grafica_error_epoch);

        for (RedNeuronal redNeuronal : redesNeuronales) {
            actualizarYDibujarRed(pApplet, redNeuronal, dimensionCuadrante, coords_grafica_error_epoch);
        }
    }

    private void actualizarYDibujarRed(PApplet pApplet, RedNeuronal redNeuronal, Rectangle dimensionCuadrante, Rectangle coordenadasGrafica) {

        int x_init = (redNeuronal.ID % dimensionCuadrante.y) * dimensionCuadrante.width;
        int y_init = (int) ((Math.floor((double) redNeuronal.ID / dimensionCuadrante.y)) * dimensionCuadrante.height);
        Rectangle area_cuadrante = new Rectangle(x_init, y_init, dimensionCuadrante.width, dimensionCuadrante.height);

        redNeuronal.actualizar(pApplet);

        redNeuronal.dibujarCuadricula(pApplet, area_cuadrante);
        redNeuronal.dibujarValoresGrafica(pApplet, coordenadasGrafica, redNeuronal.getEpochs(), redNeuronal.getErroresEpochs());
    }

    private void dibujarEstructuraGrafica(PApplet pApplet, Rectangle areaGrafica) {
        final int MARGEN_GRAFICA = 50;
        RedNeuronal redNeuronal = redesNeuronales.get(0);
        redNeuronal.dibujarEstructuraGrafica(pApplet, areaGrafica, MARGEN_GRAFICA, redNeuronal.getEpochs());
    }

    private void setUpRedNeuronalXOR_entradas() {
        int numEntradas = 2;
        int numSalidas = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el número de epochs: [1, 10, 100, 1000, etc.)");
        int epochs = scanner.nextInt();
        System.out.println("Ingrese el número de neuronas ocultas: [2, 4, 6, etc.)");
        int numOcultas = scanner.nextInt();
        System.out.println("Ingrese el tamaño del batch: [1, 10, 50, 100, etc.)");
        int batchSize = scanner.nextInt();
        System.out.println("Ingrese la tasa de aprendizaje: [0 - 1]. Ej.:  0,1");
        float tasaAprendizaje = scanner.nextFloat();
        System.out.println("Cuantas redes neuronales desea crear? [1 - 8]");
        int numRedes = scanner.nextInt();

        for (int i = 0; i < numRedes; i++) {
            System.out.println("¿Que tipo de función quiere usar como capa oculta?:\n1. ReLU\n2. Sigmoide\n3. Tanh (Tangente Hiperbólica)\n4. Escalón\n5. LeakyReLU\n6. Swish\n7. Softplus\n8. Mish\n(Conteste con el Número)");
            int seleccion = scanner.nextInt();
            FuncionDeActivacion<Float> funcionOculta = FuncionDeActivacionContainer.getInstance().getFuncionDeActivacion(seleccion);
            RedNeuronal redNeuronal = new RedNeuronal(epochs, batchSize, numEntradas, numOcultas, funcionOculta, numSalidas, FuncionDeActivacionContainer.SIGMOIDE);

            redNeuronal.setTasa_aprendizaje(tasaAprendizaje);

            redNeuronal.setDatosTest(DATASET);
            redNeuronal.setDatosEntrenamiento(DATASET);

            redesNeuronales.add(redNeuronal);
        }
    }


}
