package javamodelo.utils;

import processing.core.PApplet;

import java.awt.*;

public class Dibujador {
    public static void dibujarGrafica(PApplet pApplet, float xi, float yi, float xf, float yf, float margin, Float[] errores, int epochs) { // Los vertices del área que ocupará la gráfica

        // Área de la gráfica
        float x1 = xi + margin;
        float x2 = xf - margin;
        float y1 = yi + margin; // = margin
        float y2 = yf - margin;

        pApplet.strokeWeight(2); // Aumenta el grosor de la línea
        pApplet.line(x1, y2, x2, y2); // Horizontal
        pApplet.line(x1, y1, x1, y2); // Vertical

        Point tamanoTextoEpoch = new Point(30, 20);
        Point tamanoTextoError = new Point(30, 20);

        pApplet.textSize(12);
        pApplet.fill(0);
        pApplet.text("Epochs", x1 - tamanoTextoEpoch.x, y2 + tamanoTextoEpoch.y);
        pApplet.text("Error (MSE)", x1 - tamanoTextoError.x, y1 - tamanoTextoError.y);

        if (epochs > 1000) {
            pApplet.textSize(8);
        }

        for (int i = 1; i <= 10; i++) {
            pApplet.line(x1 + i * (x2 - x1) / 10, y2 - 2, x1 + i * (x2 - x1) / 10, y2 + 2);
            pApplet.text(String.valueOf(i * epochs / 10), x1 + i * (x2 - x1) / 10 - 5, y2 + 25);
        }
        pApplet.textSize(12);

        for (int i = 1; i <= 10; i++) {
            pApplet.line(x1 - 2, y2 - i * (y2 - margin) / 10, x1 + 2, y2 - i * (y2 - margin) / 10);
            pApplet.text(String.format("%.1f", (float) i / 10), x1 - 35, y2 - i * (y2 - margin) / 10 + 5);
        }


        pApplet.stroke(255, 0, 0); // Color rojo para la línea de error


        for (int i = 0; i < epochs / (epochs / 100); i++) {
            float distanciasEjes = (x2 - x1);
            float partesEjes_cien = distanciasEjes / 100;

            float x11 = x1 + (partesEjes_cien * i);
            float y11 = y2 - ((errores[i] * 100) * partesEjes_cien);

            if (i + 1 < errores.length) {
                float x12 = x1 + (partesEjes_cien * (i + 1));
                float y12 = y2 - ((errores[i + 1] * 100) * partesEjes_cien);
                pApplet.line(x11, y11, x12, y12);
            } else {
                pApplet.line(x11, y11, x2, y2);
            }
        }

        pApplet.endShape();

    }
}
