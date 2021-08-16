
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.ger.common.nav.Navigation
import com.ger.common.nav.Screens
import java.util.*

class NavigationImp : Navigation {
    private val _state = mutableStateOf(Screens.MAIN_LIST)
    override val state: State<Screens> = _state

    private val _stack = Stack<Screens>()
    init {
        _stack.push(Screens.MAIN_LIST)
    }


    override fun moveToScreen(screen: Screens) {
        _state.value = screen
        _stack.push(screen)
    }

    override fun back() {
        if (_stack.size > 1) {
            _stack.pop()
            _state.value = _stack.peek()
        }
    }
}
