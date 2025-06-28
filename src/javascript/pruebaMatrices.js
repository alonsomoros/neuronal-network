class pruebaMatrices {

    constructor() {
        this.test();
    }

    test() {
        this.test_multiplicacion_matriz_x_matriz();
        this.test_multiplicacion_matriz_x_numero();
        this.test_multiplicacion_matriz_x_matriz_static();
        this.test_multiplicacion_matriz_x_numero_static();

        this.test_suma_matriz_x_matriz();
        this.test_suma_matriz_x_numero();
        this.test_suma_matriz_x_matriz_static();
        this.test_suma_matriz_x_numero_static();

        this.test_transponer_matriz();
        this.test_transponer_matriz_static();

        this.test_funcion_duplicar_matriz_x_numero();
    }

    test_multiplicacion_matriz_x_matriz() {
        let m1 = new Matrix(2, 3);
        m1.randomizar();
        let m2 = new Matrix(3, 2);
        m2.randomizar();

        // Esto debería funcionar correctamente
        m1.multiplicar(m2); // 2x2

        try {
            m1.multiplicar(m2); // Esto debería lanzar un error
        } catch (Exception) {
            console.error("Error esperado: " + Exception.message);
        }
    }

    test_multiplicacion_matriz_x_numero() {
        let m1 = new Matrix(2, 3);
        m1.randomizar();

        m1.multiplicar(10); // Esto debería funcionar correctamente

        try {
            m1.randomizar();

            m1.multiplicar("10"); // Esto debería lanzar un error
        } catch (Exception) {
            console.error("Error esperado: " + Exception.message);
        }
    }

    test_multiplicacion_matriz_x_matriz_static() {
        let m1 = new Matrix(2, 3);
        m1.randomizar();
        let m2 = new Matrix(3, 2);
        m2.randomizar();

        // Esto debería funcionar correctamente
        let multiplicacionPorMatriz = Matrix.multiplicarMatrices(m1, m2); // 2x2

        try {
            Matrix.multiplicarMatrices(multiplicacionPorMatriz, m2); // Esto debería lanzar un error
        } catch (Exception) {
            console.error("Error esperado: " + Exception.message);
        }
    }

    test_multiplicacion_matriz_x_numero_static() {
        let m1 = new Matrix(2, 3);
        m1.randomizar();
        // Esto debería funcionar correctamente
        let multiplicacionPorNumero = Matrix.multiplicarMatrices(m1, 2);

        try {
            Matrix.multiplicarMatrices(m1, "2"); // Esto debería lanzar un error
        } catch (Exception) {
            console.error("Error esperado: " + Exception.message);
        }
    }

    test_suma_matriz_x_matriz() {
        let m1 = new Matrix(2, 3);
        m1.randomizar();
        let m2 = new Matrix(2, 3);
        m2.randomizar();

        // Esto debería funcionar correctamente
        m1.sumar(m2); // 2x3

        try {
            let m3 = new Matrix(3, 2);
            m1.sumar(m3); // Esto debería lanzar un error
        } catch (Exception) {
            console.error("Error esperado: " + Exception.message);
        }
    }

    test_suma_matriz_x_numero() {
        let m1 = new Matrix(2, 3);
        m1.randomizar();

        // Esto debería funcionar correctamente
        m1.sumar(10); // 2x3

        try {
            m1.randomizar();
            m1.sumar("10"); // Esto debería lanzar un error
        } catch (Exception) {
            console.error("Error esperado: " + Exception.message);
        }
    }

    test_suma_matriz_x_matriz_static() {
        let m1 = new Matrix(2, 3);
        m1.randomizar();
        let m2 = new Matrix(2, 3);
        m2.randomizar();

        // Esto debería funcionar correctamente
        let suma = Matrix.sumarMatrices(m1, m2); // 2x3

        try {
            let m3 = new Matrix(3, 2);
            Matrix.sumarMatrices(m1, m3); // Esto debería lanzar un error
        } catch (Exception) {
            console.error("Error esperado: " + Exception.message);
        }
    }

    test_suma_matriz_x_numero_static() {
        let m1 = new Matrix(2, 3);
        m1.randomizar();

        // Esto debería funcionar correctamente
        let suma = Matrix.sumarMatrices(m1, 5); // 2x3

        try {
            Matrix.sumarMatrices(m1, "5"); // Esto debería lanzar un error
        } catch (Exception) {
            console.error("Error esperado: " + Exception.message);
        }
    }

    test_transponer_matriz() {
        let m1 = new Matrix(2, 3);
        m1.randomizar();

        // Esto debería funcionar correctamente
        m1.transponer(); // 3x2
    }

    test_transponer_matriz_static() {
        let m1 = new Matrix(2, 3);
        m1.randomizar();

        // Esto debería funcionar correctamente
        let traspuesta = Matrix.transponerMatriz(m1); // 3x2
    }

    test_funcion_duplicar_matriz_x_numero() {
        let duplicada = new Matrix(2, 2);
        duplicada.map(RedNeuronal.prototype.duplicar);
    }
}