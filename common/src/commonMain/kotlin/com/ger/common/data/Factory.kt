package com.ger.common.data

import androidx.compose.runtime.MutableState
import com.ger.common.data.сompletedPallet.CompletedPallet
import com.ger.common.data.сompletedPallet.Line

interface Factory {
    val conveyors: MutableState<List<Conveyor>>
    val areas: MutableState<List<Area>>
    val products: MutableState<List<Product>>
    val pallets: MutableState<List<Pallet>>
    val completedPallets: MutableState<List<CompletedPallet>>

    fun addConveyor(conveyor: Conveyor)
    fun addArea(area: Area)
    fun addProduct(product: Product)
    fun addPallet(pallet: Pallet)

    fun deleteConveyor(conveyorIndex: Int)
    fun deleteArea(areaIndex: Int)
    fun deleteProduct(productIndex: Int)
    fun deletePallet(palletIndex: Int)

    fun addCompletedPallet(completedPallet: CompletedPallet)
    fun addLevel(completedPallet: CompletedPallet, line: Line)
    fun deleteCompletedPallet(index: Int)
    fun deleteLevel(completedPallet: CompletedPallet, index: Int)
}
