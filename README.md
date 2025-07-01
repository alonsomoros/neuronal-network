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

- **Lenguaje:** Java y JavaScript
- **IDE:** IntelliJ IDEA
- **Dependencias:** Java processing (p5)

---

## 📁 Estructura del repositorio

```
/src
├── javamodelo
│   ├── Main.java
│   ├── Perceptron.java
│   ├── Punto.java
│   ├── RedNeuronal.java
│   ├── Matrix.java
│   └── FuncionesDeActivacion.java
│
├── js
│   ├── index.html
│   ├── matrix.js
│   ├── RedNeuronal.js
│   ├── sketch.js
│   └── pruebaMatrices.js
/rec
└── esquema_red_neuronal.png
```

---

## ✅ Estado actual

- [x] Perceptrón simple funcionando
- [x] Funciones de activación personalizables
- [x] Red neuronal multicapa
- [x] Algoritmo de retropropagación
- [x] Simulación de agentes que aprenden

---

## 🧪 Funcionamiento y fórmulas

![img.png](rec/esquema_red_neuronal.png)

## 📊 Prueba

#### Descargar en Google Drive:
- [red_neuronal-1.0.jar](https://drive.google.com/file/d/1J6UBHd_In5-A8cpG_sAto0-rRcQJQh-b/view?usp=drive_link)
- [red_neuronal-1.0.zip](https://drive.google.com/file/d/1ZfCAtxRAkUYRgs2b0cimTeczp6Jlz8Kh/view?usp=drive_link)


Coloca el archivo `red_neuronal-1.0.jar` en el directorio donde ejecutarás el comando y utiliza:

```shell
java -jar red_neuronal-1.0.jar
```

## 💡 Ideas futuras

- Visualización del aprendizaje (gráficas, evolución del error...)

- Interfaz gráfica (Processing p5)

- Integración con entornos simulados

- Aprendizaje por refuerzo básico

- Algoritmos evolutivos

