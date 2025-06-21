import processing.core.PApplet;

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
        perceptron = new Perceptron(2, 0.1f);

        for (int i = 0; i < puntos.length; i++) {
            puntos[i] = new Punto(this);
        }
    }

    public void draw() {
        background(255);
        stroke(0);
        line(0, 0, width, height);

        // Mostrar los puntos
        for (Punto punto : puntos) {
            punto.show(this);
        }

        // Mostrar la etiqueta de cada punto
        for (Punto punto : puntos) {

            // Usar los puntos generados para la entrada del perceptrón
            float[] entradas = {punto.x, punto.y};
            int objetivo = punto.etiqueta;

            // Predecir la etiqueta de los puntos
            int intento = perceptron.predecirSalida(entradas);
            if (intento == objetivo) {
                fill(0, 255, 0);
            } else {
                fill(255, 0, 0);
            }
            noStroke();
            ellipse(punto.x, punto.y, 4, 4);
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
}
