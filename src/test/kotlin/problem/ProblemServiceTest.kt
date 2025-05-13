package problem

import com.github.axelasports.execution.SupportedLanguage
import com.github.axelasports.problem.Example
import com.github.axelasports.problem.Problem
import com.github.axelasports.problem.ProblemService
import com.github.axelasports.problem.TestCase
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ProblemServiceTest {
    val service = ProblemService()

    @Test
    fun testTryProblem() {
        val problem =
            Problem(
                1,
                "Hello World!",
                "改行なしの Hello World! という文字列を出力してください",
                listOf(
                    Example(null, null),
                ),
                listOf(TestCase("test", "Hello World!")),
            )

        val result = service.tryProblem(problem, SupportedLanguage.PYTHON, "print(\"Hello World!\", end=\"\")")
        assertTrue(result)
    }

    @Test
    fun testTryProblemManyTestcases() {
        val problem =
            Problem(
                1,
                "Hello World!",
                "Hello World! という文字列を出力してください",
                listOf(
                    Example(null, null),
                ),
                listOf(TestCase("test", "Hello World!"), TestCase("test2", "Hello World!")),
            )

        val result = service.tryProblem(problem, SupportedLanguage.PYTHON, "print(\"Hello World!\")")
        assertTrue(result)
    }

    @Test
    fun testCheckTestcase() {
        val testcase = TestCase("test", "Hello World!")
        val result = service.checkTestcase(testcase, "Hello World!")
    }

    @Test
    fun getAllProblemsSummaryJsonTest() {
        val problems = service.getAllProblemsSummaryJson()
        assertTrue(problems[0].title == "Hello World!")
    }

    @Test
    fun findProblemByIdTest() {
        val problem = service.findProblemById(1)
        assertNotNull(problem)
        assertTrue(problem.title == "Hello World!")
    }
}
