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

let redNeuronal;

let batchSize = 100; // Tamaño del batch para el entrenamiento
let epochs = 100000;
let filtro_epochs = 10000; // Cada cuántas epochs se imprime el resultado

let ta_slider;

function setup() {
    createCanvas(400, 400);

    redNeuronal = new RedNeuronal(2, 2, 1);
    redNeuronal.setTasaAprendizaje(0.01);
    pruebaRedNeuronal();

    // ta_slider = createSlider(0, 0.5, 0.1, 0.01);
    // redNeuronal.setTasaAprendizaje(ta_slider.value())
    // pruebaMatrices.prototype.test();
}

function draw() {
    background(0);

    let resolution = 10;
    let cols = width / resolution;
    let rows = height / resolution;
    for (let i = 0; i < cols; i++) {
        for (let j = 0; j < rows; j++) {
            let x = i / cols;
            let y = j / rows;
            let inputs = [x, y];
            let prediction = redNeuronal.predict(inputs);
            stroke(0);
            fill(prediction * 255);
            rect(i * resolution, j * resolution, resolution, resolution);
        }
    }
}

function pruebaRedNeuronal() {
    for (let i = 0; i < epochs; i++) {
        let batch = [];
        for (let j = 0; j < batchSize; j++) { // Tamaño del batch
            batch.push(random(datos_entrenamiento));
        }
        let sumMSE = 0;
        let sumCorrectos = 0;
        for (let data of batch) {
            let resultadoEntrenamiento = redNeuronal.entrenar(data.entradas, data.objetivo); // MSE y Correctos
            sumCorrectos += resultadoEntrenamiento[1];
            sumMSE += resultadoEntrenamiento[0];
        }
        if (i % filtro_epochs === 0) {
            console.log(`Epoch: ${i}, MSE: ${sumMSE / batchSize}\nAccuracy: ${sumCorrectos / batchSize}\nCorrectos: ${sumCorrectos} de ${batchSize}`);
        }
    }


    for (let data of datos_test) {
        console.log(`Entradas: ${data.entradas}, Objetivo: ${data.objetivo}`);
        console.table(redNeuronal.predict(data.entradas));
    }
}
