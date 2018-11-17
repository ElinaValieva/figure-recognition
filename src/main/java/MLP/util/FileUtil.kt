package MLP.util

import mu.KLogging
import org.apache.commons.io.IOUtils
import java.io.IOException


class FileUtil {
    companion object : KLogging() {
        override val logger = logger()

        fun getFilesPath(fileName: String): String? {
            var path: String? = null
            try {
                path = IOUtils.toString(FileUtil::class.java!!
                        .getClassLoader()
                        .getResourceAsStream(fileName))
            } catch (e: IOException) {
                logger.error(e.message)
            }

            return path
        }
    }
}
