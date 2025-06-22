import processing.core.PApplet;

public class Punto {
    public float x;
    public float y;
    public int etiqueta;

    public PApplet applet;

    public Punto(PApplet applet) {
        this.applet = applet;
        this.x = applet.random(applet.width); // Random x between -1 and 1
        this.y = applet.random(applet.height); // Random y between -1 and 1
        if (x > y) {
            etiqueta = 1; // Positive class
        } else {
            etiqueta = -1; // Negative class
        }
    }
    public Punto(PApplet applet, float x, float y) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        if (x > y) {
            etiqueta = 1; // Positive class
        } else {
            etiqueta = -1; // Negative class
        }
    }

    public void show(PApplet applet) {
        if (etiqueta == 1) {
            applet.fill(255); // Blanco
        } else if (etiqueta == -1) {
            applet.fill(0); // Negro
        }
        applet.ellipse(x, y, 8, 8);
    }

    public void showEtiqueta(PApplet applet, Perceptron perceptron, Punto punto) {
        // Usar los puntos generados para la entrada del perceptrón
        float[] entradas = {punto.x, punto.y};
        int objetivo = punto.etiqueta;

        // Predecir la etiqueta de los puntos
        int intento = perceptron.predecirSalida(entradas);
        if (intento == objetivo) {
            applet.fill(0, 255, 0);
        } else {
            applet.fill(255, 0, 0);
        }
        applet.noStroke();
        applet.ellipse(punto.x, punto.y, 4, 4);
    }
}
