package com.ger.common.strings

interface Strings {
    val name: String
    val position: String
    val add: String
    val edit: String
    val lengthMeasurementSystem: String
    val weightMeasurementSystem: String
    val width: String
    val length: String
    val height: String
    val weight: String

    // Main list screen
    val mainListScreen: String

    // Add area screen
    val conveyorsList: String
    val addConveyor: String
    val areasList: String
    val addArea: String

    // Add conveyor screen
    val conveyorName: String

    // Add area screen
    val areaName: String

    // Create object screen
    val addProducts: String
    val addNewProduct: String
    val productList: String
    val productName: String

    // Create pallet screen
    val addPallet: String
    val addNewPallet: String
    val palletList: String
    val palletName: String

    // Create layout screen
    val packingPallets: String
    val lineList: String
    val addNewLine: String
    val lineName: String
    val overhang: String
    val distancesBetweenProducts: String
    val numberOfProducts: String
    val rotate: String
    val delete: String
    val close: String

    // Connect screen
    val port: String
    val ip: String
    val connect: String

    // Add point
    val update: String
    val addPoint: String
}
