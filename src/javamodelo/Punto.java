package javamodelo;

import processing.core.PApplet;
import static processing.core.PApplet.map;

public class Punto {
    public float x;
    public float y;
    public Float etiqueta;

    public PApplet applet;

    public Punto(PApplet applet) {
        this.applet = applet;
        this.x = applet.random(-1, 1); // Random X entre -1 y 1
        this.y = applet.random(-1, 1); // Random Y entre -1 y 1

        float lineaY = Main.function(x);

        if (y > lineaY) {
            etiqueta = 1.0f; // Clase Positiva
        } else {
            etiqueta = -1.0f; // Clase Negativa
        }
    }

    public Punto(PApplet applet, float x, float y) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        if (x > y) {
            etiqueta = 1.0f; // Clase positiva
        } else {
            etiqueta = -1.0f; // Clase negativa
        }
    }

    public void show(PApplet applet) {
        if (etiqueta == 1) {
            applet.fill(255); // Blanco
        } else if (etiqueta == -1) {
            applet.fill(0); // Negro
        }
        applet.ellipse(getXpixel(), getYpixel(), 8, 8);
    }

    public void showEtiqueta(PApplet applet, Perceptron perceptron, Punto punto) {
        // Usar los puntos generados para la entrada del perceptrón
        Float[] entradas = {punto.x, punto.y};
        Float objetivo = punto.etiqueta;

        // Predecir la etiqueta de los puntos
        Float intento = perceptron.predict(entradas);
        if (intento.equals(objetivo)) {
            applet.fill(0, 255, 0);
        } else {
            applet.fill(255, 0, 0);
        }
        applet.noStroke();
        applet.ellipse(punto.getXpixel(), punto.getYpixel(), 4, 4);
    }

    // Para mappear valores
    public Float getXpixel() {
        return map(x, -1, 1, 0, applet.width); // X -> entre -1 y 1 a 0 y width
    }

    public Float getYpixel() {
        return map(y, -1, 1, applet.height, 0); // Y -> entre -1 y 1 a height y 0 (invertido)
    }
}
