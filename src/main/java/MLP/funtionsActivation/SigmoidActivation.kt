package MLP.funtionsActivation

class SigmoidActivation : FunctionActivation {

    override fun evaluate(value: Double): Double {
        return 1 / (1 + Math.pow(Math.E, -value))
    }

    override fun evaluateDerivative(value: Double): Double {
        return value - Math.pow(value, 2.0)
    }
}

