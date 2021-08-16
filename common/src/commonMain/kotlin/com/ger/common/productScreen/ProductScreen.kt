package com.ger.common.productScreen

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
import com.ger.common.data.Product
import com.ger.common.strings.S
import com.ger.common.utils.ListWithHeaderAndCloseButton
import com.ger.common.utils.VerticalSeparator

@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    products: State<List<Product>>,
    onAddProduct: (Product) -> Unit,
    onDeleteProduct: (index: Int) -> Unit,
    editProduct: Product,
    onCheck: (index: Int) -> Unit,
    editPalletIndex: MutableState<Int>,
) {
    Column(modifier) {
        Header(
            onBack = onBack,
            headerText = S.strings.addProducts
        )

        Row {
            ProductList(
                modifier = Modifier.width(300.dp),
                products = products,
                onDelete = onDeleteProduct,
                onCheck = onCheck
            )

            VerticalSeparator(width = 1.dp)

            AddNewProduct(
                modifier = Modifier.weight(1f),
                onAddProduct = onAddProduct,
                editProduct = editProduct,
                editPalletIndex = editPalletIndex
            )
        }
    }
}

@Composable
private fun AddNewProduct(
    modifier: Modifier = Modifier,
    onAddProduct: (Product) -> Unit,
    editProduct: Product,
    editPalletIndex: MutableState<Int>,
) {
    val name = remember (editProduct.name) { mutableStateOf(editProduct.name.toString()) }
    val width = remember (editProduct.width) { mutableStateOf(editProduct.width.toString()) }
    val length = remember (editProduct.length) { mutableStateOf(editProduct.length.toString()) }
    val height = remember (editProduct.height) { mutableStateOf(editProduct.height.toString()) }
    val weight = remember (editProduct.weight) { mutableStateOf(editProduct.weight.toString()) }

    fun onDone() {
        val widthDouble = width.value.toIntOrNull()
        val lengthDouble = length.value.toIntOrNull()
        val heightDouble = height.value.toIntOrNull()
        val weightDouble = weight.value.toDoubleOrNull()

        if (widthDouble != null && lengthDouble != null && weightDouble != null && heightDouble != null) {
            onAddProduct(
                Product(
                    name = name.value,
                    width = widthDouble,
                    length = lengthDouble,
                    weight = weightDouble,
                    height = heightDouble
                )
            )

            name.value = ""
            width.value = ""
            length.value = ""
            weight.value = ""
            height.value = ""
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
        )
        Spacer(Modifier.width(5.dp))
        EditNumber(
            value = weight,
            label = S.strings.weight,
            onDone = { _ -> onDone() }
        )
        Spacer(Modifier.width(5.dp))
        FocusableButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                onDone()
            },
            text = if (editPalletIndex.value == -1) S.strings.add else S.strings.edit
        )
    }
}

@Composable
private fun ProductList(
    modifier: Modifier = Modifier,
    products: State<List<Product>>,
    onDelete: (index: Int) -> Unit,
    onCheck: (index: Int) -> Unit,
) {
    ListWithHeaderAndCloseButton(
        modifier = modifier,
        headerText = S.strings.productList,
        contentArray = products.value,
        onDelete = onDelete,
        onCheck = onCheck,
    )
    { _, product ->
        Column(modifier = Modifier.padding(10.dp)) {
            Text("${S.strings.name}: ${product.name}")
            Spacer(Modifier.height(5.dp))
            Text("${S.strings.width}: ${product.width} ${S.strings.lengthMeasurementSystem}")
            Spacer(Modifier.height(5.dp))
            Text("${S.strings.length}: ${product.length} ${S.strings.lengthMeasurementSystem}")
            Spacer(Modifier.height(5.dp))
            Text("${S.strings.height}: ${product.height} ${S.strings.lengthMeasurementSystem}")
            Spacer(Modifier.height(5.dp))
            Text("${S.strings.weight}: ${product.weight} ${S.strings.weightMeasurementSystem}")
        }
    }
}
