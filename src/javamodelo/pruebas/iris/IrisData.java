package javamodelo.pruebas.iris;

import javamodelo.pruebas.Data;

public class IrisData extends Data {
    public Float[] caracteristicas;
    public String nombre;

    public IrisData(Float[] caracteristicas, String nombre) {
        super(caracteristicas, labelToOneHot(nombre));
        this.nombre = nombre;
    }

    public static Float[] labelToOneHot(String label) {
        switch (label) {
            case "setosa": return new Float[]{1f, 0f, 0f};
            case "versicolor": return new Float[]{0f, 1f, 0f};
            case "virginica": return new Float[]{0f, 0f, 1f};
            default: throw new IllegalArgumentException("Etiqueta desconocida: " + label);
        }
    }

    public Float[] getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Float[] caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
