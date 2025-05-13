package com.github.axelasports.execution

data class Image(val value: String)

interface ImageBuilder {
    fun build(): Image
}

class PythonImage : ImageBuilder {
    override fun build(): Image {
        return Image("python:latest")
    }
}

class NodeImage : ImageBuilder {
    override fun build(): Image {
        return Image("node:latest")
    }
}

class CImage : ImageBuilder {
    override fun build(): Image {
        return Image("gcc:latest")
    }
}
