package com.github.axelasports.utils

fun String.removeLastNewline(): String {
    return this.removeSuffix("\r\n").removeSuffix("\n")
}
