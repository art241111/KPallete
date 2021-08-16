package com.ger.common.strings

class RusStrings : Strings {
    override val name: String
        get() = "Имя"
    override val position: String
        get() = "Позиция"
    override val add: String
        get() = "Добавить"
    override val edit: String
        get() = "Редактировать"
    override val lengthMeasurementSystem: String
        get() = "мм"
    override val weightMeasurementSystem: String
        get() = "кг"
    override val width: String
        get() = "Ширина"
    override val length: String
        get() = "Длинна"
    override val height: String
        get() = "Высота"
    override val weight: String
        get() = "Вес"

    // Main list screen
    override val mainListScreen: String
        get() = "Основные настройки"

    // Add area screen
    override val conveyorsList: String
        get() = "Список конвейеров"
    override val addConveyor: String
        get() = "Добавить новый конвейер"
    override val areasList: String
        get() = "Список областей"
    override val addArea: String
        get() = "Добавить новую область"

    // Add conveyor screen
    override val conveyorName: String
        get() = "Введите имя конвеера"

    // Add area screen
    override val areaName: String
        get() = "Введите имя области"

    // Create object
    override val addProducts: String
        get() = "Добавление продуктов"
    override val addNewProduct: String
        get() = "Добавление нового продукта"
    override val productList: String
        get() = "Список продуктов"
    override val productName: String
        get() = "Имя продукта"

    // Create pallet
    override val addPallet: String
        get() = "Добавление паллет"
    override val addNewPallet: String
        get() = "Добавить новую паллету"
    override val palletList: String
        get() = "Список паллет"
    override val palletName: String
        get() = "Имя паллет"

    // Create script
    override val packingPallets: String
        get() = "Укомплектовка паллет"
    override val lineList: String
        get() = "Список слоев"
    override val addNewLine: String
        get() = "Добавить новый слой"
    override val lineName: String
        get() = "Имя слоя"
    override val overhang: String
        get() = "Свес"
    override val distancesBetweenProducts: String
        get() = "Расстояние между продутом"
    override val numberOfProducts: String
        get() = "Количество продуктов"
    override val rotate: String
        get() = "Повернуть"
    override val delete: String
        get() = "Удалить"
    override val close: String
        get() = "Закрыть"

    // Connect screen
    override val port: String
        get() = "Порт"
    override val ip: String
        get() = "Ip"
    override val connect: String
        get() = "Подключиться"

    // Add point
    override val update: String = "Обновить"
    override val addPoint: String = "Добавить точку"
}
