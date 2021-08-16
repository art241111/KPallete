package com.github.poluka.kControlLibrary.move

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.points.Point
import com.github.poluka.kControlLibrary.program.Program

/**
 * Moves the robot to the specified pose.
 * JMOVE: Moves in joint interpolated motion.
 *
 * @param point - Specifies the destination pose of the robot.
 * @param clampNumber - Specifies the clamp number to open or close at the destination pose. Positive number closes the
 * clamp, and negative number opens it. Any clamp number can be set, up to the maximum
 * number set via HSETCLAMP command (or auxiliary function 0605). If omitted, the clamp
 * does not open or close.
 */
class JMove(private val point: Point, private val clampNumber: Int? = null) : Command {
    override fun generateCommand(): String {
        return "JMOVE ${if (point.name == "") point.toString() else point.name}${if(clampNumber != null) ", $clampNumber" else  ""}"
    }
}

fun Program.JMOVE(point: Point, clampNumber: Int? = null) = this.add(JMove(point, clampNumber))
