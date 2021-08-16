package com.ger.common.palletScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ger.common.customUI.EditNumber
import com.ger.common.customUI.EditText
import com.ger.common.customUI.FocusableButton
import com.ger.common.customUI.Header
import com.ger.common.data.Pallet
import com.ger.common.strings.S
import com.ger.common.utils.ListWithHeaderAndCloseButton
import com.ger.common.utils.VerticalSeparator

@Composable
fun PalletScreen(
    modifier: Modifier = Modifier,
    onAddPallet: (pallet: Pallet) -> Unit,
    onDeletePallet: (palletIndex: Int) -> Unit,
    onBack: () -> Unit,
    pallets: State<List<Pallet>>,
    editPallet: Pallet,
    onCheck: (index: Int) -> Unit,
    editPalletIndex: MutableState<Int>
) {
    Column(modifier) {
        Header(
            onBack = onBack,
            headerText = S.strings.addPallet
        )

        Row {
            PalletList(
                modifier = Modifier.width(300.dp),
                pallets = pallets,
                onDelete = onDeletePallet,
                onCheck = onCheck
            )

            VerticalSeparator(width = 1.dp)

            AddNewPallet(
                modifier = Modifier.weight(1f),
                onAddPallet = onAddPallet,
                editPallet = editPallet,
                editPalletIndex = editPalletIndex
            )
        }
    }
}

@Composable
fun AddNewPallet(
    modifier: Modifier = Modifier,
    onAddPallet: (pallet: Pallet) -> Unit,
    editPallet: Pallet,
    editPalletIndex: MutableState<Int>
) {
    val name = remember (editPallet.name) { mutableStateOf(editPallet.name) }
    val width = remember (editPallet.width) { mutableStateOf(editPallet.width.toString()) }
    val height = remember (editPallet.height) { mutableStateOf(editPallet.height.toString()) }
    val length = remember (editPallet.length) { mutableStateOf(editPallet.length.toString()) }

    fun onDone() {
        val widthDouble = width.value.toIntOrNull()
        val heightDouble = height.value.toIntOrNull()
        val lengthDouble = length.value.toIntOrNull()

        if (widthDouble != null && heightDouble != null && lengthDouble != null) {
            onAddPallet(
                Pallet(
                    name = name.value,
                    width = widthDouble,
                    length = lengthDouble,
                    height = heightDouble,
                )
            )

            name.value = ""
            width.value = ""
            height.value = ""
            length.value = ""
        }
    }

    Column(modifier.fillMaxHeight()) {
        Header(headerText = S.strings.addNewProduct)

        EditText(
            value = name,
            label = S.strings.productName,
        )
        Spacer(Modifier.width(5.dp))
        EditNumber(
            value = width,
            label = S.strings.width,
        )
        Spacer(Modifier.width(5.dp))
        EditNumber(
            value = length,
            label = S.strings.length,
        )
        Spacer(Modifier.width(5.dp))
        EditNumber(
            value = height,
            label = S.strings.height,
            onDone = { _ -> onDone() }
        )
        Spacer(Modifier.width(5.dp))
        FocusableButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                onDone()
            },
            text = if(editPalletIndex.value == -1) S.strings.add else S.strings.edit
        )
    }
}

@Composable
private fun PalletList(
    modifier: Modifier = Modifier,
    pallets: State<List<Pallet>>,
    onDelete: (index: Int) -> Unit,
    onCheck: (index: Int) -> Unit,
) {
    ListWithHeaderAndCloseButton(
        modifier = modifier,
        headerText = S.strings.palletList,
        contentArray = pallets.value,
        onDelete = onDelete,
        onCheck = onCheck)
    { _, pallet ->
        Column(modifier = Modifier.padding(10.dp)) {
            Text("${S.strings.name}: ${pallet.name}")
            Spacer(Modifier.height(5.dp))
            Text("${S.strings.width}: ${pallet.width} ${S.strings.lengthMeasurementSystem}")
            Spacer(Modifier.height(5.dp))
            Text("${S.strings.length}: ${pallet.length} ${S.strings.lengthMeasurementSystem}")
            Spacer(Modifier.height(5.dp))
            Text("${S.strings.height}: ${pallet.height} ${S.strings.lengthMeasurementSystem}")
        }
    }
}

