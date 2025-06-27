function sigmoide(x) { // Ya no se usa sigmoid se usa porEj ReLu
    return 1 / (1 + Math.exp(-x));
}

function derivadaSigmoide(x) {
    return x * (1 - x); // Derivada de la función sigmoide
}


class RedNeuronal {
    constructor(numEntradas, numOcultos, numSalidas) {
        this.nodos_entradas = numEntradas;
        this.nodos_ocultos = numOcultos;
        this.nodos_salidas = numSalidas;

        this.pesos_entradas_a_ocultos = new Matrix(this.nodos_ocultos, this.nodos_entradas); // Filas: Capa Oculta - Columnas: Capa Entrada
        this.pesos_entradas_a_ocultos.randomizar();
        this.pesos_ocultos_a_salidas = new Matrix(this.nodos_salidas, this.nodos_ocultos); // Filas: Capa Salida - Columnas: Capa Oculta
        this.pesos_ocultos_a_salidas.randomizar();

        this.bias_ocultos = new Matrix(numOcultos, 1); // Bias para la capa oculta
        this.bias_ocultos.randomizar();
        this.bias_salidas = new Matrix(numSalidas, 1); // Bias para la capa de salida
        this.bias_salidas.randomizar();
        this.tasa_aprendizaje = 0.1; // Tasa de aprendizaje
    }

    feedforward(entrada_array) {
        // Convertir el array de entrada a una matriz
        let entradas = Matrix.fromArray(entrada_array);

        // Calcular la capa oculta
        let salidaOculto = Matrix.multiplicarMatrices(this.pesos_entradas_a_ocultos, entradas); // Se pone al revés para que porEj 2x1 (inputs) * 4x2 (pesos) sea 4x2·2x1 -> dando una 4x1
        salidaOculto.sumar(this.bias_ocultos);
        salidaOculto.map(sigmoide); // Aplica la función de activación a la matriz

        // Calcular la salida
        let salidaFinal = Matrix.multiplicarMatrices(this.pesos_ocultos_a_salidas, salidaOculto);
        salidaFinal.sumar(this.bias_salidas);

        salidaFinal.map(sigmoide); // Aplica la función de activación a la matriz

        // Convertir la salida a un array
        return salidaFinal.toArray();
    }

