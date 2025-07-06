package javamodelo.pruebas.perceptron;

import processing.core.PApplet;
import static processing.core.PApplet.map;

public class Punto {
    public float x;
    public float y;
    public Float etiqueta;


    public Punto() {
        this.x = (float) (Math.random() * 2 - 1); // Random X entre -1 y 1
        this.y = (float) (Math.random() * 2 - 1); // Random Y entre -1 y 1

        float lineaY = Perceptron.function(x);

        if (y > lineaY) {
            etiqueta = 1.0f; // Clase Positiva
        } else {
            etiqueta = -1.0f; // Clase Negativa
        }
    }

    public Punto(float x, float y) {
        this.x = x;
        this.y = y;
        if (x > y) {
            etiqueta = 1.0f; // Clase positiva
        } else {
            etiqueta = -1.0f; // Clase negativa
        }
    }

    public void draw(PApplet applet) {
        if (etiqueta == 1) {
            applet.fill(255); // Blanco
        } else if (etiqueta == -1) {
            applet.fill(0); // Negro
        }
        applet.ellipse(getXpixel(applet), getYpixel(applet), 8, 8);
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
        applet.ellipse(punto.getXpixel(applet), punto.getYpixel(applet), 4, 4);
    }

    // Para mappear valores
    public Float getXpixel(PApplet applet) {
        return map(x, -1, 1, 0, applet.width); // X -> entre -1 y 1 a 0 y width
    }

    public Float getYpixel(PApplet applet) {
        return map(y, -1, 1, applet.height, 0); // Y -> entre -1 y 1 a height y 0 (invertido)
    }
}
