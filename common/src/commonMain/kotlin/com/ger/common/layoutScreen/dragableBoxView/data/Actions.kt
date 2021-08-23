package draggableBox.data

data class Actions(
    var isMove: (x: Int, y: Int) -> Boolean = { _, _ -> true },
    val whenDragEnd: (x: Int, y: Int) -> Unit = {_, _ -> },
    val isReturnHome: Boolean = false,
    val isUpdateInitXY: Int = -1
)