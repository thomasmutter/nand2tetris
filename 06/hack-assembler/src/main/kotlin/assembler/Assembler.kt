package assembler

import util.HackFileIO
import java.nio.file.Paths

class Assembler(
    private val encoder: Encoder,
    private val symbolTable: SymbolTable,
    private val hackFileIO: HackFileIO
) {

    private val aRegex = "(@)(.*)".toRegex()
    private val cRegex = "([ADM]{1,3})?=?([AMD01&!|+-]{1,3});?(J\\w{1,2})?".toRegex()
    private val labelRegex = "^\\(.+\\)\$".toRegex()

    private var labelOffset = 0

    fun assemble(path: String) {
        val code = hackFileIO.loadAsmFile(path)
        storeLoopVariables(code)
        val translatedCode = translateInstructions(code)
        hackFileIO.writeHackFile(translatedCode, Paths.get(path.replace(".asm", ".hack")))
    }

    private fun storeLoopVariables(code: List<String>) {
        code
            .map { it.trim() }
            .filter { !it.startsWith("//") && it.isNotEmpty() }
            .forEachIndexed { index, instruction ->
                if (labelRegex.containsMatchIn(instruction)) {
                    symbolTable.setLabelAddress(labelRegex.find(instruction)!!.value, (index - labelOffset).toShort())
                    labelOffset++
                }
            }
    }

    private fun translateInstructions(code: List<String>): List<String> {
        val translatedCode = mutableListOf<String>()
        code
            .map { it.trim() }
            .filter { !it.startsWith("//") && !labelRegex.containsMatchIn(it)}
            .forEach { it ->
                val instruction = when {
                    aRegex.containsMatchIn(it) -> convertAInstruction(it)
                    cRegex.containsMatchIn(it) -> convertCInstruction(it)
                    else -> null
                }
                instruction?.let { translatedCode.add(it)}
            }
        return translatedCode
    }

    private fun convertAInstruction(instruction: String): String {
        val addressString: String = aRegex.find(instruction)!!.destructured.component2()
        val address = if (!addressString.matches("^[0-9]+".toRegex())) {
            symbolTable.provideVariableAddress(addressString)
        } else {
            addressString.toShort()
        }
        return encoder.encodeAInstruction(address)
    }

    private fun convertCInstruction(instruction: String): String {
        val (dest, comp, jump) = cRegex.find(instruction)!!.destructured
        return encoder.encodeCInstruction(comp, dest, jump)
    }
}
