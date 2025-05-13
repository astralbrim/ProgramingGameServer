package com.github.axelasports.api

import io.ktor.server.application.Application

fun Application.module() {
    route()
    configureSerialization()
}
