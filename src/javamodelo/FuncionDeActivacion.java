package javamodelo;

import java.util.function.Function;

public class FuncionDeActivacion<T extends Number> {
    private final Function<T,T> function;
    private final Function<T,T> derivative;

    public FuncionDeActivacion(Function<T,T> function, Function<T,T> derivative) {
        this.function = function;
        this.derivative = derivative;
    }

    public Function<T,T> getFunction() {
        return function;
    }

    public Function<T,T> getDerivative() {
        return derivative;
    }
}
