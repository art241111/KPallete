package com.github.poluka.kControlLibrary.decision

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Stops execution of the robot program immediately.
 *
 *  This command has the same effect as when HOLD/RUN state is changed from RUN to HOLD.
 *  Program execution is resumed using the CONTINUE command
 */
class Yes() : Command {
    override fun generateCommand(): String {
        return "1"
    }
}

fun Program.YES() = this.add(Yes())