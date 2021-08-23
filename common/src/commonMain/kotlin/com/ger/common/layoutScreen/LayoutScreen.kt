package com.ger.common.layoutScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ger.common.customUI.EditNumber
import com.ger.common.customUI.Header
import com.ger.common.customUI.TabList
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.data.сompletedPallet.CompletedPallet
import com.ger.common.data.сompletedPallet.Line
import com.ger.common.layoutScreen.dragableBoxView.data.Block
import com.ger.common.strings.S
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
    val editLayout = remember { mutableStateOf(Line()) }

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
                completedPallets.add(CompletedPallet(name = "test2", pallet = Pallet(), product = Product()))
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
                product = products.value[0],
                pallet = pallets.value[0],
                onAddLine = { newLevel ->
                    if (editLayoutIndex.value == -1) {
                        completedPallets.value[index.value].lines.add(newLevel)
                    } else {
                        completedPallets.value[index.value].lines.change(editLayoutIndex.value, newLevel)
                        editLayoutIndex.value = -1
                    }

                },
            )

        }

    }
}

@Composable
fun AddNewLine(
    modifier: Modifier,
    product: Product,
    pallet: Pallet,
    onAddLine: (line: Line) -> Unit,
    editLayout: State<Line>,
    isEdit: Boolean
) {
    val overhang = remember(editLayout.value.overhang) { mutableStateOf(editLayout.value.overhang.toString()) }
    val distancesBetweenProducts =
        remember(editLayout.value.distancesBetweenProducts) { mutableStateOf(editLayout.value.distancesBetweenProducts.toString()) }


    Column(modifier.fillMaxHeight()) {
        Header(headerText = S.strings.addNewLine)

        EditNumber(
            value = overhang,
            label = S.strings.overhang,
        )

        EditNumber(
            value = distancesBetweenProducts,
            label = S.strings.distancesBetweenProducts,
        )


        Spacer(Modifier.height(10.dp))

        val density = LocalDensity.current
        val productLayout = remember(
            product, distancesBetweenProducts.value,
            pallet, overhang.value, editLayout.value
        ) {
            mutableStateOf(
                ProductLayout(
                    pallet = pallet,
                    block = Block(
                        product = product,
                        overhang = distancesBetweenProducts.value.toInt() / 2
                    ),
                    density = density,
                    overhang = overhang.value.toInt(),
                    defaultList = editLayout.value.layouts as MutableState<List<Block>>
                )
            )
        }

        val selectedIndex = remember(editLayout.value) {
            mutableStateOf(productLayout.value.optimalListIndex)
        }


        // Select default layots
        LazyRow(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            itemsIndexed(items = productLayout.value.layouts) { index, _ ->
                Button(
                    colors = ButtonDefaults.buttonColors(if (selectedIndex.value == index) MaterialTheme.colors.primary else Color.Transparent),
                    onClick = {
                        selectedIndex.value = index
                    }
                ) {
                    Text(index.toString())
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        PositioningView(
            modifier = modifier.fillMaxWidth(0.9f).align(Alignment.CenterHorizontally),
            product = product,
            pallet = pallet,
            overhang = overhang.value.toIntOrNull(),
            distancesBetweenProducts = distancesBetweenProducts.value.toIntOrNull(),
            listOfBlocks = productLayout.value.layouts[selectedIndex.value]
        )

        Spacer(Modifier.height(5.dp))
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                val overhangDouble = overhang.value.toIntOrNull()
                val distancesBetweenProductsDouble = distancesBetweenProducts.value.toIntOrNull()

                if (overhangDouble != null && distancesBetweenProductsDouble != null) {
                    onAddLine(
                        Line(
                            overhang = overhangDouble,
                            distancesBetweenProducts = distancesBetweenProductsDouble,
                            layouts = productLayout.value.layouts[selectedIndex.value]
                        ),
                    )

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
    { index, line ->
        Column(modifier = Modifier.padding(10.dp)) {
            Text("${S.strings.name}: $index")
            Spacer(Modifier.height(5.dp))
            Text("${S.strings.numberOfProducts}: ${line.layouts.value.size}")
        }
    }


}
