package com.github.art241111.tcpClient.writer

import java.net.Socket

/**
 * Interface that should implement the class
 * that will connect to send data to the server.
 * @author Artem Gerasimov.
 */
interface RemoteWriter : Sender {
    fun createWriter(socket: Socket, isLogging: Boolean)
    fun destroyWriter(stopSymbol: String = "")
}
