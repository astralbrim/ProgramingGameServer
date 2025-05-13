package com.github.axelasports.docker

import de.gesellix.docker.client.DockerClientImpl
import de.gesellix.docker.client.EngineResponseContent
import de.gesellix.docker.remote.api.ContainerCreateRequest
import de.gesellix.docker.remote.api.ContainerCreateResponse
import de.gesellix.docker.remote.api.core.Frame
import de.gesellix.docker.remote.api.core.StreamCallback
import java.util.concurrent.CountDownLatch
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toJavaDuration

class Docker {
    private var dockerClient: DockerClientImpl

    constructor (host: String = "unix:///var/run/docker.sock") {
        dockerClient = DockerClientImpl(host)
    }

    fun run(
        image: String,
        entrypoint: MutableList<String>,
    ): EngineResponseContent<ContainerCreateResponse> {
        return dockerClient.run(
            ContainerCreateRequest(
                image = image,
                entrypoint = entrypoint,
            ),
        )
    }

    fun getStdOutput(containerId: String): String {
        val outputBuilder = StringBuilder()
        val latch = CountDownLatch(1) // 処理の完了を待つためのラッチ

        val callback =
            object : StreamCallback<Frame> {
                override fun onNext(frame: Frame) {
                    val payloadBytes = frame.payload
                    if (payloadBytes != null) {
                        outputBuilder.append(String(payloadBytes, Charsets.UTF_8))
                    } else {
                        outputBuilder.append(frame.payload.toString())
                    }
                }

                override fun onFailed(e: Exception) {
                    System.err.println("Failed to get logs: ${e.message}")
                    e.printStackTrace()
                    latch.countDown()
                }

                override fun onFinished() {
                    latch.countDown()
                }
            }

        val logOptions = mapOf<String, Any>()
        dockerClient.logs(
            containerId,
            logOptions,
            callback,
            5000.milliseconds.toJavaDuration(),
        )

        return outputBuilder.toString()
    }
}
