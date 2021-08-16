package com.github.poluka.kControlLibrary.program

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.utils.GenerateRandomString

/**
 * DSL метод, за счет которого можно создавать свои программы.
 * @param commands - команды, которые нужно добавить в программу.
 */

fun motion(name: String = "", commands: Program.() -> Unit): Program {
    var defName = name
    if (name == "") defName = GenerateRandomString.generateString(14)

    return Program(defName).apply(commands).build()
}

/**
 * Класс, который позволяет добавлять команды в програму.
 * @author artem241120@gmail.com (Artem Gerasimov)
 */
class Program(val programName: String) {
    private val _commands: MutableList<Command> = mutableListOf()
    val commands: List<Command> = _commands

    infix fun add(command: Command) {
        _commands.add(command)
    }

    infix fun add(program: Program) {
        _commands.addAll(program._commands)
    }

    internal fun build(): Program = this

    fun getProgram(): List<String> {
        val program = mutableListOf<String>()

        program.addAll(commands.map { it.generateCommand() })

        return program
    }
}
