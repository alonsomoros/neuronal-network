package javamodelo;

import javamodelo.pruebas.iris.Prueba_IRIS;
import javamodelo.pruebas.perceptron.Prueba_PerceptronSimple;
import javamodelo.pruebas.xor.Prueba_XOR;
import javamodelo.pruebas.xor.RedNeuronal;
import processing.core.PApplet;
import processing.event.KeyEvent;


public class Main extends PApplet {
    private final Prueba_XOR prueba_xor = new Prueba_XOR();
    private final Prueba_PerceptronSimple prueba_perceptron_simple = new Prueba_PerceptronSimple();
    private final Prueba_IRIS prueba_iris = new Prueba_IRIS(80);


    public static void main(String[] args) {
        PApplet.main("javamodelo.Main");
    }

    public void settings() {
        size(1000, 1000);
    }

    public void setup() {
        inicializarCanvas();
    }



    public void draw() {
//        prueba_perceptron_simple.draw(this);
        prueba_xor.draw(this);
//        prueba_iris.draw(this);
    }

    private void inicializarCanvas() {
        background(255);
        stroke(0);
        fill(255);

        textFont(createFont("Calibri", 12));
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ENTER) {
            for (RedNeuronal rn : prueba_xor.redesNeuronales) {
                rn.reset();
            }
        }
        super.keyPressed(event);

    }
}
