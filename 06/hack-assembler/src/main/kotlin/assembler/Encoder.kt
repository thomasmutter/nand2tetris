package assembler

class Encoder {

    fun encodeAInstruction(instruction: Short): String = "0" + encodeAddress(instruction)

    fun encodeCInstruction(comp: String, dest: String, jump: String): String = "111" + encodeComp(comp) + encodeDest(dest) + encodeJump(jump)

    private fun encodeJump(jump: String): String {
        val bits = requireNotNull(JUMP_MAP[jump])
        return padZeroes(bits.toString(2), JUMP_LENGTH)
    }

    private fun encodeDest(dest: String): String {
        val bits = requireNotNull(DEST_MAP[dest])
        return padZeroes(bits.toString(2), DEST_LENGTH)
    }

    private fun encodeComp(comp: String): String {
        val bits = requireNotNull(COMP_MAP[comp])
        return padZeroes(bits.toString(2), COMP_LENGTH)
    }

    private fun encodeAddress(address: Short): String = padZeroes(address.toString(2), ADDRESS_LENGTH)

    private fun padZeroes(bitString: String, responseLength: Int): String {
        val numberOfPadZeroes = responseLength - bitString.length
        return "0".repeat(numberOfPadZeroes).plus(bitString)
    }

    companion object {
        private val JUMP_MAP: Map<String, Byte> = mapOf(
            ""    to 0, "JGT" to 1,
            "JEQ" to 2, "JGE" to 3,
            "JLT" to 4, "JNE" to 5,
            "JLE" to 6, "JMP" to 7
        )
        private val DEST_MAP: Map<String, Byte> = mapOf(
            "" to 0, "M" to 1,
            "D" to 2, "MD" to 3,
            "A" to 4, "AM" to 5,
            "AD" to 6, "AMD" to 7
        )
        private val COMP_MAP: Map<String, Byte> = mapOf(
            "0" to 42, "1" to 63, "-1" to 58,
            "D" to 12, "M" to 112, "!D" to 13,
            "!M" to 113, "-D" to 15, "-M" to 115,
            "D+1" to 31, "M+1" to 119, "D-1" to 14,
            "M-1" to 114, "D+M" to 66, "D-M" to 83,
            "M-D" to 71, "D&M" to 64, "D|M" to 85,
            "A" to 48, "!A" to 49, "-A" to 51,
            "A+1" to  55, "A-1" to 50, "D+A" to 2,
            "D-A" to 19, "A-D" to 7, "D&A" to 0,
            "D|A" to 21
        )
        private const val COMP_LENGTH = 7
        private const val DEST_LENGTH = 3
        private const val JUMP_LENGTH = 3
        private const val ADDRESS_LENGTH = 15
    }
}
