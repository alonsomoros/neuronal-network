import processing.core.PApplet;

public class Punto extends PApplet {
    public float x;
    public float y;
    public int etiqueta;

    public Punto(PApplet applet) {
        this.x = random(applet.width); // Random x between -1 and 1
        this.y = random(applet.height); // Random y between -1 and 1
        if (x > y) {
            etiqueta = 1; // Positive class
        } else {
            etiqueta = -1; // Negative class
        }
    }

    public void show(PApplet applet) {
        if (etiqueta == 1) {
            applet.fill(255);
        } else if (etiqueta == -1) {
            applet.fill(0);
        }
        applet.ellipse(x, y, 8, 8);
    }
}
