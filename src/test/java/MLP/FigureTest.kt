package MLP

import MLP.funtionsActivation.SigmoidActivation
import MLP.util.FileUtil
import MLP.util.ImageRecognitionUtil
import org.junit.Assert
import org.junit.Test

import java.util.stream.IntStream

class FigureTest {

    @Test
    fun recognizeTriangle() {
        val inputs = ImageRecognitionUtil.loadImage(FileUtil.getFilesPath("img/tests/3.png")!!, SIZE_IMAGE_PIXELS_WIDTH, SIZE_IMAGE_PIXELS_HEIGHT)
        val result = TestPrecision(BATCH_SIZE, inputs)
        Assert.assertTrue(result == 1.0)
    }

    @Test
    fun recognizeSquare() {
        val inputs = ImageRecognitionUtil.loadImage(FileUtil.getFilesPath("img/tests/2.png")!!, SIZE_IMAGE_PIXELS_WIDTH, SIZE_IMAGE_PIXELS_HEIGHT)
        val result = TestPrecision(BATCH_SIZE, inputs)
        Assert.assertTrue(result == 3.0 || result == 2.0)
    }

    @Test
    fun recognizeCircle() {
        val inputs = ImageRecognitionUtil.loadImage(FileUtil.getFilesPath("img/tests/1.png")!!, SIZE_IMAGE_PIXELS_WIDTH, SIZE_IMAGE_PIXELS_HEIGHT)
        val result = TestPrecision(BATCH_SIZE, inputs)
        Assert.assertTrue(result == 2.0)
    }

    companion object {

        private val BATCH_SIZE = 10000
        private val SIZE_HIDDEN_LAYERS = 7
        private val SIZE_IMAGE_PIXELS_WIDTH = 7
        private val SIZE_IMAGE_PIXELS_HEIGHT = 7
        private val SIZE_INPUT = 49
        private val SIZE_OUTPUT = 3

        fun TestPrecision(iteration: Int, inputForTest: DoubleArray): Double {
            val ERROR = DoubleArray(1)
            val layers = intArrayOf(SIZE_INPUT, SIZE_HIDDEN_LAYERS, SIZE_OUTPUT)
            val multiLayerPerception = MultiLayerPerceptron(layers, 0.6, SigmoidActivation())

            IntStream.range(0, iteration)
                    .forEach { i ->
                        IntStream.range(1, SIZE_OUTPUT)
                                .forEach { k ->
                                    val pattern = FileUtil.getFilesPath("img/patterns/$k.png")
                                    val inputs = ImageRecognitionUtil.loadImage(pattern!!, SIZE_IMAGE_PIXELS_WIDTH, SIZE_IMAGE_PIXELS_HEIGHT)
                                    if (inputs == null)
                                        println("Cant find $pattern")
                                    val output = DoubleArray(SIZE_OUTPUT)
                                    IntStream.range(0, SIZE_OUTPUT)
                                            .forEach { ind -> output[ind] = 0.0 }
                                    output[k - 1] = 1.0
                                    ERROR[0] = multiLayerPerception.backPropagate(inputs, output)
                                    println("Error is " + ERROR[0] + " (" + i + " " + " " + k + " )")
                                }
                    }


            val output = multiLayerPerception.execute(inputForTest)

            val max = intArrayOf(0)
            IntStream.range(0, SIZE_OUTPUT)
                    .forEach { i ->
                        if (output[i] > output[max[0]])
                            max[0] = 1
                    }

            println("Recognize value is ' " + output[max[0]] + " pattern " + (max[0] + 1))
            return (max[0] + 1).toDouble()
        }
    }

}
