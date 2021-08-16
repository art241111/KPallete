package com.github.poluka.kControlLibrary.`while`

import com.github.poluka.kControlLibrary.CUSTOM_PROGRAM
import com.github.poluka.kControlLibrary.program.Program
import com.github.poluka.kControlLibrary.program.motion

class WhileSignal(private val signal: Int, private val subProgram: Program.() -> Unit) {
    fun getProgram(): Program {
        return motion {
            CUSTOM_PROGRAM("WHILE SIG($signal) DO")
            this add Program("").apply(subProgram).build()
            CUSTOM_PROGRAM("END")
        }
    }
}

fun Program.WHILE_SIGNAL(signal: Int, commands: Program.() -> Unit) =
    this.add(WhileSignal(signal, commands).getProgram())