package com.github.art241111.tcpClient.connection

/**
 * Interface that should implement the
 * class that will connect to the server.
 * @author Artem Gerasimov.
 */
interface Connect {
    fun connect(address: String, port: Int, timeout: Int = 2000)
    fun disconnect()
}
