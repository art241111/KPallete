package com.ger.common.data

data class Point(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
    var o: Double = 0.0,
    var a: Double = 0.0,
    var t: Double = 0.0,
) {
    override fun toString(): String {
        return "$x,$y,$z,$o,$a,$t"
    }

    /**
     * Получаем доступ через координаты.
     * @param coordinate - координата, по которой нужно олучить значения.
     */
    operator fun get(index: Int): Double {
        return when (index) {
            0 -> x
            1 -> y
            2 -> z
            3 -> o
            4 -> a
            5 -> t
            else -> x
        }
    }

    /**
     * Изменяем значения через координаты.
     * @param coordinate - координата, по которой нужно изменить значения,
     * @param value - значение, которое нужно установить.
     */
    operator fun set(index: Int, value: Double) {
        when (index) {
            0 -> x = value
            1 -> y = value
            2 -> z = value
            3 -> o = value
            4 -> a = value
            5 -> t = value
            else -> x = value
        }
    }

    companion object {
        fun fromString(string: String): Point {
            val split = string.split(",")
            return Point(
                x = split[0].trim().toDouble(),
                y = split[1].trim().toDouble(),
                z = split[2].trim().toDouble(),
                o = split[3].trim().toDouble(),
                a = split[4].trim().toDouble(),
                t = split[5].trim().toDouble(),

            )
        }
    }
}
