package javamodelo;

import java.util.function.Function;

public class FuncionDeActivacion<T extends Number> {
    private final String nombre;
    private final Function<T,T> function;
    private final Function<T,T> derivative;

    public FuncionDeActivacion(String nombre, Function<T,T> function, Function<T,T> derivative) {
        this.nombre = nombre;
        this.function = function;
        this.derivative = derivative;
    }

    public Function<T,T> getFunction() {
        return function;
    }

    public Function<T,T> getDerivative() {
        return derivative;
    }

    public String getNombre() {
        return this.nombre;
    }
}
