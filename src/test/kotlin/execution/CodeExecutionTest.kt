package com.github.axelasports.execution

import org.junit.jupiter.api.Test // もしJUnit 5を使用している場合
import kotlin.test.assertEquals // もしkotlin.testを使用している場合

class CodeExecutionTest {
    private val codeExecution = CodeExecution()

    @Test
    fun executePythonCodeTest() {
        val language = SupportedLanguage.PYTHON
        val code = "print('Hello, World!')"
        val expectedOutput = "Hello, World!\n"
        val actualOutput = codeExecution.executeCode(language, code, null)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun executeNodeCodeTest() {
        val language = SupportedLanguage.NODE
        val code = "console.log('Hello, World!')"
        val expectedOutput = "Hello, World!\n"
        val actualOutput = codeExecution.executeCode(language, code, null)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun executeCCodeTest() {
        val language = SupportedLanguage.C
        val code = "#include <stdio.h>\nint main() { printf(\"Hello World!\"); return 0; }"
        val expectedOutput = "Hello World!"
        val actualOutput = codeExecution.executeCode(language, code, null)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun executeLongPythonCodeTest() {
        val language = SupportedLanguage.PYTHON
        val code =
            """
            def fibonacci(n):
                if n <= 1:
                    return n
                return fibonacci(n-1) + fibonacci(n-2)
                
            for i in range(10):
                print(fibonacci(i), end=' ')
            """.trimIndent()
        val expectedOutput = "0 1 1 2 3 5 8 13 21 34 "
        val actualOutput = codeExecution.executeCode(language, code, null)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun executeLongCCodeTest() {
        val language = SupportedLanguage.C
        val code = """
            #include <stdio.h>
            int fibonacci(int n) {
                if (n <= 1) return n;
                return fibonacci(n-1) + fibonacci(n-2);
            }
            int main() {
                int i;
                for(i = 0; i < 10; i++) {
                    printf("%d ", fibonacci(i));
                }
                return 0;
            }
        """
        val expectedOutput = "0 1 1 2 3 5 8 13 21 34 "
        val actualOutput = codeExecution.executeCode(language, code, null)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun executeLongNodeCodeTest() {
        val language = SupportedLanguage.NODE
        val code = """
            class BinaryTreeNode {
                constructor(value) {
                    this.value = value;
                    this.left = null;
                    this.right = null;
                }
            }
    
            function createBinaryTree() {
                const root = new BinaryTreeNode(1);
                root.left = new BinaryTreeNode(2);
                root.right = new BinaryTreeNode(3);
                root.left.left = new BinaryTreeNode(4);
                root.left.right = new BinaryTreeNode(5);
                return root;
            }
    
            function inorderTraversal(node, result = []) {
                if (node !== null) {
                    inorderTraversal(node.left, result);
                    result.push(node.value);
                    inorderTraversal(node.right, result);
                }
                return result;
            }
    
            const tree = createBinaryTree();
            console.log(inorderTraversal(tree).join(' '));
        """
        val expectedOutput = "4 2 5 1 3\n"
        val actualOutput = codeExecution.executeCode(language, code, null)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun executeNodeCodeWithInputTest() {
        val language = SupportedLanguage.NODE
        val code =
            """
            const inputs = require("fs").readFileSync("/dev/stdin", "utf-8").trim().split("\n");
            console.log(inputs[0]);
            """.trimIndent()
        val expectedOutput = "Hello, World!\n"
        val actualOutput = codeExecution.executeCode(language, code, "Hello, World!")
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun executePythonCodeWithInputTest() {
        val language = SupportedLanguage.PYTHON
        val code = "print(input())"
        val expectedOutput = "test\n"
        val actualOutput = codeExecution.executeCode(language, code, "test")
        assertEquals(expectedOutput, actualOutput)
    }
}
