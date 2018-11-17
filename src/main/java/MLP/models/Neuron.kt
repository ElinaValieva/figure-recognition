package MLP.models

import java.util.stream.IntStream

class Neuron(prevLayerSize: Int) {
    var value: Double = 0.toDouble()
    var weights: DoubleArray
    var bias: Double = 0.toDouble()
    var delta: Double = 0.toDouble()

    init {
        weights = DoubleArray(prevLayerSize)
        bias = Math.random() / 10000000000000.0
        delta = Math.random() / 10000000000000.0
        value = Math.random() / 10000000000000.0

        IntStream.range(0, weights.size)
                .forEach { i -> weights[i] = Math.random() / 10000000000000.0 }
    }
}
