package com.github.poluka.kControlLibrary.editorCommands

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Exits from the editor mode and returns to monitor mode.
 */
class Exit() : Command {
    override fun generateCommand(): String {
        return "E"
    }
}

fun Program.E() = this.add(Exit())