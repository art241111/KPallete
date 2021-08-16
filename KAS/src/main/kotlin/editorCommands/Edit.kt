package com.github.poluka.kControlLibrary.editorCommands

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Enters the editor mode that enables program creation and editing.
 *
 * @param programName - Selects a program for editing. If a program name is not specified, then the last program edited
 * or held (or stopped by an error) is opened for editing. If the specified program does not exist, a
 * new program is created.
 *
 * @param stepNumber - Selects the step number to start editing. If no step is specified, editing starts at the
 * last step edited. If an error occurred during the last program executed, the step where the error occurred
 * is selected.
 */
class Edit(private val programName: String? = null, private val stepNumber: Int? = null) : Command {
    override fun generateCommand(): String {
        return "EDIT ${programName ?: ""} ${stepNumber ?: ""}"
    }
}

fun Program.EDIT(programName: String? = null, stepNumber: Int? = null) = this.add(Edit(programName, stepNumber))