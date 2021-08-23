package com.ger.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.ger.common.addPoint.AddPoint
import com.ger.common.areaScreen.AreaScreen
import com.ger.common.connectScreen.ConnectScreen
import com.ger.common.conveyorScreen.ConveyorScreen
import com.ger.common.data.Area
import com.ger.common.data.Conveyor
import com.ger.common.data.Factory
import com.ger.common.data.Pallet
import com.ger.common.data.Product
import com.ger.common.data.Robot
import com.ger.common.layoutScreen.LayoutScreen
import com.ger.common.mainListScreen.MainListScreen
import com.ger.common.nav.Navigation
import com.ger.common.nav.Screens
import com.ger.common.palletScreen.PalletScreen
import com.ger.common.productScreen.ProductScreen
import com.ger.common.settingEnvironment.SettingEnvironmentScreen
import com.ger.common.utils.ProgramCreator
import com.ger.common.utils.add
import com.ger.common.utils.change
import com.ger.common.utils.delete
import com.github.poluka.kControlLibrary.program.Program
import com.github.poluka.kControlLibrary.programControlCommands.Hold
import com.github.poluka.kControlLibrary.runProgram.loadProgram
import com.github.poluka.kControlLibrary.runProgram.runCommand
import com.github.poluka.kControlLibrary.runProgram.runProgram

