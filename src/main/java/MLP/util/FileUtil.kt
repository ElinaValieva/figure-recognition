package MLP.util

import mu.KLogging


class FileUtil {
    companion object : KLogging() {
        override val logger = logger()

        fun getFilesPath(fileName: String): String? {
            return System.getProperty("user.dir") + "/" + fileName
        }
    }
}
