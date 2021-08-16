package com.github.poluka.kControlLibrary.wait

import com.github.poluka.kControlLibrary.Command
import com.github.poluka.kControlLibrary.program.Program

/**
 * Waits until the specified external I/O or internal signal meets the set condition.
 *
 * @param signalNumber - Specifies the number of the external I/O or internal signal to monitor.
 * Negative numbers indicate that the conditions are satisfied when the signals are OFF.
 *
 * Internal signal 2001 - 2960
 */
class SWait(private val signalNumber: Int) : Command {
    override fun generateCommand(): String {
        return "SWAIT $signalNumber "
    }
}

fun Program.SWAIT(signalNumber: Int) = this.add(SWait(signalNumber))