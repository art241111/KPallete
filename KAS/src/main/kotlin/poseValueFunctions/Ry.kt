package com.github.poluka.kControlLibrary.poseValueFunctions

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.points.Point
import com.github.poluka.kControlLibrary.program.Program

/**
 * Returns the transformation values that represent the rotation around the specified axis.
 *
 * @param point - Edit point.
 * @param angle - Specifies the value of the rotation in degrees.
 */
class Ry(private val point: Point, private val angle: Int) : Command {
    override fun generateCommand(): String {
        return "POINT ${point.name} = ${point.name} + RY($angle)"
    }
}

fun Program.RY(point: Point,angle: Int) = this.add(Ry(point, angle))