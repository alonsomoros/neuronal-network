package javamodelo.utils;

import java.util.Arrays;
import java.util.function.Function;

public class Matrix {
    private int filas; // Número de filas
    private int columnas; // Número de columnas
    private double[][] datos; // Datos de la matriz

    public Matrix(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.datos = new double[filas][columnas];
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                this.datos[i][j] = 0;
            }
        }
    }

    // Init prueba1
    public void initPrueba() {
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                this.datos[i][j] = i * this.filas + j + 1;
            }
        }
    }

    // Crea una matriz a partir de un array unidimensional
    public static Matrix fromIntArray(int[] array) {
        Matrix matriz = new Matrix(array.length, 1);
        for (int i = 0; i < array.length; i++) {
            matriz.datos[i][0] = array[i];
        }
        return matriz;
    }

    public static Matrix fromDoubleArray(Double[] array) {
        Matrix matriz = new Matrix(array.length, 1);
        for (int i = 0; i < array.length; i++) {
            matriz.datos[i][0] = array[i];
        }
        return matriz;
    }

    public static Matrix fromFloatArray(Float[] array) {
        Matrix matriz = new Matrix(array.length, 1);
        for (int i = 0; i < array.length; i++) {
            matriz.datos[i][0] = array[i];
        }
        return matriz;
    }

    public double[][] toArray() {
        double[][] array = new double[this.filas][this.columnas];
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                array[i][j] = this.datos[i][j];
            }
        }
        return array;
    }

    public Float[] toArrayUnidimensional() {
        Float[] matrixArray = new Float[this.filas * this.columnas];
        int index = 0;
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                matrixArray[index++] = (float) this.datos[i][j];
            }
        }
        return matrixArray;
    }

    // Randomiza la matriz
    public void randomizar() {
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                this.datos[i][j] = this.randomDel_menos1_1();
            }
        }
    }

    // Randomiza la matriz con instancia
    public static void randomizar(Matrix m) {
        if (m != null) {
            for (int i = 0; i < m.filas; i++) {
                for (int j = 0; j < m.columnas; j++) {
                    m.datos[i][j] = m.randomDel_menos1_1();
                }
            }
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix para randomizar.");
        }
    }

    public double randomDel_1_10() {
        return Math.floor(Math.random() * 10);
    }

    public double randomDel_menos1_1() {
        return Math.random() * 2 - 1;
    }

    // Suma dos matrices o una matriz por un número
    public static Matrix sumarMatrices(Matrix m1, Matrix m2) {
        int i, j;
        Matrix resultado = new Matrix(m1.filas, m1.columnas);
        if (m1.filas != m2.filas || m1.columnas != m2.columnas) {
            throw new Error("Las matrices no se pueden sumar: dimensiones incompatibles.");
        } else {
            for (i = 0; i < m1.filas; i++) {
                for (j = 0; j < m1.columnas; j++) {
                    resultado.datos[i][j] = m1.datos[i][j] + m2.datos[i][j];
                }
            }
            return resultado;
        }
    }

    public static Matrix sumarMatrices(Matrix m1, double m2) {
        int i;
        int j;
        Matrix resultado = new Matrix(m1.filas, m1.columnas);
        for (i = 0; i < m1.filas; i++) {
            for (j = 0; j < m1.columnas; j++) {
                resultado.datos[i][j] = m1.datos[i][j] + m2;
            }
        }
        return resultado;
    }

    // Resta dos matrices o una matriz por un número
    public static Matrix restarMatrices(Matrix m1, Matrix m2) {
        int i, j;
        Matrix resultado = new Matrix(m1.filas, m1.columnas);
        if (m1.filas != m2.filas || m1.columnas != m2.columnas) {
            throw new Error("Las matrices no se pueden sumar: dimensiones incompatibles.");
        } else {
            for (i = 0; i < m1.filas; i++) {
                for (j = 0; j < m1.columnas; j++) {
                    resultado.datos[i][j] = m1.datos[i][j] - m2.datos[i][j];
                }
            }
            return resultado;
        }
    }

    public static Matrix restarMatrices(Matrix m1, double m2) {
        int i, j;
        Matrix nuevaMatrix = new Matrix(m1.filas, m1.columnas);
        for (i = 0; i < m1.filas; i++) {
            for (j = 0; j < m1.columnas; j++) {
                nuevaMatrix.datos[i][j] = m1.datos[i][j] - m2;
            }
        }
        return nuevaMatrix;
    }

    // Suma dos matrices o una matriz por un número
    public void sumar(Matrix m) {
        int i, j;
        if (m != null) {
            if (this.filas != m.filas || this.columnas != m.columnas) {
                throw new Error(
                        "No se puede sumar matrices: this es una " + this.filas + "×" + this.columnas +
                                ", y el argumento es una " + m.filas + "×" + m.columnas
                );
            } else {
                for (i = 0; i < this.filas; i++) {
                    for (j = 0; j < this.columnas; j++) {
                        this.datos[i][j] += m.datos[i][j];
                    }
                }
            }
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix o un número para sumar.");
        }
    }

    public Matrix sumar(double m) {
        int i, j;
        for (i = 0; i < this.filas; i++) {
            for (j = 0; j < this.columnas; j++) {
                this.datos[i][j] += m;
            }
        }
        return this;
    }

    // Resta dos matrices o una matriz por un número
    public Matrix restar(Matrix m) {
        int i, j;
        if (m != null) {
            if (this.filas != m.filas || this.columnas != m.columnas) {
                throw new Error(
                        "No se puede sumar matrices: this es una " + this.filas + "×" + this.columnas +
                                ", y el argumento es una " + m.filas + "×" + m.columnas
                );
            } else {
                for (i = 0; i < this.filas; i++) {
                    for (j = 0; j < this.columnas; j++) {
                        this.datos[i][j] -= m.datos[i][j];
                    }
                }
            }
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix o un número para restar.");
        }
        return this;
    }

    public Matrix restar(double m) {
        int i, j;
        for (i = 0; i < this.filas; i++) {
            for (j = 0; j < this.columnas; j++) {
                this.datos[i][j] -= m;
            }
        }
        return this;
    }


    // Multiplica dos matrices o una matriz por un número con ambos parámetros
    public static Matrix multiplicarMatrices(Matrix m1, Matrix m2) { // m1 2x3 - m2 3x2
        if (m1 != null && m2 != null) {
            if (m1.columnas != m2.filas) {
                throw new Error("Las matrices no se pueden multiplicar: dimensiones incompatibles.");
            } else {
                double[][] resultado = new double[m1.filas][m2.columnas];
                for (int k = 0; k < m1.filas; k++) {
                    for (int h = 0; h < m2.columnas; h++) {
                        resultado[k][h] = 0;
                    }
                }

                for (int i = 0; i < m1.filas; i++) {
                    for (int j = 0; j < m2.columnas; j++) {
                        double suma = 0;
                        for (int k = 0; k < m1.columnas; k++) {
                            suma += m1.datos[i][k] * m2.datos[k][j];
                        }
                        resultado[i][j] = suma;
                    }
                }

                // Crear y retornar una nueva instancia de Matrix
                Matrix nuevaMatrix = new Matrix(m1.filas, m2.columnas);
                nuevaMatrix.datos = resultado;
                return nuevaMatrix;
            }
        } else {
            throw new Error("Ambos argumentos deben ser instancias de Matrix o uno de ellos un número para multiplicar.");
        }
    }

    public static Matrix multiplicarMatrices(Matrix m1, double m2) {
        Matrix nuevaMatrix = new Matrix(m1.filas, m1.columnas);
        for (int i = 0; i < m1.filas; i++) {
            for (int j = 0; j < m1.columnas; j++) {
                nuevaMatrix.datos[i][j] = m1.datos[i][j] * m2;
            }
        }
        return nuevaMatrix;
    }

    public static Matrix multiplicarMatrices(double m1, Matrix m2) {
        Matrix nuevaMatrix = new Matrix(m2.filas, m2.columnas);
        for (int i = 0; i < m2.filas; i++) {
            for (int j = 0; j < m2.columnas; j++) {
                nuevaMatrix.datos[i][j] = m2.datos[i][j] * m1;
            }
        }
        return nuevaMatrix;
    }


    // Multiplica dos matrices o una matriz por un número sobre el mismo objeto .this
    public Matrix multiplicar(Matrix m) {
        if (m != null) {
            if (this.columnas != m.filas) {
                throw new Error("Las matrices no se pueden multiplicar: dimensiones incompatibles.");
            } else {
                double[][] resultado = new double[m.filas][this.columnas];
                for (int k = 0; k < m.filas; k++) {
                    for (int h = 0; h < this.columnas; h++) {
                        resultado[k][h] = 0;
                    }
                }

                for (int i = 0; i < this.filas; i++) {
                    for (int j = 0; j < m.columnas; j++) {
                        double suma = 0;
                        for (int k = 0; k < this.columnas; k++) {
                            suma += this.datos[i][k] * m.datos[k][j];
                        }
                        resultado[i][j] = suma;
                    }
                }
                // Asignamos el resultado a datos y actualizamos columnas
                this.datos = resultado;
                this.columnas = m.columnas;
            }
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix o un número para multiplicar.");
        }
        return this;
    }

    public void multiplicar(double m) {
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                this.datos[i][j] *= m;
            }
        }
    }

    // Multiplica dos matrices elemento a elemento
    public void multiplicarMatrizElementwise(Matrix m) {
        if (m != null) {
            for (int i = 0; i < this.filas; i++) {
                for (int j = 0; j < this.columnas; j++) {
                    this.datos[i][j] *= m.datos[i][j];
                }
            }
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix para multiplicar elemento a elemento.");
        }
    }

    // Multiplica dos matrices elemento a elemento en una nueva instancia
    public static Matrix multiplicarElementwise(Matrix m1, Matrix m2) {
        if (m1 != null && m2 != null) {
            if (m1.filas != m2.filas || m1.columnas != m2.columnas) {
                throw new Error("Las matrices no se pueden multiplicar: dimensiones incompatibles.");
            } else {
                Matrix resultado = new Matrix(m1.filas, m1.columnas);
                for (int i = 0; i < m1.filas; i++) {
                    for (int j = 0; j < m1.columnas; j++) {
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
    public void transponer() {
        if (this.filas == 1 && this.columnas == 1) {
            return;
        }
        double[][] resultado = new double[this.filas][this.columnas];
        for (int k = 0; k < this.filas; k++) {
            for (int h = 0; h < this.columnas; h++) {
                resultado[k][h] = 0;
            }
        }
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                resultado[j][i] = this.datos[i][j];
            }
        }
        this.datos = resultado;
    }

    // Instancia la traspuesta de la matriz
    public static Matrix transponerMatriz(Matrix m) {
        if (m != null) {
            if (m.filas == 1 && m.columnas == 1) {
                return m;
            }
            double[][] resultado = new double[m.columnas][m.filas];
            for (int k = 0; k < m.columnas; k++) {
                for (int h = 0; h < m.filas; h++) {
                    resultado[k][h] = 0;
                }
            }

            for (int i = 0; i < m.filas; i++) {
                for (int j = 0; j < m.columnas; j++) {
                    resultado[j][i] = m.datos[i][j];
                }
            }
            // Crear y retornar una nueva instancia de Matrix
            Matrix nuevaMatrix = new Matrix(m.columnas, m.filas);
            nuevaMatrix.datos = resultado;
            return nuevaMatrix;
        } else {
            throw new Error("Debe ser una matriz para calcular su transpuesta");
        }
    }

    public static float sumaElementosMatriz(Matrix m) {
        if (m != null) {
            float suma = 0;
            for (int i = 0; i < m.filas; i++) {
                for (int j = 0; j < m.columnas; j++) {
                    suma += (float) m.datos[i][j];
                }
            }
            return suma;
        } else {
            throw new Error("El argumento debe ser una instancia de Matrix para sumar sus elementos.");
        }
    }

    public void print() {
        for (double[] fila : this.datos) {
            System.out.println(Arrays.toString(fila));
        }
    }

    public static void print(Matrix m) {
        if (m != null) {
            for (double[] fila : m.datos) {
                System.out.println(Arrays.toString(fila));
            }
        }
    }

    public void mapFloat(Function<Float, Float> f) {
        // this.map(val -> val * 2);
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                float val = (float) this.datos[i][j];
                this.datos[i][j] = f.apply(val);
            }
        }
    }

    public void mapDouble(Function<Double, Double> f) {
        // this.map(val -> val * 2);
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                double val = (float) this.datos[i][j];
                this.datos[i][j] = f.apply(val);
            }
        }
    }

    // Matrix = Matrix.map(m, val -> val * 2);
    public static Matrix mapDouble(Matrix m, Function<Double, Double> f) {
        Matrix resultado = new Matrix(m.filas, m.columnas);
        for (int i = 0; i < m.filas; i++) {
            for (int j = 0; j < m.columnas; j++) {
                double val = m.datos[i][j];
                resultado.datos[i][j] = f.apply(val);
            }
        }
        return resultado;
    }

    public static Matrix mapFloat(Matrix m, Function<Float, Float> f) {
        Matrix resultado = new Matrix(m.filas, m.columnas);
        for (int i = 0; i < m.filas; i++) {
            for (int j = 0; j < m.columnas; j++) {
                float val = (float) m.datos[i][j];
                resultado.datos[i][j] = f.apply(val);
            }
        }
        return resultado;
    }
    public void mapArrayFunction(Function<Float[], Float[]> arrayFunction) {
        // Convert matrix to array
        Float[] matrixArray = this.toArrayUnidimensional();

        // Apply function
        Float[] resultArray = arrayFunction.apply(matrixArray);

        // Convert back to matrix
        Matrix resultado = new Matrix(this.filas, this.columnas);
        int index = 0;
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                resultado.datos[i][j] = resultArray[index++];
            }
        }

    }

    public static Matrix mapArrayFunction(Matrix m, Function<Float[], Float[]> arrayFunction) {
        // Convert matrix to array
        Float[] matrixArray = m.toArrayUnidimensional();

        // Apply function
        Float[] resultArray = arrayFunction.apply(matrixArray);

        // Convert back to matrix
        Matrix resultado = new Matrix(m.filas, m.columnas);
        int index = 0;
        for (int i = 0; i < m.filas; i++) {
            for (int j = 0; j < m.columnas; j++) {
                resultado.datos[i][j] = resultArray[index++];
            }
        }

        return resultado;
    }


    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public double[][] getDatos() {
        return datos;
    }

    public void setDatos(double[][] datos) {
        this.datos = datos;
    }
}
