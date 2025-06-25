var brain;

function setup() {
    // pruebaMatrices();
    pruebaRedNeuronal();
}

function draw() {

}

function duplicar(x) {
    return x * 2;
}

function duplicarDiagonal(x, fila, columna) {
    if (fila === columna) {
        return x * 2;
    }
}

function pruebaRedNeuronal() {
    let redNeuronal = new RedNeuronal(4, 2, 1);
    let entradas = [1, 0, 0, 1];
    let salidas = redNeuronal.feedforward(entradas);
    console.log("Para entradas: " + entradas + ", la salida es: " + salidas);
}

function pruebaMatrices() {
    // << Multiplicación de matrices por número y matriz en variable >>
    let a = new Matrix(4, 1);
    let b = new Matrix(1, 3);

    a.randomizar();
    b.randomizar();
    a.print();
    b.print();

    let multiplicacionPorNumero = Matrix.multiplicarMatrices(a, 2);
    let multiplicacionPorMatriz = Matrix.multiplicarMatrices(a, b);
    multiplicacionPorNumero.print();
    multiplicacionPorMatriz.print();

    // << Multiplicación de matrices en propia instancia >>
    let d = new Matrix(4, 1);
    let e = new Matrix(1, 3);
    d.randomizar();
    e.randomizar();
    d.print();
    e.print();

    d.multiplicar(e);
    d.print();

    // << Multiplicación de matriz por un número en propia instancia >>
    let f = new Matrix(3, 3);
    f.randomizar();
    f.print();

    f.multiplicar(2)
    f.print();


    // << Suma de matrices en variable >>
    let g = new Matrix(5, 4);
    let h = new Matrix(5, 4);
    g.randomizar();
    h.randomizar();
    g.print();
    h.print();
    let suma = Matrix.sumarMatrices(g, h);
    suma.print();

    // << Suma de matrices en propia instancia >>
    let i = new Matrix(4, 4);
    let j = new Matrix(4, 4);
    i.randomizar();
    j.randomizar();
    i.print();
    j.print();
    i.sumar(j);
    i.print();

    // << Suma de matriz por un número en propia instancia >>
    let k = new Matrix(2, 2);
    k.randomizar();
    k.print();
    k.sumar(10);
    k.print();

    // << Transposición de matrices en variable >>
    let l = new Matrix(2, 3);
    l.randomizar();
    l.print();

    let traspuestaVariable = Matrix.transponerMatriz(l);
    traspuestaVariable.print();

    // << Transposición de matrices en propia instancia >>
    let m = new Matrix(2, 3);
    m.randomizar();
    m.print();

    let traspuestaPropia = Matrix.transponerMatriz(m);
    traspuestaPropia.print();

    let duplicada = new Matrix(2, 2);
    duplicada.randomizar();
    duplicada.print();
    duplicada.map(duplicar);
    duplicada.print();
}
