package com.github.axelasports.problem

import com.github.axelasports.execution.CodeExecution
import com.github.axelasports.execution.SupportedLanguage
import com.github.axelasports.utils.removeLastNewline

class ProblemService {
    private val execution = CodeExecution()
    private val repository = ProblemRepository()

    fun tryProblem(
        problem: Problem,
        language: SupportedLanguage,
        code: String,
    ): Boolean {
        problem.testcases.forEach { testcase ->
            val result = execution.executeCode(language, code, testcase.input)
            if (!checkTestcase(testcase, result)) return false
        }
        return true
    }

    fun checkTestcase(
        testcase: TestCase,
        result: String,
    ): Boolean {
        return testcase.output == result.removeLastNewline()
    }

    fun getAllProblemsSummaryJson(): List<ProblemSummary> {
        val problems = repository.getAllProblems()
        return problems.map { ProblemSummary(it.id, it.title) }
    }

    fun findProblemById(id: Int): Problem? {
        return repository.findProblemById(id)
    }
}
