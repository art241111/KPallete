package com.github.poluka.kControlLibrary.points

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

class Here(private val pointName: String) : Command {
    constructor(point: Point) : this (point.name)

    override fun generateCommand(): String {
        return "HERE $pointName"
    }
}

fun Program.HERE(pointName: String) = add(Here(pointName))
fun Program.HERE(point: Point) = add(Here(point))
