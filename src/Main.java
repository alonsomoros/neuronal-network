import processing.core.PApplet;
import processing.event.KeyEvent;

public class Main extends PApplet {
    Perceptron perceptron;
    Punto[] puntos = new Punto[10];

    public static void main(String[] args) {
        PApplet.main("Main");
    }

    public void settings() {
        size(400, 400);
    }

    public void setup() {
        perceptron = new Perceptron(2, 0.1f);

        for (int i = 0; i < puntos.length; i++) {
            puntos[i] = new Punto(this);
        }
    }

    public void draw() {
        background(255);
        stroke(0);
        line(0, 0, width, height);

        // Mostrar los puntos y sus etiquetas
        for (Punto punto : puntos) {
            punto.show(this);
//            punto.showEtiqueta(this, perceptron, punto);


        }

        for (Punto punto : puntos) {
            punto.showEtiqueta(this, perceptron, punto);
        }


        // Aprovecha el bucle de dibujo para entrenar el perceptrón
//        Punto entrenamiento = puntos[entrenamientoIndex];
//        float[] entradas = {entrenamiento.x, entrenamiento.y};
//        int objetivo = entrenamiento.etiqueta;
//        perceptron.entrenamiento(entradas, objetivo);
//        entrenamientoIndex++;
//        if (entrenamientoIndex >= puntos.length) {
//            entrenamientoIndex = 0;
//        }
    }

    public void mousePressed() {
        for (Punto punto : puntos) {
            float[] entradas = {punto.x, punto.y};
            int objetivo = punto.etiqueta;
            perceptron.entrenamiento(entradas, objetivo);
        }
        System.out.println("------------------------------------------------------------");
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ENTER) {
            for (int i = 0; i < puntos.length; i++) {
                puntos[i] = new Punto(this);
                perceptron.resetPesosRandom();
            }
        }
        super.keyPressed(event);
    }
}
