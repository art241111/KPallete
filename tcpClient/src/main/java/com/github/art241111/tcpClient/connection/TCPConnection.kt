package com.github.art241111.tcpClient.connection

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Create connection class.
 * @author Artem Gerasimov.
 */
class TCPConnection : Connect {
    var socket = Socket()

    private val _statusState: MutableStateFlow<Status> = MutableStateFlow(Status.DISCONNECTED)
    val statusState: StateFlow<Status> = _statusState

    /**
     * Disconnect from server.
     */
    override fun disconnect() {
        if (statusState.value == Status.RECONNECTING) {
            _statusState.value = Status.DISCONNECTED
        } else if ((statusState.value == Status.COMPLETED)) {
            socket.close()
            _statusState.value = Status.DISCONNECTED

            socket = Socket()
        }
    }

    /**
     * Connect to server by TCP/IP.
     * If the connection did not occur within [timeout] seconds,
     * the error status is displayed.
     *
     * @param address - the address at which the connection will take place
     * @param port - the port on which the connection will take place
     * @param timeout - connection time, if it is exceeded, the connection failed
     */
    override fun connect(address: String, port: Int, timeout: Int) {
        if (_statusState.value != Status.CONNECTING && _statusState.value != Status.COMPLETED) {
            try {
                // Set connecting status
                if (_statusState.value != Status.RECONNECTING) {
                    _statusState.value = Status.CONNECTING
                }

                // Try to connect
                socket.connect(InetSocketAddress(address, port), timeout)

                // If the connection is successful, we notify you about it
                _statusState.value = Status.COMPLETED
            } catch (e: Exception) {
                println(e.message)

                if ((_statusState.value == Status.RECONNECTING) or (socket.isClosed)) {
                    _statusState.value = Status.DISCONNECTED
                } else {
                    _statusState.value = Status.ERROR
                }
                socket = Socket()
            }
        }
    }

    /**
     * Reconnecting to the server.
     *
     */
    fun reconnect() {
        if (_statusState.value != Status.RECONNECTING) {
            disconnect()
            _statusState.value = Status.RECONNECTING
        }
    }
}
