package com.github.art241111.tcpClient.reader

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.net.Socket
import java.util.NoSuchElementException
import java.util.Scanner

/**
 * This class creates a Reader that will listen to the
 * incoming stream and process the data that the server sends.
 * @author Artem Gerasimov.
 */
class DefaultRemoteReader : RemoteReader {
    private val _incomingText: MutableSharedFlow<String> = MutableSharedFlow()

    /**
     * This parameter allows you to get the text.
     */
    override val incomingText: SharedFlow<String> = _incomingText

    private var world: String = ""
    private val _incomingLetter: MutableSharedFlow<String> = MutableSharedFlow()

    /**
     * This parameter allows you to get the text by letter.
     */
    override val incomingLetter: SharedFlow<String> = _incomingLetter

    // A variable that displays whether our reader is working.
    private var isReading = false

    // Will the output of the sent data occur
    private var isLogging = false

    // Reader for receiving and reading incoming data from the server
    private lateinit var reader: Scanner

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * Stop reading track.
     */
    override fun destroyReader() {
        if (::reader.isInitialized) {
            reader.close()
        }
        isReading = false
    }

    /**
     * Start reading from InputStream
     * @param socket - the connection that you want to listen to.
     */
    override fun createReader(socket: Socket, isReaderLogging: Boolean, onDisconnect: () -> Unit) {
        reader = Scanner(socket.getInputStream()).useDelimiter("")
        isReading = true
        isLogging = true

        scope.launch(Dispatchers.IO) {
            while (isReading) {
                try {
                    val letter = reader.next()
                    _incomingLetter.emit(letter)


                    if (letter.contains("\n") || world.contains("login:")) {
                        if (isLogging) {
                            println("DEFAULT_READER: $world")
                        }

                        _incomingText.emit(world)
                        world = ""
                    } else {
                        world += letter
                    }
                } catch (e: NoSuchElementException) {
                    onDisconnect()
                    // Log.e("reader", "Unknown error", e)
                } catch (e: Exception) {
                    // Log.e("reader", "Unknown error", e)
                }
            }
        }
    }
}
