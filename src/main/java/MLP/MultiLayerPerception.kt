package MLP

import MLP.funtionsActivation.FunctionActivation
import MLP.models.Layer

import java.util.stream.IntStream

class MultiLayerPerception(layers: IntArray, protected var fLearningRate: Double, protected var fFunctionActivation: FunctionActivation) {
    protected var fLayers: Array<Layer?>


    init {

        fLayers = arrayOfNulls(layers.size)

        IntStream.range(0, layers.size)
                .forEach { i ->
                    if (i != 0)
                        fLayers[i] = Layer(layers[i], layers[i - 1])
                    else
                        fLayers[i] = Layer(layers[i], 0)
                }
    }


    fun execute(input: DoubleArray): DoubleArray {

        val newValue = DoubleArray(1)

        val output = DoubleArray(fLayers[fLayers.size - 1]!!.length)

        IntStream.range(0, fLayers[0]!!.length)
                .forEach { i -> fLayers[0]!!.neurons[i]!!.value = input[i] }


        IntStream.range(1, fLayers.size)
                .forEach { k ->
                    IntStream.range(0, fLayers.get(k)!!.length)
                            .forEach { i ->
                                newValue[0] = 0.0
                                IntStream.range(0, fLayers[k - 1]!!.length)
                                        .forEach { j -> newValue[0] = newValue[0] + fLayers[k]!!.neurons[i]!!.weights[j] * fLayers[k - 1]!!.neurons[j]!!.value }
                                newValue[0] += fLayers[k]!!.neurons[i]!!.bias
                                fLayers[k]!!.neurons[i]!!.value = fFunctionActivation.evaluate(newValue[0])
                            }
                }


        IntStream.range(0, fLayers[fLayers.size - 1]!!.length)
                .forEach { i -> output[i] = fLayers[fLayers.size - 1]!!.neurons[i]!!.value }

        return output
    }


    fun backPropagate(input: DoubleArray, output: DoubleArray): Double {
        val newOutput = execute(input)
        val error = DoubleArray(1)

        IntStream.range(0, fLayers[fLayers.size - 1]!!.length)
                .forEach { i ->
                    error[0] = output[i] - newOutput[i]
                    fLayers[fLayers.size - 1]!!.neurons[i]!!.delta = error[0] * fFunctionActivation.evaluateDerivative(newOutput[i])
                }

        for (k in fLayers.size - 2 downTo 0) {
            IntStream.range(0, fLayers[k]!!.length)
                    .forEach { i ->
                        error[0] = 0.0
                        IntStream.range(0, fLayers[k + 1]!!.length)
                                .forEach { j -> error[0] = error[0] + fLayers[k + 1]!!.neurons[j]!!.delta * fLayers[k + 1]!!.neurons[j]!!.weights[i] }

                        fLayers[k]!!.neurons[i]!!.delta = error[0] * fFunctionActivation.evaluateDerivative(fLayers[k]!!.neurons[i]!!.value)
                    }
            IntStream.range(0, fLayers[k + 1]!!.length)
                    .forEach { i ->
                        IntStream.range(0, fLayers[k]!!.length)
                                .forEach { j ->
                                    fLayers[k + 1]!!.neurons[i]!!.weights[j] = fLayers[k + 1]!!.neurons[i]!!.weights[j] + fLearningRate * fLayers[k + 1]!!.neurons[i]!!.delta *
                                            fLayers[k]!!.neurons[j]!!.value
                                }
                        fLayers[k + 1]!!.neurons[i]!!.bias = fLayers[k + 1]!!.neurons[i]!!.bias + fLearningRate * fLayers[k + 1]!!.neurons[i]!!.delta
                    }
        }

        error[0] = 0.0
        IntStream.range(0, output.size)
                .forEach { i -> error[0] = error[0] + Math.abs(newOutput[i] - output[i]) }

        return error[0] / output.size
    }
}

