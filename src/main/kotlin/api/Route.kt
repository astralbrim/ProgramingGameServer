package com.github.axelasports.api

import com.github.axelasports.problem.ProblemController
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.route() {
    val problemController = ProblemController()
    routing {
        get("/") { call.respondText("Hello World!") }
        get("/problem") { problemController.index(call) }
        get("/problem/{id}") { problemController.index(call, call.parameters["id"]!!) }
        post("/problem/{id}/try") { problemController.tryProblem(call, call.parameters["id"]) }
    }
}
