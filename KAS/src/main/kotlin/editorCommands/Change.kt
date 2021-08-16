package com.github.poluka.kControlLibrary.editorCommands

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Changes the program currently selected in editor mode.
 *
 * @param programName - Selects the program to be edited.
 * @param stepNumber - Selects the step number to start editing. If no step is specified,
 * the first step of the program is selected.
 */
class Change(private val programName: String, private val stepNumber: Int? = null) : Command {
    override fun generateCommand(): String {
        return "C $programName ${stepNumber ?: ""}"
    }
}

fun Program.C(programName: String, stepNumber: Int? = null) = this.add(Change(programName, stepNumber))