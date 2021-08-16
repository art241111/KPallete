package com.github.poluka.kControlLibrary.move.depart

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Moves the robot to a pose at a specified distance away from the current pose along the Z axis of
 * the tool coordinates.
 * JDEPART : Moves in joint interpolated motions.
 *
 * @param point - Pose variable. Specifies the end pose (in transformation values or joint displacement values)
 * @param distance - Specifies the distance in millimeters between the current pose and the destination pose along the
 * Z axis of the tool coordinates. If the specified distance is a positive value, the robot moves
 * “back” or towards the negative direction of the Z axis. If the specified distance is a negative
 * value, the robot moves “forward” or towards the positive direction of the Z axis.
 */
class JDepart(private val distance: Float) : Command {
    override fun generateCommand(): String {
        return "JDEPART $distance"
    }
}

fun Program.JDEPART(distance: Float) = this.add(JDepart(distance))