package util

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.writeLines

class HackFileIO {

    fun loadAsmFile(path: String): List<String> {
        return File(path).bufferedReader().readLines()
    }

    fun writeHackFile(contents: List<String>, path: Path) {
        if (Files.exists(path)) {
            Files.delete(path)
        }
        path.writeLines(contents)
    }
}
