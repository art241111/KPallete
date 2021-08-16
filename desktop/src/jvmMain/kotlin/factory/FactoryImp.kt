package factory

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.ger.common.data.Area
import com.ger.common.data.Conveyor
import com.ger.common.data.Factory
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.data.сompletedPallet.CompletedPallet
import com.ger.common.data.сompletedPallet.Line
import com.ger.common.utils.add
import com.ger.common.utils.delete

class FactoryImp : Factory {
    private val _conveyors = mutableStateOf(listOf<Conveyor>())
    override val conveyors: MutableState<List<Conveyor>> = _conveyors

    private val _areas = mutableStateOf(listOf<Area>())
    override val areas: MutableState<List<Area>> = _areas

    private val _products = mutableStateOf(listOf<Product>())
    override val products: MutableState<List<Product>> = _products

    private val _pallets = mutableStateOf(listOf<Pallet>())
    override val pallets: MutableState<List<Pallet>> = _pallets

    private val _completedPallets = mutableStateOf(listOf<CompletedPallet>())
    override val completedPallets: MutableState<List<CompletedPallet>> = _completedPallets

    override fun addConveyor(conveyor: Conveyor) {
        _conveyors.add(conveyor)
    }

    override fun addArea(area: Area) {
        _areas.add(area)
    }

    override fun addProduct(product: Product) {
        _products.add(product)
    }

    override fun addPallet(pallet: Pallet) {
        _pallets.add(pallet)
    }

    override fun deleteConveyor(conveyorIndex: Int) {
        _conveyors.delete(conveyorIndex)
    }

    override fun deleteArea(areaIndex: Int) {
        _areas.delete(areaIndex)
    }

    override fun deleteProduct(productIndex: Int) {
        _products.delete(productIndex)
    }

    override fun deletePallet(palletIndex: Int) {
        _pallets.delete(palletIndex)
    }

    override fun addCompletedPallet(completedPallet: CompletedPallet) {
        _completedPallets.add(completedPallet)
    }

    override fun addLevel(completedPallet: CompletedPallet, line: Line) {
        val value = mutableListOf<CompletedPallet>()
        value.addAll(_completedPallets.value)
        value[_completedPallets.value.indexOf(completedPallet)].lines.add(line)

        _completedPallets.value = value
    }

    override fun deleteCompletedPallet(index: Int) {
        _completedPallets.delete(index)
    }

    override fun deleteLevel(completedPallet: CompletedPallet, index: Int) {
        val value = mutableListOf<CompletedPallet>()
        value.addAll(_completedPallets.value)
        value[_completedPallets.value.indexOf(completedPallet)].lines.delete(index)
        _completedPallets.value = value
    }

    override fun toString(): String {
        return products.value.joinToString(separator = "\n") +
                "\n_____________________________________________________\n" +
                _pallets.value.joinToString(separator = "\n") +
                "\n_____________________________________________________\n" +
                _completedPallets.value.joinToString(separator = "\n--------\n") +
                "\n_____________________________________________________\n" +
                _areas.value.joinToString(separator = "\n") +
                "\n_____________________________________________________\n" +
                _conveyors.value.joinToString(separator = "\n")
    }
}
