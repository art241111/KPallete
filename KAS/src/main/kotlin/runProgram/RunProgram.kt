package com.github.poluka.kControlLibrary.runProgram

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.editorCommands.Edit
import com.github.poluka.kControlLibrary.editorCommands.Exit
import com.github.poluka.kControlLibrary.program.Program

fun RobotSender.loadProgram(program: Program) {
//    send("Del/p ${program.programName}")
//    send("1")
    runCommand(Edit(programName = program.programName))
    program.getProgram().forEach { command ->
        send(command)
    }
    runCommand(Exit())
}

fun RobotSender.runProgram(program: Program, speed: Int = 30) {
    send("SPEED $speed")
    send("EXECUTE ${program.programName}")
}

fun RobotSender.runCommand(command: Command) {
    send(command.generateCommand())
}