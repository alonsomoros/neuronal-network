package javamodelo.pruebas;

public class Prueba_XOR {
    public final int NUM_NEURONAS = 5;
    public final int EPOCHS = 100;
    public final int NUM_CAPAS_ENTRADAS = 2;
    public final int NUM_CAPAS_OCULTAS = 4;
    public final int NUM_CAPAS_SALIDAS = 1;
    public final int BATCH_SIZE = 50;

    public Float[][][] datos_entrenamiento_XOR = {
            {{0f, 0f}, {0f}},
            {{0f, 1f}, {1f}},
            {{1f, 0f}, {1f}},
            {{1f, 1f}, {0f}}
    };

    public Float[][][] datos_test_XOR = {
            {{0f, 0f}, {0f}},
            {{0f, 1f}, {1f}},
            {{1f, 0f}, {1f}},
            {{1f, 1f}, {0f}}
    };

    public Prueba_XOR() {
    }
}
