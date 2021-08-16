package factory

import androidx.compose.runtime.mutableStateOf
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.data.сompletedPallet.CompletedPallet
import com.ger.common.data.сompletedPallet.Line

fun FactoryImp.default() {
    val defaultPalletList = listOf(
        Pallet(
            name = "Second",
            width = 160,
            length = 160,
            height = 1
        ),
        Pallet(
            name = "First",
            width = 100,
            length = 100,
            height = 1
        ),
        Pallet(
            name = "Third",
            width = 160,
            length = 200,
            height = 1
        )
    )

    defaultPalletList.forEach {
        this.addPallet(it)
    }

    val defaultProductList = listOf(
        Product(
            name = "Second",
            width = 100,
            length = 30,
            weight = 1.0
        ),
        Product(
            name = "First",
            width = 50,
            length = 50,
            weight = 10.0
        ),

        Product(
            name = "Third",
            width = 30,
            length = 100,
            weight = 1.0
        ),
    )
    defaultProductList.forEach {
        this.addProduct(it)
    }

    this.addCompletedPallet(
        CompletedPallet(
            name = "First",
            lines = mutableStateOf(listOf<Line>()),
            pallet = Pallet()
        )
    )
}