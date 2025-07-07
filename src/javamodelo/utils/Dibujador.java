package javamodelo.utils;

import javamodelo.pruebas.RedNeuronal;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

public class Dibujador {

    private final RedNeuronal redNeuronal;

    public final int MARGEN_GRAFICA = 50;
    public final int MARGEN_CUADRICULA = 5;

    public Dibujador(RedNeuronal redNeuronal) {
        this.redNeuronal = redNeuronal;
    }

    public void dibujarEstructuraGrafica(PApplet pApplet, Rectangle areaGrafica, float margin, int epochs) {

        // Área de la gráfica
        float x1 = areaGrafica.x + margin;
        float x2 = areaGrafica.x + areaGrafica.width - margin;
        float y1 = areaGrafica.y + margin; // = margin
        float y2 = areaGrafica.y + areaGrafica.height - margin;
        Point tamanoTextoEpoch = new Point(30, 20);
        Point tamanoTextoError = new Point(30, 20);

        pApplet.fill(0);
        pApplet.stroke(0);
        pApplet.strokeWeight(2); // Aumenta el grosor de la línea
        pApplet.line(x1, y2, x2, y2); // Horizontal
        pApplet.line(x1, y1, x1, y2); // Vertical

        if (areaGrafica.width < 300) {
            pApplet.textSize(8);
        } else pApplet.textSize(15);

        pApplet.text("Epochs", x1 - tamanoTextoEpoch.x, y2 + tamanoTextoEpoch.y);
        pApplet.text("Error (MSE)", x1 - tamanoTextoError.x, y1 - tamanoTextoError.y);

        for (int i = 1; i <= 10; i++) {
            pApplet.line(x1 + i * (x2 - x1) / 10, y2 - 2, x1 + i * (x2 - x1) / 10, y2 + 2);
            pApplet.text(String.valueOf(i * epochs / 10), x1 + i * (x2 - x1) / 10 - 5, y2 + 25);
        }

        for (int i = 1; i <= 10; i++) {
            pApplet.line(x1 - 2, y2 - i * (y2 - margin) / 10, x1 + 2, y2 - i * (y2 - margin) / 10);
            pApplet.text(String.format("%.1f", (float) i / 10), x1 - 35, y2 - i * (y2 - margin) / 10 + 5);
        }
    }

    public void dibujarValoresGrafica(PApplet pApplet, Rectangle areaGrafica, int epochs, ArrayList<Float> errores) { // Los vertices del área que ocupará la gráfica

        // Área de la gráfica
        float x1 = areaGrafica.x + MARGEN_GRAFICA;
        float x2 = areaGrafica.x + areaGrafica.width - MARGEN_GRAFICA;
        float y1 = areaGrafica.y + MARGEN_GRAFICA; // = margin
        float y2 = areaGrafica.y + areaGrafica.height - MARGEN_GRAFICA;

        Color color = new Color((redNeuronal.ID * 50) % 255, (redNeuronal.ID * 80) % 255, (redNeuronal.ID * 120) % 255);
        int margenEtiquetasFuncionesTop = 20;
        int espacioEtiquetaCirculo = 20;
        float x_init_etiquetas = x1 + margenEtiquetasFuncionesTop + (redNeuronal.ID / 5) * 100;
        float y_init_etiquetas = margenEtiquetasFuncionesTop + y1 + ((redNeuronal.ID + 1) % 4) * 15;
        // Texto
        pApplet.fill(0);
        pApplet.text(redNeuronal.getFuncionDeActivacionOcultas().getNombre(), x_init_etiquetas + espacioEtiquetaCirculo, y_init_etiquetas);
        // Color
        pApplet.fill(color.getRGB());
        pApplet.stroke(color.getRGB());
        pApplet.rect(x_init_etiquetas, y_init_etiquetas - 10, 10, 10, 5);


        for (int i = 0; i < errores.size(); i++) {
            float distanciasEjes = (x2 - x1);
            float partesEjeX_cien = distanciasEjes / epochs;
            float partesEjeY_cien = distanciasEjes / 100;

            float x_inicial = x1 + (partesEjeX_cien * i);
            float y_inicial = y2 - ((errores.get(i) * 100) * partesEjeY_cien);

            if (i + 1 < errores.size()) { // Si no es el último
                float x_final = x1 + (partesEjeX_cien * (i + 1));
                float y_final = y2 - ((errores.get(i + 1) * 100) * partesEjeY_cien);
                pApplet.line(x_inicial, y_inicial, x_final, y_final);
            }
        }
        pApplet.endShape();
    }

    public void dibujarCuadricula(PApplet pApplet, Rectangle area) {
        int resolution = 10;
        int total_width = area.width;
        int total_height = area.height;
        int cols = (total_width - (MARGEN_CUADRICULA * 2)) / resolution;
        int rows = (total_height - (MARGEN_CUADRICULA * 2)) / resolution;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                float x = (float) i / cols;
                float y = (float) j / rows;
                Float[] inputs = {x, y};
                Float[] prediction = this.redNeuronal.predict(inputs);
                pApplet.stroke(0);
                pApplet.strokeWeight(2);
                pApplet.fill((prediction[0] * 255));
                pApplet.rect(area.x + MARGEN_CUADRICULA + i * resolution, area.y + MARGEN_CUADRICULA + j * resolution, resolution, resolution);
            }
        }
    }
}
