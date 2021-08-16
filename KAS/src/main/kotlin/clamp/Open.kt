package com.github.poluka.kControlLibrary.clamp

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Opens robot clamps (outputs clamp open signal).
 * With the OPEN instruction, the signal is not output until the next motion starts.
 *
 * @param clampNumber - Specifies the number of the clamp. If omitted, 1 is assumed
 */
class Open(private val clampNumber: Int? = null) : Command {
    override fun generateCommand(): String {
        return "OPEN ${clampNumber ?: ""}"
    }
}

fun Program.OPEN(clampNumber: Int? = null) = this.add(Close(clampNumber))