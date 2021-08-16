package factory

import com.ger.common.data.Area
import com.ger.common.data.Conveyor
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.data.сompletedPallet.CompletedPallet

fun FactoryImp.fromString(string: String) {
    val split = string.split( "\n_____________________________________________________\n")

    split.forEachIndexed { index, s ->
        when(index) {
            0 -> {
                val productSplit = s.split("\n")
                productSplit.forEach {
                    this.addProduct(Product.fromString(it))
                }
            }
            1 -> {
                val palletSplit = s.split("\n")
                palletSplit.forEach {
                    this.addPallet(Pallet.fromString(it))
                }
            }
            2 -> {
                val completedPalletSplit = s.split("\n--------\n")
                completedPalletSplit.forEach {
                    this.addCompletedPallet(CompletedPallet.fromString(it))
                }
            }
            3 -> {
                val areaSplit = s.split("\n")
                areaSplit.forEach {
                    this.addArea(Area.fromString(it))
                }
            }
            4 -> {
                val conveyorSplit = s.split("\n")
                conveyorSplit.forEach {
                    this.addConveyor(Conveyor.fromString(it))
                }
            }
        }
    }





}