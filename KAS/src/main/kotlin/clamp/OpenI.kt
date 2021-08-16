package com.github.poluka.kControlLibrary.clamp

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Opens robot clamps (outputs clamp open signal).
 *
 * @param clampNumber - Specifies the number of the clamp. If omitted, 1 is assumed
 *
 * The timing for signal output using the OPENI instruction is as follows:
 * 1. If the robot is currently in motion, the signal is output after that motion is completed. If the
 * robot is moving in CP motion, the CP motion is suspended (BREAK).
 * 2. If the robot is not in motion, the signal is sent immediately to the control valve.
 */
class OpenI(private val clampNumber: Int? = null) : Command {
    override fun generateCommand(): String {
        return "OPENI ${clampNumber ?: ""}"
    }
}

fun Program.OPENI(clampNumber: Int? = null) = this.add(CloseI(clampNumber))