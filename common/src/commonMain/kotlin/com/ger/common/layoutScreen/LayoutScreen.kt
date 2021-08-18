package com.ger.common.layoutScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ger.common.customUI.EditNumber
import com.ger.common.customUI.EditText
import com.ger.common.customUI.Header
import com.ger.common.customUI.TabList
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.data.сompletedPallet.CompletedPallet
import com.ger.common.data.сompletedPallet.Line
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.strings.S
import com.ger.common.utils.CustomDropdownMenu
import com.ger.common.utils.ListWithHeaderAndCloseButton
import com.ger.common.utils.VerticalSeparator
import com.ger.common.utils.add
import com.ger.common.utils.change

@Composable
fun LayoutScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    completedPallets: MutableState<List<CompletedPallet>>,
    products: State<List<Product>>,
    pallets: State<List<Pallet>>,
    onDeleteLevel: (completedPallet: CompletedPallet, index: Int) -> Unit
) {
    val index = remember { mutableStateOf(0) }
    val completedPallet = remember(index.value) { completedPallets.value[index.value] }
    val editLayoutIndex = remember { mutableStateOf(-1) }
    val editLayout = remember {
        mutableStateOf(
            Line(
                pallet = pallets.value[0],
                product = products.value[0]
            )
        )
    }

    Column(modifier) {
        Header(
            onBack = {
                if (editLayoutIndex.value == -1) {
                    onBack()
                } else {
                    editLayoutIndex.value = -1
                }
            },
            headerText = S.strings.packingPallets
        )

        val completedPalletsName = remember(completedPallets.value) { completedPallets.value.map { it.name } }
        TabList(
            nameList = completedPalletsName,
            onAdd = {
                completedPallets.add(CompletedPallet(name = "test2", pallet = Pallet()))
            },
            onEditName = { index, newName ->
                completedPallets.change(index, completedPallets.value[index].copy(name = newName))
            },
            onCheck = {
                index.value = it
            }
        )

        Row {
            LevelList(
                modifier = Modifier.weight(1f),
                editScript = completedPallet.lines.value,
                onDelete = { onDeleteLevel(completedPallet, it) },
                onCheck = { index ->
                    editLayout.value = completedPallet.lines.value[index]
                    editLayoutIndex.value = index
                }
            )
            VerticalSeparator(width = 1.dp)

            AddNewLine(
                isEdit = editLayoutIndex.value != -1,
                editLayout = editLayout,
                modifier = Modifier.weight(1f),
                products = products,
                pallets = pallets,
                onAddLine = { newLevel, pallet ->
                    if (editLayoutIndex.value == -1) {
                        completedPallets.value[index.value].lines.add(newLevel)
                        completedPallets.value[index.value].pallet = pallet
                    } else {
                        completedPallets.value[index.value].lines.change(editLayoutIndex.value, newLevel)
                        completedPallets.value[index.value].pallet = pallet
                        editLayoutIndex.value = -1
                    }

                    editLayout.value = Line(
                        pallet = pallets.value[0],
                        product = products.value[0]
                    )
                },
            )

        }

    }
}

