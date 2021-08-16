package com.github.art241111.tcpClient

import com.github.art241111.tcpClient.connection.Status
import com.github.art241111.tcpClient.connection.TCPConnection
import com.github.art241111.tcpClient.reader.DefaultRemoteReader
import com.github.art241111.tcpClient.reader.RemoteReader
import com.github.art241111.tcpClient.writer.DefaultRemoteWriter
import com.github.art241111.tcpClient.writer.RemoteWriter
import com.github.art241111.tcpClient.writer.Sender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * TCP client.
 * @author Artem Gerasimov.
 */
class Client : Sender {
    private val connection = TCPConnection()
    private val remoteReader: RemoteReader = DefaultRemoteReader()
    private val remoteWriter: RemoteWriter = DefaultRemoteWriter()

    val incomingText = remoteReader.incomingText
    val incomingLetter = remoteReader.incomingLetter

    /**
     * @return connect status
     */
    val statusState: StateFlow<Status?> = connection.statusState

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    // Connection parameters. Saved to use when reconnecting
    private var oldAddress = ""
    private var oldPort = -1
    private var isWriterLogging = false
    private var isReaderLogging = false

    /**
     * Connect to TCP sever.
     * @param address - server ip port,
     * @param port - server port,
     * @param isWriterLogging - will the output of the sent data occur,
     * @param isReaderLogging - will the output of the received data occur.
     */
    suspend fun connect(
        address: String,
        port: Int,
        timeout: Int = 2000,
        isWriterLogging: Boolean = false,
        isReaderLogging: Boolean = false,
        defaultMessage: String = ""
    ) {
        oldAddress = address
        oldPort = port
        this.isWriterLogging = isWriterLogging
        this.isReaderLogging = isReaderLogging

        // When the device connects to the server, it creates Reader and Writer
        withContext(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                connection.connect(address, port, timeout)
            }

            createReaderAndWriter(isWriterLogging, isReaderLogging)

            if(defaultMessage.isNotEmpty()) send(defaultMessage)
        }
    }

    /**
     * Disconnect from TCP sever.
     *
     * @param stopSymbol - the character that is sent when disconnecting from the server.
     */
    fun disconnect(stopSymbol: String = "") {
        if (statusState.value != Status.DISCONNECTED) {
            remoteWriter.destroyWriter(stopSymbol)

            scope.launch {
                delay(50L)
                connection.disconnect()
            }
            remoteReader.destroyReader()
        }
    }

    /**
     * Send text to the server.
     */
    override fun send(text: String) {
        if (statusState.value == Status.RECONNECTING) {
            reconnect()
        }

        if (statusState.value == Status.COMPLETED) {
            remoteWriter.send(text)
        }
    }

    private fun createReaderAndWriter(isWriterLogging: Boolean, isReaderLogging: Boolean) {
        if (!connection.socket.isClosed and connection.socket.isConnected) {
            remoteReader.createReader(connection.socket, isReaderLogging) {
                connection.reconnect()
            }
            remoteWriter.createWriter(connection.socket, isWriterLogging)
        }
    }

    /**
     * Reconnecting to th server.
     */
    private fun reconnect() {
        remoteReader.destroyReader()
        remoteWriter.destroyWriter()

        scope.launch {
            // When the device connects to the server, it creates Reader and Writer
            withContext(Dispatchers.IO) {
                withContext(Dispatchers.IO) {
                    connection.connect(oldAddress, oldPort, 40000)
                }

                createReaderAndWriter(isWriterLogging, isReaderLogging)
            }
        }
    }
}
