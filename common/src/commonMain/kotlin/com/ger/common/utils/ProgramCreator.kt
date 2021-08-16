package com.ger.common.utils

import com.ger.common.data.Area
import com.ger.common.data.Conveyor
import com.ger.common.data.сompletedPallet.CompletedPallet
import com.github.poluka.kControlLibrary.`while`.WHILE_SIGNAL
import com.github.poluka.kControlLibrary.clamp.CLOSEI
import com.github.poluka.kControlLibrary.move.LMOVE
import com.github.poluka.kControlLibrary.move.appro.JAPPRO
import com.github.poluka.kControlLibrary.move.depart.LDEPART
import com.github.poluka.kControlLibrary.points.Point
import com.github.poluka.kControlLibrary.program.Program
import com.github.poluka.kControlLibrary.program.motion
import com.github.poluka.kControlLibrary.utils.GenerateRandomString
import com.github.poluka.kControlLibrary.wait.SWAIT


class ProgramCreator {
    fun generateMoveProgram(
        conveyor: Conveyor,
        area: Area,
        completedPallet: CompletedPallet,
        isConveyorSignal: Int,
        isPalletSignal: Int,
        isProgramWork: Int,
        palletPositionIndex: Int,
        zGap: Int
    ): Program {
        val conveyorPoint = Point.createPoint(
            name = "conveyorPoint",
            x = conveyor.takePosition.x,
            y = conveyor.takePosition.y,
            z = conveyor.takePosition.z,
            o = conveyor.takePosition.o,
            a = conveyor.takePosition.a,
            t = conveyor.takePosition.t,

            )

        val stayPoint = when (palletPositionIndex) {
            0 -> Point.createPoint(
                name = "stayPoint",
                x = area.leftTopPosition.x,
                y = area.leftTopPosition.y - completedPallet.pallet.height,
                z = area.leftTopPosition.z,
                o = conveyor.takePosition.o,
                a = conveyor.takePosition.a,
                t = conveyor.takePosition.t,
            )

            1 -> Point.createPoint(
                name = "stayPoint",
                x = area.leftTopPosition.x - completedPallet.pallet.length,
                y = area.leftTopPosition.y - completedPallet.pallet.height,
                z = area.leftTopPosition.z,
                o = conveyor.takePosition.o,
                a = conveyor.takePosition.a,
                t = conveyor.takePosition.t,
            )

            2 -> Point.createPoint(
                name = "stayPoint",
                x = area.leftBottomPosition.x,
                y = area.leftBottomPosition.y,
                z = area.leftBottomPosition.z,
                o = conveyor.takePosition.o,
                a = conveyor.takePosition.a,
                t = conveyor.takePosition.t,
            )

            3 -> Point.createPoint(
                name = "stayPoint",
                x = area.leftBottomPosition.x - completedPallet.pallet.length,
                y = area.leftBottomPosition.y,
                z = area.leftBottomPosition.z,
                o = conveyor.takePosition.o,
                a = conveyor.takePosition.a,
                t = conveyor.takePosition.t,
            )

            else -> Point.createPoint(
                name = "stayPoint",
                x = area.leftBottomPosition.x,
                y = area.leftBottomPosition.y,
                z = area.leftBottomPosition.z,
                o = conveyor.takePosition.o,
                a = conveyor.takePosition.a,
                t = conveyor.takePosition.t,
            )
        }


        val stayPoints = mutableListOf<PointWithRotation>()
        completedPallet.lines.value.forEachIndexed { index, line ->
            line.layouts.forEach { block ->
                stayPoints.add(
                    PointWithRotation(
                        point = stayPoint.copy(
                            name = GenerateRandomString.generateString(14),
                            x = stayPoint.x + (block.x + block.length / 2),
                            y = stayPoint.y + (block.y + block.width / 2),
                            z = stayPoint.z + index * block.height + zGap
                        ),
                        isRotated = block.isRotated
                    )
                )
            }
        }

        return motion() {
            this add conveyorPoint
            this add stayPoint

            WHILE_SIGNAL(isProgramWork) {
                stayPoints.forEach {
                    SWAIT(isConveyorSignal)
                    this add getBlock(conveyorPoint)
                    SWAIT(isPalletSignal)
                    this add stayBlock(it)
                }
            }
        }
    }
}

class PointWithRotation(
    val point: Point,
    val isRotated: Boolean = false
)

private fun getBlock(conveyorPoint: Point) = motion {
    val distance = 100F
    JAPPRO(conveyorPoint, distance)
    LMOVE(conveyorPoint)
    CLOSEI()
    LDEPART(distance)
}

private fun stayBlock(stayPoint: PointWithRotation) = motion {
    val distance = 100F
    val point = stayPoint.point.copy(
        o = if (stayPoint.isRotated) (stayPoint.point.o - 90).toInt().toDouble() else stayPoint.point.o
    )
    this add point

    JAPPRO(point, distance)
    LMOVE(point)
    CLOSEI()
    LDEPART(distance)
}
