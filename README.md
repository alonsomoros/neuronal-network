# 🧠 Red Neuronal en Java

Este repositorio contiene una implementación desde cero de una red neuronal en Java. Comienza con perceptrones simples y evoluciona hacia redes multicapa, con el objetivo de experimentar con funciones de activación, entrenamiento, y más adelante, construir un entorno donde mini entidades aprendan a través de estas redes.

---

## 🚀 Objetivos del proyecto

- Implementar perceptrones simples.
- Probar distintas funciones de activación (ReLU, Sigmoid, Tanh, etc.).
- Construir arquitecturas más complejas (redes multicapa, feedforward...).
- Entrenamiento con y sin retropropagación.
- Resolver tareas simples (XOR, clasificación binaria...).
- En etapas futuras: crear un entorno de mini agentes que aprendan.

---

## 🛠️ Tecnologías utilizadas

- **Lenguaje:** Java
- **IDE:** IntelliJ IDEA
- **Dependencias:** Ninguna (implementación 100% desde cero)

---

## 📁 Estructura del repositorio

```
/src
└── neuralnet
├── Neuron.java
├── Perceptron.java
├── ActivationFunction.java
├── NeuralNetwork.java
└── utils/
└── MatrixUtils.java
```

---

## ✅ Estado actual

- [x] Perceptrón simple funcionando
- [ ] Funciones de activación personalizables
- [ ] Red neuronal multicapa
- [ ] Algoritmo de retropropagación
- [ ] Simulación de agentes que aprenden

---

## 🧪 Ejemplo de uso (provisional)

```java
double[] input = {1.0, 0.0};
Perceptron p = new Perceptron(input.length);
double output = p.feedForward(input);
System.out.println("Resultado: " + output);
```

## 💡 Ideas futuras

- Visualización del aprendizaje (gráficas, evolución del error...)

- Interfaz gráfica (JavaFX)

- Integración con entornos simulados

- Aprendizaje por refuerzo básico

- Algoritmos evolutivos

