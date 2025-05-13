package com.github.axelasports.execution

data class Entrypoint(
    val value: MutableList<String>,
)

interface EntrypointBuilder {
    fun withInput(
        code: String,
        input: String,
    ): Entrypoint

    fun withoutInput(code: String): Entrypoint
}

class PythonEntryPoint : EntrypointBuilder {
    override fun withInput(
        code: String,
        input: String,
    ): Entrypoint {
        return Entrypoint(mutableListOf("/bin/sh", "-c", "echo '$input' | python -c '$code'"))
    }

    override fun withoutInput(code: String): Entrypoint {
        return Entrypoint(mutableListOf("python", "-c", code))
    }
}

class NodeEntryPoint : EntrypointBuilder {
    override fun withInput(
        code: String,
        input: String,
    ): Entrypoint {
        return Entrypoint(mutableListOf("/bin/sh", "-c", "echo '$input' | node -e '$code'"))
    }

    override fun withoutInput(code: String): Entrypoint {
        return Entrypoint(mutableListOf("node", "-e", code))
    }
}

class CEntryPoint : EntrypointBuilder {
    override fun withInput(
        code: String,
        input: String,
    ): Entrypoint {
        return Entrypoint(mutableListOf("/bin/sh", "-c", "echo '$input' | echo '$code' | gcc -xc - && ./a.out"))
    }

    override fun withoutInput(code: String): Entrypoint {
        return Entrypoint(mutableListOf("/bin/sh", "-c", "echo '$code' | gcc -xc - && ./a.out"))
    }
}
