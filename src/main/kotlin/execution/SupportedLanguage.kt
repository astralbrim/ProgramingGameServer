package com.github.axelasports.execution

enum class SupportedLanguage(val languageName: String) {
    PYTHON("python"),
    NODE("node"),
    C("c"),
    ;

    companion object {
        fun toSupportedLanguage(language: String): SupportedLanguage {
            val supportedLanguage: SupportedLanguage? =
                entries.find { it.languageName == language }

            return when (supportedLanguage) {
                PYTHON -> PYTHON
                NODE -> NODE
                C -> C
                null -> throw Exception("Language not supported")
            }
        }
    }
}
