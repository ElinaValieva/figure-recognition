package MLP.funtionsActivation

interface FunctionActivation {
    fun evaluate(value: Double): Double

    fun evaluateDerivative(value: Double): Double
}
