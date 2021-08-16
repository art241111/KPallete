package com.github.poluka.kControlLibrary.programControlCommands

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Resumes execution of a program stopped by PAUSE instruction, ABORT or HOLD command, or
 * as a result of an error. This command can also be used to start programs made ready to execute
 * by PRIME, STEP or MSTEP command.
 *
 * @param next - If NEXT is not entered, execution resumes from the step at which execution stopped. If it is
 * entered, execution resumes from the step following the step at which execution stopped.
 */
class Continue(private val next: Int? = null) : Command {
    override fun generateCommand(): String {
        return "CONTINUE ${next ?: ""}"
    }
}

fun Program.CONTINUE(next: Int? = null) = this.add(Continue(next))