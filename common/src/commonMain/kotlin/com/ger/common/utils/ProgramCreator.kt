package com.ger.common.utils

import com.ger.common.data.Area
import com.ger.common.data.Conveyor
import com.ger.common.data.сompletedPallet.CompletedPallet
import com.github.poluka.kControlLibrary.`while`.WHILE_SIGNAL
import com.github.poluka.kControlLibrary.clamp.CLOSEI
import com.github.poluka.kControlLibrary.move.LMOVE
import com.github.poluka.kControlLibrary.move.appro.JAPPRO
import com.github.poluka.kControlLibrary.move.depart.LDEPART
import com.github.poluka.kControlLibrary.points.INIT_POINT
import com.github.poluka.kControlLibrary.points.Point
import com.github.poluka.kControlLibrary.poseValueFunctions.RZ
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
            0 -> conveyorPoint.copy(
                name = "stayPoint",
                x = (area.leftTopPosition.x).round(2),
                y = (area.leftTopPosition.y - completedPallet.pallet.length).round(2),
                z = area.leftTopPosition.z,
            )

            1 -> conveyorPoint.copy(
                name = "stayPoint",
                x = (area.rightTopPosition.x - completedPallet.pallet.width).round(2),
                y = (area.rightTopPosition.y - completedPallet.pallet.length).round(2),
                z = area.rightTopPosition.z,
            )

            3 -> conveyorPoint.copy(
                name = "stayPoint",
                x = (area.rightTopPosition.x - completedPallet.pallet.width).round(2),
                y = area.leftBottomPosition.y,
                z = area.leftBottomPosition.z,
            )

            else -> conveyorPoint.copy(
                name = "stayPoint",
                x = area.leftBottomPosition.x,
                y = area.leftBottomPosition.y,
                z = area.leftBottomPosition.z,
            )
        }


        val stayPoints = mutableListOf<PointWithRotation>()
        completedPallet.lines.value.forEachIndexed { index, line ->
            line.layouts.value.forEach { block ->
                stayPoints.add(
                    PointWithRotation(
                        point = stayPoint.copy(
                            name = GenerateRandomString.generateString(14),
                            x = (stayPoint.x + (block.x + block.length / 2)).round(3),
                            y = (stayPoint.y + (block.y + block.width / 2)).round(3),
                            z = (stayPoint.z + index * block.height + zGap).round(3)
                        ),
                        isRotated = block.isRotated
                    )
                )
            }
        }

        return motion() {
            INIT_POINT(conveyorPoint)
            INIT_POINT(stayPoint)

            WHILE_SIGNAL(isProgramWork) {
                stayPoints.forEach {
                    SWAIT(isConveyorSignal)
                    getBlock(conveyorPoint)
                    SWAIT(isPalletSignal)
                    stayBlock(it)
                }
            }
        }
    }
}

class PointWithRotation(
    val point: Point,
    val isRotated: Boolean = false
)

private fun Program.getBlock(conveyorPoint: Point)  {
    val distance = 100F
    JAPPRO(conveyorPoint, distance)
    LMOVE(conveyorPoint)
    CLOSEI()
    LDEPART(distance)
}

private fun Program.stayBlock(stayPoint: PointWithRotation) {
    val distance = 100F
    val point = stayPoint.point
    INIT_POINT(point)

    if (stayPoint.isRotated) {
       RZ(point, 90)
    }

    JAPPRO(point, distance)
    LMOVE(point)
    CLOSEI()
    LDEPART(distance)
}
