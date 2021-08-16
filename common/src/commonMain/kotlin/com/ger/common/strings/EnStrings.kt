package com.ger.common.strings

class EnStrings : Strings {
    override val name: String
        get() = "Name"
    override val position: String
        get() = "Position"
    override val add: String
        get() = "Add"
    override val edit: String
        get() = "Edit"
    override val lengthMeasurementSystem: String
        get() = "mm"
    override val weightMeasurementSystem: String
        get() = "kg"
    override val width: String
        get() = "Width"
    override val length: String
        get() = "Length"
    override val height: String
        get() = "Height"
    override val weight: String
        get() = "Weight"

    // Main list screen
    override val mainListScreen: String
        get() = "Main settings"

    // Add area screen
    override val conveyorsList: String
        get() = "List of conveyors"
    override val addConveyor: String
        get() = "Add new conveyor"
    override val areasList: String
        get() = "List of areas"
    override val addArea: String
        get() = "Add new area"

    // Add conveyor screen
    override val conveyorName: String
        get() = "Enter conveyor name"

    // Add area screen
    override val areaName: String
        get() = "Enter area name"

    // Create product
    override val addProducts: String
        get() = "Add products"
    override val addNewProduct: String
        get() = "Add new product"
    override val productList: String
        get() = "Product list"
    override val productName: String
        get() = "Product name"

    // Create pallet
    override val addPallet: String
        get() = "Add pallet"
    override val addNewPallet: String
        get() = "Add new pallet"
    override val palletList: String
        get() = "Pallet list"
    override val palletName: String
        get() = "Pallet name"

    // Create script
    override val packingPallets: String
        get() = "Packing of pallets"
    override val lineList: String
        get() = "List of layers"
    override val addNewLine: String
        get() = "Add new layer"
    override val lineName: String
        get() = "Layers name"
    override val overhang: String
        get() = "Overhang"
    override val distancesBetweenProducts: String
        get() = "Distances between products"
    override val numberOfProducts: String
        get() = "Number of products"
    override val rotate: String
        get() = "Rotate"
    override val delete: String
        get() ="Delete"
    override val close: String
        get() = "Close"

    // Connect screen
    override val port: String
        get() = "Port"
    override val ip: String
        get() = "Ip"
    override val connect: String
        get() = "Connect"

    // Add point
    override val update: String = "Update"
    override val addPoint: String = "Add point"
}
