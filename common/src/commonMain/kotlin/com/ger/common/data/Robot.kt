package com.ger.common.data

import androidx.compose.runtime.State
import com.github.poluka.kControlLibrary.runProgram.RobotSender

interface Robot: RobotSender {
    val position: State<Point>
    fun updatePosition()

    val isConnect: State<Boolean>
    fun connect(ip: String, port: Int)
    fun disconnect()
}