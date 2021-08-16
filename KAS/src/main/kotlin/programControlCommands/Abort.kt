package com.github.poluka.kControlLibrary.programControlCommands

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Stops execution of the robot program after the current step is completed. If the robot is in
 * motion, the execution stops after that motion is completed.
 *
 * Program execution is resumed using the CONTINUE command.
 */
class Abort() : Command {
    override fun generateCommand(): String {
        return "ABORT"
    }
}

fun Program.ABORT() = this.add(Abort())