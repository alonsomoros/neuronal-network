let datos_entrenamiento = [ // XOR
    {entradas: [0, 0], objetivo: [0]},
    {entradas: [0, 1], objetivo: [1]},
    {entradas: [1, 0], objetivo: [1]},
    {entradas: [1, 1], objetivo: [0]}
];

let datos_test = [ // XOR
    {entradas: [0, 0], objetivo: [0]},
    {entradas: [0, 1], objetivo: [1]},
    {entradas: [1, 0], objetivo: [1]},
    {entradas: [1, 1], objetivo: [0]}
];

let epochs = 100000;

function setup() {
    // pruebaMatrices.prototype.test();
    pruebaRedNeuronal();
}

function draw() {

}

function pruebaRedNeuronal() {
    let redNeuronal = new RedNeuronal(2, 2, 1);
    for (let i = 0; i < epochs; i++) {
        let data = random(datos_entrenamiento);
        redNeuronal.entrenar(data.entradas, data.objetivo);
    }


    for (let data of datos_test) {
        console.log(`Entradas: ${data.entradas}, Objetivo: ${data.objetivo}`);
        console.table(redNeuronal.feedforward(data.entradas));
    }
}
