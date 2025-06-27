let datos_entrenamiento = [ // XOR
    { entradas: [0, 0], objetivo: [0] },
    { entradas: [0, 1], objetivo: [1] },
    { entradas: [1, 0], objetivo: [1] },
    { entradas: [1, 1], objetivo: [0] }
];

let datos_test = [ // XOR
    { entradas: [0, 0], objetivo: [0] },
    { entradas: [0, 1], objetivo: [1] },
    { entradas: [1, 0], objetivo: [1] },
    { entradas: [1, 1], objetivo: [0] }
];

let epochs = 10000;

function setup() {
    // pruebaMatrices();
    pruebaRedNeuronal();
}

function draw() {

}

function pruebaRedNeuronal() {
    let redNeuronal = new RedNeuronal(2, 2, 1);
    for (let i = 0; i < epochs; i++) {
        for (let data of datos_entrenamiento) {
            redNeuronal.entrenar(data.entradas, data.objetivo);
        }
    }

    console.table(redNeuronal.feedforward([0, 0]));
    console.table(redNeuronal.feedforward([0, 1]));
    console.table(redNeuronal.feedforward([1, 0]));
    console.table(redNeuronal.feedforward([1, 1]));
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
    duplicada.map(this.duplicar);
    duplicada.print();
}
