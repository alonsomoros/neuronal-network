package javamodelo.pruebas;

public class Data {
    protected Float[] entradas;
    protected Float[] objetivos;

    public Data(Float[] entradas, Float[] objetivos) {
        this.entradas = entradas;
        this.objetivos = objetivos;
    }

    public Float[] getEntradas() {
        return entradas;
    }
    public Float[] getObjetivos() {
        return objetivos;
    }
    public void setEntradas(Float[] entradas) {
        this.entradas = entradas;
    }
    public void setObjetivos(Float[] objetivos) {
        this.objetivos = objetivos;
    }
}
