package com.github.poluka.kControlLibrary.clamp

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Closes robot clamps (outputs clamp close signal).
 * With the CLOSE instruction, the signal is not output until the next motion starts.
 *
 * @param clampNumber - Specifies the number of the clamp. If omitted, 1 is assumed
 */
class Close(private val clampNumber: Int? = null) : Command {
    override fun generateCommand(): String {
        return "CLOSE ${clampNumber ?: ""}"
    }
}

fun Program.CLOSE(clampNumber: Int? = null) = this.add(Close(clampNumber))