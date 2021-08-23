package draggableBox.data

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource

class Focus(
    val interactionSource: MutableInteractionSource = MutableInteractionSource(),
    val isFocus: Boolean = false
)