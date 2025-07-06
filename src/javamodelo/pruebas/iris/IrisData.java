package javamodelo.pruebas.iris;

public class IrisData {
    public float[] caracteristicas;
    public String nombre;

    public IrisData(float[] caracteristicas, String nombre) {
        this.caracteristicas = caracteristicas;
        this.nombre = nombre;
    }

    public static double[] labelToOneHot(String label) {
        switch (label) {
            case "setosa": return new double[]{1, 0, 0};
            case "versicolor": return new double[]{0, 1, 0};
            case "virginica": return new double[]{0, 0, 1};
            default: throw new IllegalArgumentException("Etiqueta desconocida: " + label);
        }
    }
}
