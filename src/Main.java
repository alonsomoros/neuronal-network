import processing.core.PApplet;
import processing.event.KeyEvent;


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

        // Dibujar la función lineal
//        dibujarFuncionLineal();

        // Dibujar la línea de decisión del perceptrón
        dibujarLineaDecision();

        // Mostrar los puntos
        for (Punto punto : puntos) {
            punto.show(this);
        }

        // Mostrar las etiquetas de los puntos
        for (Punto punto : puntos) {
            punto.showEtiqueta(this, perceptron, punto);
        }

//         Aprovecha el bucle de dibujo para entrenar el perceptrón
        entrenamiento();
    }

    private void entrenamiento() {
        Punto entrenamiento = puntos[entrenamientoIndex];
        float[] entradas = {entrenamiento.x, entrenamiento.y};
        int objetivo = entrenamiento.etiqueta;
        perceptron.entrenamiento(entradas, objetivo);
        entrenamientoIndex++;
        if (entrenamientoIndex >= puntos.length) {
            entrenamientoIndex = 0;
        }
    }

    private void dibujarLineaDecision() {
        Punto p3 = new Punto(this, -1, perceptron.predecirY(-1));
        Punto p4 = new Punto(this, 1, perceptron.predecirY(1));
        line(p3.getXpixel(), p3.getYpixel(), p4.getXpixel(), p4.getYpixel());
    }

    private void dibujarFuncionLineal() {
        Punto p1 = new Punto(this, -1, function(-1));
        Punto p2 = new Punto(this, 1, function(1));
        line(p1.getXpixel(), p1.getYpixel(), p2.getXpixel(), p2.getYpixel());
    }

    // Y = MX + N
    public static float function(float x) {
        return - (0.3f * x) - (0.2f);
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
