// var Matrix = new Matrix(2, 3);
class Matrix {
    constructor(filas, columnas) {
        this.filas = filas;
        this.columnas = columnas;
        // this.data = Array.from({ length: rows }, () => Array(cols).fill(0));
        this.datos = [];
        for (let i = 0; i < this.filas; i++) {
            this.datos[i] = [];
            for (let j = 0; j < this.columnas; j++) {
                this.datos[i][j] = 0;
            }
        }
    }

    // Init prueba1
    initPrueba() {
        for (let i = 0; i < this.filas; i++) {
            for (let j = 0; j < this.columnas; j++) {
                this.datos[i][j] = i * this.filas + j + 1;
            }
        }
    }


    // Randomiza la matriz
    randomizar() {
        for (let i = 0; i < this.filas; i++) {
            for (let j = 0; j < this.columnas; j++) {
                this.datos[i][j] = Math.floor(Math.random() * 10);
            }
        }
    }

    sumarMatrices(m1, m2) {
        let i;
        let j;
        if (m1 instanceof Matrix && m2 instanceof Matrix) {
            if (m1.filas !== m2.filas || m1.columnas !== m2.columnas) {
                throw new Error("Las matrices no se pueden sumar: dimensiones incompatibles.");
            } else {
                const resultado = new Matrix(m1.filas, m1.columnas);
                for (i = 0; i < m1.filas; i++) {
                    for (j = 0; j < m1.columnas; j++) {
                        resultado.datos[i][j] = m1.datos[i][j] + m2.datos[i][j];
                    }
                }
                return resultado;
            }
        } else if (typeof m2 === "number") {
            const nuevaMatrix = new Matrix(m1.filas, m1.columnas);
            for (i = 0; i < m1.filas; i++) {
                for (j = 0; j < m1.columnas; j++) {
                    nuevaMatrix.datos[i][j] = m1.datos[i][j] + m2;
                }
            }
        }
    }

    // Suma dos matrices o una matriz por un número
    sumar(m) {
        let j;
        let i;
        if (m instanceof Matrix) {
            for (i = 0; i < this.filas; i++) {
                for (j = 0; j < this.columnas; j++) {
                    this.datos[i][j] += m.datos[i][j];
                }
            }
        } else if (typeof m === "number") {
            for (i = 0; i < this.filas; i++) {
                for (j = 0; j < this.columnas; j++) {
                    this.datos[i][j] += m;
                }
            }
        }
    }

    // Multiplica dos matrices o una matriz por un número con ambos parámetros
    multiplicarMatrices(m1, m2) { // m1 2x3 - m2 3x2
        let i;
        let j;
        if (m1 instanceof Matrix && m2 instanceof Matrix) {
            if (m1.filas !== m2.columnas) {
                throw new Error("Las matrices no se pueden multiplicar: dimensiones incompatibles.");
            } else {
                const resultado = Array.from({length: m1.filas}, () => Array(m2.columnas).fill(0));

                for (let i = 0; i < m1.filas; i++) {
                    for (let j = 0; j < m2.columnas; j++) {
                        let suma = 0;
                        for (let k = 0; k < m1.columnas; k++) {
                            suma += m1.datos[i][k] * m2.datos[k][j];
                        }
                        resultado[i][j] = suma;
                    }
                }

                // Crear y retornar una nueva instancia de Matrix
                const nuevaMatrix = new Matrix(m2.filas, m1.columnas);
                nuevaMatrix.datos = resultado;
                return nuevaMatrix;
            }
        } else if (typeof m2 === "number") {
            const nuevaMatrix = new Matrix(m1.filas, m1.columnas);
            for (i = 0; i < m1.filas; i++) {
                for (j = 0; j < m1.columnas; j++) {
                    nuevaMatrix.datos[i][j] = m1.datos[i][j] * m2;
                }
            }
            return nuevaMatrix;
        } else if (typeof m1 === "number") {
            const nuevaMatrix = new Matrix(m2.filas, m2.columnas);
            for (i = 0; i < m2.filas; i++) {
                for (j = 0; j < m2.columnas; j++) {
                    nuevaMatrix.datos[i][j] = m2.datos[i][j] * m1;
                }
            }
            return nuevaMatrix;
        }
    }


    // Multiplica dos matrices o una matriz por un número sobre el mismo objeto .this
    multiplicar(m) {
        let i;
        let j;
        if (m instanceof Matrix) {
            if (this.filas !== m.columnas) {
                throw new Error("Las matrices no se pueden multiplicar: dimensiones incompatibles.");
            } else {
                const resultado = Array.from({length: this.filas}, () => Array(m.columnas).fill(0));

                for (let i = 0; i < this.filas; i++) {
                    for (let j = 0; j < m.columnas; j++) {
                        let suma = 0;
                        for (let k = 0; k < this.columnas; k++) {
                            suma += this.datos[i][k] * m.datos[k][j];
                        }
                        resultado[i][j] = suma;
                    }
                }

                // Asignamos el resultado a datos y actualizamos columnas
                this.datos = resultado;
                this.columnas = m.columnas;
                this.filas // No cambia porque  [3]x2 * 2x3 = [3]x3
            }
        } else if (typeof m === "number") {
            for (i = 0; i < this.filas; i++) {
                for (j = 0; j < this.columnas; j++) {
                    this.datos[i][j] *= m;
                }
            }
        }
    }

}