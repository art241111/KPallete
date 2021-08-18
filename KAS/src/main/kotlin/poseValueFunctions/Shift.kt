package com.github.poluka.kControlLibrary.poseValueFunctions

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.points.Point
import com.github.poluka.kControlLibrary.program.Program

/**
 * Returns the transformation values of the pose shifted by the distance specified for each base axis
 * (X,Y,Z) from the specified pose.
 *
 * @param initPoint - Specifies a transformation value variable for the pose to be shifted.
 * @param initPointName - Name of initPoint.
 *
 * @param x, y, z - Specifies the shift amount in X, Y, Z directions of the base coordinates. If any value is omitted,
 * 0 is assumed.
 * */
class Shift(
    private val initPointName: String,
    private val x: Double? = null,
    private val y: Double? = null,
    private val z: Double? = null,
) : Command {
    override fun generateCommand(): String {
        return "SHIFT($initPointName BY ${x ?: ""},${y ?: ""},${z ?: ""}"
    }

    constructor(
        initPoint: Point,
        x: Double? = null,
        y: Double? = null,
        z: Double? = null,
    ) : this(initPointName = initPoint.name, x, y, z)
}

fun Program.SHIFT(
    initPointName: String,
    x: Double? = null,
    y: Double? = null,
    z: Double? = null,
) = this.add(Shift(initPointName, x, y, z))

fun Program.SHIFT(
    initPoint: Point,
    x: Double? = null,
    y: Double? = null,
    z: Double? = null,
) = this.add(Shift(initPoint, x, y, z))