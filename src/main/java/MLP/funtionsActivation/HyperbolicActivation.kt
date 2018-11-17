package MLP.funtionsActivation

class HyperbolicActivation : FunctionActivation {

    override fun evaluate(value: Double): Double {
        return Math.tanh(value)
    }

    override fun evaluateDerivative(value: Double): Double {
        return 1 - Math.pow(value, 2.0)
    }
}
