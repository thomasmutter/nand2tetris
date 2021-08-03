package application

import assembler.Assembler
import assembler.Encoder
import assembler.SymbolTable
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import util.HackFileIO

class CLI: CliktCommand() {
    private val path by argument()
    private val assembler: Assembler = Assembler(
        Encoder(),
        SymbolTable(),
        HackFileIO()
    )

    override fun run() {
        assembler.assemble(path)
    }

}


fun main(args: Array<String>) = CLI().main(args)