@Composable
fun AddNewLine(
    modifier: Modifier,
    products: State<List<Product>>,
    pallets: State<List<Pallet>>,
    onAddLine: (line: Line, pallet: Pallet) -> Unit,
    editLayout: State<Line>,
    isEdit: Boolean
) {
    val name = remember(editLayout.value.name) { mutableStateOf(editLayout.value.name) }

    val choosePallet = remember(
        pallets.value,
        editLayout.value.pallet
    ) { mutableStateOf(pallets.value.indexOf(editLayout.value.pallet)) }
    val pallet = remember(editLayout.value.pallet) { mutableStateOf(editLayout.value.pallet) }
    val chooseProduct = remember(
        products.value,
        editLayout.value.product
    ) { mutableStateOf(products.value.indexOf(editLayout.value.product)) }
    val product = remember(editLayout.value.product) { mutableStateOf(editLayout.value.product) }

    val overhang = remember(editLayout.value.overhang) { mutableStateOf(editLayout.value.overhang.toString()) }
    val distancesBetweenProducts =
        remember(editLayout.value.distancesBetweenProducts) { mutableStateOf(editLayout.value.distancesBetweenProducts.toString()) }


    Column(modifier.fillMaxHeight()) {
        Header(headerText = S.strings.addNewLine)

        EditText(
            value = name,
            label = S.strings.lineName,
        )

        EditNumber(
            value = overhang,
            label = S.strings.overhang,
        )

        EditNumber(
            value = distancesBetweenProducts,
            label = S.strings.distancesBetweenProducts,
        )

        Row(Modifier.heightIn(max = 100.dp).fillMaxWidth(0.9f).align(Alignment.CenterHorizontally)) {
            CustomDropdownMenu(
                modifier = Modifier.weight(1f),
                list = pallets.value,
                chooseIndex = choosePallet,
                dropdownMenuName = S.strings.palletList
            ) { index ->
                pallet.value = pallets.value[index]
                choosePallet.value = index
            }

            CustomDropdownMenu(
                modifier = Modifier.weight(1f),
                list = products.value,
                chooseIndex = chooseProduct,
                dropdownMenuName = S.strings.productList
            ) { index ->
                product.value = products.value[index]
                chooseProduct.value = index
            }
        }

        Spacer(Modifier.height(10.dp))

        val density = LocalDensity.current
        val productLayout = remember(
            product.value, distancesBetweenProducts.value,
            pallet.value, overhang.value, editLayout.value
        ) {
            mutableStateOf(
                ProductLayout(
                    pallet = pallet.value,
                    block = Block(
                        width = product.value.length,
                        length = product.value.width,
                        height = product.value.height,
                        weight = product.value.weight,
                        overhang = distancesBetweenProducts.value.toInt() / 2
                    ),
                    density = density,
                    overhang = overhang.value.toInt(),
                    defaultList = editLayout.value.layouts as MutableState<List<Block>>
                )
            )
        }

        val selectedIndex = remember(
            product.value, pallet.value, editLayout.value
        ) {
            mutableStateOf(productLayout.value.optimalListIndex)
        }

        PositioningView(
            modifier = modifier.fillMaxWidth(0.9f).align(Alignment.CenterHorizontally),
            product = product.value,
            pallet = pallet.value,
            overhang = overhang.value.toIntOrNull(),
            distancesBetweenProducts = distancesBetweenProducts.value.toIntOrNull(),
            selectedIndex = selectedIndex,
            productLayout = productLayout
        ) {
            selectedIndex.value = it
        }

        Spacer(Modifier.height(5.dp))
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                val overhangDouble = overhang.value.toIntOrNull()
                val distancesBetweenProductsDouble = distancesBetweenProducts.value.toIntOrNull()

                if (overhangDouble != null && distancesBetweenProductsDouble != null) {
                    onAddLine(
                        Line(
                            name = name.value,
                            pallet = pallet.value,
                            product = product.value,
                            overhang = overhangDouble,
                            distancesBetweenProducts = distancesBetweenProductsDouble,
                            layouts = productLayout.value.layouts[selectedIndex.value]
                        ),
                        pallet.value
                    )

                    name.value = ""
                    overhang.value = "0"
                    distancesBetweenProducts.value = "0"
                }
            }
        ) {
            Text(if (isEdit) S.strings.edit else S.strings.add)
        }
    }
}

@Composable
private fun LevelList(
    modifier: Modifier,
    editScript: List<Line>,
    onDelete: (index: Int) -> Unit,
    onCheck: (index: Int) -> Unit
) {
    ListWithHeaderAndCloseButton(
        modifier = modifier,
        headerText = S.strings.palletList,
        contentArray = editScript,
        onDelete = onDelete,
        onCheck = onCheck
    )
    { _, line ->
        Column(modifier = Modifier.padding(10.dp)) {
            Text("${S.strings.name}: ${line.name}")
            Spacer(Modifier.height(5.dp))
            Text("${S.strings.numberOfProducts}: ${line.layouts.value.size}")
        }
    }


}
