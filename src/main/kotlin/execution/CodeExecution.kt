package com.github.axelasports.execution
import com.github.axelasports.docker.Docker

class CodeExecution {
    private val docker = Docker(System.getenv("DOCKER_HOST") ?: "unix:///var/run/docker.sock")

    private val supportedLanguages: Map<SupportedLanguage, LanguageConfig> =
        mapOf(
            SupportedLanguage.PYTHON to LanguageConfig(PythonImage(), PythonEntryPoint()),
            SupportedLanguage.NODE to LanguageConfig(NodeImage(), NodeEntryPoint()),
            SupportedLanguage.C to LanguageConfig(CImage(), CEntryPoint()),
        )

    fun executeCode(
        language: SupportedLanguage,
        code: String,
        input: String?,
    ): String {
        val languageConfig = supportedLanguages[language]!!

        val entrypoint =
            input
                ?.let { languageConfig.entrypoint.withInput(code, it) }
                ?: languageConfig.entrypoint.withoutInput(code)

        val engineResponseContent =
            docker.run(languageConfig.image.build().value, entrypoint.value)

        val containerId = engineResponseContent.content.id

        return docker.getStdOutput(containerId)
    }
}
