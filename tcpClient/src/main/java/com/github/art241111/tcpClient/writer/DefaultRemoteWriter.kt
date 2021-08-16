package com.github.art241111.tcpClient.writer

import java.io.PrintStream
import java.net.Socket

/**
 * This class creates a Writer that allows
 * you to send text strings to the server.
 * @author Artem Gerasimov.
 */
class DefaultRemoteWriter : RemoteWriter {
    // Will the output of the sent data occur
    private var isLogging = false

    // Writer, which allows you to send data to the server
    private lateinit var writer: PrintStream

    /**
     * Sending a message to the server.
     * @param text - the text that will be sent to the server.
     */
    override fun send(text: String) {
        if (isLogging) {
            println("DEFAULT_WRITER: $text")
        }

        writer.print(text + "\n")
        writer.flush()
    }

    /**
     * Creating a writer that will send data to the server.
     * @param socket - - the connection that you want to listen to.
     */
    override fun createWriter(socket: Socket, isLogging: Boolean) {
        writer = PrintStream(socket.getOutputStream())
        this.isLogging = isLogging
    }

    /**
     * Sending the final command and closing Writer.
     */
    override fun destroyWriter(stopSymbol: String) {
        if (::writer.isInitialized) {
            if (stopSymbol != "") {
                send(stopSymbol)
                Thread.sleep(50L)
            }
            writer.close()
        }
    }
}
