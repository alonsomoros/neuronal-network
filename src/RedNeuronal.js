function sigmoide(x) {
    return 1 / (1 + Math.exp(-x));
}
class RedNeuronal {
    constructor(numEntradas, numOcultos, numSalidas) {
        this.nodos_entradas = numEntradas;
        this.nodos_ocultos = numOcultos;
        this.nodos_salidas = numSalidas;

        this.pesos_entradas_a_ocultos = new Matrix(this.nodos_ocultos, this.nodos_entradas); // Filas: Capa Oculta - Columnas: Capa Entrada
        this.pesos_entradas_a_ocultos.randomizar();
        this.pesos_ocultos_a_salidas = new Matrix(this.nodos_salidas, this.nodos_ocultos); // // Filas: Capa Salida - Columnas: Capa Oculta
        this.pesos_ocultos_a_salidas.randomizar();

        this.bias_ocultos = new Matrix(numOcultos, 1); // Bias para la capa oculta
        this.bias_ocultos.randomizar();
        this.bias_salidas = new Matrix(numSalidas, 1); // Bias para la capa de salida
        this.bias_salidas.randomizar();

        // Imprimir los valores
        console.log("Pesos Entradas a Ocultos con dimensiones [" + this.pesos_entradas_a_ocultos.filas + ", " + this.pesos_entradas_a_ocultos.columnas + "]");
        this.pesos_entradas_a_ocultos.print();
        console.log("Pesos Ocultos a Salidas con dimensiones [" + this.pesos_ocultos_a_salidas.filas + ", " + this.pesos_ocultos_a_salidas.columnas + "]");
        this.pesos_ocultos_a_salidas.print();
        console.log("Bias Ocultos con dimensiones [" + this.bias_ocultos.filas + ", " + this.bias_ocultos.columnas + "]");
        this.bias_ocultos.print();
        console.log("Bias Salidas con dimensiones [" + this.bias_salidas.filas + ", " + this.bias_salidas.columnas + "]");
        this.bias_salidas.print();
    }

    feedforward(entrada_array) {
        // Convertir el array de entrada a una matriz
        let entradas = Matrix.fromArray(entrada_array);
        console.log("Entradas: " + entrada_array + " matriz de dimensiones [" + entradas.filas + ", " + entradas.columnas + "]");
        entradas.print();

        // Calcular la capa oculta
        let salidaOculto = Matrix.multiplicarMatrices(this.pesos_entradas_a_ocultos, entradas); // Se pone al revés para que porEj 2x1 (inputs) * 4x2 (pesos) sea 4x2·2x1 -> dando una 4x1
        salidaOculto.sumar(this.bias_ocultos);
        salidaOculto.print();
        salidaOculto.map(sigmoide); // Aplica la función de activación
        console.log("Tras la funcion de activación, la salida oculta es:");
        salidaOculto.print();

        // Calcular la salida
        let salidaFinal = Matrix.multiplicarMatrices(this.pesos_ocultos_a_salidas, salidaOculto);
        salidaFinal.sumar(this.bias_salidas);
        salidaFinal.print();
        salidaFinal.map(sigmoide); // Aplica la función de activación
        console.log("Tras la funcion de activación, la salida final es:");
        salidaFinal.print();

        // Convertir la salida a un array
        return salidaFinal.toArray();
    }
}