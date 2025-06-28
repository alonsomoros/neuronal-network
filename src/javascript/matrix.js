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

    // Crea una matriz a partir de un array unidimensional
    static fromArray(array) {
        const matriz = new Matrix(array.length, 1);
        for (let i = 0; i < array.length; i++) {
            matriz.datos[i][0] = array[i];
        }
        return matriz;
    }

    toArray() {
        const array = [];
        for (let i = 0; i < this.filas; i++) {
            for (let j = 0; j < this.columnas; j++) {
                array.push(this.datos[i][j]);
            }
        }
        return array;
    }

    // Randomiza la matriz
    randomizar() {
        for (let i = 0; i < this.filas; i++) {
            for (let j = 0; j < this.columnas; j++) {
                this.datos[i][j] = this.randomDel_menos1_1();
            }
        }
    }

    // Randomiza la matriz con instancia
    static randomizar(m) {
        if (m instanceof Matrix) {
            for (let i = 0; i < m.filas; i++) {
                for (let j = 0; j < m.columnas; j++) {
                    m.datos[i][j] = m.randomDel_menos1_1();
                }
            }
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix para randomizar.");
        }
    }

    randomDel_1_10() {
        return Math.floor(Math.random() * 10);
    }

    randomDel_menos1_1() {
        return Math.random() * 2 - 1;
    }

    // Suma dos matrices o una matriz por un número
    static sumarMatrices(m1, m2) {
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
            return nuevaMatrix;
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix o un número para sumar.");
        }
    }

    // Resta dos matrices o una matriz por un número
    static restarMatrices(m1, m2) {
        let i;
        let j;
        if (m1 instanceof Matrix && m2 instanceof Matrix) {
            if (m1.filas !== m2.filas || m1.columnas !== m2.columnas) {
                throw new Error("Las matrices no se pueden sumar: dimensiones incompatibles.");
            } else {
                const resultado = new Matrix(m1.filas, m1.columnas);
                for (i = 0; i < m1.filas; i++) {
                    for (j = 0; j < m1.columnas; j++) {
                        resultado.datos[i][j] = m1.datos[i][j] - m2.datos[i][j];
                    }
                }
                return resultado;
            }
        } else if (typeof m2 === "number") {
            const nuevaMatrix = new Matrix(m1.filas, m1.columnas);
            for (i = 0; i < m1.filas; i++) {
                for (j = 0; j < m1.columnas; j++) {
                    nuevaMatrix.datos[i][j] = m1.datos[i][j] - m2;
                }
            }
            return nuevaMatrix;
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix o un número para restar.");
        }
    }

    // Suma dos matrices o una matriz por un número
    sumar(m) {
        let j;
        let i;
        if (m instanceof Matrix) {
            if (this.filas !== m.filas || this.columnas !== m.columnas) {
                throw new Error(
                    `No se puede sumar matrices: this. es una ${this.filas}×${this.columnas}, ` +
                    `y el argumento es una ${m.filas}×${m.columnas}`
                );
            } else {
                for (i = 0; i < this.filas; i++) {
                    for (j = 0; j < this.columnas; j++) {
                        this.datos[i][j] += m.datos[i][j];
                    }
                }
            }
        } else if (typeof m === "number") {
            for (i = 0; i < this.filas; i++) {
                for (j = 0; j < this.columnas; j++) {
                    this.datos[i][j] += m;
                }
            }
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix o un número para sumar.");
        }
    }

    // Resta dos matrices o una matriz por un número
    restar(m) {
        let j;
        let i;
        if (m instanceof Matrix) {
            if (this.filas !== m.filas || this.columnas !== m.columnas) {
                throw new Error(
                    `No se puede sumar matrices: this. es una ${this.filas}×${this.columnas}, ` +
                    `y el argumento es una ${m.filas}×${m.columnas}`
                );
            } else {
                for (i = 0; i < this.filas; i++) {
                    for (j = 0; j < this.columnas; j++) {
                        this.datos[i][j] -= m.datos[i][j];
                    }
                }
            }
        } else if (typeof m === "number") {
            for (i = 0; i < this.filas; i++) {
                for (j = 0; j < this.columnas; j++) {
                    this.datos[i][j] -= m;
                }
            }
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix o un número para restar.");
        }
    }

    // Multiplica dos matrices o una matriz por un número con ambos parámetros
    static multiplicarMatrices(m1, m2) { // m1 2x3 - m2 3x2
        let i;
        let j;
        if (m1 instanceof Matrix && m2 instanceof Matrix) {
            if (m1.columnas !== m2.filas) {
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
                const nuevaMatrix = new Matrix(m1.filas, m2.columnas);
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
        } else {
            throw new Error("Ambos argumentos deben ser instancias de Matrix o uno de ellos un número para multiplicar.");
        }
    }


    // Multiplica dos matrices o una matriz por un número sobre el mismo objeto .this
    multiplicar(m) {
        let i;
        let j;
        if (m instanceof Matrix) {
            if (this.columnas !== m.filas) {
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
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix o un número para multiplicar.");
        }
    }

    // Multiplica dos matrices elemento a elemento
    multiplicarMatrizElementwise(m) {
        let i;
        let j;
        if (m instanceof Matrix) {
            for (i = 0; i < this.filas; i++) {
                for (j = 0; j < this.columnas; j++) {
                    this.datos[i][j] *= m.datos[i][j];
                }
            }
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix para multiplicar elemento a elemento.");
        }
    }

    // Multiplica dos matrices elemento a elemento en una nueva instancia
    static multiplicarElementwise(m1, m2) {
        let i;
        let j;
        if (m1 instanceof Matrix && m2 instanceof Matrix) {
            if (m1.filas !== m2.filas || m1.columnas !== m2.columnas) {
                throw new Error("Las matrices no se pueden multiplicar: dimensiones incompatibles.");
            } else {
                const resultado = new Matrix(m1.filas, m1.columnas);
                for (i = 0; i < m1.filas; i++) {
                    for (j = 0; j < m1.columnas; j++) {
                        resultado.datos[i][j] = m1.datos[i][j] * m2.datos[i][j];
                    }
                }
                return resultado;
            }
        } else {
            throw new Error("Ambos argumentos deben ser instancias de Matrix para multiplicar elemento a elemento.");
        }
    }


    // Transpone la matriz original
    transponer() {
        if (this instanceof Matrix) {
            const resultado = Array.from({length: this.columnas}, () => Array(this.filas).fill(0));
            for (let i = 0; i < this.filas; i++) {
                for (let j = 0; j < this.columnas; j++) {
                    resultado[j][i] = this.datos[i][j];
                }
            }
            this.datos = resultado;
        } else {
            throw new Error("Debe ser una matriz para calcular su transpuesta");
        }
    }

    // Instancia la traspuesta de la matriz
    static transponerMatriz(m) {
        if (m instanceof Matrix) {
            const resultado = Array.from({length: m.columnas}, () => Array(m.filas).fill(0));
            for (let i = 0; i < m.filas; i++) {
                for (let j = 0; j < m.columnas; j++) {
                    resultado[j][i] = m.datos[i][j];
                }
            }
            // Crear y retornar una nueva instancia de Matrix
            const nuevaMatrix = new Matrix(m.columnas, m.filas);
            nuevaMatrix.datos = resultado;
            return nuevaMatrix;
        } else {
            throw new Error("Debe ser una matriz para calcular su transpuesta");
        }
    }

    print() {
        console.table(this.datos);
    }

    static print(m) {
        if (m instanceof Matrix) {
            console.table(m.datos);
        }
    }

    map(f) {
        for (let i = 0; i < this.filas; i++) {
            for (let j = 0; j < this.columnas; j++) {
                let val = this.datos[i][j];
                this.datos[i][j] = f(val);
            }
        }
    }

    static map(m, f) {
        let resultado = new Matrix(m.filas, m.columnas);
        for (let i = 0; i < m.filas; i++) {
            for (let j = 0; j < m.columnas; j++) {
                let val = m.datos[i][j];
                resultado.datos[i][j] = f(val);
            }
        }
        return resultado;
    }

}