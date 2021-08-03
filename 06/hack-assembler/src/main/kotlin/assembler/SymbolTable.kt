package assembler

class SymbolTable {

    private val symbolTable: MutableMap<String, Short> = mutableMapOf(
        "SP" to 0,
        "LCL" to 1,
        "ARG" to 2,
        "THIS" to 3,
        "THAT" to 4,
        "SCREEN" to 16384,
        "KBD" to 24576
    )
    private var entryCount: Short = 16

    init {
        assignPredefinedRegisters()
    }

    private fun assignPredefinedRegisters() {
        for (index in 0..16) {
            symbolTable["R$index"] = index.toShort()
        }
    }

    fun setLabelAddress(label: String, programCount: Short) {
        symbolTable[label.removeSurrounding("(", ")")] = programCount
    }

    fun provideVariableAddress(symbol: String): Short {
        if (!symbolTable.containsKey(symbol)) symbolTable[symbol] = entryCount++
        return requireNotNull(symbolTable[symbol])
    }
}
