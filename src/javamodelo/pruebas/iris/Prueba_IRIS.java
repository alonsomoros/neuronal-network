package javamodelo.pruebas.iris;

import javamodelo.pruebas.Data;
import javamodelo.pruebas.Prueba;
import javamodelo.pruebas.xor.RedNeuronal;
import javamodelo.utils.Helper;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

public class Prueba_IRIS extends Prueba {

    public Prueba_IRIS(int porcentajeEntrenamiento) {
        super(porcentajeEntrenamiento);
    }

    public void draw(PApplet pApplet) {
        if (getRedesNeuronales().isEmpty()) return;

        // Cálculos para dimensiones de las gráficas
        int columnasDeGraficas = (int) Math.round(Math.sqrt(this.redesNeuronales.size() + 1));                      // Porciones de columnas
        int filasDeGraficas = (int) Math.ceil((double) (this.redesNeuronales.size() + 1) / columnasDeGraficas);     // Porciones de filas
        int anchoCuadrante = pApplet.width / columnasDeGraficas;                                                    // Ancho de cada cuadrante
        int altoCuadrante = pApplet.height / filasDeGraficas;

        Rectangle coords_grafica_error_epoch = new Rectangle(0, 0, anchoCuadrante, altoCuadrante);

        for (RedNeuronal redNeuronal : redesNeuronales) {
            actualizarYDibujarRed(pApplet, redNeuronal, coords_grafica_error_epoch);
        }
    }

    private void actualizarYDibujarRed(PApplet pApplet, RedNeuronal redNeuronal, Rectangle coordenadasGrafica) {
        redNeuronal.actualizar(pApplet);
        redNeuronal.dibujarValoresGrafica(pApplet, coordenadasGrafica, redNeuronal.getEpochs(), redNeuronal.getErroresEpochs());
    }
}
