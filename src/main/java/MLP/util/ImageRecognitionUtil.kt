package MLP.util

import mu.KLogging
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.stream.IntStream
import javax.imageio.ImageIO

class ImageRecognitionUtil {

    companion object : KLogging() {
        override val logger = logger()

        fun loadImage(path: String, sizeWidth: Int, sizeHeight: Int): DoubleArray {
            val imgLoc = File(path)
            val data = DoubleArray(sizeWidth * sizeHeight)
            try {
                val img = ImageIO.read(imgLoc)
                val bi = BufferedImage(
                        sizeWidth,
                        sizeHeight,
                        BufferedImage.TYPE_BYTE_GRAY)
                val g = bi.createGraphics()
                g.drawImage(img, 0, 0, null)
                g.dispose()

                IntStream.range(0, sizeWidth)
                        .forEach { i ->
                            IntStream.range(0, sizeHeight)
                                    .forEach { j ->
                                        val d = IntArray(3)
                                        bi.raster.getPixel(i, j, d)
                                        if (d[0] > 128)
                                            data[i * sizeWidth + j] = 0.0
                                        else
                                            data[i * sizeWidth + j] = 1.0
                                    }
                        }
            } catch (ex: IOException) {
                logger.error("$path not loaded")
            }

            return data
        }
    }
}