    entrenar(entradasArray, objetivosArray) {

        /* Para la prueba:
        let redNeuronal = new RedNeuronal(2, 2, 2);
        let entradas = [1, 0];
        let objetivos = [1, 0];

        Entradas: [2x1]
        Objetivos: [2x1]
        Salidas: [2x1]

        Pesos Entradas a Ocultos: [2x2]
        Pesos Ocultos a Salidas: [2x2]
        Bias Ocultos: [2x1]
        Bias Salidas: [2x1]

        Por lo tanto:
        Salidas Ocultos: [2x1]
        Salidas: [2x1]
        Error Salidas: [2x1]
        Error Ocultos: [2x1]

        Gradiente Salidas: [2x1]
        Gradiente Ocultos: [2x1]

        Pesos Ocultos a Salidas Delta: [2x2]
        Pesos Entradas a Ocultos Delta: [2x2]
        Bias Ocultos Delta: [2x1]
        Bias Salidas Delta: [2x1]
        */

        // ------ INICIO DE FEED FORWARD ------

        // Convertir el array de entradas y objetivos a una matriz
        let entradas = Matrix.fromArray(entradasArray);
        let objetivos = Matrix.fromArray(objetivosArray);

        // Calcular la capa oculta
        let salidasOculto = Matrix.multiplicarMatrices(this.pesos_entradas_a_ocultos, entradas); // Se pone al revés para que porEj 2x1 (inputs) * 4x2 (pesos) sea 4x2·2x1 -> dando una 4x1
        salidasOculto.sumar(this.bias_ocultos);
        salidasOculto.map(sigmoide); // Aplica la función de activación

        // Calcular la salida
        let salidas = Matrix.multiplicarMatrices(this.pesos_ocultos_a_salidas, salidasOculto);
        salidas.sumar(this.bias_salidas);
        salidas.map(sigmoide); // Aplica la función de activación

        // ------ FIN DE FEED FORWARD ------

        // ------ INICIO DE BACKPROPAGATION ------

        //  <[ Hidden <- Output ]> = ΔW,ho = η (T - O) σ′(zO) Hᵀ

        // 1º Calcular el error de las salidas
        // ERROR = OBJETIVO - SALIDA
        let error_salidas = Matrix.restarMatrices(objetivos, salidas); // [(T - O)] - [2x1]

        // 2º Gradiente
        let gradiente_salidas = Matrix.map(salidas, derivadaSigmoide); // [σ′(zOutput)] - [2x1]
        gradiente_salidas.multiplicarElementwise(error_salidas); // [(T - O)] σ′(zOutput) - [2x1] (Element-wise)
        // 3º Multiplicar por la tasa de aprendizaje
        gradiente_salidas.multiplicar(this.tasa_aprendizaje); // [η] (T - O) σ′(zO)

        // 4º Calcular la traspuesta de las salidas ocultas
        let salidasOculto_Traspuesta = Matrix.transponerMatriz(salidasOculto); // [Hᵀ] - [1x2]

        // 5º Calcular el delta de los pesos de la capa oculta a la capa de salida
        // [ΔW, ho] = η (T - O) σ′(zO) Hᵀ
        let pesos_ocultos_a_salidas_delta = Matrix.multiplicarMatrices(gradiente_salidas, salidasOculto_Traspuesta); // Debería ser: δ outputs(#Salidas x 1) x Hᵀ(1 x #Ocultos) = (#Salidas x #Ocultos)

        // 6º Actualizar los pesos de la capa oculta a la capa de salida
        this.pesos_ocultos_a_salidas.sumar(pesos_ocultos_a_salidas_delta);
        this.bias_salidas.sumar(gradiente_salidas);


        //  <[ Input <- Hidden ]> = ΔW,ih = η (W,ho)ᵀ (T - O) σ′(zO) * σ′(zH) Iᵀ

        // 1º Calcular la traspuesta de los pesos de la capa oculta → salidas
        let pesos_ocultos_a_salidas_transpuestos = Matrix.transponerMatriz(this.pesos_ocultos_a_salidas); // [W,ho]ᵀ - [2x2]

        // 2º Calcular el error de las salidas de la capa oculta
        // ERROR = (W, ho)ᵀ (T - O) σ′(zO)
        let error_ocultos = Matrix.multiplicarMatrices(pesos_ocultos_a_salidas_transpuestos, error_salidas); // [(W, ho)ᵀ (T - O) σ′(zO)] - [2x1]

        // 3º Gradiente de la capa oculta
        let gradiente_ocultos = Matrix.map(salidasOculto, derivadaSigmoide); // [σ′(zH)] - [2x1]
        gradiente_ocultos.multiplicarElementwise(error_ocultos); // [(W, ho)ᵀ (T - O) σ′(zO)] σ′(zH) // - [2x1] (Element-wise)
        // 4º Multiplicar por la tasa de aprendizaje
        gradiente_ocultos.multiplicar(this.tasa_aprendizaje); // [η] (W, ho)ᵀ (T - O) σ′(zO) σ′(zH)

        // 5º Calcular la traspuesta de las entradas
        let entradas_Traspuesta = Matrix.transponerMatriz(entradas); // [Iᵀ] - [1x2]

        // 6º Calcular el delta de los pesos de la capa de entradas a la capa oculta
        // [ΔW, ih] = η (W, ho)ᵀ (T - O) σ′(zO) * σ′(zH) Iᵀ
        let pesos_entradas_a_ocultos_delta = Matrix.multiplicarMatrices(gradiente_ocultos, entradas_Traspuesta); // Debería ser: δ ocultos(#Ocultos x 1) x Iᵀ(1 x #Entradas) = (#Ocultos x #Entradas)

        // 7º Actualizar los pesos de la capa de entrada a la capa oculta
        this.pesos_entradas_a_ocultos.sumar(pesos_entradas_a_ocultos_delta);
        this.bias_ocultos.sumar(gradiente_ocultos);

        // ------ FIN DE BACKPROPAGATION ------


    }

    imprimirDatosYError(salidas, objetivos, output_errors) {
        console.log("Salidas de la red:");
        salidas.print();
        console.log("Objetivos:");
        objetivos.print();
        console.log("Error:");
        output_errors.print();
    }

    duplicar(x) {
        return x * 2;
    }

    duplicarDiagonal(x, fila, columna) {
        if (fila === columna) {
            return x * 2;
        }
    }
}
