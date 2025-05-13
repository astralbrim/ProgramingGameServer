package com.github.axelasports.problem
import kotlinx.serialization.Serializable

@Serializable
data class Problem(
    val id: Int,
    val title: String,
    val question: String,
    val examples: List<Example>?,
    val testcases: List<TestCase>,
)

fun Problem.toOmittedProblemDTO(): OmittedProblemDTO {
    return OmittedProblemDTO(id, title, question, examples)
}

@Serializable
data class Example(
    val input: String?,
    val output: String?,
)

@Serializable
data class TestCase(
    val input: String?,
    val output: String,
)

@Serializable
data class ProblemSummary(
    val id: Int,
    val title: String,
)

@Serializable
data class ProblemTryDTO(
    val language: String,
    val code: String,
)

@Serializable
data class OmittedProblemDTO(
    val id: Int,
    val title: String,
    val question: String,
    val examples: List<Example>?,
)
