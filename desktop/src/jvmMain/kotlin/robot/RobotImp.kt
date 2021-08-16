package robot

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.ger.common.data.Point
import com.ger.common.data.Robot
import com.github.art241111.tcpClient.Client
import com.github.art241111.tcpClient.connection.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RobotImp(private val coroutineScope: CoroutineScope): Robot {
    private val client = Client()

    private val _isConnect = mutableStateOf(false)
    private val _position: MutableState<Point> = mutableStateOf(Point())
    override val position: State<Point> = _position

    override fun updatePosition() {
        client.send("WHERE")
    }

    override val isConnect: State<Boolean> = _isConnect

    override fun connect(ip: String, port: Int) {
        if (port != 0 && ip != "") {
            coroutineScope.launch(Dispatchers.IO) {
                client.connect(
                    ip,
                    port,
                    defaultMessage = "as"
                )

                coroutineScope.launch(Dispatchers.IO) {
                    client.statusState.collect {
                        _isConnect.value = it == Status.COMPLETED
                    }
                }

                coroutineScope.launch(Dispatchers.IO) {
                    var isNextPosition = false
                    client.incomingText.collect { message ->
                        // Это условие связано с тем, что робот пересылает сообщение с символом '>' или без символа
                        // TODO: Обработчик полученных сообщений
                        if(isNextPosition) {
                            val point = Point()
                            val split = message.split(" ")
                            var index = 0

                            split.forEach {
                                if (it.isNotEmpty() || it != "") {
                                    point[index] = it.trim().toDouble()
                                    index++
                                }
                            }
                            _position.value = point
                        }
                        isNextPosition = message.contains("X[mm]")
//                        _position.value = Point()
                    }
                }
            }
        }
    }

    override fun disconnect() {
        client.disconnect(stopSymbol = "q")
    }

    override fun send(message: String) {
        client.send(message)
    }
}