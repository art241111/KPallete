package com.ger.common.data.сompletedPallet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.ger.common.data.Pallet
import com.ger.common.data.WithName

class CompletedPallet(
    override val name: String = "",
    val lines: MutableState<List<Line>> = mutableStateOf(listOf()),
    var pallet: Pallet = Pallet()
) : WithName {
    override fun toString(): String {
        return "$name\n" +
                "$pallet\n" +
                lines.value.joinToString(separator = "\n")
    }

    companion object {
        fun fromString(string: String): CompletedPallet {
            val split = string.split("\n")
            val name = split[0]
            val pallet = Pallet.fromString(split[1])
            val lines = mutableListOf<Line>()
            for (i in 2..split.lastIndex) {
                if (split[i].isNotEmpty()) {
                    lines.add(Line.fromString(split[i]))
                }
            }

            return CompletedPallet(name, mutableStateOf(lines), pallet)
        }
    }

    fun copy(
        name: String? = null,
        lines: MutableState<List<Line>>? = null,
        pallet: Pallet? = null
    ): CompletedPallet {
        return CompletedPallet(
            name = name ?: this.name,
            lines = lines ?: this.lines,
            pallet = pallet ?: this.pallet
        )
    }
}