import processing.core.PApplet;

import java.awt.*;

public class Main extends PApplet {
    Perceptron perceptron;
    Punto[] puntos = new Punto[100];

    int entrenamientoIndex = 0;
    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size(400, 400);
    }
    public void setup() {
        perceptron = new Perceptron();

        for (int i = 0; i < puntos.length; i++) {
            puntos[i] = new Punto(this);
        }
    }

    public void draw() {
        background(255);
        stroke(0);
        line(0,0, width, height);

        for (Punto punto : puntos) {
            punto.show(this);
        }

        for (Punto punto : puntos){
            float[] entradas = {punto.x, punto.y};
            int objetivo = punto.etiqueta;

            int intento = perceptron.guess(entradas);
            if (intento == objetivo) {
                fill(0, 255, 0);
            } else {
                fill(255, 0, 0);
            }
            noStroke();
            ellipse(punto.x, punto.y, 4, 4);
        }

        Punto entrenamiento = puntos[entrenamientoIndex];
        float[] entradas = {entrenamiento.x, entrenamiento.y};
        int objetivo = entrenamiento.etiqueta;
        perceptron.entrenamiento(entradas, objetivo);
        entrenamientoIndex++;
        if (entrenamientoIndex >= puntos.length) {
            entrenamientoIndex = 0;
        }
    }

    public void mousePressed() {
        for (Punto punto : puntos) {
//            float[] entradas = {punto.x, punto.y};
//            int objetivo = punto.etiqueta;
//            perceptron.entrenamiento(entradas, objetivo);
        }
    }
}
