package com.github.poluka.kControlLibrary.points

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Changes the program currently selected in editor mode.
 *
 * @param programName - Selects the program to be edited.
 * @param stepNumber - Selects the step number to start editing. If no step is specified,
 * the first step of the program is selected.
 */
class InitPoint(private val point: Point) : Command {
    override fun generateCommand(): String = point.generateCommand()
}

fun Program.INIT_POINT(point: Point) = this.add(InitPoint(point))