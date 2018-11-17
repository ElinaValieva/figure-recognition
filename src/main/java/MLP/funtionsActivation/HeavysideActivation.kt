package MLP.funtionsActivation


class HeavysideActivation : FunctionActivation {

    override fun evaluate(value: Double): Double {
        return if (value >= 0.0) 1.0 else 0.0
    }

    override fun evaluateDerivative(value: Double): Double {
        return 1.0
    }

}
