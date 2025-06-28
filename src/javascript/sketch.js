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

let epochs = 100000;

let ta_slider;

function setup() {
    createCanvas(400, 400);

    redNeuronal = new RedNeuronal(2, 4, 1);
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
        let data = random(datos_entrenamiento);
        redNeuronal.entrenar(data.entradas, data.objetivo);
    }


    for (let data of datos_test) {
        console.log(`Entradas: ${data.entradas}, Objetivo: ${data.objetivo}`);
        console.table(redNeuronal.predict(data.entradas));
    }
}
