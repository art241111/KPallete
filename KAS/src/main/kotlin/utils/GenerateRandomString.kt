package com.github.poluka.kControlLibrary.utils

object GenerateRandomString {
    private const val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"

    fun generateString(length: Long): String {
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
