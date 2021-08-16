package com.github.poluka.kControlLibrary

import com.github.poluka.kControlLibrary.program.Program

class CustomCommand (private val command: String): Command {
    override fun generateCommand(): String {
        return command
    }
}

fun Program.CUSTOM_PROGRAM(command: String) = this.add(CustomCommand(command))