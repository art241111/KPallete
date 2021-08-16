package com.github.art241111.tcpClient.reader

import kotlinx.coroutines.flow.SharedFlow
import java.net.Socket

/**
 * Interface that should implement the class that
 * will connect to read input data from the server.
 * @author Artem Gerasimov.
 */
interface RemoteReader {
    /**
     * This parameter allows you to get the text.
     */
    val incomingText: SharedFlow<String>

    /**
     * This parameter allows you to get the text by letter.
     */
    val incomingLetter: SharedFlow<String>

    fun destroyReader()
    fun createReader(socket: Socket, isReaderLogging: Boolean, onDisconnect: () -> Unit)
}
