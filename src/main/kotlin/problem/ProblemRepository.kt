package com.github.axelasports.problem

import kotlinx.serialization.json.Json
import java.io.File

class ProblemRepository(private val problemsFilePath: String = "src/main/resources/problems.json") {
    fun getAllProblems(): List<Problem> {
        val jsonFile = File(problemsFilePath)
        if (!jsonFile.exists()) return emptyList()
        val jsonContent = jsonFile.readText()
        return Json.decodeFromString<List<Problem>>(jsonContent)
    }

    fun findProblemById(id: Int): Problem? {
        return getAllProblems().find { it.id == id }
    }
}
