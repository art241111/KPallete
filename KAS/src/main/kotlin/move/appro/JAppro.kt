package com.github.poluka.kControlLibrary.move.appro

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.points.Point
import com.github.poluka.kControlLibrary.program.Program

/**
 * Moves in tool Z direction to a specified distance from the taught pose.
 * JAPPRO: Moves in joint interpolated motion.
 *
 * @param point - Pose variable. Specifies the end pose (in transformation values or joint displacement values)
 * @param distance - Distance /Specifies the offset distance between the end pose and the pose the robot actually
 * reaches on the Z axis direction of the tool coordinates (in millimeters). If the specified distance is a positive
 * value, the robot moves towards the negative direction of the Z axis. If the specified distance is a
 * negative value, the robot moves towards the positive direction of the Z axis.
 */
class JAppro(private val point: Point, private val distance: Float) : Command {
    override fun generateCommand(): String {
        return "JAPPRO ${if (point.name == "") point.toString() else point.name },$distance"
    }
}

fun Program.JAPPRO(point: Point, distance: Float) = this.add(JAppro(point, distance))