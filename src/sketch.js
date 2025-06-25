var brain;

function setup() {

    let a = new Matrix(2, 3);
    let b = new Matrix(3, 2);

    a.randomizar()
    b.randomizar()

    console.table(a.datos);
    console.table(b.datos);

    let c = a.multiplicar(a,b);

    console.table(c.datos);
}

function draw() {

}