@Composable
fun App(
    factory: Factory,
    robot: Robot,
    navigation: Navigation,
) {
    val changePointIndex = remember { mutableStateOf(-1) }

    var isLoad = remember(factory.conveyors.value, factory.areas.value, factory.completedPallets.value) { false }
    val programCreator = remember { ProgramCreator() }
    var program = remember { Program("Hello") }

    val editArea = remember { mutableStateOf(Area()) }
    val editAreaIndex = remember { mutableStateOf(-1) }

    val editConveyor = remember { mutableStateOf(Conveyor()) }
    val editConveyorIndex = remember { mutableStateOf(-1) }

    val editProduct = remember { mutableStateOf(Product()) }
    val editProductIndex = remember { mutableStateOf(-1) }

    val editPallet = remember { mutableStateOf(Pallet()) }
    val editPalletIndex = remember { mutableStateOf(-1) }

    MaterialTheme {
        when (navigation.state.value) {
            Screens.MAIN_LIST -> {
                MainListScreen(
                    navigation = navigation,
                    onConnect = {
                        if (!robot.isConnect.value) {
                            navigation.moveToScreen(Screens.CONNECT_SCREEN)
                        } else {
                            robot.disconnect()
                        }
                    },
                    isConnect = robot.isConnect,
                )
            }
            Screens.ADD_AREA -> {
                AreaScreen(
                    onBack = {
                        navigation.back()
                    },
                    areas = factory.areas,
                    onDelete = { index ->
                        factory.areas.delete(index)
                    },
                    point = robot.position,
                    onAddPoint = {
                        navigation.moveToScreen(Screens.ADD_POINT)
                        changePointIndex.value = it
                    },
                    changePointIndex = changePointIndex,
                    editArea = editArea.value,
                    editAreaIndex = editAreaIndex,
                    onCheck = {
                        editArea.value = factory.areas.value[it]
                        editAreaIndex.value = it
                    },
                    onAddArea = {
                        if (editAreaIndex.value == -1) {
                            factory.areas.add(it)
                        } else {
                            factory.areas.change(
                                editAreaIndex.value,
                                it
                            )
                            editAreaIndex.value = -1
                        }

                        editArea.value = Area()
                    },
                )
            }

            Screens.ADD_CONVEYOR -> {
                ConveyorScreen(
                    editConveyor = editConveyor.value,
                    editConveyorIndex = editConveyorIndex,
                    onAddConveyor = {
                        if (editConveyorIndex.value == -1) {
                            factory.conveyors.add(it)
                        } else {
                            factory.conveyors.change(
                                editConveyorIndex.value,
                                it
                            )
                            editConveyorIndex.value = -1
                        }

                        editConveyor.value = Conveyor()
                    },
                    conveyors = factory.conveyors,
                    onBack = {
                        if (editConveyorIndex.value == -1) {
                            navigation.back()
                        } else {
                            editConveyorIndex.value = -1
                        }
                    },
                    onDelete = { index ->
                        factory.conveyors.delete(index)
                    },
                    point = robot.position,
                    addPoint = {
                        navigation.moveToScreen(Screens.ADD_POINT)
                    },
                    onCheck = { index ->
                        editConveyor.value = factory.conveyors.value[index]
                        editConveyorIndex.value = index
                    }
                )
            }

            Screens.ADD_PRODUCT -> {
                ProductScreen(
                    editProduct = editProduct.value,
                    editPalletIndex = editProductIndex,
                    onBack = {
                        navigation.back()
                    },
                    products = factory.products,
                    onAddProduct = { product ->
                        if (editProductIndex.value == -1) {
                            factory.products.add(product)
                        } else {
                            factory.products.change(
                                editProductIndex.value,
                                product
                            )
                            editConveyorIndex.value = -1
                        }
                        editConveyor.value = Conveyor()
                    },
                    onDeleteProduct = { index ->
                        factory.products.delete(index)
                    },
                    onCheck = { index ->
                        editProduct.value = factory.products.value[index]
                        editProductIndex.value = index
                    }
                )
            }

            Screens.ADD_PALLET -> {
                PalletScreen(
                    editPallet = editPallet.value,
                    editPalletIndex = editPalletIndex,
                    pallets = factory.pallets,
                    onBack = {
                        if (editPalletIndex.value == -1) {
                            navigation.back()
                        } else {
                            editPalletIndex.value = -1
                            editPallet.value = Pallet()
                        }
                    },
                    onAddPallet = { pallet ->
                        if (editPalletIndex.value == -1) {
                            factory.pallets.add(pallet)
                        } else {
                            factory.pallets.change(
                                editPalletIndex.value,
                                pallet
                            )

                            editPalletIndex.value = -1
                        }

                        editPallet.value = Pallet()
                    },
                    onDeletePallet = { index ->
                        factory.pallets.delete(index)
                    },
                    onCheck = { index ->
                        editPallet.value = factory.pallets.value[index]
                        editPalletIndex.value = index
                    }
                )
            }

            Screens.CREATE_LAYOUT -> {
                LayoutScreen(
                    products = factory.products,
                    pallets = factory.pallets,
                    completedPallets = factory.completedPallets,
                    onBack = {
                        navigation.back()
                    },
                    onDeleteLevel = { pallet, level ->
                        factory.deleteLevel(pallet, level)
                    }
                )
            }

            Screens.CONNECT_SCREEN -> {
                ConnectScreen(
                    isConnect = robot.isConnect,
                    onClick = { ip, port ->
                        robot.connect(ip, port)
                    },
                    onBack = {
                        navigation.back()
                    }
                )
            }

            Screens.ADD_POINT -> {
                AddPoint(
                    onBack = {
                        navigation.back()
                    },
                    point = robot.position,
                    updatePosition = {
                        robot.updatePosition()
                    }
                )
            }

            Screens.SETTING_ENVIRONMENT -> {
                SettingEnvironmentScreen(
                    conveyors = factory.conveyors.value,
                    areas = factory.areas.value,
                    completedPallets = factory.completedPallets.value,
                    onAddSettings = { areaIndex, conveyorIndex: Int, isConveyorSignal: Int,
                                      completedPalletIndex: Int, isPalletSignal: Int,
                                      isProgramWork: Int, palletPositionIndex: Int, zGap: Int ->
                        program = programCreator.generateMoveProgram(
                            conveyor = factory.conveyors.value[conveyorIndex],
                            isConveyorSignal = isConveyorSignal,
                            area = factory.areas.value[areaIndex],
                            completedPallet = factory.completedPallets.value[completedPalletIndex],
                            isPalletSignal = isPalletSignal,
                            isProgramWork = isProgramWork,
                            palletPositionIndex = palletPositionIndex,
                            zGap = zGap
                        )
                        isLoad = false
                    },
                    onRun = {
                        if (!isLoad) {
                            isLoad = true
                            robot.loadProgram(program)
                        }
                        robot.runProgram(program)
                    },
                    onStop = {
                        robot.runCommand(Hold())
                    },
                    onBack = {
                        navigation.back()
                    },
                )
            }
        }
    }
}
