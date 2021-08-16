
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.ger.common.App
import factory.FactoryImp
import factory.default
import factory.fromString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import robot.RobotImp
import saveData.FileDataHelper
import saveData.Result

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    val coroutineScope = rememberCoroutineScope()
    val factoryImp = FactoryImp()
    val navigationImp = NavigationImp()
    val robotImp = RobotImp(coroutineScope)

    coroutineScope.launch {
        withContext(Dispatchers.Default) {
            when (val f: Result = FileDataHelper.getContentAsync("factory.data")) {
                is Result.Success -> factoryImp.fromString(f.data)
                is Result.Error -> factoryImp.default()
            }
        }
    }

    Window(
        onCloseRequest = {
            coroutineScope.launch(Dispatchers.IO) {
                FileDataHelper.writeContentAsync("factory.data", factoryImp.toString().toByteArray())
            }
//            coroutineScope.cancel()
            robotImp.disconnect()
            this.exitApplication()

        },
        title = "KPrizes",
        state = rememberWindowState(width = 1000.dp, height = 800.dp),
        onKeyEvent = {
            when (it.key) {
                Key.Escape -> {
                    navigationImp.back()
                    true
                }
                else -> false
            }
        }
    ) {
        App(
            factory = factoryImp,
            navigation = navigationImp,
            robot = robotImp
        )
    }
}
