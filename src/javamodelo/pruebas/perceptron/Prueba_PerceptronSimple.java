package javamodelo.pruebas.perceptron;

import processing.core.PApplet;

public class Prueba_PerceptronSimple {

    private Perceptron perceptron;

    public Prueba_PerceptronSimple() {
        setUpPerceptron();
    }

    private void setUpPerceptron() {
        perceptron = new Perceptron(2, 0.1);
    }

    public void draw(PApplet pApplet) {
        perceptron.dibujarPruebaPerceptronSimple(pApplet);
    }

    public Perceptron getPerceptron() {
        return perceptron;
    }

    public void setPerceptron(Perceptron perceptron) {
        this.perceptron = perceptron;
    }
}
