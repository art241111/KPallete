package com.ger.common.data.сompletedPallet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.data.WithName

class CompletedPallet(
    override val name: String = "",
    val lines: MutableState<List<Line>> = mutableStateOf(listOf()),
    var pallet: Pallet = Pallet(),
    val product: Product = Product(),
) : WithName {
    override fun toString(): String {
        return "$name\n" +
                "$pallet\n" +
                "$product\n" +
                lines.value.joinToString(separator = "\n")
    }

    companion object {
        fun fromString(string: String): CompletedPallet {
            val split = string.split("\n")
            val name = split[0]
            val pallet = Pallet.fromString(split[1])
            val product = Product.fromString(split[2])
            val lines = mutableListOf<Line>()
            for (i in 3..split.lastIndex) {
                if (split[i].isNotEmpty()) {
                    lines.add(Line.fromString(split[i]))
                }
            }

            return CompletedPallet(name, mutableStateOf(lines), pallet, product)
        }
    }

    fun copy(
        name: String? = null,
        lines: MutableState<List<Line>>? = null,
        pallet: Pallet? = null,
        product: Product? = null
    ): CompletedPallet {
        return CompletedPallet(
            name = name ?: this.name,
            lines = lines ?: this.lines,
            pallet = pallet ?: this.pallet,
            product = product ?: this.product
        )
    }
}