package com.github.axelasports.problem

import com.github.axelasports.execution.SupportedLanguage
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import kotlinx.serialization.json.Json

class ProblemController {
    val problemService = ProblemService()

    suspend fun index(call: ApplicationCall) {
        val problemsSummaryJson = problemService.getAllProblemsSummaryJson()
        val json = Json.Default.encodeToString<List<ProblemSummary>>(problemsSummaryJson)

        call.respondText(json, ContentType.Application.Json)
    }

    suspend fun index(
        call: ApplicationCall,
        id: String,
    ) {
        val problemId = id.toIntOrNull()
        if (problemId == null) {
            call.respond(HttpStatusCode.Companion.BadRequest, "無効なID形式です。IDは数値である必要があります。")
            return
        }

        val problem = problemService.findProblemById(problemId)

        if (problem != null) {
            call.respond(Json.Default.encodeToString<OmittedProblemDTO>(problem.toOmittedProblemDTO()))
        } else {
            call.respond(HttpStatusCode.Companion.NotFound, "ID $problemId の問題は見つかりませんでした。または問題データを読み込めませんでした。")
        }
    }

    suspend fun tryProblem(
        call: ApplicationCall,
        id: String?,
    ) {
        val body = call.receive<ProblemTryDTO>()
        if (id == null) {
            call.respond(HttpStatusCode.Companion.BadRequest, "IDが指定されていません。")
            return
        }

        val problemId = id.toIntOrNull()
        if (problemId == null) {
            call.respond(HttpStatusCode.Companion.BadRequest, "無効なID形式です。IDは数値である必要があります。")
            return
        }
        val problem = problemService.findProblemById(problemId)

        if (problem == null) {
            call.respond(HttpStatusCode.Companion.NotFound, "ID $problemId の問題は見つかりませんでした。または問題データを読み込めませんでした。")
            return
        }

        var language: SupportedLanguage

        try {
            language = SupportedLanguage.Companion.toSupportedLanguage(body.language)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Companion.BadRequest, "指定された言語 ${body.language} はサポートされていません")
            return
        }
        val result = problemService.tryProblem(problem, language, body.code)

        if (result) {
            call.respond(HttpStatusCode.Companion.OK, "正解！\n")
        } else {
            call.respond(HttpStatusCode.Companion.OK, "不正解! \n")
        }
    }
}
