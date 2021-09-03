package com.github.poluka.kControlLibrary.move.depart

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Moves the robot to a pose at a specified distance away from the current pose along the Z axis of
 * the tool coordinates.
 * LDEPART : Moves in linear interpolated motions.
 *
 * @param distance - Specifies the distance in millimeters between the current pose and the destination pose along the
 * Z axis of the tool coordinates. If the specified distance is a positive value, the robot moves
 * “back” or towards the negative direction of the Z axis. If the specified distance is a negative
 * value, the robot moves “forward” or towards the positive direction of the Z axis.
 */
class LDepart(private val distance: Float) : Command {
    override fun generateCommand(): String {
        return "LDEPART $distance"
    }
}

fun Program.LDEPART(distance: Float) = this.add(LDepart(distance))