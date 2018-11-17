package MLP.models

import java.util.stream.IntStream

class Layer(var length: Int, prev: Int) {
    var neurons: Array<Neuron?>

    init {
        neurons = arrayOfNulls(length)

        IntStream.range(0, length)
                .forEach { i -> neurons[i] = Neuron(prev) }
    }
}
