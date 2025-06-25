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
        this.datos = [
            [1, 2, 3],
            [4, 5, 6],
            [7, 8, 9]
        ];
    }


    // Randomiza la matriz
    randomizar() {
        for (let i = 0; i < this.filas; i++) {
            for (let j = 0; j < this.columnas; j++) {
                this.datos[i][j] = Math.floor(Math.random() * 10);
            }
        }
    }

    // Suma dos matrices o una matriz por un número
    sumar(matrix) {
        let j;
        let i;
        if (matrix instanceof Matrix) {
            for (i = 0; i < this.filas; i++) {
                for (j = 0; j < this.columnas; j++) {
                    this.datos[i][j] += matrix.datos[i][j];
                }
            }
        } else if (matrix instanceof Number) {
            for (i = 0; i < this.filas; i++) {
                for (j = 0; j < this.columnas; j++) {
                    this.datos[i][j] += matrix;
                }
            }
        }
    }

    // Multiplica dos matrices o una matriz por un número
    multiplicar(m1, m2) { // m1 2x3 - m2 3x2
        let i;
        let j;
        if (m1 instanceof Matrix && m2 instanceof Matrix) {
            if (m1.filas !== m2.columnas) {
                throw new Error("Las matrices no se pueden multiplicar: dimensiones incompatibles.");
            } else {
                const resultado = Array.from({ length: m1.filas }, () => Array(m2.columnas).fill(0));

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

                // Asignamos el resultado a datos y actualizamos columnas
                // this.datos = resultado;
                // this.columnas = matrix.columnas;
                // this.filas no cambia porque  [3]x2 * 2x3 = [3]x3
            }
        } else if (m1 instanceof Number) {
            const nuevaMatrix = new Matrix(m2.filas, m2.columnas);
            for (i = 0; i < m2.filas; i++) {
                for (j = 0; j < m2.columnas; j++) {
                    nuevaMatrix.datos[i][j] = m2.datos[i][j] * m1;
                }
            }
            return nuevaMatrix;
        }
    }

}